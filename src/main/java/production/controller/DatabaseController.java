package production.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import production.model.Product;
import production.model.Components;
import production.model.Details;
import production.repository.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/sql")
public class DatabaseController {

    private final JdbcTemplate jdbcTemplate;
    private final ComponentsRepository componentsRepo;
    private final DetailsRepository detailsRepo;

    public DatabaseController(JdbcTemplate jdbcTemplate, ProductRepository p, ComponentsRepository c, DetailsRepository d) {
        this.jdbcTemplate = jdbcTemplate;
        this.componentsRepo = c;
        this.detailsRepo = d;
    }

    @GetMapping
    public String showForm() { return "query-form"; }

    @PostMapping
    public String executeSql(@RequestParam("query") String sql, Model model) {
        String cleanSql = sql.trim();
        model.addAttribute("query", cleanSql);

        try {
            String upper = cleanSql.toUpperCase();

            if (upper.startsWith("SELECT")) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(cleanSql);
                if (result.isEmpty()) model.addAttribute("message", "Пусто.");
                else {
                    model.addAttribute("resultList", result);
                    model.addAttribute("headers", result.get(0).keySet());
                }
            } else if (upper.startsWith("INSERT INTO")) {
                if (upper.contains("PRODUCTS")) insertProduct(cleanSql, model);
                else if (upper.contains("COMPONENTS")) insertComponent(cleanSql, model);
                else if (upper.contains("DETAILS")) insertDetail(cleanSql, model);
                else throw new IllegalArgumentException("Невідома таблиця.");
            } else if (upper.startsWith("UPDATE") || upper.startsWith("DELETE")) {
                model.addAttribute("message", "Виконано. Змінено рядків: " + jdbcTemplate.update(cleanSql));
            } else {
                model.addAttribute("error", "Невідома команда.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Помилка: " + e.getMessage());
        }
        return "query-result";
    }

    // INSERT
    private void insertProduct(String sql, Model model) {
        String[] v = parseValues(sql);
        UUID id = (v.length == 3 || v[0].toUpperCase().contains("UUID")) ? UUID.randomUUID() : UUID.fromString(clean(v[0]));
        int offset = (v.length == 3) ? 0 : 1; // Зсув індексу, якщо ID був у запиті

        Product obj = new Product(id, clean(v[offset]), clean(v[offset+1]), clean(v[offset+2]));
        
        jdbcTemplate.update("INSERT INTO products (id, name, serial_number, category) VALUES (?, ?, ?, ?)",
                obj.id(), obj.name(), obj.serialNumber(), obj.category());
        model.addAttribute("message", "Додано Product: " + obj.name());
    }

    private void insertComponent(String sql, Model model) {
        String[] v = parseValues(sql);
        boolean autoId = v.length == 3 || (v.length == 4 && (clean(v[0]).isEmpty() || clean(v[0]).equalsIgnoreCase("null")));
        int offset = (v.length == 3) ? 0 : 1;

        String name = clean(v[offset]);
        String desc = clean(v[offset+1]);
        UUID pId = UUID.fromString(clean(v[offset+2]));

        if (autoId) {
            componentsRepo.save(new Components(null, name, desc, pId));
        } else {
            Integer id = Integer.parseInt(clean(v[0]));
            jdbcTemplate.update("INSERT INTO components (id, name, description, product_id) VALUES (?, ?, ?, ?)", id, name, desc, pId);
        }
        model.addAttribute("message", "Додано Component: " + name);
    }

    private void insertDetail(String sql, Model model) {
        String[] v = parseValues(sql);
        boolean autoId = v.length == 3 || (v.length == 4 && (clean(v[0]).isEmpty() || clean(v[0]).equalsIgnoreCase("null")));
        int offset = (v.length == 3) ? 0 : 1;

        String name = clean(v[offset]);
        String mat = clean(v[offset+1]);
        Integer cId = Integer.parseInt(clean(v[offset+2]));

        if (autoId) {
            detailsRepo.save(new Details(null, name, mat, cId));
        } else {
            Integer id = Integer.parseInt(clean(v[0]));
            jdbcTemplate.update("INSERT INTO details (id, name, material, component_id) VALUES (?, ?, ?, ?)", id, name, mat, cId);
        }
        model.addAttribute("message", "Додано Detail: " + name);
    }

    private String[] parseValues(String sql) {
        Matcher m = Pattern.compile("VALUES\\s*\\((.+)\\)", Pattern.CASE_INSENSITIVE).matcher(sql);
        if (m.find()) return m.group(1).split(",");
        throw new RuntimeException("Помилка формату VALUES (...)");
    }

    private String clean(String s) { return s.trim().replace("'", ""); }
}
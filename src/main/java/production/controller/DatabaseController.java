package production.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import production.model.Product;
import production.model.Components;
import production.model.Details;
import production.services.ComponentsService;
import production.services.DetailsService;
import production.services.ProductService;

import java.util.*;

@Controller
@RequestMapping("/dashboard")
public class DatabaseController {

    private final ProductService productService;
    private final ComponentsService componentsService;
    private final DetailsService detailsService;
    private final ObjectMapper objectMapper;

    public DatabaseController(ProductService ps, ComponentsService cs, DetailsService ds, ObjectMapper om) {
        this.productService = ps;
        this.componentsService = cs;
        this.detailsService = ds;
        this.objectMapper = om;
    }

    @GetMapping
    public String showDashboard() {
        return "dashboard";
    }

    @PostMapping("/execute")
    public String executeCommand(@RequestParam("query") String jsonInput, Model model) {
        model.addAttribute("lastCommand", jsonInput);

        try {
            RequestDto request = objectMapper.readValue(jsonInput, RequestDto.class);
            
            String action = request.action.toLowerCase();
            String entity = request.entity.toLowerCase();

            switch (action) {
                case "select" -> handleSelect(entity, model);
                case "insert" -> handleInsert(entity, request.data, model);
                case "update" -> handleUpdate(entity, request.data, model);
                case "delete" -> handleDelete(entity, request.data, model);
                default -> throw new IllegalArgumentException("Невідома дія: " + action);
            }

        } catch (Exception e) {
            model.addAttribute("error", "Помилка: " + e.getMessage());
            e.printStackTrace();
        }
        return "dashboard";
    }

    // SELECT
    private void handleSelect(String entity, Model model) {
        switch (entity) {
            case "product", "products" -> model.addAttribute("productsList", productService.findAll());
            case "component", "components" -> model.addAttribute("componentsList", componentsService.findAll());
            case "detail", "details" -> model.addAttribute("detailsList", detailsService.findAll());
            default -> throw new IllegalArgumentException("Невідома сутність для SELECT: " + entity);
        }
    }

    // INSERT
    private void handleInsert(String entity, Map<String, Object> data, Model model) {
        switch (entity) {
            case "product", "products" -> {
                Product p = objectMapper.convertValue(data, Product.class);
                if (p.getId() == null) p = new Product(UUID.randomUUID(), p.getName(), p.getSerialNumber(), p.getCategory());
                p.setIsNew(true);
                productService.save(p);
                model.addAttribute("message", "Додано Product");
                handleSelect("products", model);
            }
            case "component", "components" -> {
                Components c = objectMapper.convertValue(data, Components.class);
                componentsService.save(c);
                model.addAttribute("message", "Додано Component");
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                Details d = objectMapper.convertValue(data, Details.class);
                detailsService.save(d);
                model.addAttribute("message", "Додано Detail");
                handleSelect("details", model);
            }
            default -> throw new IllegalArgumentException("Невідома сутність для INSERT: " + entity);
        }
    }

    // UPDATE
    private void handleUpdate(String entity, Map<String, Object> data, Model model) {
        switch (entity) {
            case "product", "products" -> {
                Product p = objectMapper.convertValue(data, Product.class);
                p.setIsNew(false);
                productService.save(p);
                model.addAttribute("message", "Оновлено Product");
                handleSelect("products", model);
            }
            case "component", "components" -> {
                Components c = objectMapper.convertValue(data, Components.class);
                componentsService.save(c);
                model.addAttribute("message", "Оновлено Component");
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                Details d = objectMapper.convertValue(data, Details.class);
                detailsService.save(d);
                model.addAttribute("message", "Оновлено Detail");
                handleSelect("details", model);
            }
            default -> throw new IllegalArgumentException("Невідома сутність для UPDATE: " + entity);
        }
    }

    // DELETE
    private void handleDelete(String entity, Map<String, Object> data, Model model) {
        Object idVal = data.get("id");
        if (idVal == null) throw new IllegalArgumentException("ID не вказано для видалення");

        switch (entity) {
            case "product", "products" -> {
                productService.deleteById(UUID.fromString(idVal.toString()));
                handleSelect("products", model);
            }
            case "component", "components" -> {
                componentsService.deleteById(Integer.parseInt(idVal.toString()));
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                detailsService.deleteById(Integer.parseInt(idVal.toString()));
                handleSelect("details", model);
            }
            default -> throw new IllegalArgumentException("Невідома сутність для DELETE: " + entity);
        }
        model.addAttribute("message", "Видалено успішно");
    }

    public static class RequestDto {
        public String action;
        public String entity;
        public Map<String, Object> data;
    }
}
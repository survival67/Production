package production.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

import production.model.Product;
import production.model.Components;
import production.model.Details;
import production.services.ComponentsService;
import production.services.DetailsService;
import production.services.ProductService;

<<<<<<< HEAD
import java.security.Principal;
import java.util.*;

@Controller
=======
import java.util.*;

@Controller
@RequestMapping("/dashboard")
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
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

<<<<<<< HEAD
    @GetMapping("/")
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("/dashboard")
=======
    @GetMapping
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    public String showDashboard() {
        return "dashboard";
    }

<<<<<<< HEAD
    @PostMapping("/dashboard/execute")
    public String executeCommand(@RequestParam("query") String jsonInput, Model model, Principal principal) {
        
        model.addAttribute("lastCommand", jsonInput); 

        try {
            RequestController requestDto = objectMapper.readValue(jsonInput, RequestController.class);

            // Отримуємо права
            Authentication auth = (Authentication) principal;
            boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            String action = requestDto.action;
            if (action == null) action = "";
            
            // Якщо не адмін
            if (!isAdmin && !action.equalsIgnoreCase("select")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Only SELECT is allowed for Users");
            }

            // Логіка виконання
            String entity = requestDto.entity;
            if (entity == null) entity = "";
            entity = entity.toLowerCase();
            action = action.toLowerCase();

            switch (action) {
                case "select" -> handleSelect(entity, model);
                case "insert" -> handleInsert(entity, requestDto.data, model);
                case "update" -> handleUpdate(entity, requestDto.data, model);
                case "delete" -> handleDelete(entity, requestDto.data, model);
                default -> throw new IllegalArgumentException("Невідома дія: " + action);
            }

        } catch (ResponseStatusException e) {
            throw e; // Помилка доступу далі до сторінки 403
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Помилка виконання: " + e.getMessage());
        }

        return "dashboard"; 
    }

    // Private Methods

=======
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
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    private void handleSelect(String entity, Model model) {
        switch (entity) {
            case "product", "products" -> model.addAttribute("productsList", productService.findAll());
            case "component", "components" -> model.addAttribute("componentsList", componentsService.findAll());
            case "detail", "details" -> model.addAttribute("detailsList", detailsService.findAll());
<<<<<<< HEAD
            default -> throw new IllegalArgumentException("Невідома сутність: " + entity);
        }
    }

=======
            default -> throw new IllegalArgumentException("Невідома сутність для SELECT: " + entity);
        }
    }

    // INSERT
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    private void handleInsert(String entity, Map<String, Object> data, Model model) {
        switch (entity) {
            case "product", "products" -> {
                Product p = objectMapper.convertValue(data, Product.class);
                if (p.getId() == null) p = new Product(UUID.randomUUID(), p.getName(), p.getSerialNumber(), p.getCategory());
                p.setIsNew(true);
                productService.save(p);
<<<<<<< HEAD
=======
                model.addAttribute("message", "Додано Product");
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
                handleSelect("products", model);
            }
            case "component", "components" -> {
                Components c = objectMapper.convertValue(data, Components.class);
                componentsService.save(c);
<<<<<<< HEAD
=======
                model.addAttribute("message", "Додано Component");
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                Details d = objectMapper.convertValue(data, Details.class);
                detailsService.save(d);
<<<<<<< HEAD
                handleSelect("details", model);
            }
        }
        model.addAttribute("message", "Успішно додано!");
    }

=======
                model.addAttribute("message", "Додано Detail");
                handleSelect("details", model);
            }
            default -> throw new IllegalArgumentException("Невідома сутність для INSERT: " + entity);
        }
    }

    // UPDATE
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    private void handleUpdate(String entity, Map<String, Object> data, Model model) {
        switch (entity) {
            case "product", "products" -> {
                Product p = objectMapper.convertValue(data, Product.class);
                p.setIsNew(false);
                productService.save(p);
<<<<<<< HEAD
=======
                model.addAttribute("message", "Оновлено Product");
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
                handleSelect("products", model);
            }
            case "component", "components" -> {
                Components c = objectMapper.convertValue(data, Components.class);
                componentsService.save(c);
<<<<<<< HEAD
=======
                model.addAttribute("message", "Оновлено Component");
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
                handleSelect("components", model);
            }
            case "detail", "details" -> {
                Details d = objectMapper.convertValue(data, Details.class);
                detailsService.save(d);
<<<<<<< HEAD
                handleSelect("details", model);
            }
        }
        model.addAttribute("message", "Успішно оновлено!");
    }

    private void handleDelete(String entity, Map<String, Object> data, Model model) {
        Object idVal = data.get("id");
        if (idVal == null) throw new IllegalArgumentException("ID не вказано");
=======
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
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

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
<<<<<<< HEAD
        }
        model.addAttribute("message", "Успішно видалено!");
=======
            default -> throw new IllegalArgumentException("Невідома сутність для DELETE: " + entity);
        }
        model.addAttribute("message", "Видалено успішно");
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    }

    public static class RequestDto {
        public String action;
        public String entity;
        public Map<String, Object> data;
    }
}
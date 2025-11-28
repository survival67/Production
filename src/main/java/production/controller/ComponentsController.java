package production.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import production.model.Components;
import production.repository.ComponentsRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentsController {

    private final ComponentsRepository componentsRepository;

    public ComponentsController(ComponentsRepository componentsRepository) {
        this.componentsRepository = componentsRepository;
    }

    @GetMapping
    public ResponseEntity<List<Components>> getAllComponents() {
        return ResponseEntity.ok(componentsRepository.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Components>> getComponentsByProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(componentsRepository.findByProductId(productId));
    }
}
package production.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import production.model.Details;
import production.repository.DetailsRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/details")
public class DetailsController {

    private final DetailsRepository detailsRepository;

    public DetailsController(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @GetMapping
    public ResponseEntity<List<Details>> getAllDetails() {
        return ResponseEntity.ok(detailsRepository.findAll());
    }

    @GetMapping("/component/{componentId}")
    public ResponseEntity<List<Details>> getDetailsByComponent(@PathVariable Integer componentId) {
        return ResponseEntity.ok(detailsRepository.findByComponentId(componentId));
    }
}
package production.services;

import org.springframework.stereotype.Service;
import production.model.Components;
import production.repository.ComponentsRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ComponentsService {

    private final ComponentsRepository repository;

    public ComponentsService(ComponentsRepository repository) {
        this.repository = repository;
    }

    public List<Components> findAll() {
        return repository.findAll();
    }

    public List<Components> findByProductId(UUID productId) {
        return repository.findByProductId(productId);
    }

    public void save(Components component) {
        repository.save(component);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
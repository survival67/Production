package production.services;

import org.springframework.stereotype.Service;
import production.model.Components;
import production.repository.ComponentsRepository;

import java.util.List;
import java.util.UUID;
<<<<<<< HEAD
import org.springframework.data.repository.CrudRepository;
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

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
<<<<<<< HEAD
    
    public Components findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

    public void save(Components component) {
        repository.save(component);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
package production.services;

import org.springframework.stereotype.Service;
import production.model.Details;
import production.repository.DetailsRepository;

import java.util.List;
<<<<<<< HEAD
import org.springframework.data.repository.CrudRepository;
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

@Service
public class DetailsService {

    private final DetailsRepository repository;

    public DetailsService(DetailsRepository repository) {
        this.repository = repository;
    }

    public List<Details> findAll() {
        return repository.findAll();
    }

    public List<Details> findByComponentId(Integer componentId) {
        return repository.findByComponentId(componentId);
    }
<<<<<<< HEAD
    
    public Details findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

    public void save(Details detail) {
        repository.save(detail);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
package production.services;

import org.springframework.stereotype.Service;
import production.model.Product;
import production.repository.ProductRepository;

import java.util.List;
import java.util.UUID;
<<<<<<< HEAD
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }
<<<<<<< HEAD
    
    public Optional<Product> findById(UUID id) {
        return repository.findById(id); 
    }
=======
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6

    public void save(Product product) {
        repository.save(product);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
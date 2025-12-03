package production.services;

import org.springframework.stereotype.Service;
import production.model.Product;
import production.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
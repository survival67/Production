package production.repository;

import org.springframework.data.repository.ListCrudRepository;
import production.model.Details;
import java.util.List;

public interface DetailsRepository extends ListCrudRepository<Details, Integer> {
    List<Details> findByComponentId(Integer componentId);
}
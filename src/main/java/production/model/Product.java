package production.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("products")
public class Product implements Persistable<UUID> {

    @Id
    private UUID id;
    private String name;
    private String serialNumber;
    private String category;

    @Transient
    private boolean isNew = true;

    public Product() {}
<<<<<<< HEAD
    
=======

>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    public Product(UUID id, String name, String serialNumber, String category) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.category = category;
    }

<<<<<<< HEAD
    public void setId(UUID id) {
        this.id = id;
=======
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public boolean isNew() {
        return isNew; 
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
    }

    @Override
    public UUID getId() { return id; }
<<<<<<< HEAD

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public void setIsNew(boolean isNew) { this.isNew = isNew; }

    @Override
    public boolean isNew() { return isNew; }
=======
    public String getName() { return name; }
    public String getSerialNumber() { return serialNumber; }
    public String getCategory() { return category; }
>>>>>>> 7adadccd91c69313aa7d5caf4e8f22ee1ad413f6
}
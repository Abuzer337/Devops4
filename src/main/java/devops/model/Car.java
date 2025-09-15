package devops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
@Table(name = "car")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String brand;
    String model;
    Date year;
    int mileage;
    String vin;
}


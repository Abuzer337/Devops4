package devops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "handling")
public class Handling {
    static String[] typesOfHandling = { "Замена воздушного фильтра",
            "Замена салонного фильтра",
            "Замена масла двигателя",
            "Замена масла в КПП",
            "Замена тормозных колодок",
            "Замена тормозных дисков",
            "Замена свечей зажигания",
            "Замена антифриза",
            "Замена тормозной жидкости",
            "Замена стоек стабилизатора",
            "Замена пыльников амортизатора",
            "Замена тормозных трубок"
    };

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    Car car;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Date date;
    String type;
    int cost;
}

package guru.springframework.spring6webclient.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BeerDTO {

    private String id;
    private String beerName;
    private String beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}

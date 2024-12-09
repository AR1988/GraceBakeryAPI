package cohort46.gracebakeryapi.bakery.temp.model;


import cohort46.gracebakeryapi.bakery.product.model.Product;
import cohort46.gracebakeryapi.bakery.size.model.Size;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;


    @NonNull
    @Column(nullable = false)
    private double price;

    @ManyToOne
    private Size size;

    @ManyToOne
    private Product product;
}

/*
    @ManyToOne
    private Product poduct;
//*/


package cohort46.gracebakeryapi.bakery.temp.model;

import cohort46.gracebakeryapi.accounting.model.UserInfo;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NonNull
    @Column(nullable = false, unique=true)
    private String address;

    @Nullable
    private String city;

    @Nullable
    private String street;

    @Nullable
    private Integer building;

    @Nullable
    private String apartment;

    @ManyToOne
    private UserInfo userInfo;
}

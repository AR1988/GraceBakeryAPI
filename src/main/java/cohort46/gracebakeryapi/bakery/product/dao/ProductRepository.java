package cohort46.gracebakeryapi.bakery.product.dao;

import cohort46.gracebakeryapi.bakery.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Stream<Product> findProductsByIsActive(Boolean isactive);
}



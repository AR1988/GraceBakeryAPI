package cohort46.gracebakeryapi.bakery.image.dao;

import cohort46.gracebakeryapi.bakery.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
package cohort46.gracebakeryapi.bakery.image.service;

import cohort46.gracebakeryapi.bakery.filter.model.Filter;
import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;
import cohort46.gracebakeryapi.bakery.image.model.Image;


public interface ImageService {
    ImageDto addImage(ImageDto imageDto);//Long
    ImageDto findImageById(Long imageId);
    ImageDto deleteImage(Long id);
    ImageDto updateImage(ImageDto imageDto, Long id);
    Image store(Image image);
    Iterable<ImageDto> findByProductId(Long product_id);
}

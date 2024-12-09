package cohort46.gracebakeryapi.bakery.image.service;

import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;


public interface ImageService {
    ImageDto addImage(ImageDto imageDto);//Long
    ImageDto findImageById(Long imageId);
    ImageDto deleteImage(Long id);
    ImageDto updateImage(ImageDto imageDto, Long id);
}

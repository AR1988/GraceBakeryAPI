package cohort46.gracebakeryapi.bakery.image.controller;

import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;
import cohort46.gracebakeryapi.bakery.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto addImage(@RequestBody ImageDto imageDto) {
        return imageService.addImage(imageDto)  ;
    }

    @GetMapping("/image/{id}")
    public ImageDto findImageById(@PathVariable Long id) {
        return imageService.findImageById(id);
    }

    @DeleteMapping("/image/{id}")
    public ImageDto deleteImage(@PathVariable Long id) {
        return imageService.deleteImage(id);
    }

    @PutMapping("/image/{id}")
    public ImageDto updateImage( @RequestBody ImageDto imageDto, @PathVariable Long id) {
        return imageService.updateImage(imageDto, id);
    }

    @GetMapping("/images/product/{product_id}")
    public Iterable<ImageDto> findByProductId(@PathVariable Long product_id) {
        return imageService.findByProductId(product_id);
    }
}

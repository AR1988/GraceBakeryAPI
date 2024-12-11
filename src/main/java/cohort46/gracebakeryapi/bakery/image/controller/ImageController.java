package cohort46.gracebakeryapi.bakery.image.controller;

import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;
import cohort46.gracebakeryapi.bakery.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor

public class ImageController {
    private final ImageService imageService;

    @PostMapping("/api/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto addImage(@RequestBody ImageDto imageDto) {
        return imageService.addImage(imageDto)  ;
    }

    @GetMapping("/api/image/{id}")
    public ImageDto findImageById(@PathVariable Long id) {
        return imageService.findImageById(id);
    }

    @DeleteMapping("/api/image/{id}")
    public ImageDto deleteImage(@PathVariable Long id) {
        return imageService.deleteImage(id);
    }

    @PutMapping("/api/image/{id}")
    public ImageDto updateImage( @RequestBody ImageDto imageDto, @PathVariable Long id) {
        return imageService.updateImage(imageDto, id);
    }
}

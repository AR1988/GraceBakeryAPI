package cohort46.gracebakeryapi.bakery.image.service;

import cohort46.gracebakeryapi.bakery.filter.model.Filter;
import cohort46.gracebakeryapi.bakery.image.controller.ImageController;
import cohort46.gracebakeryapi.bakery.product.dao.ProductRepository;
import cohort46.gracebakeryapi.bakery.image.dao.ImageRepository;
import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import cohort46.gracebakeryapi.bakery.image.model.Image;
import cohort46.gracebakeryapi.bakery.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private String patch = "../Images/";

    private ImageController imageController;

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ImageDto addImage(ImageDto imageDto) {
        Image image = modelMapper.map(imageDto, Image.class);
        Product product = productRepository.findById(imageDto.getProductid()).orElseThrow(EntityNotFoundException::new);
        image.setProduct(product);
        image.setId(null);
        image = imageRepository.save(image);
        if(image != null) {
            return modelMapper.map(image, ImageDto.class);
        }
        return null;
    }

    @Override
    public ImageDto findImageById(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(image, ImageDto.class);
    }

    @Transactional
    @Override
    public ImageDto deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(EntityNotFoundException::new);
        imageRepository.delete(image);
        return modelMapper.map(image, ImageDto.class);
    }

    @Transactional
    @Override
    public ImageDto updateImage(ImageDto imageDto, Long id) {
        imageDto.setId(id);
        Image image = imageRepository.findById(imageDto.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(imageDto, image);
        image.setProduct(productRepository.findById(imageDto.getProductid()).orElseThrow(EntityNotFoundException::new));
        return modelMapper.map(imageRepository.save(image), ImageDto.class);
    }

    @Transactional
    @Override
    public Image store(Image image) {
        return imageRepository.saveAndFlush(image);
    }

    @Override
    public Iterable<ImageDto> findByProductId(Long product_id) {
        Product temp1 = productRepository.findById(product_id).orElseThrow(EntityNotFoundException::new);
        Stream<Image> temp = imageRepository.findByProduct( temp1 );
        return null;//imageRepository.findByProductId(product_id, Sort.by("id")).map(i -> modelMapper.map(i, ImageDto.class)).toList();
    }

    private boolean SaveImage(String name) {
        //String
        return false;
    }

    private boolean DeleteImage(String name) {
        return false;
    }

}
package cohort46.gracebakeryapi.bakery.product.service;

import cohort46.gracebakeryapi.bakery.product.controller.ProductController;
import cohort46.gracebakeryapi.bakery.product.dao.ProductRepository;
import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import cohort46.gracebakeryapi.bakery.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductController productController;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    //@Transactional
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(null);
        product = productRepository.save(product);
        if(product != null) {
            return modelMapper.map(product, ProductDto.class);
        }
        return null;
    }

    @Override
    public ProductDto findProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(productDto, product);
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto activateProduct(Long productId, Boolean activate) {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        product.setIsActive(activate);
        return modelMapper.map(productRepository.saveAndFlush(product), ProductDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ProductDto> findProductsByIsActive(Boolean isactive) {
        return productRepository.findProductsByIsActive(isactive).map(s -> modelMapper.map(s, ProductDto.class)).toList() ;//return modelMapper.map(productRepository.findProductsByIsActive(isactive), ProductDto.class);//productRepository.findProductsByIsActive(isactive).map(s -> modelMapper.map(s, ProductDto.class)).collect(Collectors.toList());  // Собираем в список и возвращаем
    }
}

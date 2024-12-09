package cohort46.gracebakeryapi.bakery.product.service;

import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);//Long

    ProductDto findProductById(Long productId);

    ProductDto updateProduct(ProductDto productDto);

    ProductDto activateProduct(Long Id, Boolean activate);

    Iterable<ProductDto> findProductsByIsActive(Boolean isactive);
}


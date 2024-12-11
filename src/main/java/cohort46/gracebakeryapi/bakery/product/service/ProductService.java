package cohort46.gracebakeryapi.bakery.product.service;

import cohort46.gracebakeryapi.bakery.bakeryoptional.dto.BakeryoptionalDto;
import cohort46.gracebakeryapi.bakery.filter.dto.FilterDto;
import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    ProductDto findProductById(Long productId);
    ProductDto updateProduct(ProductDto productDto, Long id);
    ProductDto activateProduct(Long Id, Boolean activate);
    Iterable<ProductDto> findProductsByIsActive(Boolean isactive);
    Iterable<ProductDto> findProductsByCategory(Long category_id);
    Iterable<ProductDto> getProductsAll();
    Iterable<ProductDto> findProductsByFilters(Iterable<FilterDto> filters);
}


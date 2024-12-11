package cohort46.gracebakeryapi.bakery.product.controller;

import cohort46.gracebakeryapi.bakery.filter.dto.FilterDto;
import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;
import cohort46.gracebakeryapi.bakery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto)  ;
    }//Long

    @GetMapping("/product/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("/product/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto updateProduct( @RequestBody ProductDto productDto, @PathVariable Long id) {
        return productService.updateProduct(productDto, id);
    }
    @PatchMapping("/product/{id}/isactive/{isactive}")
    ProductDto activateProduct(@PathVariable Long id, @PathVariable Boolean isactive){
        return productService.activateProduct( id, isactive );
    }

    @GetMapping("/products/isactive/{isactive}")
    public Iterable<ProductDto> findProductsByIsActive(@PathVariable Boolean isactive) {
        return productService.findProductsByIsActive(isactive);
    }

    @GetMapping("/products/category/{category_id}")
    public Iterable<ProductDto> findProductsByCategory(@PathVariable Long category_id) {
        return productService.findProductsByCategory(category_id);
    }

    @GetMapping("/products/filters")
    public Iterable<ProductDto> findProductsByFilters(@RequestBody Iterable<FilterDto> filters) {
        return productService.findProductsByFilters(filters);
    }

    @GetMapping("/products")
    public Iterable<ProductDto> getProductsAll() {
        return productService.getProductsAll();
    }
}

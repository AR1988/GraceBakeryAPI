package cohort46.gracebakeryapi.bakery.product.controller;

import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;
import cohort46.gracebakeryapi.bakery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/api/product")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto)  ;
    }//Long

    @GetMapping("/api/product/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("/api/product")
    public ProductDto updateProduct( @RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }
    @PatchMapping("/api/product/{id}/isactive/{isactive}")
    ProductDto activateProduct(@PathVariable Long id, @PathVariable Boolean isactive){
        return productService.activateProduct( id, isactive );
    }

    @GetMapping("/api/products/isactive/{isactive}")
    public Iterable<ProductDto> findProductsByIsActive(@PathVariable Boolean isactive) {
        return productService.findProductsByIsActive(isactive);
    }
}

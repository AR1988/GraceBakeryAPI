package cohort46.gracebakeryapi.bakery.product.service;

import cohort46.gracebakeryapi.bakery.bakeryoptional.dao.BakeryoptionalRepository;
import cohort46.gracebakeryapi.bakery.bakeryoptional.dto.BakeryoptionalDto;
import cohort46.gracebakeryapi.bakery.bakeryoptional.model.Bakeryoptional;
import cohort46.gracebakeryapi.bakery.bakeryoptional.service.BakeryoptionalService;
import cohort46.gracebakeryapi.bakery.filter.dao.FilterRepository;
import cohort46.gracebakeryapi.bakery.filter.dto.FilterDto;
import cohort46.gracebakeryapi.bakery.filter.model.Filter;
import cohort46.gracebakeryapi.bakery.filter.service.FilterService;
import cohort46.gracebakeryapi.bakery.image.dao.ImageRepository;
import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;
import cohort46.gracebakeryapi.bakery.image.model.Image;
import cohort46.gracebakeryapi.bakery.image.service.ImageService;
import cohort46.gracebakeryapi.bakery.ingredient.dao.IngredientRepository;
import cohort46.gracebakeryapi.bakery.ingredient.dto.IngredientDto;
import cohort46.gracebakeryapi.bakery.ingredient.model.Ingredient;
import cohort46.gracebakeryapi.bakery.ingredient.service.IngredientService;
import cohort46.gracebakeryapi.bakery.optionsize.service.OptionsizeService;
import cohort46.gracebakeryapi.bakery.product.controller.ProductController;
import cohort46.gracebakeryapi.bakery.product.dao.ProductRepository;
import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;
import cohort46.gracebakeryapi.bakery.productsize.model.Productsize;
import cohort46.gracebakeryapi.bakery.productsize.service.ProductsizeService;
import cohort46.gracebakeryapi.bakery.size.dao.SizeRepository;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import cohort46.gracebakeryapi.bakery.product.model.Product;
import cohort46.gracebakeryapi.helperclasses.SizePrice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ImageRepository imageRepository;
    private ProductController productController;

    private final FilterRepository filterRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final IngredientRepository ingredientRepository;
    private final BakeryoptionalRepository bakeryoptionalRepository;

    private final ProductsizeService productsizeService;
    private final FilterService filterService;
    private final IngredientService ingredientService;
    private final BakeryoptionalService bakeryoptionalService;
    private final ImageService imageService;

    private final ModelMapper modelMapper;




    @Transactional
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        if(!checkSource(productDto)) return null;
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(null);
        product = productRepository.save(product);
        if(product != null) {
            return updateProduct(productDto , product.getId());
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
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        if(!checkSource(productDto)) return null;
        productDto.setId(id);
        productRepository.findById(productDto.getId()).orElseThrow(EntityNotFoundException::new);
        Product product = modelMapper.map(productDto, Product.class);

        if (productDto.getSizeprices() != null && !productDto.getSizeprices().isEmpty()) {
            for (SizePrice sizePrice : productDto.getSizeprices()) {
                Productsize productsize = new Productsize();
                productsize.setSize(sizeRepository.findById(sizePrice.getSizeid()).orElseThrow(EntityNotFoundException::new));
                productsize.setPrice(sizePrice.getPrice());
                productsize.setProduct(product);
                productsize.setId(null);
                productsize = productsizeService.store(productsize);
                product.getProductsizes().add(productsize);
            }
            for (FilterDto filterDto : productDto.getFilters()) {
                Filter filter = filterRepository.findById(filterDto.getId()).orElseThrow(EntityNotFoundException::new);
                filter.getProducts().add(product);
                filterService.store(filter);
            }
            for (IngredientDto ingredientDto : productDto.getIngredients()) {
                Ingredient ingredient = ingredientRepository.findById(ingredientDto.getId()).orElseThrow(EntityNotFoundException::new);
                ingredient.getProducts().add(product);
                ingredientService.store(ingredient);
            }

            for (BakeryoptionalDto bakeryoptionalDto : productDto.getBakeryoptionals()) {
                Bakeryoptional bakeryoptional = bakeryoptionalRepository.findById(bakeryoptionalDto.getId()).orElseThrow(EntityNotFoundException::new);
                bakeryoptional.getProducts().add(product);
                bakeryoptionalService.store(bakeryoptional);
            }

            for (ImageDto imageDto : productDto.getImages()) {
                Image image = imageRepository.findById(imageDto.getId()).orElseThrow(EntityNotFoundException::new);
                image.setProduct(product);
                imageService.store(image);
            }
        }
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
        return productRepository.findProductsByIsActive(isactive).map(s -> modelMapper.map(s, ProductDto.class)).toList() ;
    }

    @Override
    public Iterable<ProductDto> findProductsByCategory(Long category_id) {
        //сначала выборка по category.id, потом сортировка по полю "ingredients.id" , потом сортировка по полю "productsizes.size.persons"
        return productRepository.findProductsByCategoryId(category_id, Sort.by("ingredients.id").and((Sort.by("productsizes.size.persons")))).map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }

    @Override
    public Iterable<ProductDto> getProductsAll() {
        //сортировка сначала по category.id, потом сортировка по полю "ingredients.id" , потом сортировка по полю "productsizes.size.persons"
        Sort sort = Sort.by("category.id").and(Sort.by("ingredients.id").and((Sort.by("productsizes.size.persons"))));
        return productRepository.findAll(sort).stream().map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }

    @Override
    public Iterable<ProductDto> findProductsByFilters(Iterable<FilterDto> filters) {
        return null;
    }

    private boolean checkSource(ProductDto productDto)
    {
        for (IngredientDto ingredientDto : productDto.getIngredients()) {
            if(filterRepository.findById(ingredientDto.getId()).isEmpty()) return false;
        };
        for (FilterDto filterDto : productDto.getFilters()) {
            if(filterRepository.findById(filterDto.getId()).isEmpty()) return false;
        };
        for (BakeryoptionalDto bakeryoptionalDto : productDto.getBakeryoptionals()) {
            if(filterRepository.findById(bakeryoptionalDto.getId()).isEmpty()) return false;
        };
        for (ImageDto imageDto : productDto.getImages()) {
            if(imageRepository.findById(imageDto.getId()).isEmpty()) return false;
        };
        for (SizePrice sizePrice : productDto.getSizeprices()) {
            if(sizeRepository.findById(sizePrice.getSizeid()).isEmpty()) return false;
        }
        return true;
    };
}


package cohort46.gracebakeryapi.bakery.product.dto;

import cohort46.gracebakeryapi.bakery.bakeryoptional.dto.BakeryoptionalDto;
import cohort46.gracebakeryapi.bakery.ingredient.dto.IngredientDto;
import cohort46.gracebakeryapi.bakery.image.dto.ImageDto;
import cohort46.gracebakeryapi.bakery.temp.dto.ProductSizeDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    @NotNull
    private Long id;

    private String title_de;
    private String title_ru;
    private String description_de;
    private String description_ru;
    private Boolean isActive;

    @NotNull
    @Setter
    private Long categoryid;

    @JsonProperty("image")
    private Set<ImageDto> images;


    private Set<IngredientDto> ingridients;


    private Set<BakeryoptionalDto> bakeryOptionals;

    @JsonProperty("productSize")
    private Set<ProductSizeDto> productSizes;


}
//{product_id*, category_id, title_de, title_ru, description_de, description_ru , [image,...], [option_id,...], [ingridient_id,...], [<size, price>,....], is_active}

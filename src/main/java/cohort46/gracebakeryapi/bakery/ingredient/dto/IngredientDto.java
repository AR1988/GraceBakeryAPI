package cohort46.gracebakeryapi.bakery.ingredient.dto;

import cohort46.gracebakeryapi.bakery.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDto {
    @NotNull
    @Setter
    private Long id;
    private String title_de;
    private String title_ru;
    private String description_de;
    private String description_ru;
    @NotNull
    private String image_de;
    @NotNull
    private String image_ru;

    //@JsonProperty("product")
    private Set<ProductDto> products;
}


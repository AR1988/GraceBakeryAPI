package cohort46.gracebakeryapi.bakery.ingredient.service;

import cohort46.gracebakeryapi.bakery.bakeryoptional.model.Bakeryoptional;
import cohort46.gracebakeryapi.bakery.ingredient.controller.IngredientController;
import cohort46.gracebakeryapi.bakery.ingredient.dao.IngredientRepository;
import cohort46.gracebakeryapi.bakery.section.dao.SectionRepository;
import cohort46.gracebakeryapi.bakery.ingredient.dto.IngredientDto;
import cohort46.gracebakeryapi.bakery.size.dto.SizeDto;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import cohort46.gracebakeryapi.bakery.ingredient.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private IngredientController ingredientController;

    private final IngredientRepository ingredientRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    //@Transactional
    @Override
    public IngredientDto addIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        ingredient.setId(null);
        ingredient = ingredientRepository.save(ingredient);
        if(ingredient != null) {
            return modelMapper.map(ingredient, IngredientDto.class);
        }
        return null;
    }

    @Override
    public IngredientDto findIngredientById(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    @Transactional
    @Override
    public IngredientDto updateIngredient(IngredientDto ingredientDto, Long id) {
        ingredientDto.setId(id);
        Ingredient ingredient = ingredientRepository.findById(ingredientDto.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(ingredientDto, ingredient);
        return modelMapper.map(ingredientRepository.save(ingredient), IngredientDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<IngredientDto> getIngredientAll() {
        return ingredientRepository.findAll().stream().map(i -> modelMapper.map(i, IngredientDto.class)).toList() ;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<IngredientDto> findIngredientsByProduct(Long product_id) {
        return ingredientRepository.findByProductsId(product_id).map(i -> modelMapper.map(i, IngredientDto.class)).toList() ;
    }

    @Transactional
    @Override
    public Ingredient store(Ingredient ingredient) {
        return ingredientRepository.saveAndFlush(ingredient);
    }
}

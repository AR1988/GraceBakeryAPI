package cohort46.gracebakeryapi.bakery.category.service;

import cohort46.gracebakeryapi.bakery.category.controller.CategoryController;
import cohort46.gracebakeryapi.bakery.category.dao.CategoryRepository;
import cohort46.gracebakeryapi.bakery.section.dao.SectionRepository;
import cohort46.gracebakeryapi.bakery.category.dto.CategoryDto;
import cohort46.gracebakeryapi.bakery.section.dto.SectionDto;
import cohort46.gracebakeryapi.bakery.size.dto.SizeDto;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import cohort46.gracebakeryapi.bakery.category.model.Category;
import cohort46.gracebakeryapi.bakery.section.model.Section;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryController categoryController;

    private final CategoryRepository categoryRepository;
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;


    //@Transactional
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        sectionRepository.findById(categoryDto.getSectionid());
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setId(null);
        Section section = sectionRepository.findById(categoryDto.getSectionid()).orElseThrow(EntityNotFoundException::new);
        category.setSection(section);
        category = categoryRepository.save(category);
        if(category != null) {
            return modelMapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        categoryDto.setId(id);
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(categoryDto, category);
        category.setSection(sectionRepository.findById(categoryDto.getSectionid()).orElseThrow(EntityNotFoundException::new));
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    @Transactional
    @Override
    public CategoryDto activateCategory(Long categoryId, Boolean activate) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        category.setIsActive(activate);
        return modelMapper.map(categoryRepository.saveAndFlush(category), CategoryDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CategoryDto> findCategoriesByIsActive(Boolean isactive) {
        return categoryRepository.findCategoriesByIsActive(isactive).map(s -> modelMapper.map(s, CategoryDto.class)).toList() ;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CategoryDto> findCategoriesBySection(Long section_id) {
        sectionRepository.findById(section_id).orElseThrow(EntityNotFoundException::new);
        //сортировка по IsActive по убыванию(сначала активные, потом неактивные)
        return categoryRepository.findCategoriesBySectionId(section_id, Sort.by("isActive").descending()).map(s -> modelMapper.map(s, CategoryDto.class)).toList() ;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CategoryDto> getCategoriesAll() {
        //сортировка сначала по IsActive по убыванию(сначала активные, потом неактивные), потом сортировка по полю "section.id"
        return categoryRepository.findAll(Sort.by("isActive").descending().and(Sort.by("section.id"))).stream().map(c -> modelMapper.map(c, CategoryDto.class)).toList() ;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CategoryDto> findCategoriesBySectionByIsActive(Long section_id, Boolean isactive) {
        sectionRepository.findById(section_id).orElseThrow(EntityNotFoundException::new);
        return categoryRepository.findCategoriesBySectionIdAndIsActive(section_id, isactive).map(s -> modelMapper.map(s, CategoryDto.class)).toList() ;
    }

}
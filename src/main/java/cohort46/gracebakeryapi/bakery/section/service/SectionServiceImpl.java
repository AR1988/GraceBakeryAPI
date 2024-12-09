package cohort46.gracebakeryapi.bakery.section.service;

import cohort46.gracebakeryapi.bakery.section.controller.SectionController;
import cohort46.gracebakeryapi.bakery.section.dao.SectionRepository;
import cohort46.gracebakeryapi.bakery.section.dto.SectionDto;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import cohort46.gracebakeryapi.bakery.section.model.Section;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private SectionController sectionController;

    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;


    //@Transactional
    @Override
    public SectionDto addSection(SectionDto sectionDto) {
        Section section = modelMapper.map(sectionDto, Section.class);
        section.setId(null);
        section = sectionRepository.save(section);
        if(section != null) {
            return modelMapper.map(section, SectionDto.class);
        }
        return null;
    }

    @Override
    public SectionDto findSectionById(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(section, SectionDto.class);
    }

    @Transactional
    @Override
    public SectionDto updateSection(SectionDto sectionDto, Long id) {
        sectionDto.setId(id);
        Section section = sectionRepository.findById(sectionDto.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(sectionDto, section);
        return modelMapper.map(sectionRepository.save(section), SectionDto.class);
    }

    @Transactional
    @Override
    public SectionDto activateSection(Long sectionId, Boolean activate) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(EntityNotFoundException::new);
        section.setIsActive(activate);
        return modelMapper.map(sectionRepository.saveAndFlush(section), SectionDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<SectionDto> findSectionsByIsActive(Boolean isactive) {
        return sectionRepository.findSectionsByIsActive(isactive).map(s -> modelMapper.map(s, SectionDto.class)).toList() ;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<SectionDto> getSectionsAll() {
        List<SectionDto> sections = new ArrayList<>(sectionRepository.findSectionsByIsActive(Boolean.TRUE).map(s -> modelMapper.map(s, SectionDto.class)).toList());
        sections.addAll(sectionRepository.findSectionsByIsActive(Boolean.FALSE).map(s -> modelMapper.map(s, SectionDto.class)).toList());
        return sections;
    }
}
/*

        SectionDto tempDto = modelMapper.map(sectionDto, SectionDto.class);
        tempDto.setCategories(null);
        Section section = modelMapper.map(tempDto, Section.class);
        section.setId(null);
        section = sectionRepository.saveAndFlush(section);
        if(section != null) {

            for (Category temp : section.getCategories()) {
                temp.setSection(section);
                categoryRepository.saveAndFlush(temp);
            }

            return modelMapper.map(section, SectionDto.class);
        }
        return null;
 */
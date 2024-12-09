package cohort46.gracebakeryapi.bakery.section.controller;

import cohort46.gracebakeryapi.bakery.section.dto.SectionDto;
import cohort46.gracebakeryapi.bakery.section.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping("/api/section")
    public SectionDto addSection(@RequestBody SectionDto sectionDto) {
        return sectionService.addSection(sectionDto)  ;
    }//Long

    @GetMapping("/api/section/{id}")
    public SectionDto findSectionById(@PathVariable Long id) {
        return sectionService.findSectionById(id);
    }

    @PutMapping("/api/section/{id}")
    public SectionDto updateSection( @RequestBody SectionDto sectionDto, @PathVariable Long id) {
        return sectionService.updateSection(sectionDto, id);
    }
    @PatchMapping("/api/section/{id}/isactive/{isactive}")
    SectionDto activateSection(@PathVariable Long id, @PathVariable Boolean isactive){
        return sectionService.activateSection( id, isactive );
    }

    @GetMapping("/api/sections/isactive/{isactive}")
    public Iterable<SectionDto> findSectionsByIsActive(@PathVariable Boolean isactive) {
        return sectionService.findSectionsByIsActive(isactive);
    }

    @GetMapping("/api/sections")
    public Iterable<SectionDto> getSectionsAll() {
        return sectionService.getSectionsAll();
    }
}

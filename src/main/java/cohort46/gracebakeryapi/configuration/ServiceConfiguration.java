package cohort46.gracebakeryapi.configuration;

import cohort46.gracebakeryapi.bakery.bakeryoptional.dto.BakeryoptionalDto;
import cohort46.gracebakeryapi.bakery.bakeryoptional.model.Bakeryoptional;
import cohort46.gracebakeryapi.bakery.category.dto.CategoryDto;
import cohort46.gracebakeryapi.bakery.category.model.Category;
import cohort46.gracebakeryapi.bakery.optionsize.dto.OptionsizeDto;
import cohort46.gracebakeryapi.bakery.optionsize.model.Optionsize;
import cohort46.gracebakeryapi.helperclasses.SizePrice;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Configuration
public class ServiceConfiguration {


    @Bean
    ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Настройка, чтобы игнорировать несовпадающие поля
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) // Можно использовать STANDARD или LOOSE
                .setSkipNullEnabled(true); // Игнорировать null-поля



        // Создаем маппинг для Category -> CategoryDto
        modelMapper.addMappings(new PropertyMap<Category, CategoryDto>() {
            @Override
            protected void configure() {
                // маппинг полей с разными именами
                map(source.getSection().getId(), destination.getSectionid());
                map(source.getProducts(), destination.getProducts());
            }
        });

        modelMapper.addMappings(new PropertyMap<Optionsize, OptionsizeDto>() {
            @Override
            protected void configure() {
                map(source.getSize().getId(), destination.getSizeid());
            }
        });

        modelMapper.addMappings(new PropertyMap<BakeryoptionalDto, Bakeryoptional>() {
            @Override
            protected void configure() {
                skip(destination.getOptionsizes() );
            }
        });
        modelMapper.addMappings(new PropertyMap<Bakeryoptional, BakeryoptionalDto>() {
            protected void configure() {
                using(new Converter<Set<Optionsize>, Set<SizePrice>>() {
                    public Set<SizePrice> convert(MappingContext<Set<Optionsize>, Set<SizePrice>> context) {
                        Set<SizePrice> sizePrices = new HashSet<>();
                        for (Optionsize optionSize : context.getSource()) {
                            // Создаем объект SizePrice с sizeid, равным id объекта Size
                            sizePrices.add(new SizePrice(optionSize.getSize().getId(), optionSize.getPrice()));
                        }
                        return sizePrices;
                    }
                }).map(source.getOptionsizes(), destination.getSizeprices());
            }
        });

        return modelMapper;
    }
}
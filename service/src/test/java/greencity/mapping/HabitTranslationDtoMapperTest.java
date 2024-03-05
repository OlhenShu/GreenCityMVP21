package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.habittranslation.HabitTranslationDto;
import greencity.entity.HabitTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HabitTranslationDtoMapperTest {
    @InjectMocks
    HabitTranslationDtoMapper mapper;
    @Test
    void convert(){
        HabitTranslation habitTranslation = ModelUtils.getHabitTranslation();
        HabitTranslationDto expected = HabitTranslationDto.builder()
                .description(habitTranslation.getDescription())
                .name(habitTranslation.getName())
                .habitItem(habitTranslation.getHabitItem())
                .build();

        HabitTranslationDto actual = mapper.convert(habitTranslation);

        assertEquals(expected.getHabitItem(), actual.getHabitItem());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void mapAllToList(){
        List<HabitTranslation> habitTranslations = Collections.singletonList(ModelUtils.getHabitTranslation());
        List<HabitTranslationDto> expected = Collections.singletonList(
                HabitTranslationDto.builder()
                        .description(habitTranslations.getFirst().getDescription())
                        .name(habitTranslations.getFirst().getName())
                        .habitItem(habitTranslations.getFirst().getHabitItem())
                        .build()
        );

        List<HabitTranslationDto> actual = mapper.mapAllToList(habitTranslations);

        assertEquals(expected.getFirst().getHabitItem(), actual.getFirst().getHabitItem());
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getDescription(), actual.getFirst().getDescription());
    }
}

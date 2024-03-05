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
public class HabitTranslationMapperTest {
    @InjectMocks
    HabitTranslationMapper mapper;
    @Test
    void convert(){
        HabitTranslation expected = ModelUtils.getHabitTranslationWithoutLanguage();
        HabitTranslationDto habitTranslationDto = HabitTranslationDto.builder()
                .description(expected.getDescription())
                .name(expected.getName())
                .habitItem(expected.getHabitItem())
                .build();

        HabitTranslation actual = mapper.convert(habitTranslationDto);

        assertEquals(expected, actual);
    }

    @Test
    void mapAllToList(){
        List<HabitTranslation> expected = Collections.singletonList(ModelUtils.getHabitTranslationWithoutLanguage());
        List<HabitTranslationDto> dtoList = Collections.singletonList(
                HabitTranslationDto.builder()
                .description(expected.getFirst().getDescription())
                .name(expected.getFirst().getName())
                .habitItem(expected.getFirst().getHabitItem())
                .build()
        );

        List<HabitTranslation> actual = mapper.mapAllToList(dtoList);

        assertEquals(expected, actual);
    }
}

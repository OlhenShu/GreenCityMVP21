package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.language.LanguageTranslationDTO;
import greencity.entity.HabitFactTranslation;
import greencity.entity.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LanguageTranslationDtoMapperTest {
    @InjectMocks
    LanguageTranslationDtoMapper mapper;

    @Test
    void convert(){
        LanguageTranslationDTO expected = ModelUtils.getLanguageTranslationDTO();
        HabitFactTranslation habitFactTranslation = HabitFactTranslation.builder()
                .content(expected.getContent())
                .language(Language.builder().id(expected.getLanguage().getId()).code(expected.getLanguage().getCode()).build())
                .build();

        LanguageTranslationDTO actual = mapper.convert(habitFactTranslation);

        assertEquals(expected, actual);
    }
}

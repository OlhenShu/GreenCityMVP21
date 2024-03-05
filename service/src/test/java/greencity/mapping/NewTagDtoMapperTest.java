package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.tag.NewTagDto;
import greencity.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NewTagDtoMapperTest {
    @InjectMocks
    NewTagDtoMapper mapper;

    @Test
    void convert(){
        NewTagDto expected = ModelUtils.getNewTagDto();
        Tag source = Tag.builder()
                .id(1L)
                .tagTranslations(ModelUtils.getTagTranslations())
                .build();

        NewTagDto actual = mapper.convert(source);

        assertEquals(expected, actual);
    }
}

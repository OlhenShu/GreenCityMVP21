package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.tag.TagDto;
import greencity.entity.Tag;
import greencity.entity.localization.TagTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagDtoMapperTest {
    @InjectMocks
    TagDtoMapper mapper;
    @Test
    void convert() {
        TagDto expected = ModelUtils.getTagDto();
        TagTranslation tagTranslation = TagTranslation.builder()
                .id(expected.getId())
                .tag(Tag.builder().id(expected.getId()).build())
                .name(expected.getName())
                .build();

        assertEquals(expected, mapper.convert(tagTranslation));
    }
}

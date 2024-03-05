package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.tag.TagVO;
import greencity.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static greencity.enums.TagType.ECO_NEWS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagMapperTest {
    @InjectMocks
    TagMapper mapper;

    @Test
    void convert() {
        Tag expected = ModelUtils.getTag();
        TagVO tagVO = TagVO.builder()
                .id(1L)
                .type(ECO_NEWS)
                .tagTranslations(ModelUtils.getTagTranslationsVO())
                .ecoNews(Collections.emptyList())
                .habits(Collections.emptySet())
                .build();

        Tag actual = mapper.convert(tagVO);

        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getId(), actual.getId());
    }
}

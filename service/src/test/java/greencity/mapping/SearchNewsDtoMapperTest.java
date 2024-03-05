package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.search.SearchNewsDto;
import greencity.dto.user.EcoNewsAuthorDto;
import greencity.entity.EcoNews;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SearchNewsDtoMapperTest {
    @InjectMocks
    SearchNewsDtoMapper mapper;
    @Test
    void convert(){
        SearchNewsDto expected = ModelUtils.getSearchNewsDto();
        expected.setTags(Collections.emptyList());
        EcoNewsAuthorDto ecoNewsAuthorDto = expected.getAuthor();
        EcoNews source = EcoNews.builder()
                .id(expected.getId())
                .title(expected.getTitle())
                .author(User.builder().id(ecoNewsAuthorDto.getId()).name(ecoNewsAuthorDto.getName()).build())
                .creationDate(expected.getCreationDate())
                .tags(ModelUtils.getTags())
                .build();

        SearchNewsDto actual = mapper.convert(source);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getTags(), actual.getTags());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getCreationDate(), actual.getCreationDate());
    }
}

package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econews.EcoNewsDto;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.entity.CustomShoppingListItem;
import greencity.entity.EcoNews;
import greencity.entity.Tag;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@TODO make tags not empty
@ExtendWith(MockitoExtension.class)
public class EcoNewsDtoMapperTest {
    @InjectMocks
    EcoNewsDtoMapper mapper;
    @Test
    void convert() {
        EcoNewsDto expected = ModelUtils.getEcoNewsDto();
        expected.setTags(Collections.emptyList());
        expected.setTagsUa(Collections.emptyList());
        EcoNews ecoNews = EcoNews.builder()
                .id(expected.getId())
                .author(ModelUtils.getUserShorten())
                .text(expected.getContent())
                .creationDate(expected.getCreationDate())
                .imagePath(expected.getImagePath())
                .usersLikedNews(Set.of(new User()))
                .shortInfo(expected.getShortInfo())
                .tags(Collections.emptyList())
                .usersDislikedNews(Collections.emptySet())
                .title(expected.getTitle())
                .ecoNewsComments(Collections.emptyList())
                .build();
        assertEquals(expected, mapper.convert(ecoNews));
    }
}

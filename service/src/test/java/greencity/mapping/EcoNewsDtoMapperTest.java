package greencity.mapping;

import greencity.ModelUtils;
import greencity.constant.AppConstant;
import greencity.dto.econews.EcoNewsDto;
import greencity.entity.*;
import greencity.entity.localization.TagTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EcoNewsDtoMapperTest {
    @InjectMocks
    EcoNewsDtoMapper mapper;

    @Test
    void convert() {
        EcoNewsDto expected = ModelUtils.getEcoNewsDto();
        Tag tag1 = Tag.builder()
                .tagTranslations(List.of(TagTranslation.builder()
                        .language(Language.builder().code(AppConstant.DEFAULT_LANGUAGE_CODE).build())
                        .name("tag")
                        .build()))
                .build();
        Tag tag2 = Tag.builder()
                .tagTranslations(List.of(TagTranslation.builder()
                        .language(Language.builder().code("ua").build())
                        .name("тег")
                        .build()))
                .build();
        EcoNews ecoNews = EcoNews.builder()
                .id(expected.getId())
                .author(ModelUtils.getUserShorten())
                .text(expected.getContent())
                .creationDate(expected.getCreationDate())
                .imagePath(expected.getImagePath())
                .usersLikedNews(Set.of(new User()))
                .shortInfo(expected.getShortInfo())
                .tags(List.of(tag1, tag2))
                .usersDislikedNews(Collections.emptySet())
                .title(expected.getTitle())
                .ecoNewsComments(Collections.emptyList())
                .build();
        assertEquals(expected, mapper.convert(ecoNews));
    }
}

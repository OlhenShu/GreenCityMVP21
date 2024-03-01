package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econews.EcoNewsDto;
import greencity.dto.econews.EcoNewsVO;
import greencity.dto.econewscomment.EcoNewsCommentVO;
import greencity.dto.tag.TagVO;
import greencity.dto.user.UserVO;
import greencity.entity.EcoNews;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@TODO write test
@ExtendWith(MockitoExtension.class)
public class EcoNewsVOMapperTest {
    @InjectMocks
    EcoNewsVOMapper mapper;

    @Test
    void convert() {
//        EcoNewsVO expected = ModelUtils.getEcoNewsVO();

//        EcoNews ecoNews = EcoNews.builder()
//                .id(expected.getId())
//                .author(ModelUtils.getUserShorten())
//                .creationDate(expected.getCreationDate())
//                .imagePath(expected.getImagePath())
//                .source(expected.getSource())
//                .text(expected.getText())
//                .title(expected.getTitle())
//                .tags(Collections.emptyList())
//                .usersLikedNews(Collections.emptySet())
//                .usersDislikedNews(Collections.emptySet())
//                .ecoNewsComments(Collections.emptyList())
//                .build();
//        assertEquals(expected, mapper.convert(ecoNews));
    }
}

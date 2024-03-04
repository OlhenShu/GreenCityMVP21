package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econews.EcoNewsVO;
import greencity.entity.EcoNews;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static greencity.enums.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class EcoNewsVOMapperTest {
    @InjectMocks
    EcoNewsVOMapper mapper;

    @Test
    void convert() {
        User user = ModelUtils.getUserShorten();
        user.setRole(ROLE_USER);
        EcoNewsVO expected = ModelUtils.getEcoNewsVO();
        expected.setAuthor(ModelUtils.getUserVOShorten());
        expected.setEcoNewsComments(Collections.singletonList(ModelUtils.getEcoNewsCommentVO()));
        EcoNews ecoNews = EcoNews.builder()
                .id(expected.getId())
                .author(user)
                .creationDate(expected.getCreationDate())
                .imagePath(expected.getImagePath())
                .source(expected.getSource())
                .text(expected.getText())
                .title(expected.getTitle())
                .tags(Collections.singletonList(ModelUtils.getTag()))
                .usersLikedNews(Collections.emptySet())
                .usersDislikedNews(Collections.emptySet())
                .ecoNewsComments(Collections.singletonList(ModelUtils.getEcoNewsComment()))
                .build();

        EcoNewsVO actual = mapper.convert(ecoNews);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getUsersLikedNews(), actual.getUsersLikedNews());
    }
}

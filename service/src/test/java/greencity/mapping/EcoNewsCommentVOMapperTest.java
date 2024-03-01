package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econewscomment.EcoNewsCommentVO;
import greencity.entity.EcoNewsComment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EcoNewsCommentVOMapperTest {
    @InjectMocks
    EcoNewsCommentVOMapper mapper;
    @Test
    void givenEcoNewsCommentWithParentCommentNull_whenConvert_thenReturnEcoNewsCommentVO() {
        EcoNewsCommentVO expected = ModelUtils.getEcoNewsCommentVOWithParentCommentNull();
        EcoNewsComment ecoNewsComment = EcoNewsComment.builder()
                .id(expected.getId())
                .text(expected.getText())
                .createdDate(expected.getCreatedDate())
                .modifiedDate(expected.getModifiedDate())
                .parentComment(null)
                .comments(null)
                .user(ModelUtils.getUser())
                .ecoNews(ModelUtils.getEcoNews())
                .deleted(false)
                .currentUserLiked(expected.isCurrentUserLiked())
                .usersLiked(Collections.emptySet())
                .build();
        assertEquals(expected, mapper.convert(ecoNewsComment));
    }

    @Test
    void givenEcoNewsCommentWithParentComment_whenConvert_thenReturnEcoNewsCommentVO() {
        EcoNewsCommentVO expected = ModelUtils.getEcoNewsCommentVO();
        expected.setUser(ModelUtils.getUserVOShorten());
        expected.setEcoNews(ModelUtils.getEcoNewsVOShorten());
        expected.setComments(null);

        LocalDateTime time = expected.getParentComment().getCreatedDate();
        EcoNewsComment ecoNewsCommentWiden = ModelUtils.getEcoNewsCommentWiden();
        ecoNewsCommentWiden.setCreatedDate(time);
        ecoNewsCommentWiden.setModifiedDate(time);

        EcoNewsComment ecoNewsComment = EcoNewsComment.builder()
                .id(expected.getId())
                .text(expected.getText())
                .createdDate(expected.getCreatedDate())
                .modifiedDate(expected.getModifiedDate())
                .parentComment(ecoNewsCommentWiden)
                .comments(null)
                .user(ModelUtils.getUser())
                .ecoNews(ModelUtils.getEcoNews())
                .deleted(false)
                .currentUserLiked(expected.isCurrentUserLiked())
                .usersLiked(Collections.emptySet())
                .build();
        assertEquals(expected, mapper.convert(ecoNewsComment));
    }
}

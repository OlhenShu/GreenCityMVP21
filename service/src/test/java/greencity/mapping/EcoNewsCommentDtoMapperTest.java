package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econewscomment.EcoNewsCommentDto;
import greencity.dto.user.EcoNewsAuthorDto;
import greencity.entity.EcoNewsComment;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EcoNewsCommentDtoMapperTest {
    @InjectMocks
    EcoNewsCommentDtoMapper mapper;
    public EcoNewsAuthorDto convert(User author) {
        return new EcoNewsAuthorDto(author.getId(), author.getName());
    }
    @Test
    void givenDeletedEcoNewsComment_whenConvert_thenDtoStatusIsDeleted() {
        EcoNewsCommentDto expected = ModelUtils.getEcoNewsCommentDtoStatusDeleted();
        EcoNewsComment ecoNewsComment = EcoNewsComment.builder()
                .id(expected.getId())
                .deleted(true)
                .modifiedDate(expected.getModifiedDate())
                .build();
        assertEquals(expected, mapper.convert(ecoNewsComment));
    }

    @Test
    void givenModifiedDateEqualsCreatedDate_whenConvert_thenDtoStatusIsOriginal() {
        EcoNewsCommentDto expected = ModelUtils.getEcoNewsCommentDto();
        EcoNewsComment ecoNewsComment = EcoNewsComment.builder()
                .id(expected.getId())
                .text(expected.getText())
                .deleted(false)
                .modifiedDate(expected.getModifiedDate())
                .createdDate(expected.getModifiedDate())
                .currentUserLiked(expected.isCurrentUserLiked())
                .user(ModelUtils.getUser())
                .usersLiked(Collections.emptySet())
                .build();
        assertEquals(expected, mapper.convert(ecoNewsComment));
    }

    @Test
    void givenEcoNewsComment_whenConvert_thenDtoStatusIsEdited() {
        EcoNewsCommentDto expected = ModelUtils.getEcoNewsCommentDtoStatusEdited();
        EcoNewsComment ecoNewsComment = EcoNewsComment.builder()
                .id(expected.getId())
                .text(expected.getText())
                .deleted(false)
                .modifiedDate(expected.getModifiedDate())
                .createdDate(expected.getModifiedDate().minusDays(6))
                .currentUserLiked(expected.isCurrentUserLiked())
                .user(ModelUtils.getUser())
                .usersLiked(Collections.emptySet())
                .build();
        assertEquals(expected, mapper.convert(ecoNewsComment));
    }
}

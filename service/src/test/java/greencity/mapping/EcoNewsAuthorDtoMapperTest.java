package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.dto.user.EcoNewsAuthorDto;
import greencity.entity.CustomShoppingListItem;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EcoNewsAuthorDtoMapperTest {
    @InjectMocks
    EcoNewsAuthorDtoMapper mapper;
    public EcoNewsAuthorDto convert(User author) {
        return new EcoNewsAuthorDto(author.getId(), author.getName());
    }
    @Test
    void convert() {
        EcoNewsAuthorDto expected = ModelUtils.getEcoNewsAuthorDto();
        User user = User.builder()
                .id(expected.getId())
                .name(expected.getName())
                .build();
        assertEquals(expected, mapper.convert(user));
    }
}

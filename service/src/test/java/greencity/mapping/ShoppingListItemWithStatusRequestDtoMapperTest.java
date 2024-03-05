package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.ShoppingListItemWithStatusRequestDto;
import greencity.entity.UserShoppingListItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static greencity.enums.ShoppingListItemStatus.DONE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShoppingListItemWithStatusRequestDtoMapperTest {
    @InjectMocks
    ShoppingListItemWithStatusRequestDtoMapper mapper;
    @Test
    void convert(){
        UserShoppingListItem expected = ModelUtils.getUserShoppingListItem();
        ShoppingListItemWithStatusRequestDto itemDto = ShoppingListItemWithStatusRequestDto.builder()
                .id(1L)
                .status(DONE)
                .build();

        UserShoppingListItem actual = mapper.convert(itemDto);

        assertEquals(expected.getShoppingListItem().getId(), actual.getShoppingListItem().getId());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}

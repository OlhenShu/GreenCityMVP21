package greencity.mapping;


import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.ShoppingListItemRequestDto;
import greencity.entity.UserShoppingListItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShoppingListItemRequestDtoMapperTest {
    @InjectMocks
    ShoppingListItemRequestDtoMapper mapper;
    @Test
    void convert() {
        UserShoppingListItem expected = ModelUtils.getUserShoppingListItem();
        ShoppingListItemRequestDto shoppingListItemRequestDto = ShoppingListItemRequestDto.builder()
                .id(1L)
                .build();

        UserShoppingListItem actual = mapper.convert(shoppingListItemRequestDto);

        assertEquals(expected.getShoppingListItem().getId(), actual.getShoppingListItem().getId());
    }
}

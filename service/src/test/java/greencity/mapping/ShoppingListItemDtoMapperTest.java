package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.entity.ShoppingListItem;
import greencity.entity.localization.ShoppingListItemTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShoppingListItemDtoMapperTest {
    @InjectMocks
    ShoppingListItemDtoMapper mapper;

    @Test
    void convert() {
        ShoppingListItemDto expected = ModelUtils.getShoppingListItemDto();
        ShoppingListItemTranslation shoppingListItemTranslation = ShoppingListItemTranslation.builder()
                .shoppingListItem(ShoppingListItem.builder().id(1L).build())
                .content("text")
                .build();

        ShoppingListItemDto actual = mapper.convert(shoppingListItemTranslation);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}

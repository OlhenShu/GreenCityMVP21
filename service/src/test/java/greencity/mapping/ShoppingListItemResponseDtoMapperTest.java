package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.ShoppingListItemResponseDto;
import greencity.entity.ShoppingListItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShoppingListItemResponseDtoMapperTest {
    @InjectMocks
    ShoppingListItemResponseDtoMapper mapper;
    @Test
    void convert(){
        ShoppingListItemResponseDto expected = ModelUtils.getShoppingListItemResponseDto();
        ShoppingListItem shoppingListItem = ShoppingListItem.builder()
                .id(1L)
                .translations(ModelUtils.getShoppingListItemTranslationsShorten())
                .build();

        ShoppingListItemResponseDto actual = mapper.convert(shoppingListItem);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTranslations().getFirst().getId(), actual.getTranslations().getFirst().getId());
    }
}

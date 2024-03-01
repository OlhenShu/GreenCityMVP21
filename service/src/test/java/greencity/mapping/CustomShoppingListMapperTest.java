package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.entity.CustomShoppingListItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomShoppingListMapperTest {
    @InjectMocks
    CustomShoppingListMapper mapper;
    @Test
    void convert() {
        CustomShoppingListItem expected = ModelUtils.getCustomShoppingListItem();
        CustomShoppingListItemResponseDto customShoppingListItemResponseDto = CustomShoppingListItemResponseDto.builder()
                .id(expected.getId())
                .text(expected.getText())
                .status(expected.getStatus())
                .build();
        assertEquals(expected, mapper.convert(customShoppingListItemResponseDto));
    }

    @Test
    void mapAllToList() {
        List<CustomShoppingListItem> expected = ModelUtils.getCustomShoppingListItemList();
        List<CustomShoppingListItemResponseDto> customShoppingListItemResponseDtoList = new ArrayList<>();
        customShoppingListItemResponseDtoList.add(CustomShoppingListItemResponseDto.builder()
                .id(expected.getFirst().getId())
                .text(expected.getFirst().getText())
                .status(expected.getFirst().getStatus())
                .build());
        assertEquals(expected, mapper.mapAllToList(customShoppingListItemResponseDtoList));
    }
}

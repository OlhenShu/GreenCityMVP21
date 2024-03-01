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
public class CustomShoppingListResponseDtoMapperTest {
    @InjectMocks
    CustomShoppingListResponseDtoMapper mapper;
    @Test
    void convert() {
        CustomShoppingListItemResponseDto expected = ModelUtils.getCustomShoppingListItemResponseDtoWithStatusInProgress();
        CustomShoppingListItem customShoppingListItem = CustomShoppingListItem.builder()
                .id(expected.getId())
                .text(expected.getText())
                .status(expected.getStatus())
                .build();
        assertEquals(expected, mapper.convert(customShoppingListItem));
    }

    @Test
    void mapAllToList() {
        List<CustomShoppingListItemResponseDto> expected = ModelUtils.getCustomShoppingListItemResponseDtoWithStatusInProgressList();
        List<CustomShoppingListItem> customShoppingListItemList = new ArrayList<>();
        customShoppingListItemList.add(CustomShoppingListItem.builder()
                .id(expected.getFirst().getId())
                .text(expected.getFirst().getText())
                .status(expected.getFirst().getStatus())
                .build());
        assertEquals(expected, mapper.mapAllToList(customShoppingListItemList));
    }
}

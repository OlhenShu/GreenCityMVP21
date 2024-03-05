package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.user.UserFilterDtoRequest;
import greencity.entity.Filter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FilterDtoRequestMapperTest {
    @InjectMocks
    FilterDtoRequestMapper mapper;

    @Test
    void convert(){
        Filter expected = ModelUtils.getFilter();
        UserFilterDtoRequest shoppingListItem = UserFilterDtoRequest.builder()
                .searchCriteria("Test")
                .userRole("ADMIN")
                .userStatus("ACTIVATED")
                .name("Test")
                .build();

        Filter actual = mapper.convert(shoppingListItem);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getValues(), actual.getValues());
    }
}

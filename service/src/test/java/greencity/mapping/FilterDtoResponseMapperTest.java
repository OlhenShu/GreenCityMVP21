package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.user.UserFilterDtoResponse;
import greencity.entity.Filter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FilterDtoResponseMapperTest {
    @InjectMocks
    FilterDtoResponseMapper mapper;

    @Test
    void convert(){
        UserFilterDtoResponse expected = ModelUtils.getUserFilterDtoResponse();
        Filter filter = Filter.builder()
                .id(1L)
                .name("Test")
                .values("Test;ADMIN;ACTIVATED")
                .build();

        UserFilterDtoResponse actual = mapper.convert(filter);

        assertEquals(expected, actual);
    }
}

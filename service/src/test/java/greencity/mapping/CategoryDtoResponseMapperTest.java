package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.category.CategoryDtoResponse;
import greencity.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryDtoResponseMapperTest {
    @InjectMocks
    CategoryDtoResponseMapper mapper;
    @Test
    void convert() {
        CategoryDtoResponse expected = ModelUtils.getCategoryDtoResponse();
        Category category = Category.builder()
                .id(expected.getId())
                .name(expected.getName())
                .build();
        assertEquals(expected, mapper.convert(category));
    }
}

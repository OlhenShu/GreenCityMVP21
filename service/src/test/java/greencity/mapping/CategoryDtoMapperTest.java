package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.category.CategoryDto;
import greencity.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryDtoMapperTest {
    @InjectMocks
    CategoryDtoMapper mapper;
    @Test
    void convert() {
        Category expected = ModelUtils.getCategoryOnlyWithName();
        CategoryDto categoryDto = CategoryDto.builder()
                .name(expected.getName())
                .build();
        assertEquals(expected, mapper.convert(categoryDto));
    }
}

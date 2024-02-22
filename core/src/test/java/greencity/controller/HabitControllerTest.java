package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.dto.habit.AddCustomHabitDtoRequest;
import greencity.dto.user.UserVO;
import greencity.service.HabitService;
import greencity.service.TagsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HabitControllerTest {
    private static final String habitLink = "/habit";
    private MockMvc mockMvc;
    @InjectMocks
    private HabitController habitController;
    @Mock
    private HabitService habitService;
    @Mock
    private TagsService tagsService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(habitController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getHabitById() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/{id}", 1))
                .andExpect(status().isOk());

        verify(habitService).getByIdAndLanguageCode(1L, "en");
    }

    @Test
    void getAllTest() throws Exception {
        int pageNumber = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UserVO userVO = new UserVO();

        mockMvc.perform(get(STR."\{habitLink}?page=1&size=2"))
                .andExpect(status().isOk());

        verify(habitService).getAllHabitsByLanguageCode(userVO, pageable, "en");
    }

    @Test
    void getShoppingListItemsTest() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/{id}/shopping-list", 1))
                .andExpect(status().isOk());

        verify(habitService).getShoppingListForHabit(1L, "en");
    }

    @Test
    void getAllByTagsAndLanguageCodeTest() throws Exception {
        int pageNumber = 5;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<String> tags = Collections.singletonList("eco");

        mockMvc.perform(get(STR."\{habitLink}/tags/search?tags=eco&page=5"))
                .andExpect(status().isOk());

        verify(habitService).getAllByTagsAndLanguageCode(pageable, tags, "en");
    }

    @Test
    void getAllByDifferentParametersTest() throws Exception {
        int pageNumber = 5;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UserVO userVO = new UserVO();
        Optional<List<String>> tags = Optional.of(Collections.singletonList("eco"));

        mockMvc.perform(get(STR."\{habitLink}/search?tags=eco&isCustomHabit=false&complexities=7&page=5"))
                .andExpect(status().isOk());

        verify(habitService).getAllByDifferentParameters(userVO, pageable, tags,
                Optional.of(false), Optional.of(Collections.singletonList(7)), "en");
    }

    @Test
    void findAllHabitsTagsTest() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/tags?locale=en"))
                .andExpect(status().isOk());

        verify(tagsService).findAllHabitsTags("en");
    }

    @Test
    void addCustomHabitTest() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("Olivia.Johnson@gmail.com");
        String json = """
                        {
                        "complexity": 1,
                         "defaultDuration": 1,
                         "habitTranslations": null,
                        "customShoppingListItemDto": null,
                         "image": null,
                         "tagIds": null
                         }
                """;
        MockMultipartFile jsonFile =
                new MockMultipartFile("request", "", "application/json", json.getBytes());

        this.mockMvc.perform(multipart(STR."\{habitLink}/custom")
                        .file(jsonFile)
                        .principal(principal))
                .andExpect(status().isCreated());

        ObjectMapper mapper = new ObjectMapper();
        AddCustomHabitDtoRequest addCustomHabitDtoRequest = mapper.readValue(json, AddCustomHabitDtoRequest.class);

        verify(habitService)
                .addCustomHabit(eq(addCustomHabitDtoRequest), isNull(), eq("Olivia.Johnson@gmail.com"));
    }

    @Test
    void getFriendsAssignedToHabitProfilePicturesTest() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/{habitId}/friends/profile-pictures", 1))
                .andExpect(status().isOk());

        verify(habitService).getFriendsAssignedToHabitProfilePictures(1L, null);
    }
}

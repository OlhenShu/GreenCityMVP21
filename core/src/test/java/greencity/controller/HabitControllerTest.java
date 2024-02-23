package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.dto.habit.AddCustomHabitDtoRequest;
import greencity.dto.habit.HabitDto;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    //@TODO Ask about mockMvcTest and about returning HabitDto which dont have toString annotation

    @Test
    void given_validHabitIdAndLocale_when_getHabitById_then_returnHabitDto() throws Exception {
        HabitDto mockHabitDto = HabitDto.builder()
                .id(1L)
                .image("png")
                .build();

        when(habitService.getByIdAndLanguageCode(1L, "en")).thenReturn(mockHabitDto);

        mockMvc.perform(get(STR."\{habitLink}/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(TestResources.getFileContent("controller/habitController/expected-habit-dto-list.json")));

        verify(habitService).getByIdAndLanguageCode(1L, "en");
    }

    @Test
    void given_userVOAndLocaleAndPageable_when_getAll_then_returnPageableDto() throws Exception {
        int pageNumber = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UserVO userVO = new UserVO();

        mockMvc.perform(get(STR."\{habitLink}?page=1&size=2"))
                .andExpect(status().isOk());

        verify(habitService).getAllHabitsByLanguageCode(userVO, pageable, "en");
    }

    @Test
    void given_validHabitIdAndLocale_when_getShoppingListItems_then_returnShoppingListItems() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/{id}/shopping-list", 1))
                .andExpect(status().isOk());

        verify(habitService).getShoppingListForHabit(1L, "en");
    }

    @Test
    void given_tagsAndLocaleAndPageable_when_getAllByTagsAndLanguageCode_then_returnPageableDto() throws Exception {
        int pageNumber = 5;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<String> tags = Collections.singletonList("eco");

        mockMvc.perform(get(STR."\{habitLink}/tags/search?tags=eco&page=5"))
                .andExpect(status().isOk());

        verify(habitService).getAllByTagsAndLanguageCode(pageable, tags, "en");
    }

    @Test
    void given_userVOAndLocaleAndTagsAndCustomHabitAndComplexitiesAndPageable_when_getAllByDifferentParameters_then_returnPageableDto() throws Exception {
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
    void given_validLocale_when_findAllHabitsTags_then_returnListOfTags() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/tags?locale=en"))
                .andExpect(status().isOk());

        verify(tagsService).findAllHabitsTags("en");
    }

    @Test
    void given_validRequestAndImageAndPrincipal_when_addCustomHabit_then_returnAddCustomHabitDtoResponse() throws Exception {
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
    void given_validHabitIdAndUserVO_when_getFriendsAssignedToHabitProfilePictures_then_returnListOfProfilePictures() throws Exception {
        mockMvc.perform(get(STR."\{habitLink}/{habitId}/friends/profile-pictures", 1))
                .andExpect(status().isOk());

        verify(habitService).getFriendsAssignedToHabitProfilePictures(1L, null);
    }

    static class TestResources {
        static String getFileContent(String path) throws URISyntaxException, IOException {
            URL resource = TestResources.class.getClassLoader().getResource(path);
            return new String(Files.readAllBytes(Path.of(Objects.requireNonNull(resource).toURI())));
        }
    }
}

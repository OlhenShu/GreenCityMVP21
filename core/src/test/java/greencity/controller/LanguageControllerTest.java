package greencity.controller;

import greencity.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class LanguageControllerTest {

    @InjectMocks
    private LanguageController languageController;

    @Mock
    private LanguageService languageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllLanguageCodesTest() {

        List<String> expected = Arrays.asList("en", "ua");
        when(languageService.findAllLanguageCodes()).thenReturn(expected);


        ResponseEntity<List<String>> result = languageController.getAllLanguageCodes();


        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expected, result.getBody());
    }
}

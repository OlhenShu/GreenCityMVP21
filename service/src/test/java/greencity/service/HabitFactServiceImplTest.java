package greencity.service;

import greencity.dto.PageableDto;
import greencity.dto.language.LanguageTranslationDTO;
import greencity.entity.HabitFactTranslation;
import greencity.exception.exceptions.BadRequestException;
import greencity.repository.HabitFactRepo;
import greencity.repository.HabitFactTranslationRepo;
import greencity.repository.HabitRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HabitFactServiceImplTest {

    private HabitFactServiceImpl habitFactService;

    @Mock
    private HabitFactRepo habitFactRepo;

    @Mock
    private HabitFactTranslationRepo habitFactTranslationRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HabitRepo habitRepo;

    @BeforeEach
    public void init() {
        habitFactService = new HabitFactServiceImpl(habitFactRepo, habitFactTranslationRepo, modelMapper, habitRepo);
    }

    @Test
    public void getAllHabitFacts_whenSuccessFlow_thenReturnExpectedResult() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<HabitFactTranslation> habitFactTranslationPage = Mockito.mock(Page.class);
        LanguageTranslationDTO languageTranslationDTO = Mockito.mock(LanguageTranslationDTO.class);
        HabitFactTranslation habitFactTranslation = Mockito.mock(HabitFactTranslation.class);


        Integer totalPages = 5;
        Long totalElements = 10L;
        String languageCode = "en";

        when(habitFactTranslationRepo.findAllByLanguageCode(pageable, languageCode)).thenReturn(
                habitFactTranslationPage);
        when(habitFactTranslationPage.getTotalPages()).thenReturn(totalPages);
        when(pageable.getPageNumber()).thenReturn(totalPages - 1);
        when(modelMapper.map(habitFactTranslation, LanguageTranslationDTO.class)).thenReturn(languageTranslationDTO);
        when(habitFactTranslationPage.stream()).thenReturn(Stream.of(habitFactTranslation));
        when(habitFactTranslationPage.getTotalElements()).thenReturn(totalElements);
        when(habitFactTranslationPage.getPageable()).thenReturn(pageable);

        PageableDto<LanguageTranslationDTO> result = habitFactService.getAllHabitFacts(pageable, languageCode);

        assertEquals(totalPages, result.getTotalPages());
        assertEquals(totalElements, result.getTotalElements());
        assertEquals(totalPages - 1, result.getCurrentPage());
        assertEquals(List.of(languageTranslationDTO), result.getPage());

        verify(habitFactTranslationRepo).findAllByLanguageCode(pageable, languageCode);
        verify(habitFactTranslationPage, times(2)).getTotalPages();
        verify(pageable, times(2)).getPageNumber();
        verify(modelMapper).map(habitFactTranslation, LanguageTranslationDTO.class);
        verify(habitFactTranslationPage).stream();
        verify(habitFactTranslationPage).getTotalElements();
        verify(habitFactTranslationPage).getPageable();
    }

    @Test
    public void getAllHabitFacts_whenActualPagesNumberLessThanRequested_throwsBadRequestException() {
        Pageable pageable = Mockito.mock(Pageable.class);
        String languageCode = "en";

        Integer requestedPage = 5;

        Page<HabitFactTranslation> habitFactTranslationPage = Mockito.mock(Page.class);

        when(habitFactTranslationRepo.findAllByLanguageCode(pageable, languageCode)).thenReturn(
                habitFactTranslationPage);
        when(habitFactTranslationPage.getTotalPages()).thenReturn(requestedPage - 1);
        when(pageable.getPageNumber()).thenReturn(requestedPage);

        assertThrows(BadRequestException.class, () -> habitFactService.getAllHabitFacts(pageable, languageCode));

        verify(habitFactTranslationRepo).findAllByLanguageCode(pageable, languageCode);
        verifyNoMoreInteractions(habitFactTranslationPage);
        verifyNoInteractions(modelMapper);
        verifyNoInteractions(habitFactRepo);
        verifyNoInteractions(habitRepo);
    }
}
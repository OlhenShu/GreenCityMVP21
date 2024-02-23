package greencity.service;

import greencity.dto.user.UserVO;
import greencity.entity.*;
import greencity.enums.HabitAssignStatus;
import greencity.exception.exceptions.NotFoundException;
import greencity.exception.exceptions.UserAlreadyHasHabitAssignedException;
import greencity.exception.exceptions.UserHasNoPermissionToAccessException;
import greencity.repository.*;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HabitAssignServiceImplTest {
    @Mock
    private HabitAssignRepo habitAssignRepo;

    @Mock
    private HabitRepo habitRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ShoppingListItemRepo shoppingListItemRepo;

    @Mock
    private UserShoppingListItemRepo userShoppingListItemRepo;

    @Mock
    private CustomShoppingListItemRepo customShoppingListItemRepo;

    @Mock
    private ShoppingListItemTranslationRepo shoppingListItemTranslationRepo;

    @Mock
    private HabitStatusCalendarRepo habitStatusCalendarRepo;

    @Mock
    private ShoppingListItemService shoppingListItemService;

    @Mock
    private CustomShoppingListItemService customShoppingListItemService;

    @Mock
    private HabitStatisticService habitStatisticService;

    @Mock
    private HabitStatusCalendarService habitStatusCalendarService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    HabitAssignServiceImpl habitAssignService;


    @Test
    void getByHabitAssignIdAndUserId_habitAssignIdNotExists_throwException() {
        Long nonExistedHabitAssignId = 1L;
        Mockito.when(habitAssignRepo.findById(nonExistedHabitAssignId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> habitAssignService.getByHabitAssignIdAndUserId(
                nonExistedHabitAssignId, 1L, "Java"));
    }

    @Test
    void getByHabitAssignIdAndUserId_habitAssignIdExists_getHabitAssign() {
        Long existedHabitAssignId = 1L;
        User user = new User();
        user.setId(2L);

        Habit habit = new Habit();
        habit.setId(3L);

        HabitAssign habitAssign = new HabitAssign();
        habitAssign.setUser(user);
        habitAssign.setHabit(habit);
        habitAssign.setId(existedHabitAssignId);

        Optional<HabitAssign> optionalHabitAssign = Optional.of(habitAssign);
        Mockito.when(habitAssignRepo.findById(existedHabitAssignId)).thenReturn(optionalHabitAssign);


        Assertions.assertDoesNotThrow(() -> habitAssignService.getByHabitAssignIdAndUserId(
                existedHabitAssignId, 2L, "Java"));
    }

    @Test
    void getByHabitAssignIdAndUserId_userIdNotEquals_throwException() {
        Long unexpectedUserId = 1L;
        Long existedHabitAssignId = 2L;
        User user = new User();
        user.setId(unexpectedUserId);
        HabitAssign habitAssign = new HabitAssign();
        habitAssign.setId(existedHabitAssignId);
        habitAssign.setUser(user);

        Mockito.when(habitAssignRepo.findById(existedHabitAssignId)).thenReturn(Optional.of(habitAssign));

        assertThrows(UserHasNoPermissionToAccessException.class,
                () -> habitAssignService.getByHabitAssignIdAndUserId(
                existedHabitAssignId, 3L, "Java"
        ));
    }


    @Test
    void getByHabitAssignIdAndUserId_habitTranslationDoesNotEqualsLanguage_throwException(){
        HabitAssign habitAssign = new HabitAssign();
        habitAssign.setId(1L);

        User user = new User();
        user.setId(2L);
        habitAssign.setUser(user);

        Language language = new Language();
        language.setCode("Java");

        List<HabitTranslation> habitTranslations = new ArrayList<>();
        habitTranslations.add(HabitTranslation.builder().language(language).build());

        habitAssign.setHabit(Habit.builder().habitTranslations(habitTranslations).build());
        Mockito.when(habitAssignRepo.findById(1L)).thenReturn(Optional.of(habitAssign));
        assertThrows(NotFoundException.class,() -> habitAssignService
                .getByHabitAssignIdAndUserId(1L,2L,"JS"));

    }


    @Test
    void assignDefaultHabitForUser_checkStatusInProgressExists_throwException(){
        Habit habit = new Habit();
        habit.setId(1L);
        UserVO userVO = new UserVO();
        userVO.setId(2L);
        HabitAssign habitAssign = HabitAssign.builder().habit(habit).build();
        habitAssign.setId(10L);
        habitAssign.setStatus(HabitAssignStatus.INPROGRESS);

        List<HabitAssign> habitAssigns = new ArrayList<>();
        habitAssigns.add(habitAssign);

        Mockito.when(habitAssignRepo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(UserAlreadyHasHabitAssignedException.class,() -> habitAssignService
                .assignDefaultHabitForUser(1L,userVO));
    }

    @Test
    void assignDefaultHabitForUser_findHabitByNotExistsId_throwException(){
        UserVO userVO = new UserVO();
        Habit habit = new Habit();
        habit.setId(1L);
        HabitAssign habitAssign = new HabitAssign();
        habitAssign.setHabit(habit);

        Mockito.when(habitRepo.findById(1L)).thenReturn(Optional.of(habit));

        assertThrows(NotFoundException.class,() -> habitAssignService.assignDefaultHabitForUser(2L,userVO));

    }






}

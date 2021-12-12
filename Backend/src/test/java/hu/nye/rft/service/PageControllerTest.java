package hu.nye.rft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.List;

import hu.nye.rft.model.Subject;
import hu.nye.rft.service.PageController;
import hu.nye.rft.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;

public class PageControllerTest {

    private static final String ADD_SUBJECTS = "addsubjects";
    private static final String LOGIN = "login";
    private static final String TAKE_SUBJECT = "takesubject";

    private static final String SUBJECT_CODE = "BPI2119";

    private static final String USER_NAME = "userName";
    private static final String INDEX_CSS = "index.css";

    private static final Subject SUBJECT = new Subject("Rendszerközeli Programozás", "BPI2119", "Discord", "19:00", false);


    private PageController underTest;

    @Test
    public void testGetSubjectsPageShouldReturnWithAddSubjectsString() {
       // given
       underTest = new PageController(null);

       // when
       String result = underTest.getSubjectsPage(null, null);

       // then
       assertEquals(result, ADD_SUBJECTS);
   }

    @Test
    public void testLoginPageShouldReturnWithLogin() {
        // given
        underTest = new PageController(null);

        // when
        String result = underTest.loginPage();

        // then
        assertEquals(result, LOGIN);
    }

    @Test
    public void testAddSubjectShouldCallUserServiceSaveSubject() {
        // given
        UserService userService = Mockito.mock(UserService.class);
        underTest = new PageController(userService);

        // when
        underTest.addSubject(SUBJECT);

        // then
        then(userService).should().saveSubject(SUBJECT);
    }

    @Test
    public void testAddSubjectShouldCallUserServiceSaveSubjectAndReturnAddSubjects() {
        // given
        UserService userService = Mockito.mock(UserService.class);
        underTest = new PageController(userService);

        // when
        String result = underTest.addSubject(SUBJECT, null);

        // then
        assertEquals(result, ADD_SUBJECTS);
        then(userService).should().saveSubject(SUBJECT);
    }

    @Test
    public void testTakeSubjectShouldCallUserServiceTakeSubjectAndReturnWithTakeSubject() {
        // given
        UserService userService = Mockito.mock(UserService.class);
        Model model = Mockito.mock(Model.class);
        underTest = new PageController(userService);
        List<Subject> emptyList = new ArrayList<>();

        // when
        String result = underTest.takeSubject(null, SUBJECT_CODE, model);

        // then
        assertEquals(result, TAKE_SUBJECT);
        then(userService).should().takeSubject(SUBJECT_CODE);
        then(model).should().addAttribute("subjects", emptyList);
        then(model).should().addAttribute("tookSubjects", emptyList);
    }

    @Test
    public void testTakeSubjectsShouldReturnWithTakeSubjectsAndCallUserServiceCreateUser() {
        // given
        UserService userService = Mockito.mock(UserService.class);
        Model model = Mockito.mock(Model.class);
        underTest = new PageController(userService);
        List<Subject> emptyList = new ArrayList<>();

        // when
        String result = underTest.takeSubjects(model, USER_NAME);

        // then
        assertEquals(result, TAKE_SUBJECT);
        then(userService).should().createUser(USER_NAME);
        then(model).should().addAttribute("subjects", emptyList);
        then(model).should().addAttribute("tookSubjects", emptyList);
        then(model).should().addAttribute("userName", USER_NAME);
    }

    @Test
    public void testTakeSubjectsShouldReturnWithTakeSubjectsWhenUserNameIsIndexCss() {
        // given
        UserService userService = Mockito.mock(UserService.class);
        underTest = new PageController(userService);

        // when
        String result = underTest.takeSubjects(null, INDEX_CSS);

        // then
        assertEquals(result, TAKE_SUBJECT);
    }



}

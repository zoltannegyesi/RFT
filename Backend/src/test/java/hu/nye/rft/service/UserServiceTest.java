package hu.nye.rft.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

import java.util.List;

import hu.nye.rft.model.Subject;
import hu.nye.rft.model.User;
import hu.nye.rft.repository.SubjectRepository;
import hu.nye.rft.repository.UserRepository;
import hu.nye.rft.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTest {

    private static final String SUBJECT_1_CODE = "BPI2119";

    private static final String NOT_SUBJECT_CODE = "notSubjectCode";

    private static final Subject SUBJECT_1_FALSE = new Subject("Rendszerközeli Programozás", SUBJECT_1_CODE, "Discord", "19:00", false);
    private static final Subject SUBJECT_2_FALSE = new Subject("RFT", "BPI2118", "Teams", "11:00", false);
    private static final Subject SUBJECT_3_FALSE = new Subject("Analízis", "BPI1111", "D9", "15:00", false);

    private static final Subject SUBJECT_1_TRUE = new Subject("Rendszerközeli Programozás", "BPI2119", "Discord", "19:00", true);
    private static final Subject SUBJECT_2_TRUE = new Subject("RFT", "BPI2118", "Teams", "11:00", true);
    private static final Subject SUBJECT_3_TRUE = new Subject("Analízis", "BPI1111", "D9", "15:00", true);

    private static final String USER_NAME = "username";

    private UserService underTest;


    @Test
    public void testFindAllSubjectShouldReturnAListOfSubjectsWithFalseTook() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);
        List<Subject> subjects = List.of( SUBJECT_1_FALSE, SUBJECT_2_FALSE, SUBJECT_3_FALSE);
        given(subjectRepository.findByTook(false)).willReturn(subjects);

        List<Subject> expectedSubjects = List.of( SUBJECT_1_FALSE, SUBJECT_2_FALSE, SUBJECT_3_FALSE);

        // when
        List<Subject> actual = underTest.findAllSubject();

        // then
        assertEquals(actual, expectedSubjects);
        then(subjectRepository).should().findByTook(false);
    }

    @Test
    public void testCreateUserShouldCreateNewUserFromUserName() {
        // given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        underTest = new UserService(null, userRepository);

        // when
        User result = underTest.createUser(USER_NAME);

        // then
        assertEquals(result.getName(), USER_NAME);
    }

    @Test
    public void testFindTakenSubjectsShouldReturnSubjectsWithTrueTook() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);
        List<Subject> subjects = List.of( SUBJECT_1_TRUE, SUBJECT_2_TRUE, SUBJECT_3_TRUE);
        given(subjectRepository.findByTook(true)).willReturn(subjects);

        List<Subject> expectedSubjects = List.of( SUBJECT_1_TRUE, SUBJECT_2_TRUE, SUBJECT_3_TRUE);

        // when
        List<Subject> actual = underTest.findTakenSubjects();

        // then
        assertEquals(actual, expectedSubjects);
        then(subjectRepository).should().findByTook(true);
    }

    @Test
    public void testTestCodeShouldReturnFalseIfThereIsASubjectWithTheGivenCode() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);
        List<Subject> subjects = List.of( SUBJECT_1_FALSE, SUBJECT_2_FALSE, SUBJECT_3_FALSE);
        given(subjectRepository.findAll()).willReturn(subjects);

        // when
        boolean result = underTest.testCode(SUBJECT_1_CODE);

        // then
        assertFalse(result);
        then(subjectRepository).should().findAll();
    }

    @Test
    public void testTestCodeShouldReturnTrueIfThereIsNotASubjectWithTheGivenCode() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);
        List<Subject> subjects = List.of( SUBJECT_1_FALSE, SUBJECT_2_FALSE, SUBJECT_3_FALSE);
        given(subjectRepository.findAll()).willReturn(subjects);

        // when
        boolean result = underTest.testCode(NOT_SUBJECT_CODE);

        // then
        assertTrue(result);
        then(subjectRepository).should().findAll();

    }

    @Test
    public void testTakeSubjectShouldSetTheSubjectsTookToTrue() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);
        UserService userServiceSpy = Mockito.spy(underTest);
        Mockito.doReturn(false).when(userServiceSpy).testCode(Mockito.any());
        given(subjectRepository.findByCode(SUBJECT_1_CODE)).willReturn(SUBJECT_1_FALSE);

        // when
        Subject result = userServiceSpy.takeSubject(SUBJECT_1_CODE);

        // then
        assertEquals(result.getCode(), SUBJECT_1_TRUE.getCode());
        then(userServiceSpy).should().testCode(SUBJECT_1_CODE);
        then(subjectRepository).should().findByCode(SUBJECT_1_CODE);
    }

    @Test
    public void testTakeSubjectShouldReturnWithNull() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);
        UserService userServiceSpy = Mockito.spy(underTest);
        Mockito.doReturn(true).when(userServiceSpy).testCode(Mockito.any());

        // when
        Subject result = userServiceSpy.takeSubject(NOT_SUBJECT_CODE);

        // then
        assertNull(result);
        then(userServiceSpy).should().testCode(NOT_SUBJECT_CODE);
    }



    @Test
    public void testSaveSubjectShouldReturnFalseAndCallSaveFromSubjectRepository() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);

        // when
        boolean result = underTest.saveSubject(SUBJECT_1_TRUE);

        // then
        assertFalse(result);
        then(subjectRepository).should().save(SUBJECT_1_TRUE);
    }

    @Test
    public void testCreateSubjectsShouldCallSubjectRepositorySave3Times() {
        // given
        SubjectRepository subjectRepository = Mockito.mock(SubjectRepository.class);
        underTest = new UserService(subjectRepository, null);

        // when
        underTest.createSubjects(SUBJECT_1_FALSE, SUBJECT_2_FALSE, SUBJECT_3_FALSE);

        // then
        then(subjectRepository).should().save(SUBJECT_1_FALSE);
        then(subjectRepository).should().save(SUBJECT_2_FALSE);
        then(subjectRepository).should().save(SUBJECT_3_FALSE);

    }
}



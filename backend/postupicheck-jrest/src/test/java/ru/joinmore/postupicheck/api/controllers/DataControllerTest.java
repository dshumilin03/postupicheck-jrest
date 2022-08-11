package ru.joinmore.postupicheck.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.joinmore.postupicheck.api.services.CourseAdmissionService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataControllerTest {

    private DataController testInstance;
    @Mock
    CourseAdmissionService courseAdmissionService;

    @BeforeEach
    void setUp() {testInstance = new DataController(courseAdmissionService);}

    @Test
    void shouldCallServiceToUpdateCurPassingScores() {
        // when
        testInstance.updatePassingScores();

        // then
        verify(courseAdmissionService).updateCourseCurPassingScore();
    }
}
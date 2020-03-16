package com.schoology.api.service;

import com.schoology.api.domain.Course;
import com.schoology.api.exceptions.CourseNotFoundException;
import com.schoology.api.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseServiceTest {

    CourseService courseService;
    List<Course> testCourses = new ArrayList<>();

    @Mock
    CourseRepository courseRepository;

    @BeforeAll
    public void setUp(){
        testCourses.add(Course.builder().id("123").name("Test course").description("A course to be used as test").build());
        testCourses.add(Course.builder().id("456").name("Another test").description("Another course to be used as test").build());
        MockitoAnnotations.initMocks(this);
        courseService = new CourseService(courseRepository);
    }

    @Test
    public void mustSaveValidCourse() {
        when(courseRepository.save(testCourses.get(0))).thenReturn(testCourses.get(0));
        Course savedCourse = courseService.save(testCourses.get(0));
        assertEquals(savedCourse, testCourses.get(0));
    }

    @Test
    public void mustFindAllCoursesTest() {
        when(courseRepository.findAll()).thenReturn(testCourses);
        List<Course> returnedCourses = courseService.findAll();
        assertEquals(returnedCourses, testCourses);
    }

    @Test
    public void mustUpdateValidCourse(){
        when(courseRepository.save(testCourses.get(1))).thenReturn(testCourses.get(1));
        when(courseRepository.findById("456")).thenReturn(Optional.of(testCourses.get(1)));
        Course savedCourse = courseService.update("456", testCourses.get(1));
        assertEquals(savedCourse, testCourses.get(1));
    }

    @Test
    public void mustReturnCourseNotFoundTHenUpdateInvalidCourseId(){
        doThrow(new CourseNotFoundException("Course with Id 123 not found")).when(courseRepository).findById("123");
        assertThrows(CourseNotFoundException.class, () -> { courseService.update("123", testCourses.get(0)); });
    }

    @Test
    public void mustFIndCourseByNameTest(){
        when(courseRepository.findByName("Test course")).thenReturn(testCourses.get(0));
        Course returnedCourse = courseService.findByName("Test course");
        assertEquals(returnedCourse, testCourses.get(0));
    }

    @Test
    public void mustDeleteCourseByIdTest(){
        doAnswer(invocation -> {
            return null;
        }).when(courseRepository).deleteById("123");
        courseService.deleteById("123");
    }

}
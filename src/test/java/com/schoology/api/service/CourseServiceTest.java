package com.schoology.api.service;

import com.schoology.api.domain.Course;
import com.schoology.api.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    void save() {
        when(courseRepository.save(testCourses.get(0))).thenReturn(testCourses.get(0));
        Course savedCourse = courseService.save(testCourses.get(0));
        assertEquals(savedCourse, testCourses.get(0));
    }

    @Test
    void findAll() {
        when(courseRepository.findAll()).thenReturn(testCourses);
        List<Course> returnedCourses = courseService.findAll();
        assertEquals(returnedCourses, testCourses);
    }

}
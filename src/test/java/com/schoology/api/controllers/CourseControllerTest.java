package com.schoology.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoology.api.domain.Course;
import com.schoology.api.service.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseControllerTest {

    @Mock
    CourseService courseService;

    @Mock
    Model model;

    CourseController courseController;
    List<Course> testCourses = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testCourses.add(Course.builder().id("123").name("Test course").description("A course to be used as test").active(true).build());
        testCourses.add(Course.builder().id("456").name("Another test").description("Another course to be used as test").active(false).build());
        courseController = new CourseController(courseService);
    }

    @Test
    public void mustPostNewValidCourseTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        when(courseService.save(testCourses.get(0))).thenReturn(testCourses.get(0));
        mockMvc.perform(post("/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(convertObjectToJsonBytes(testCourses.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("123")))
                .andExpect(jsonPath("$.name", is("Test course")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.description", is("A course to be used as test")));
    }

    @Test
    public void mustReturnBadRequestWhenPostWithoutContentTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        mockMvc.perform(post("/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void mustUpdateValueWhenPutTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        when(courseService.update("123", testCourses.get(0))).thenReturn(testCourses.get(0));
        mockMvc.perform(put("/v1/courses/123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(convertObjectToJsonBytes(testCourses.get(0))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("123")))
                .andExpect(jsonPath("$.name", is("Test course")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.description", is("A course to be used as test")));
    }

    @Test
    public void mustReturnNotAllowedWhenPutWithNoIdTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        mockMvc.perform(put("/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void mustReturnBadRequestWhenPutWithNoBody() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        mockMvc.perform(put("/v1/courses/123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void mustReturnNoContentWhenDeleteTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        mockMvc.perform(delete("/v1/courses/123")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void mustReturnNotAllowedWhenDeleteWithNoIdTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        mockMvc.perform(delete("/v1/courses/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void mustFindCourseByNameTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        when(courseService.findByName("Test course")).thenReturn(testCourses.get(0));
        mockMvc.perform(get("/v1/courses/search?name=Test course").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("123")))
                .andExpect(jsonPath("$.name", is("Test course")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.description", is("A course to be used as test")));
    }

    @Test
    public void mustReturnOkThenNoFilterIsInPlaceTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        mockMvc.perform(get("/v1/courses/search").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void mustFindAllCourses() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        when(courseService.findAll()).thenReturn(testCourses);
        mockMvc.perform(get("/v1/courses").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("123")))
                .andExpect(jsonPath("$[0].name", is("Test course")))
                .andExpect(jsonPath("$[0].active", is(true)))
                .andExpect(jsonPath("$[0].description", is("A course to be used as test")));
    }

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}

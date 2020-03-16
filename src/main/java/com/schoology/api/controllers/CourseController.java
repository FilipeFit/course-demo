package com.schoology.api.controllers;

import com.schoology.api.domain.Course;
import com.schoology.api.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course save(@RequestBody Course course) {
        return courseService.save(course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String id){
        courseService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable String id, @RequestBody Course course){
        return courseService.update(id, course);
    }

    @GetMapping
    public List<Course> getCourses(){
        return courseService.findAll();
    }

    @GetMapping("/search")
    public Course getCourseByName(@RequestParam(required = false) String name){
        return courseService.findByName(name);
    }
}

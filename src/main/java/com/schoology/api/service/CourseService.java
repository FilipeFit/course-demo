package com.schoology.api.service;

import com.schoology.api.domain.Course;
import com.schoology.api.exceptions.CourseNotFoundException;
import com.schoology.api.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course update(String id, Course course) {
        courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(String.format("Course with Id %s not found", id)));
        course.setId(id);
        return courseRepository.save(course);
    }

    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    public void deleteById(String id) {
        courseRepository.deleteById(id);
    }
}

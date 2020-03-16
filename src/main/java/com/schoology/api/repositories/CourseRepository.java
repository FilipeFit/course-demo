package com.schoology.api.repositories;

import com.schoology.api.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {

    Course findByName(String name);
}

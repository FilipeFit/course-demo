package com.schoology.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private Boolean active;
}

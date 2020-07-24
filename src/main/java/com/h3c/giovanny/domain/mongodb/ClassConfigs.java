package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "iotedu_classconfigs")
public class ClassConfigs {
    String teacher;
    String teacherTel;
    String id;
    String scenarioId;
    String schoolId;
    long years;
    int baseGrade;
    String className;
    String classId;
}

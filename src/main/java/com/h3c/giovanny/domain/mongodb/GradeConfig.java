package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "iotedu_gradeconfigs")
@Data
public class GradeConfig {
    @Id
    private String id;
    private List<ConfigList> gradeConf;
    private String scenarioId;
    private String schoolId;
    private int gradeType;
    private int gradeMonth;

}

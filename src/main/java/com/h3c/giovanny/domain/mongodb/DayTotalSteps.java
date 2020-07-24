package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "history_motion_daytotalsteps")
public class DayTotalSteps {
    @Id
    private String id;
    private String scenarioId;
    private String schoolId;
    private Long years;
    private int baseGrade;
    private String classId;
    private String studentId;
    private String recordDate;
    private int value;
    private long updateTime;


}

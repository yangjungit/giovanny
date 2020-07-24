package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author yys1722
 */
@Data
@Document(collection = "iotedu_studentinfos")
public class StudentHealthInfo {
    private String id;
    private String scenarioId;
    private String schoolId;
    private String studentId;
    private String studentName;
    private String classId;
    private String className;
    private Long years;
    private int baseGrade;
    private String INSn;
    private String birthday;
    private String sex;
    private String finalStatus;
    private boolean tempStatus;
    private boolean timeFlag;
    private Long inSchoolTime;
    private Long outSchoolTime;
    private Double restHeartRate;
    private IniInfo INInfo;
    private HealthPropertyMap raise;
    private HealthPropertyMap rope;
    private HealthPropertyMap situp;
    private HealthPropertyMap heartRate;
    private HealthPropertyMap step;
    private HealthPropertyMap temperature;
    private HealthPropertyMap stature;
    private HealthPropertyMap weight;
    private HealthPropertyMap calorie;
    private HealthPropertyMap distance;
    private String terminalType;
    private List householder;
    private List alternateName;


}
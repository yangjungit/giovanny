package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: LiaoJia
 * @Description:
 * @Date: Created in 14:11  2018/7/19
 * @Modified By:
 */
@Data
@Document(collection = "history_health_heartrates")
public class Heartrates {
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

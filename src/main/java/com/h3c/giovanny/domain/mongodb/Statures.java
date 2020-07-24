package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: LiaoJia
 * @Description:
 * @Date: Created in 16:01  2018/7/16
 * @Modified By:
 */

@Data
@Document(collection = "history_health_statures")
public class Statures {
    @Id
    private String id;
    private String scenarioId;
    private String schoolId;
    private Long years;
    private int baseGrade;
    private String classId;
    private String studentId;
    /**
     * false:设备上报；true:手工修改
     */
    private boolean isStatic;
    private String recordDate;
    private long value;
    private long updateTime;
}

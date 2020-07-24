package com.h3c.giovanny.domain.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @className: DayDetailCalorie
 * @description: 卡路里历史表
 * @author: YangJun
 * @date: 2019/8/7 22:20
 * @version: v1.0
 **/
@Data
@Document(collection = "history_motion_daydetailcalories")
@CompoundIndexes(
        {
                @CompoundIndex(name = "scenarioId_1_schoolId_1_studentId_1_updateTime_1", def = "{'scenarioId':1,'schoolId':1,'studentId':1,'updateTime':1}", unique = true, background = true),
                @CompoundIndex(name = "scenarioId_1_schoolId_1_years_1_classId_1_studentId_1_updateTime_1", def = "{'scenarioId':1,'schoolId':1,'years':1,'classId':1,'studentId':1,'updateTime':1}", unique = true, background = true)

        }

)
public class DayDetailCalorie {
    @Id
    private String id;
    private String scenarioId;
    private String schoolId;
    private Long years;
    private Integer baseGrade;
    private String classId;
    private String studentId;
    private int value;
    private String recordDate;
    private long updateTime;
    /**
     * 历史保存时间180天
     */
    @Indexed(expireAfterSeconds = 60)
    private Date ttl;
}

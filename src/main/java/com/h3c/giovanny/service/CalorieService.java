package com.h3c.giovanny.service;

import com.h3c.giovanny.domain.mongodb.DayDetailCalorie;
import com.h3c.giovanny.domain.resultbean.HistoryValue;

import java.util.List;

/**
 * @className: CalorieService
 * @description: 卡路里计算
 * @author: YangJun
 * @date: 2019/8/7 16:57
 * @version: v1.0
 **/
public interface CalorieService {
    List<HistoryValue> calorieCompute(String shopId, String schoolId, String studentId, String startTime, String endTime);

    void updateCalorie(String shopId, String schoolId, String studentId, Integer value, Long updateTime);

    void insertOne(DayDetailCalorie dayDetailCalorie);

    void upsertCalorieRecord(String shopId, String schoolId, String studentId, String recordDate, Integer value);

    void findAllSchoolByStudents();

}

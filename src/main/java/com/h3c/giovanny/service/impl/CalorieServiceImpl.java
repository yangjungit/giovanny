package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.constant.Content;
import com.h3c.giovanny.dao.StudentDao;
import com.h3c.giovanny.domain.mongodb.DayDetailCalorie;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.domain.resultbean.HistoryValue;
import com.h3c.giovanny.service.CalorieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: CalorieServiceImpl
 * @description: 卡路里计算
 * @author: YangJun
 * @date: 2019/8/7 16:57
 * @version: v1.0
 **/
@Service
@Slf4j
public class CalorieServiceImpl implements CalorieService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public List<HistoryValue> calorieCompute(String shopId, String schoolId, String studentId, String startTime, String endTime) {
        //卡路里公式:体重*距离*0.01，距离=k*身高*步数/100;男的k是0.415，女的是0.413
        StudentHealthInfo student = studentDao.findOneStudent(shopId, schoolId, studentId);
        if (student != null) {
            if (Content.TerminalType.F1.equals(student.getTerminalType())) {
                // F1手环，卡路里用数据库里面的值


            } else {
                // 不是F1手环，卡路里用自己的方式计算
                return null;

            }

        }
        return null;
    }

    @Override
    public void updateCalorie(String shopId, String schoolId, String studentId, Integer value, Long updateTime) {
        studentDao.updateCalorie(shopId, schoolId, studentId, value, updateTime);
    }

    @Override
    public void insertOne(DayDetailCalorie dayDetailCalorie) {
        studentDao.insertOne(dayDetailCalorie);
    }

    @Override
    public void upsertCalorieRecord(String shopId, String schoolId, String studentId, String recordDate, Integer value) {
        studentDao.upsertCalorie(shopId, schoolId, studentId, recordDate, value);
    }

    @Override
    public void findAllSchoolByStudents() {
        studentDao.findAllSchoolByStudents();
    }
}

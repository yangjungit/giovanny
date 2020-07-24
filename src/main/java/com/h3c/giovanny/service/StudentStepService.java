package com.h3c.giovanny.service;

import com.h3c.giovanny.domain.mongodb.DayTotalSteps;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.domain.responsebean.ClassHealthResp;
import com.h3c.giovanny.domain.responsebean.GradeHealthInfo;
import com.h3c.giovanny.domain.responsebean.StudentHealthResp;
import com.mongodb.WriteResult;

import java.text.ParseException;
import java.util.List;

/**
 * @className: StudentStepService
 * @description:
 * @author: YangJun
 * @date: 2019/3/6 15:56
 * @version: v1.0
 **/

public interface StudentStepService {

    DayTotalSteps insertDayTotalSteps(DayTotalSteps dayTotalSteps);

    WriteResult deleteDayTotalSteps();

    void updateDayTotalSteps(String[] strArray);

    int findCount();

    List<GradeHealthInfo> findGradeHealthData(String scenarioId, String schoolId, String recordDate);

    List<StudentHealthInfo> findAllStudent(String scenarioId, String schoolId, String recordDate);

    List<GradeHealthInfo> findGradeHealthData2(String scenarioId, String schoolId, String recordDate);

    List<GradeHealthInfo> findGradeHealthData3(String scenarioId, String schoolId, String recordDate);

    List<ClassHealthResp> findClassHealthData(String scenarioId, String schoolId, String grade, String recordDate);

    List<StudentHealthResp> findStudentsHealthData(String scenarioId, String schoolId, String grade, String classId, String recordDate);

    List<StudentHealthResp> findClassStudents(String scenarioId, String schoolId, String grade, String classId);

    List<DayTotalSteps> findStudentStepOfWeek(String scenarioId, String schoolId, String studentId);

    List<DayTotalSteps> findStepHistory(String scenarioId, String schoolId, String studentId, String startDate, String endDate);

    void updateStudentInfo(String scenarioId, String schoolId, String studentId);

    void insertDayTotalStepXxMillion() throws ParseException;

    void updateAll();
}

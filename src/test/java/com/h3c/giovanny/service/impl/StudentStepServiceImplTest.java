package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.constant.Content;
import com.h3c.giovanny.domain.mongodb.DayDetailCalorie;
import com.h3c.giovanny.domain.mongodb.DayTotalSteps;
import com.h3c.giovanny.domain.mongodb.Statures;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.domain.responsebean.ClassHealthResp;
import com.h3c.giovanny.domain.responsebean.GradeHealthInfo;
import com.h3c.giovanny.domain.responsebean.StudentHealthResp;
import com.h3c.giovanny.domain.resultbean.HistoryValue;
import com.h3c.giovanny.service.*;
import com.mongodb.WriteResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @className: StudentStepServiceImplTest
 * @description:
 * @author: YangJun
 * @date: 2019/3/6 15:58
 * @version: v1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentStepServiceImplTest {
    @Autowired
    private StudentStepService studentStepService;
    @Autowired
    private StudentHealthInfoService studentHealthInfoService;
    @Autowired
    private HeightService heightService;
    @Autowired
    private ClassConfigService classConfigService;
    @Autowired
    private GradeConfigService gradeConfigService;
    @Autowired
    private CalorieService calorieService;

    @Test
    public void insertDayTotalStepsTest() {
        for (int i = 0; i < 100; i++) {
            DayTotalSteps dayTotalSteps = new DayTotalSteps();
            dayTotalSteps.setScenarioId("123456");
            dayTotalSteps.setRecordDate("2019/03/13");
            dayTotalSteps.setValue((int) (Math.random() * 100 + 1));
            dayTotalSteps.setStudentId("TEST00" + i);
            dayTotalSteps.setSchoolId("1");
            // 2018年9月1日 1535731200000L  2017年9月1日 1504195200000    2016年9月1日 1472659200000L
            dayTotalSteps.setYears(1472659200000L);
            dayTotalSteps.setBaseGrade(0);
            dayTotalSteps.setClassId(String.valueOf((int) Math.random() * 10 + 1));
            dayTotalSteps.setUpdateTime(new Date().getTime());
            DayTotalSteps result = studentStepService.insertDayTotalSteps(dayTotalSteps);
        }

    }

    @Test
    public void deleteDayTotalStepsTest() {
        WriteResult writeResult = studentStepService.deleteDayTotalSteps();
        System.out.println(writeResult.getN());
        System.out.println(writeResult.wasAcknowledged());
    }

    @Test
    public void updateDayTotalStepsTest() {
        String[] strArray = new String[]{"2016", "2017", "2018"};
        studentStepService.updateDayTotalSteps(strArray);
    }

    @Test
    public void findCount() {
        int count = studentStepService.findCount();
        System.out.println(count);
    }

    @Test
    public void insertStudentHealthInfoTest() {
        studentHealthInfoService.insertStudent();
    }

    @Test
    public void insertStudentHealthInfoTest2() {
        studentHealthInfoService.insertOneStudent();
    }

    @Test
    public void findGradeHealthDataTest1() {
        String scenarioId = "123456";
        String schoolId = "1";
        String recordDate = "2019/03/13";
        List<GradeHealthInfo> gradeHealthInfoList = studentStepService.findGradeHealthData(scenarioId, schoolId, recordDate);
    }

    @Test
    public void findGradeHealthDataTest2() {
        String scenarioId = "123456";
        String schoolId = "1";
        String recordDate = "2019/03/13";
        List<GradeHealthInfo> gradeHealthInfoList = studentStepService.findGradeHealthData2(scenarioId, schoolId, recordDate);
        System.out.println(gradeHealthInfoList);
    }

    @Test
    public void findGradeHealthDataTest3() {
        String scenarioId = "123456";
        String schoolId = "1";
        String recordDate = "2019/05/12";
        List<GradeHealthInfo> gradeHealthInfoList = studentStepService.findGradeHealthData3(scenarioId, schoolId, recordDate);
        System.out.println(gradeHealthInfoList);
    }

    @Test
    public void insertClass() {
        classConfigService.insert();
    }

    @Test
    public void setClassNameTest() {
        studentHealthInfoService.setClassName();
    }


    @Test
    public void findClassHealthDataTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String grade = "2016";
        String recordDate = "2019/03/13";
        List<ClassHealthResp> classHealthList = studentStepService.findClassHealthData(scenarioId, schoolId, grade, recordDate);
        classHealthList.toString();
    }

    @Test
    public void findStudentsHealthDataTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String grade = "2016";
        String classId = "1";
        String recordDate = "2019/03/13";
        List<StudentHealthResp> classHealthList = studentStepService.findStudentsHealthData(scenarioId, schoolId, grade, classId, recordDate);
        classHealthList.toString();
    }

    @Test
    public void findClassStudentsTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String grade = "2016";
        String classId = "1";
        String recordDate = "2019/03/13";
        List<StudentHealthResp> studentHealthRespList = studentStepService.findClassStudents(scenarioId, schoolId, grade, classId);
    }

    @Test
    public void findStudentStepOfWeekTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String studentId = "2018TEST002";
        List<DayTotalSteps> dayTotalStepsList = studentStepService.findStudentStepOfWeek(scenarioId, schoolId, studentId);
    }

    @Test
    public void findStepHistoryTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String studentId = "2018TEST002";
        String startDate = "2019/03/14";
        String endDate = "2019/03/27";
        List<DayTotalSteps> dayTotalStepsList = studentStepService.findStepHistory(scenarioId, schoolId, studentId, startDate, endDate);
    }

    @Test
    public void updateStudentInfoTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String studentId = "2016TESTyj";
        studentStepService.updateStudentInfo(scenarioId, schoolId, studentId);
    }

    @Test
    public void insertDayTotalStepXxMillionTest() throws ParseException {
        studentStepService.insertDayTotalStepXxMillion();
    }

    @Test
    public void insertHeight() throws ParseException {
        heightService.insertHeight();
    }

    @Test
    public void findHeight() {
        List<Statures> height = heightService.findHeight();
//        Statures statures = height.get(0);
        System.out.println(height);
    }

    @Test
    public void findGradeByGradeNameTest() {
        String shopId = "123456";
        String schoolId = "1";
        String gradeName = "初中二年级";
        // grade : yyyy
        String grade = gradeConfigService.findGradeByGradeName(shopId, schoolId, gradeName);
        System.out.println(grade);
    }

    @Test
    public void findStudentHeightRecentTest() {
        String scenarioId = "123456";
        String schoolId = "1";
        String recordDate = "2019/02/01";
        String studentId = "qaz";
        Statures statures = heightService.findStudentHeightRecent(scenarioId, schoolId, studentId, recordDate);
        System.out.println(statures);
    }

    @Test
    public void mongoInterruptExceptionTest() {
        final Thread t1 = new Thread(() -> {
            StudentHealthInfo byId1 = studentHealthInfoService.findById(UUID.randomUUID().toString());
            System.out.println(byId1);
            StudentHealthInfo byId2 = studentHealthInfoService.findById(UUID.randomUUID().toString());
            System.out.println(byId2);
            StudentHealthInfo byId3 = studentHealthInfoService.findById(UUID.randomUUID().toString());
            System.out.println(byId3);
        });
        System.out.println("线程开始");
        t1.start();
        System.out.println("sleep");
        t1.interrupt();
    }

    /**
     * 测试卡路里计算
     */
    @Test
    public void calorieComputeTest() {
        String shopId = "0807";
        String schoolId = "1";
        String studentId = "2019080701";
        String startTime = "2019/08/01";
        String endTime = "2019/08/07";
        List<HistoryValue> list = calorieService.calorieCompute(shopId, schoolId, studentId, startTime, endTime);
    }

    @Test
    public void updateCalorieTest() {
        String shopId = "0807";
        String schoolId = "1";
        String studentId = "2019080701";
        Integer value = 3000;
        Long updateTime = System.currentTimeMillis();
        calorieService.updateCalorie(shopId, schoolId, studentId, value, updateTime);

    }

    @Test
    public void updateAllTest(){
        studentStepService.updateAll();
    }

    @Test
    public void insertOneCalorie() {
        DayDetailCalorie dayDetailCalorie = new DayDetailCalorie();
        dayDetailCalorie.setScenarioId("0807");
        dayDetailCalorie.setSchoolId("1");
        dayDetailCalorie.setYears(System.currentTimeMillis());
        dayDetailCalorie.setBaseGrade(0);
        dayDetailCalorie.setClassId("1");
        dayDetailCalorie.setStudentId("2019080701");
        dayDetailCalorie.setValue(1000);
        dayDetailCalorie.setRecordDate("2019/08/08");
        dayDetailCalorie.setUpdateTime(System.currentTimeMillis());
        dayDetailCalorie.setTtl(new Date());
        calorieService.insertOne(dayDetailCalorie);
    }

    @Test
    public void upsertOneCalorie() {
        String shopId = "0807";
        String schoolId = "1";
        String studentId = "2019080701";
        String recordDate = "2019/08/08";
        Integer value = 30003;
        calorieService.upsertCalorieRecord(shopId, schoolId, studentId, recordDate, value);
    }
    @Test
    public void findAllSchoolByStudentsTest() {
     calorieService.findAllSchoolByStudents();

    }
}
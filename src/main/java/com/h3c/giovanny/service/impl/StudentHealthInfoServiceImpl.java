package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.domain.mongodb.ClassConfigs;
import com.h3c.giovanny.domain.mongodb.HealthPropertyMap;
import com.h3c.giovanny.domain.mongodb.IniInfo;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.service.StudentHealthInfoService;
import com.h3c.giovanny.service.StudentStepService;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @className: StudentHealthInfoServiceImpl
 * @description:
 * @author: YangJun
 * @date: 2019/3/13 14:21
 * @version: v1.0
 **/
@Service
public class StudentHealthInfoServiceImpl implements StudentHealthInfoService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StudentStepService studentStepService;

    @Override
    public void setClassName() {
        Criteria criteria = Criteria.where("scenarioId").is("123456").and("schoolId").is("1");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years", "classId")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("classId").as("classId")
        );
        List<ClassConfigs> classList = mongoTemplate.aggregate(aggregation, StudentHealthInfo.class, ClassConfigs.class).getMappedResults();
        String[] strings = new String[]{"一班", "二班", "三班", "四班", "五班", "六班", "七班", "八班", "九班", "十班"};
        for (ClassConfigs clazz : classList) {
            int i = Integer.parseInt(clazz.getClassId()) - 1;
            WriteResult writeResult = mongoTemplate.updateMulti(Query.query(Criteria
                            .where("scenarioId").is(clazz.getScenarioId())
                            .and("schoolId").is(clazz.getSchoolId())
                            .and("years").is(clazz.getYears())
                            .and("classId").is(clazz.getClassId())),
                    Update.update("className", strings[i]), ClassConfigs.class);
            WriteResult writeResult1 = mongoTemplate.updateMulti(Query.query(Criteria
                            .where("scenarioId").is(clazz.getScenarioId())
                            .and("schoolId").is(clazz.getSchoolId())
                            .and("years").is(clazz.getYears())
                            .and("classId").is(clazz.getClassId())),
                    Update.update("className", strings[i]), StudentHealthInfo.class);
        }


    }

    @Override
    public StudentHealthInfo findById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), StudentHealthInfo.class);
    }


    @Override
    public void insertOneStudent() {
        StudentHealthInfo stu = new StudentHealthInfo();
        stu.setScenarioId("1009");
        stu.setSchoolId("1");
        stu.setYears(1441036800000L);
        stu.setBaseGrade(0);
        stu.setClassId("1");
        stu.setClassName("一班");
        stu.setStudentId("2019080701");
        stu.setStudentName("2019080701");
        stu.setSex("男");
        stu.setBirthday("2011-01-01");
        stu.setInSchoolTime(stu.getYears());
        stu.setOutSchoolTime(stu.getYears());
        stu.setINSn("123456789");
        stu.setRestHeartRate(60.0);
        stu.setFinalStatus("门外");
        stu.setTimeFlag(true);
        stu.setTempStatus(false);
        stu.setInSchoolTime(System.currentTimeMillis());
        stu.setOutSchoolTime(System.currentTimeMillis());
        stu.setINInfo(new IniInfo());
        stu.setRaise(new HealthPropertyMap());
        stu.setRope(new HealthPropertyMap());
        stu.setSitup(new HealthPropertyMap());
        stu.setHeartRate(new HealthPropertyMap());
        stu.setTemperature(new HealthPropertyMap());
        stu.setStature(new HealthPropertyMap());
        stu.setWeight(new HealthPropertyMap());
        stu.setStep(new HealthPropertyMap());
        stu.setHouseholder(Arrays.asList("aa", "bb"));
        stu.setTerminalType("F1");
        stu.setCalorie(new HealthPropertyMap());
        stu.setDistance(new HealthPropertyMap());
        mongoTemplate.insert(stu);
    }

    @Override
    public void insertStudent() {
        List<StudentHealthInfo> studentHealthInfoList = studentStepService.findAllStudent("123456", "1", "2019/03/13");
        for (StudentHealthInfo stu : studentHealthInfoList) {
            stu.setSex("男");
            stu.setBirthday("2011-01-01");
            stu.setInSchoolTime(stu.getYears());
            stu.setOutSchoolTime(stu.getYears());
            stu.setINSn("123456789");
            stu.setRestHeartRate(60.0);
            stu.setFinalStatus("门外");
            stu.setINInfo(new IniInfo());
            stu.setRaise(new HealthPropertyMap());
            stu.setRope(new HealthPropertyMap());
            stu.setSitup(new HealthPropertyMap());
            stu.setHeartRate(new HealthPropertyMap());
            stu.setTemperature(new HealthPropertyMap());
            stu.setStature(new HealthPropertyMap());
            stu.setWeight(new HealthPropertyMap());
            stu.setStep(new HealthPropertyMap());
            stu.setHouseholder(Arrays.asList("aa", "bb"));
        }
        mongoTemplate.insert(studentHealthInfoList, "iotedu_studentinfos");
    }
}

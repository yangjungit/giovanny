package com.h3c.giovanny.dao;

import com.h3c.giovanny.constant.Content;
import com.h3c.giovanny.domain.mongodb.DayDetailCalorie;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.domain.resultbean.ScenarioIdAndSchoolId;
import com.mongodb.WriteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @className: StudentDao
 * @description: 查询普教数据库中与学生有关的数据
 * @author: YangJun
 * @date: 2019/3/25 16:59
 * @version: v1.0
 **/
@Component
@Slf4j
public class StudentDao {
    @Autowired
    private MongoTemplate mongoTemplate;


    public StudentHealthInfo findOneStudent(String shopId, String schoolId, String studentId) {
        return mongoTemplate.findOne(new Query(
                Criteria.where(Content.SCENARIO_ID).is(shopId)
                        .and(Content.SCHOOL_ID).is(schoolId)
                        .and(Content.STUDENT_ID).is(studentId)), StudentHealthInfo.class);
    }


    public void updateCalorie(String shopId, String schoolId, String studentId, Integer value, Long updateTime) {

        WriteResult writeResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where(Content.SCENARIO_ID).is(shopId)
                        .and(Content.SCHOOL_ID).is(schoolId)
                        .and(Content.STUDENT_ID).is(studentId)),
                Update.update(Content.CALORIE_VALUE, value)
                        .set(Content.CALORIE_UPDATE_TIME, updateTime),
                StudentHealthInfo.class);
        int n = writeResult.getN();
        System.out.println(n);


    }

    public void insertOne(DayDetailCalorie dayDetailCalorie) {
        mongoTemplate.insert(dayDetailCalorie);
    }

    public void upsertCalorie(String shopId, String schoolId, String studentId, String recordDate, Integer value) {
        // 这个upsert会导致字段缺失  这样只包含了这个查询里面提及到的字段
        mongoTemplate.upsert(Query.query(Criteria.where(Content.SCENARIO_ID).is(shopId)
                        .and(Content.SCHOOL_ID).is(schoolId)
                        .and(Content.STUDENT_ID).is(studentId)
                        .and(Content.RECORD_DATE).is(recordDate)),
                Update.update(Content.VALUE, value)
                        .set("ttl", new Date()), DayDetailCalorie.class);


    }

    public void findAllSchoolByStudents() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group(Content.SCENARIO_ID, Content.SCHOOL_ID)
        );
        AggregationResults<ScenarioIdAndSchoolId> scenarioIdAndSchoolIds = mongoTemplate.aggregate(aggregation, StudentHealthInfo.class, ScenarioIdAndSchoolId.class);

        System.out.println(scenarioIdAndSchoolIds.getMappedResults().get(0));

    }
}

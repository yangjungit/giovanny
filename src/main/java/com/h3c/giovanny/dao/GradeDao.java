package com.h3c.giovanny.dao;

import com.h3c.giovanny.domain.mongodb.DayTotalSteps;
import com.h3c.giovanny.domain.mongodb.GradeConfig;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.domain.responsebean.ClassHealthResp;
import com.h3c.giovanny.domain.resultbean.HealthResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: GradeDao
 * @description:
 * @author: YangJun
 * @date: 2019/3/22 17:22
 * @version: v1.0
 **/
@Component
public class GradeDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ClassHealthResp> findClassList(String scenarioId, String schoolId, long classBeginTime) {
        //没有将场景id封装进去
        return mongoTemplate.find(Query.query(Criteria
                .where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("years").is(classBeginTime)), ClassHealthResp.class, "iotedu_classconfigs");
    }

    public List<HealthResultDto> getClassStepAvgList(String scenarioId, String schoolId, long classBeginTime, String recordDate) {
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("years").is(classBeginTime)
                .and("recordDate").is(recordDate)
                .and("value").gt(0);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years", "classId")
                        .first("scenarioId").as("shopId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("classId").as("classId")
                        .avg("value").as("value")
        );
        return mongoTemplate.aggregate(aggregation, DayTotalSteps.class, HealthResultDto.class).getMappedResults();

    }

    public List<HealthResultDto> getClassHeightAvgList(String scenarioId, String schoolId, long classBeginTime) {
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("years").is(classBeginTime)
                .and("stature.value").gt(0);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years", "classId")
                        .first("scenarioId").as("shopId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("classId").as("classId")
                        .avg("stature.value").as("value")
        );
        return mongoTemplate.aggregate(aggregation, StudentHealthInfo.class, HealthResultDto.class).getMappedResults();
    }

    public List<HealthResultDto> getClassWeightAvgList(String scenarioId, String schoolId, long classBeginTime) {
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("years").is(classBeginTime)
                .and("weight.value").gt(0);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years", "classId")
                        .first("scenarioId").as("shopId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("classId").as("classId")
                        .avg("weight.value").as("value")
        );
        return mongoTemplate.aggregate(aggregation, StudentHealthInfo.class, HealthResultDto.class).getMappedResults();
    }

    public GradeConfig getGradeConfig(String shopId, String schoolId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("scenarioId").is(shopId).and("schoolId").is(schoolId)), GradeConfig.class);
    }
}

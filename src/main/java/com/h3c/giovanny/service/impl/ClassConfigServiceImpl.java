package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.domain.mongodb.ClassConfigs;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.service.ClassConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @className: ClassConfigServiceImpl
 * @description:
 * @author: YangJun
 * @date: 2019/3/21 9:31
 * @version: v1.0
 **/
@Service
public class ClassConfigServiceImpl implements ClassConfigService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insert() {
        // 通过学生查询有哪些班级
        Criteria criteria = Criteria.where("scenarioId").is("123456").and("schoolId").is("1");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years", "classId")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("baseGrade").as("baseGrade")
                        .first("classId").as("classId")
                        .first("className").as("className")
        );
        AggregationResults<ClassConfigs> aggregate = mongoTemplate.aggregate(aggregation, StudentHealthInfo.class, ClassConfigs.class);
        // 插入
        aggregate.getMappedResults()
                .forEach(classConfigs -> {
                    classConfigs.setTeacher("qwe");
                    classConfigs.setTeacherTel("123456");
                });
        List<ClassConfigs> classConfigsList = aggregate.getMappedResults();
        mongoTemplate.insert(classConfigsList, "iotedu_classconfigs");
    }
}

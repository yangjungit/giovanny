package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.domain.mongodb.Statures;
import com.h3c.giovanny.service.HeightService;
import com.h3c.giovanny.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: HeightServiceImpl
 * @description:
 * @author: YangJun
 * @date: 2019/3/20 16:11
 * @version: v1.0
 **/
@Service
public class HeightServiceImpl implements HeightService {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Statures findStudentHeightRecent(String scenarioId, String schoolId, String studentId, String recordDate) {
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("studentId").is(studentId)
                .and("recordDate").lte(recordDate);
        return mongoTemplate.findOne(Query.query(criteria).with(new Sort(Sort.Direction.ASC,"updateTime")), Statures.class);
    }

    @Override
    public List<Statures> findHeight() {
        Criteria criteria = Criteria
                .where("scenarioId").is("123456999")
                .and("schoolId").is("1")
                .and("studentId").is("qaz");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.Direction.DESC, "updateTime"),
                Aggregation.group("recordDate")
                        .first("updateTime").as("updateTime")
                        .first("recordDate").as("recordDate")
                        .first("value").as("value"),
                Aggregation.limit(180),
                Aggregation.sort(Sort.Direction.ASC, "recordDate")
        );
        return mongoTemplate.aggregate(aggregation, Statures.class, Statures.class).getMappedResults();
    }

    @Override
    public void insertHeight() throws ParseException {
        List<Statures> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 插入十天数据，每天三条
        List<String> interval = DateUtil.getDateStrByTimeInterval("2019/03/01", "2019/03/10");
        interval.forEach(d -> {
            for (int i = 0; i < 3; i++) {
                Statures statures = new Statures();
                statures.setScenarioId("123456");
                statures.setSchoolId("1");
                statures.setYears(1472659200000L);
                statures.setBaseGrade(0);
                statures.setClassId("1");
                statures.setRecordDate(d);
                statures.setStatic(true);
                statures.setStudentId("qaz");
                try {
                    statures.setUpdateTime(simpleDateFormat.parse(d).getTime() + i);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                statures.setValue(10 * (i + 1));
                list.add(statures);
            }
        });
        mongoTemplate.insertAll(list);

    }
}

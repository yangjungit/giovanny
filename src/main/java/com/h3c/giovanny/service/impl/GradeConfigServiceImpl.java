package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.domain.mongodb.GradeConfig;
import com.h3c.giovanny.service.GradeConfigService;
import com.h3c.giovanny.utils.GradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @className: GradeConfigServiceImpl
 * @description:
 * @author: YangJun
 * @date: 2019/4/22 14:04
 * @version: v1.0
 **/
@Service
public class GradeConfigServiceImpl implements GradeConfigService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String findGradeByGradeName(String shopId, String schoolId, String gradeName) {
        GradeConfig gradeConfig = mongoTemplate.findOne(Query.query(Criteria.where("scenarioId").is(shopId).and("schoolId").is(schoolId)), GradeConfig.class);

        return gradeConfig == null ? null : GradeUtil.getGradeByGradeName(gradeConfig, gradeName);
    }
}

package com.h3c.giovanny.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: TestService
 * @description: //TestService
 * @author: YangJun
 * @date: 2019/12/9 19:50
 * @version: v1.0
 **/
@Service
@Slf4j
public class TestService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public StudentVO getStudentList(String shopId, String schoolId, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.DESC, "_id"));
        List<StudentVO.Stu> stus = mongoTemplate.find(Query.query(Criteria
                        .where("scenarioId").is(shopId)
                        .and("schoolId").is(schoolId)).with(pageRequest),
                StudentVO.Stu.class, "iotedu_studentinfos");
        StudentVO studentVO = new StudentVO();
        studentVO.setData(stus);
        studentVO.setPageNum(pageNum);
        studentVO.setPageSize(pageSize);
        long count = mongoTemplate.count(Query
                        .query(Criteria.where("shopId").is(shopId)
                                .and("schoolId").is(schoolId)),
                "iotedu_studentinfos");
        studentVO.setTotalCount((int) count);
        log.info("studentVO:{}", studentVO);
        return studentVO;
    }
}

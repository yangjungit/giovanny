package com.h3c.giovanny.service;

import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;

/**
 * @className: StudentHealthInfoService
 * @description:
 * @author: YangJun
 * @date: 2019/3/13 14:21
 * @version: v1.0
 **/
public interface StudentHealthInfoService {

    void insertStudent();

    void insertOneStudent();

    void setClassName();


    StudentHealthInfo findById(String id);
}

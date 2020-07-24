package com.h3c.giovanny.service;

/**
 * @className: GradeConfigService
 * @description:
 * @author: YangJun
 * @date: 2019/3/21 9:31
 * @version: v1.0
 **/
public interface GradeConfigService {

    /**
     * 通过gradeName查询grade
     *
     * @param shopId    shopId
     * @param schoolId  schoolId
     * @param gradeName gradeName
     * @return grade：yyyy
     */
    String findGradeByGradeName(String shopId, String schoolId, String gradeName);
}

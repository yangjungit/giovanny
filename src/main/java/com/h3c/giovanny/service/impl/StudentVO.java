package com.h3c.giovanny.service.impl;

import lombok.Data;

import java.util.List;

/**
 * @className: StudentVO
 * @description: //StudentVO
 * @author: YangJun
 * @date: 2019/12/9 20:53
 * @version: v1.0
 **/
@Data
public class StudentVO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalCount;
    private List<Stu> data;

    @Data
    public class Stu{
        private String studentId;
        private String studentName;
        private String sex;
        private String birthday;
        private String gradeType;
        private String gradeYears;
        private String className;
        private String INSn;
        private String terminalType;
    }
}

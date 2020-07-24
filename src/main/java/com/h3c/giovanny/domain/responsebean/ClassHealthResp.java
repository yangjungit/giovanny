package com.h3c.giovanny.domain.responsebean;

import lombok.Data;

/**
 * @className: ClassHealthResp
 * @description: 班级健康数据返回对象
 * @author: YangJun
 * @date: 2019/3/22 16:16
 * @version: v1.0
 **/
@Data
public class ClassHealthResp {
    private String shopId;

    private String schoolId;

    private Long years;

    private String grade;

    private String gradeType;

    private int gradeIndex;

    private String gradeName;

    private String classId;

    private String className;

    private int height;

    private int weight;

    private int step;

    private int stepRank;

    private String recordDate;

}

package com.h3c.giovanny.domain.responsebean;

import lombok.Data;

/**
 * @className: GradeHealthInfo
 * @description:
 * @author: YangJun
 * @date: 2019/3/21 14:31
 * @version: v1.0
 **/
@Data
public class GradeHealthInfo {

    private String shopId;

    private String schoolId;

    private Long years;

    private String grade;

    private String gradeName;

    private String gradeType;

    private int gradeIndex;

    private int height;

    private int weight;

    private int step;

    private int stepRank;

    private String recordDate;
}

package com.h3c.giovanny.domain.resultbean;

import lombok.Data;

/**
 * @className: HealthResultDto
 * @description:
 * @author: YangJun
 * @date: 2019/3/21 14:37
 * @version: v1.0
 **/
@Data
public class HealthResultDto {
    private String shopId;

    private String schoolId;

    private Long years;

    private String classId;

    private String className;

    private int value;

    private String recordDate;

}

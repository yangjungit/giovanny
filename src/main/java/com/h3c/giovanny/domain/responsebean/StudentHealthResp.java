package com.h3c.giovanny.domain.responsebean;

import lombok.Data;

/**
 * @className: StudentHealthResp
 * @description: 学生健康数据返回对象
 * @author: YangJun
 * @date: 2019/3/25 17:02
 * @version: v1.0
 **/
@Data
public class StudentHealthResp {

    private String studentName;

    private String studentId;

    private int step;

    private String recordDate;

}

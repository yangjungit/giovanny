package com.h3c.giovanny.domain.resultbean;

import lombok.Data;

/**
 * @className: HistoryValue
 * @description: 历史值
 * @author: YangJun
 * @date: 2019/3/27 20:06
 * @version: v1.0
 **/
@Data
public class HistoryValue {
    private double value;
    private Long updateTime;
    private double stepAverage;
    private String recordDate;

}

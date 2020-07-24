package com.h3c.giovanny.service;

import com.h3c.giovanny.domain.mongodb.Statures;

import java.text.ParseException;
import java.util.List;

/**
 * @className: HeightService
 * @description:
 * @author: YangJun
 * @date: 2019/3/20 16:10
 * @version: v1.0
 **/
public interface HeightService {
    void insertHeight() throws ParseException;

    List<Statures> findHeight();

    Statures findStudentHeightRecent(String scenarioId, String schoolId, String studentId, String recordDate);
}

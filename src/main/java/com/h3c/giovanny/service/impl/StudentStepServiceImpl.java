package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.dao.GradeDao;
import com.h3c.giovanny.domain.mongodb.ClassConfigs;
import com.h3c.giovanny.domain.mongodb.DayTotalSteps;
import com.h3c.giovanny.domain.mongodb.GradeConfig;
import com.h3c.giovanny.domain.mongodb.StudentHealthInfo;
import com.h3c.giovanny.domain.responsebean.ClassHealthResp;
import com.h3c.giovanny.domain.responsebean.GradeHealthInfo;
import com.h3c.giovanny.domain.responsebean.StudentHealthResp;
import com.h3c.giovanny.domain.resultbean.HealthResultDto;
import com.h3c.giovanny.service.StudentStepService;
import com.h3c.giovanny.utils.DateUtil;
import com.h3c.giovanny.utils.GradeUtil;
import com.mongodb.WriteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @className: StudentStepServiceImpl
 * @description:
 * @author: YangJun
 * @date: 2019/3/6 15:57
 * @version: v1.0
 **/
@Service
@Slf4j
public class StudentStepServiceImpl implements StudentStepService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GradeDao gradeDao;

    @Override
    public void updateAll() {
        WriteResult writeResult = mongoTemplate.updateMulti(null,
                Update.update("weight.value", 110).set("weight.updateTime", 1565614960107L),
                StudentHealthInfo.class);
        System.out.println(writeResult.getN());
    }

    @Override
    public void insertDayTotalStepXxMillion() throws ParseException {
        log.info("start:{}", System.currentTimeMillis());
//        String[] classNameArr = new String[]{"一班", "二班", "三班", "四班", "五班", "六班", "七班", "八班", "九班", "十班"};
        long[] yearsArr = new long[]{1535731200000L, 1504195200000L, 1472659200000L, 1441036800000L, 1409500800000L, 1377964800000L};
        List<String> dateStrList = DateUtil.getDateStrByTimeInterval("2018/01/01", "2018/12/31");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            // 学校个数
            for (int school = 0; school < 4; school++) {
                //j 年级
                for (int j = 0; j < 6; j++) {
                    // i 班级
                    for (int i = 0; i < 10; i++) {
                        // k 班级人数
                        for (int k = 0; k < 100; k++) {
                            String studentId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                            // 一年时间
                            for (int l = 0; l < dateStrList.size(); l++) {
                                DayTotalSteps dayTotalSteps = new DayTotalSteps();
                                dayTotalSteps.setScenarioId("123456");
                                dayTotalSteps.setSchoolId("1");
                                dayTotalSteps.setYears(yearsArr[j]);
                                dayTotalSteps.setBaseGrade(0);
                                dayTotalSteps.setClassId(String.valueOf(j + 1));
                                dayTotalSteps.setStudentId(studentId);
                                dayTotalSteps.setValue((int) (Math.random() * 1000 + 1));
                                try {
                                    dayTotalSteps.setUpdateTime(simpleDateFormat.parse("2019/05/12").getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                dayTotalSteps.setRecordDate("2019/05/12");
                                mongoTemplate.insert(dayTotalSteps);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("e:{}", e);
        }
        log.info("start:{}", System.currentTimeMillis());
    }

    @Override
    public void updateStudentInfo(String scenarioId, String schoolId, String studentId) {
        WriteResult writeResult = mongoTemplate.updateFirst(Query.query(Criteria
                        .where("scenarioId").is(scenarioId)
                        .and("schoolId").is(schoolId)
                        .and("studentId").is(studentId)),
                Update.update("weight.value", 66).set("stature.value", 175),
                StudentHealthInfo.class);
        long modifiedCount = writeResult.getN();
    }

    @Override
    public List<DayTotalSteps> findStepHistory(String scenarioId, String schoolId, String studentId, String startDate, String endDate) {
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("studentId").is(studentId)
                .and("recordDate").gte(startDate).lte(endDate);
        List<DayTotalSteps> list = mongoTemplate.find(Query.query(criteria), DayTotalSteps.class);
        return list;
    }

    @Override
    public List<DayTotalSteps> findStudentStepOfWeek(String scenarioId, String schoolId, String studentId) {
        long updateTime = 1552457545000L;
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("studentId").is(studentId)
                .and("updateTime").gte(updateTime)
                .and("value").gt(0);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("studentId")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("studentId").as("studentId")
                        .avg("value").as("value")
        );
        AggregationResults<DayTotalSteps> steps = mongoTemplate.aggregate(aggregation, DayTotalSteps.class, DayTotalSteps.class);

        return steps.getMappedResults();
    }

    @Override
    public List<StudentHealthResp> findClassStudents(String scenarioId, String schoolId, String grade, String classId) {
        long classBeginTime = 0;
        try {
            classBeginTime = GradeUtil.getClassBeginTimeByGrade(grade);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("years").is(classBeginTime)
                .and("classId").is(classId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("studentId")
                        .first("studentId").as("studentId")
                        .first("studentName").as("studentName")
        );
        List<StudentHealthResp> mappedResults = mongoTemplate.aggregate(aggregation, StudentHealthInfo.class, StudentHealthResp.class).getMappedResults();
        return mappedResults;
    }

    @Override
    public List<StudentHealthResp> findStudentsHealthData(String scenarioId, String schoolId, String grade, String classId, String recordDate) {
        long classBeginTime = 0;
        try {
            classBeginTime = GradeUtil.getClassBeginTimeByGrade(grade);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("years").is(classBeginTime)
                .and("classId").is(classId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("studentId")
                        .first("studentId").as("studentId")
                        .first("value").as("step")
                        .first("recordDate").as("recordDate"),
                Aggregation.lookup("iotedu_studentinfos", "studentId", "studentId", "student"),
                Aggregation.unwind("student"),
                Aggregation.project("studentId", "step", "recordDate")
                        .and("student.studentName").as("studentName")
        );
        List<StudentHealthResp> mappedResults = mongoTemplate.aggregate(aggregation, DayTotalSteps.class, StudentHealthResp.class).getMappedResults();
        return new ArrayList<>(mappedResults);
    }

    @Override
    public List<ClassHealthResp> findClassHealthData(String scenarioId, String schoolId, String grade, String recordDate) {
        long classBeginTime = 0;
        try {
            classBeginTime = GradeUtil.getClassBeginTimeByGrade(grade);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 查询出所有的班级架构
        List<ClassHealthResp> classHealthRespList = gradeDao.findClassList(scenarioId, schoolId, classBeginTime);
        // 步数查询 求步数大于零的平均值
        List<HealthResultDto> stepList = gradeDao.getClassStepAvgList(scenarioId, schoolId, classBeginTime, recordDate);
        // 身高查询 求身高大于零的平均值
        List<HealthResultDto> heightList = gradeDao.getClassHeightAvgList(scenarioId, schoolId, classBeginTime);
        // 体重查询 求体重大于零的平均值
        List<HealthResultDto> weightList = gradeDao.getClassWeightAvgList(scenarioId, schoolId, classBeginTime);
        int size = classHealthRespList.size(), stepRank = 1;
        GradeConfig gradeConfig = gradeDao.getGradeConfig(scenarioId, schoolId);
        if (gradeConfig != null) {
            int gradeType = gradeConfig.getGradeType();
            for (int i = 0; i < size; i++) {
                ClassHealthResp classHealthResp = classHealthRespList.get(i);
                // 设置步数
                for (HealthResultDto step : stepList) {
                    if (step.getClassId().equals(classHealthResp.getClassId())) {
                        classHealthResp.setStep(step.getValue());
                    }
                }
                // 设置身高
                for (HealthResultDto height : heightList) {
                    if (height.getClassId().equals(classHealthResp.getClassId())) {
                        classHealthResp.setHeight(height.getValue());
                    }
                }
                // 设置体重
                for (HealthResultDto weight : weightList) {
                    if (weight.getClassId().equals(classHealthResp.getClassId())) {
                        classHealthResp.setWeight(weight.getValue());
                    }
                }
                // 设置shopId
                classHealthResp.setShopId(scenarioId);
                // 设置grade
                classHealthResp.setGrade(GradeUtil.getGradeByClassBeginTime(classHealthResp.getYears()));
                // 设置gradeName
                int gradeIndex = GradeUtil.calGradeIndex(classHealthResp.getYears());
                classHealthResp.setGradeName(GradeUtil.getGradeName(gradeConfig, gradeIndex));
                // 设置gradeType
                classHealthResp.setGradeType(GradeUtil.transGradeType(gradeType, gradeIndex));
                // 设置gradeIndex一定要在设置gradeName和gradeType之后
                if (gradeType == 4 && gradeIndex > 3) {
                    gradeIndex = gradeIndex - 3;
                }
                classHealthResp.setGradeIndex(gradeIndex);
                // 设置recordDate
                classHealthResp.setRecordDate(recordDate);
            }
        }
        // 设置步数排名 步数相等，排名并列
        classHealthRespList.sort(((o1, o2) -> o2.getStep() - o1.getStep()));
        for (int i = 0; i < size; i++) {
            if (i > 0 && classHealthRespList.get(i).getStep() != classHealthRespList.get(i - 1).getStep()) {
                stepRank++;
            }
            classHealthRespList.get(i).setStepRank(stepRank);
        }
        // 将班级列表按照className来排序返回给前端
        classHealthRespList.sort(Comparator.comparing(ClassHealthResp::getClassName));
        return classHealthRespList;
    }

    @Override
    public List<GradeHealthInfo> findGradeHealthData3(String scenarioId, String schoolId, String recordDate) {
        // 查询年级架构 通过所有班级统计出所有年级
        Criteria criteria = Criteria.where("scenarioId").is(scenarioId).and("schoolId").is(schoolId);
        Aggregation gradeAggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years")
                        .first("scenarioId").as("shopId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
        );
        AggregationResults<GradeHealthInfo> aggregate = mongoTemplate.aggregate(gradeAggregation, ClassConfigs.class, GradeHealthInfo.class);
        List<GradeHealthInfo> gradeHealthInfoList = new ArrayList<>(aggregate.getMappedResults());
        // 步数查询 求步数大于零的平均值
        long start = System.currentTimeMillis();
        Criteria stepCriteria = Criteria.where("recordDate").is(recordDate)
                .and("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("value").gt(0);
        Aggregation stepAggregation = Aggregation.newAggregation(
                Aggregation.match(stepCriteria),
                Aggregation.group("years")
                        .first("scenarioId").as("shopId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .avg("value").as("value")
                        .first("recordDate").as("recordDate"),
                Aggregation.sort(Sort.Direction.DESC, "value")
        );
        List<HealthResultDto> stepList = mongoTemplate.aggregate(stepAggregation, DayTotalSteps.class, HealthResultDto.class).getMappedResults();
        System.out.println(System.currentTimeMillis() - start);
        // 身高查询 求身高大于零的平均值
        Criteria heightCriteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("stature.value").gt(0);
        Aggregation heightAggregation = Aggregation.newAggregation(
                Aggregation.match(heightCriteria),
                Aggregation.group("years")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .avg("stature.value").as("value")
        );
        List<HealthResultDto> heightList = mongoTemplate.aggregate(heightAggregation, StudentHealthInfo.class, HealthResultDto.class).getMappedResults();
        // 体重查询 求体重大于零的平均值
        Criteria weightCriteria = Criteria.where("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId)
                .and("weight.value").gt(0);
        Aggregation weightAggregation = Aggregation.newAggregation(
                Aggregation.match(weightCriteria),
                Aggregation.group("years")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .avg("weight.value").as("value")
        );
        List<HealthResultDto> weightList = mongoTemplate.aggregate(weightAggregation, StudentHealthInfo.class, HealthResultDto.class).getMappedResults();
        int size = gradeHealthInfoList.size(), stepRank = 1;
        GradeConfig gradeConfig = mongoTemplate.findOne(Query.query(Criteria.where("scenarioId").is("123456").and("schoolId").is("1")), GradeConfig.class);
        if (gradeConfig != null) {
            int gradeType = gradeConfig.getGradeType();
            for (GradeHealthInfo gradeHealthInfo : gradeHealthInfoList) {
                // 设置步数
                for (HealthResultDto step : stepList) {
                    if (step.getYears().equals(gradeHealthInfo.getYears())) {
                        gradeHealthInfo.setStep(step.getValue());
                    }
                }
                // 设置身高
                for (HealthResultDto height : heightList) {
                    if (height.getYears().equals(gradeHealthInfo.getYears())) {
                        gradeHealthInfo.setHeight(height.getValue());
                    }
                }
                // 设置体重
                for (HealthResultDto weight : weightList) {
                    if (weight.getYears().equals(gradeHealthInfo.getYears())) {
                        gradeHealthInfo.setWeight(weight.getValue());
                    }
                }
                // 设置grade
                gradeHealthInfo.setGrade(GradeUtil.getGradeByClassBeginTime(gradeHealthInfo.getYears()));
                // 设置gradeName
                int gradeIndex = GradeUtil.calGradeIndex(gradeHealthInfo.getYears());
                gradeHealthInfo.setGradeName(GradeUtil.getGradeName(gradeConfig, gradeIndex));
                // 设置gradeType
                gradeHealthInfo.setGradeType(GradeUtil.transGradeType(gradeType, gradeIndex));
                // 设置gradeIndex一定要在设置gradeName和gradeType之后
                if (gradeType == 4 && gradeIndex > 3) {
                    gradeIndex = gradeIndex - 3;
                }
                gradeHealthInfo.setGradeIndex(gradeIndex);
                // 设置recordDate
                gradeHealthInfo.setRecordDate(recordDate);
            }
        }
        // 设置步数排名 步数相等，排名并列
        gradeHealthInfoList.sort(((o1, o2) -> o2.getStep() - o1.getStep()));
        for (int i = 0; i < size; i++) {
            if (i > 0 && gradeHealthInfoList.get(i).getStep() != gradeHealthInfoList.get(i - 1).getStep()) {
                stepRank++;
            }
            gradeHealthInfoList.get(i).setStepRank(stepRank);
        }
        // 将年级列表按照年级由低到高返回给前端
        gradeHealthInfoList.sort((o1, o2) -> o2.getGrade().compareTo(o1.getGrade()));
        return gradeHealthInfoList;
    }


    @Override
    public List<GradeHealthInfo> findGradeHealthData2(String scenarioId, String schoolId, String recordDate) {
        Criteria criteria = Criteria.where("recordDate").is(recordDate)
                .and("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.lookup("iotedu_studentinfos", "studentId", "studentId", "students"),
                Aggregation.unwind("students"),
                Aggregation.project("scenarioId", "schoolId", "years", "recordDate")
                        .and("value").as("step")
                        .and("students.stature").as("height")
                        .and("students.weight").as("weight"),
                Aggregation.project("scenarioId", "schoolId", "years", "recordDate", "step")
                        .and("height.value").as("height")
                        .and("weight.value").as("weight"),
                Aggregation.group("years")
                        .first("scenarioId").as("shopId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("recordDate").as("recordDate")
                        .avg("step").as("step")
                        .avg("weight").as("weight")
                        .avg("height").as("height"),
                Aggregation.sort(Sort.Direction.DESC, "step")
        );
        AggregationResults<GradeHealthInfo> aggregate = mongoTemplate.aggregate(aggregation, DayTotalSteps.class, GradeHealthInfo.class);
        List<GradeHealthInfo> gradeHealthInfoList = aggregate.getMappedResults();
        GradeConfig gradeConfig = mongoTemplate.findOne(Query.query(Criteria.where("scenarioId").is("123456").and("schoolId").is("1")), GradeConfig.class);
        // 1:小学 2:初中 3:高中 4:综合制中学 5:幼儿园 6:自定义
        if (gradeConfig != null) {
            int gradeType = gradeConfig.getGradeType();
            int size = gradeHealthInfoList.size();
            int rank = 1;
            for (int i = 0; i < size; i++) {
                GradeHealthInfo gradeHealthInfo = gradeHealthInfoList.get(i);
                gradeHealthInfo.setGrade(GradeUtil.getGradeByClassBeginTime(gradeHealthInfo.getYears()));
                int gradeIndex = GradeUtil.calGradeIndex(gradeHealthInfo.getYears());
                gradeHealthInfo.setGradeType(GradeUtil.transGradeType(gradeType, gradeIndex));
                //设置排名，平均步数相等要并列处理
                gradeHealthInfo.setStepRank(rank);
                if (i > 0 && gradeHealthInfo.getStep() < gradeHealthInfoList.get(i - 1).getStep()) {
                    rank++;
                }
                String gradeName = GradeUtil.getGradeName(gradeConfig, gradeIndex);
                if (gradeName != null) {
                    gradeHealthInfo.setGradeName(gradeName);
                }
                // 设置gradeIndex一定要在设置gradeName之后
                if (gradeType == 4 && gradeIndex > 3) {
                    gradeIndex = gradeIndex - 3;
                }
                gradeHealthInfo.setGradeIndex(gradeIndex);
            }
            return gradeHealthInfoList;
        }
        return null;

    }


    @Override
    public List<StudentHealthInfo> findAllStudent(String scenarioId, String schoolId, String recordDate) {
        Criteria criteria = Criteria.where("recordDate").is(recordDate)
                .and("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("years", "classId", "studentId")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("baseGrade").as("baseGrade")
                        .first("classId").as("classId")
                        .first("recordDate").as("recordDate")
                        .first("updateTime").as("updateTime")
        );
        AggregationResults<StudentHealthInfo> aggregate = mongoTemplate.aggregate(aggregation, DayTotalSteps.class, StudentHealthInfo.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<GradeHealthInfo> findGradeHealthData(String scenarioId, String schoolId, String recordDate) {
        Criteria criteria = Criteria.where("recordDate").is(recordDate)
                .and("scenarioId").is(scenarioId)
                .and("schoolId").is(schoolId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("scenarioId", "schoolId", "years")
                        .first("scenarioId").as("scenarioId")
                        .first("schoolId").as("schoolId")
                        .first("years").as("years")
                        .first("recordDate").as("recordDate")
                        .first("updateTime").as("updateTime")
                        .avg("value").as("step"),
                Aggregation.sort(Sort.Direction.DESC, "step")
        );
        AggregationResults<GradeHealthInfo> aggregate = mongoTemplate.aggregate(aggregation, DayTotalSteps.class, GradeHealthInfo.class);
        List<GradeHealthInfo> gradeHealthInfoList = aggregate.getMappedResults();
        return gradeHealthInfoList;

    }

    @Override
    public int findCount() {
        // 这种办法肯定是错的
        return mongoTemplate.findAll(DayTotalSteps.class).size();
    }

    @Override
    public DayTotalSteps insertDayTotalSteps(DayTotalSteps dayTotalSteps) {
        mongoTemplate.insert(dayTotalSteps);
        return null;
    }

    @Override
    public void updateDayTotalSteps(String[] strArray) {
//        for (String year : strArray) {
//            List<DayTotalSteps> dayTotalSteps = mongoTemplate.find(Query.query(Criteria
//                    .where("scenarioId").is("123456")
//                    .and("schoolId").is("1")
//                    .and("years").is(DateUtil.string2InSchoolTime(year))), DayTotalSteps.class);
//            for (DayTotalSteps data : dayTotalSteps) {
//                mongoTemplate.updateFirst(Query.query(Criteria
//                                .where("scenarioId").is(data.getScenarioId())
//                                .and("schoolId").is(data.getSchoolId())
//                                .and("years").is(data.getYears())
//                                .and("studentId").is(data.getStudentId())),
//                        Update.update("studentId", year+data.getStudentId()), DayTotalSteps.class);
//            }
//        }
        List<DayTotalSteps> all = mongoTemplate.findAll(DayTotalSteps.class);
        for (DayTotalSteps data : all) {
            mongoTemplate.updateMulti(Query.query(Criteria
                            .where("scenarioId").is(data.getScenarioId())
                            .and("schoolId").is(data.getSchoolId())
                            .and("years").is(data.getYears())
                            .and("studentId").is(data.getStudentId())),
                    Update.update("classId", data.getClassId().split("\\" + ".")[0]), DayTotalSteps.class);
        }

    }

    @Override
    public WriteResult deleteDayTotalSteps() {
        WriteResult writeResult = mongoTemplate.remove(Query.query(Criteria.where("scenarioId").is("123456")), DayTotalSteps.class);
        return writeResult;
    }
}

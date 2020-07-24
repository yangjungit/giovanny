package com.h3c.giovanny.constant;

/**
 * @className: Content
 * @description:
 * @author: YangJun
 * @date: 2018/11/27 9:31
 * @version: v1.0
 **/
public class Content {

    public static final String SPLIT = ",";

    public static final String NULL = "null";

    public static final String URL = "url";

    public static final String QUERY = "query";

    public static final String GET = "get";

    public static final String POST = "post";

    public static final String PUT = "put";

    public static final String DELETE = "delete";

    public static final String BODY = "body";

    public static final String DATA = "data";

    public static final String SCENARIO_ID = "scenarioId";

    public static final String SCHOOL_ID = "schoolId";

    public static final String APP_ID = "appId";

    public static final String STUDENT_ID = "studentId";

    public static final String DAILY_RANKING = "1";

    public static final String WEEKLY_RANKING = "2";

    public static final String CLASS_ID = "classId";

    public static final String UPDATE_TIME = "updateTime";

    public static final String RECORD_DATE = "recordDate";

    public static final String FEMALE = "女";

    public static final String MALE = "男";

    public static final Integer TOO_MANY_STEPS = 20000;
    public static final Integer SUIT_STEPS = 10000;
    public static final String VALUE = "value";
    public static final String NO_DATA = "暂无数据";
    public static final String SHOP_ID = "shopId";
    public static final String CLASS_NAME = "className";
    public static final String YEARS = "years";
    public static final String ID = "id";
    public static final String MESSAGE_ID = "messageId";
    public static final String MESSAGE_STATUS = "messageStatus";
    public static final String GRADE = "grade";
    public static final String GRADE_TYPE = "gradeType";
    public static final int MAX_REST_HEART_RATE = 150;
    public static final String STEP = "step";
    public static final String STUDENT_NAME = "studentName";
    public static final String SELECT_TIME = "selectTime";
    public static final String REST_HEART_RATE = "restHeartRate";
    public static final String STATURE_VALUE = "stature.value";
    public static final String WEIGHT_VALUE = "weight.value";
    public static final String WEIGHT_UPDATE_TIME = "weight.update";
    public static final String STATURE_UPDATE_TIME = "stature.update";
    public static final String SEX = "sex";
    public static final String CALORIE_VALUE = "calorie.value";
    public static final String CALORIE_UPDATE_TIME = "calorie.updateTime";

    public interface TerminalType {
        String F1 = "F1";
    }

    public interface ExerciseAdvice {
        String NEED_MUCH_MORE_EXERCISE = "运动量不够，生命在于运动，需要多加锻炼。";
        String SUIT_EXERCISE = "运动量适中，继续保持哦。";
        String LESS_EXERCISE = "运动量太大啦，运动强度太大会让你的身体吃不消，注意补充能量。";
    }

    public interface DietaryAdvice {
        double BMI_ZERO = 0.0;

        double BMI_LOWER = 18.5;
        String NEED_MUCH_MORE_FOOD = "BMI低，需要增加锻炼与补充营养，可以适当补充维生素和钙。";

        double BMI_NORMAL = 23.9;
        String SUIT_STATURE = "BMI正常，保持生活作息节奏，注意饮食健康。";

        double BMI_HIGHER = 27.0;
        String LITTLE_FOOD = "BMI偏高，体型微胖，注意饮食，坚持运动，避免久坐。";

        double BMI_SO_HIGH = 32.0;
        String LESS_FOOD = "BMI高，增加运动量，少食多餐，少吃高热量的食物。";

        String NO_FOOD = "BMI过高，需要多加运动燃烧你的卡路里，尽量不吃高热量的食物，唯有坚持才能拥抱理想中的自己。";

        String NOTHING = "暂无膳食建议。";
    }

}

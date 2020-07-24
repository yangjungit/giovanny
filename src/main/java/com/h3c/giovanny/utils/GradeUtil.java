package com.h3c.giovanny.utils;

import com.h3c.giovanny.domain.mongodb.ConfigList;
import com.h3c.giovanny.domain.mongodb.GradeConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @className: GradeUtil
 * @description:
 * @author: YangJun
 * @date: 2019/3/19 16:15
 * @version: v1.0
 **/
public class GradeUtil {
    public static String transGradeType(int gradetype, int gradeindex) {
        if (gradetype <= 0 || gradetype > 6) {
            return null;
        }
        switch (gradetype) {
            case 1:
                return "小学";
            case 2:
                return "初中";
            case 3:
                return "高中";
            case 4:
                if (gradeindex > 3) {
                    return "高中";
                } else {
                    return "初中";
                }
            case 5:
                return "幼儿园";
            case 6:
                return "自定义";
            default:
                return null;
        }
    }

    /**
     * gradeIndex表示在classBeginTime入学的学生，当前处于几年级，如classBeginTime是2016年9月1日。
     * 如果当前时间为2018年5月8日，那么gradeIndex为2，表示二年级；如果当前时间为2016年10月1日，那么
     * gradeIndex为1，表示一年级。如果classBeginTime在当前时间之后，返回0,未开学。
     *
     * @param classBeginTime 入学时间，通常是某年的9月1日
     * @return gradeIndex
     */
    public static int calGradeIndex(long classBeginTime) {
        long now = System.currentTimeMillis();
        //如果现在在他们入学时间之前，默认是“一年级”（不分小学初中高中）
        if (classBeginTime > now) {
            return 1;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(classBeginTime);
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, 0);
        gregorianCalendar.set(Calendar.MINUTE, 0);
        gregorianCalendar.set(Calendar.SECOND, 0);
        int count = 0;
        do {
            gregorianCalendar.add(Calendar.YEAR, 1);
            count++;
        } while (gregorianCalendar.getTimeInMillis() <= now);
        return count;
    }


    public static String getGradeName(GradeConfig gradeConfig, int gradeIndex) {
        return gradeConfig.getGradeConf()
                .stream()
                .filter(e -> e.getGradeSequence() == gradeIndex - 1)
                .findAny()
                .map(ConfigList::getGradeString)
                .orElse(null);
    }


    public static String getGradeByGradeName(GradeConfig gradeConfig, String gradeName) {
        Integer gradeIndex = gradeConfig.getGradeConf()
                .stream()
                .filter(e -> e.getGradeString().equals(gradeName))
                .findAny()
                .map(ConfigList::getGradeSequence)
                .orElse(null);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        long now = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("now:" + now + ",after set calendar.getTimeInMillis():" + calendar.getTimeInMillis());
        if (gradeIndex == null) {
            return null;
        } else {
            if (now < calendar.getTimeInMillis()) {
                return String.valueOf(currentYear - gradeIndex - 1);
            } else {
                return String.valueOf(currentYear - gradeIndex);
            }
        }
    }

    public static String getGradeByClassBeginTime(long classBeginTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(classBeginTime);
        return Integer.toString(calendar.get(Calendar.YEAR));
    }

    public static long getClassBeginTimeByGrade(String grade) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(grade + "-09-01");
        return date.getTime();
    }

    public static void main(String[] args) {
//        String[] strings = new String[]{"一班", "二班", "三班", "四班", "五班", "六班", "七班", "八班", "九班", "十班"};
//        String s = "2019/01/01";
//        System.out.println(s.replace("-", "/"));
        List<String> list = new ArrayList<>();
        list.add("一班");
        list.add("二班");
        list.add("三班");
//        try {
//            list.sort(String::compareTo);
//        } finally {
//            System.out.println("sds");
//        }
//        System.out.println(list);
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i == size-1){
                sb.append(list.get(i));
            }else {
                sb.append(list.get(i)).append("|");
            }
        }
        System.out.println(sb.toString());

    }
}

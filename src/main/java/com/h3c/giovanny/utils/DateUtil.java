package com.h3c.giovanny.utils;

import com.alibaba.fastjson.JSONObject;
import com.h3c.giovanny.domain.mongodb.ClassConfigs;
import com.h3c.giovanny.domain.resultbean.HistoryValue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @className: DateUtil
 * @description:
 * @author: YangJun
 * @date: 2019/3/13 14:33
 * @version: v1.0
 **/
public class DateUtil {


    /**
     * 获取1 -- advance 内的随机数
     *
     * @param advance 范围
     * @return 返回随机数
     */
    public static int getRandom(int advance) {
        return (int) ((Math.random() + 1) * advance);
    }

    /**
     * 传入年份字符串，返回入学年份的时间戳
     *
     * @param year 入参格式，yyyy，2019
     * @return 返回入学年月日的时间戳 ms
     */
    public static Long string2InSchoolTime(String year) {
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = yyyy.parse(year + "-09-01");
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过开始时间和结束时间得到时间间隔内的每一天的日期
     *
     * @param startDate yyyy/MM/dd
     * @param endDate   yyyy/MM/dd
     * @return [yyyy/MM/dd, yyyy/MM/dd]
     */
    public static List<String> getDateStrByTimeInterval(String startDate, String endDate) throws ParseException {
        ArrayList<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plus_1(cal)) {
            list.add(sdf.format(d));
        }
        return list;
    }

    private static long get_D_Plus_1(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }

    public static void main(String[] args) throws ParseException {
//        List<String> list = getDateStrByTimeInterval("2019/02/27", "2019/03/06");
//        System.out.println(list);
//        for (int i = 0; i < 100; i++) {
//            int j = new Random().nextInt(100);
//            j = Math.abs(j);
//            if (i == 49 || i == 99) {
//                System.out.println("");
//            } else {
//                System.out.print(j + ",");
//            }
//        }
//        String string = UUID.randomUUID().toString();
//        System.out.println(string);


//        Map<String, String> map1 = new HashMap<>();
//        Map<String, String> map2 = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            if (i % 2 == 0) {
//                map1.put(String.valueOf(i), String.valueOf(i));
//            } else {
//                map2.put(String.valueOf(i), String.valueOf(i));
//            }
//        }
//        Set<String> strings = map1.keySet();
//        strings = map2.keySet();
//        System.out.println(strings);

//        List<HealthPropertyMap> list = new ArrayList<>();
//        HealthPropertyMap healthPropertyMap = new HealthPropertyMap();
//        healthPropertyMap.setUpdateTime(11122323L);
//        healthPropertyMap.setValue(1211L);
//        healthPropertyMap.setDateStr("2019/03/01");
//        list.add(healthPropertyMap);
//        HealthPropertyMap test = test(list);
//        System.out.println(test);


//        List<String> list = new ArrayList<>();
//        list.add("2019/03/05");
//        list.add("2019/03/06");
//        list.add("2019/03/07");
//        list.add("2019/03/10");
//        list.add("2019/03/20");
//        for (int i = 0; i < list.size(); i++) {
//            if ("2019/03/05".compareTo(list.get(i)) < 0) {
//                if (i == 0) {
//                    System.out.println("没找到比这个日期更早的日期");
//                    return;
//                } else {
//                    System.out.println("for:" + list.get(i - 1));
//                    return;
//                }
//            }
//            if (i == list.size() - 1) {
//                System.out.println("if:" + list.get(i));
//                return;
//            }
//        }
//        System.out.println("list 为空");
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            System.out.println("1:" + next);
//            System.out.println("2:" + next);
//        }
//        Map<String, String> map = new HashMap<>(2);
//        map.put("a", "a");
//        map.put("b", "b");
//        map.put("c", "c");
//        map.put("d", "d");
//        map.put("e", "e");
//        System.out.println(map);
//        double x = Math.log(10000) / Math.log(2);
//        System.out.println(x);
        //字符数组组成的所有字符串

        //char[] cs = {'a','b','c','d','e'};
//        char[] cs = {'a','b','c','d'};
//        int length = cs.length;
//        recursionSwap(cs, 0, length);
//        int index = 208;
//        findUglyNum(index);
//        int f1 = 1, f2 = 1, f;
//        int M = 30;
//        System.out.println(1);
//        System.out.println(1);
//        for (int i = 3; i < M; i++) {
//            f = f2;
//            f2 = f1 + f2;
//            f1 = f;
//            System.out.println(f2);
//        }

//        String a = "asdf-ghj";
//        String substring = a.substring(2, 4);
//        System.out.println(a+"/-----/"+substring);
//
//        System.out.println("3"+4+5);

//        String recordDate = "2019/08/08";
//        String recordDate1 = "2019/08/08";

//        System.out.println(recordDate.substring(0, recordDate.lastIndexOf("/")));
//        System.out.println(recordDate.substring(0, 7));
//        System.out.println(recordDate.compareTo(recordDate1));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,0);
//        calendar.set(Calendar.MINUTE,0);
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MILLISECOND,0);
//
//        long timeInMillis = calendar.getTimeInMillis();
//        System.out.println(calendar.get(Calendar.YEAR));
//        System.out.println(timeInMillis);
//        System.out.println(System.currentTimeMillis());
//        List<String> list = new ArrayList<>();
//        list.add("2019/03/05");
//        list.add("2019/03/06");
//        list.add("2019/03/07");
//        list.add("2019/03/10");
//        list.add("2019/03/20");
//        list.add("2019/03/21");
//        list.add("2019/03/22");
//        list.add("2019/03/23");
//        list.add("2019/03/24");
//        List<String> collect = list.stream()
//                .filter(str -> str.contains("24"))
//                .collect(Collectors.toList());
//        System.out.println(Arrays.toString(collect.toArray()));

//        double number = getNumber(3.5526, 2);
//        System.out.println(number);
//        HistoryValue historyValue = new HistoryValue();
//        historyValue.setUpdateTime(4L);
//         HistoryValue fac= Fac(historyValue);
//        System.out.println(fac);
//
//        int greatestCommonDivisor = getGreatestCommonDivisor(15, 17);
//        System.out.println(greatestCommonDivisor);

        List<ClassConfigs> classList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ClassConfigs classConfigs = new ClassConfigs();
            classConfigs.setClassId((int) (Math.random() * 100 + 1) + "");
//            classConfigs.setClassId(5+"");
            System.out.print(classConfigs.getClassId() + "  ");
            classList.add(classConfigs);
        }
        int maxClassId = 1;
        int classListSize = classList.size();
        for (int i = 0; i < classListSize; i++) {
            String classId = classList.get(i).getClassId();
//            maxClassId = Integer.parseInt(classId);
            if (maxClassId < Integer.parseInt(classId)) {
                maxClassId = Integer.parseInt(classId);
            }
        }
        if (200 >= 200) {
            maxClassId += 1;
        }
        System.out.println("=" + maxClassId);
    }

    /**
     * 最大公约数
     *
     * @param a a
     * @param b b
     * @return 最大公约数
     */
    private static int getGreatestCommonDivisor(int a, int b) {
        return a % b == 0 ? b : getGreatestCommonDivisor(b, a % b);
    }


    public static HistoryValue Fac(HistoryValue N) {
        if (N.getUpdateTime() == 1) {
            return N;
        } else {
            N.setUpdateTime(N.getUpdateTime() - 1);
            HistoryValue fac = Fac(N);
            fac.setUpdateTime(fac.getUpdateTime() * N.getUpdateTime());
            return fac;
        }
    }

    private static <T> T test(List<T> list) {
        for (T t : list) {
//            if (t.getClass().equals(HealthPropertyMap.class)) {
//                HealthPropertyMap h = (HealthPropertyMap) t;
//                System.out.println(h);
//                return t;
//            }
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(t));
            System.out.println("-------" + jsonObject.get("dateStr"));
            System.out.println("-------" + "2019/03/02".compareTo((String) jsonObject.get("dateStr")));

        }
        return null;
    }

    private static void findUglyNum(int index) {
        int[] num = new int[index];
        int next = 1;
        num[0] = 1;
        int index2 = 0;
        int index3 = 0;
        int index5 = 0;

        while (next < index) {
            int num2 = num[index2] * 2;
            int num3 = num[index3] * 3;
            int num5 = num[index5] * 5;

            num[next] = getSuitable(num2, num3, num5);

            while (num[index2] * 2 <= num[next]) {
                index2++;
            }
            while (num[index3] * 3 <= num[next]) {
                index3++;
            }
            while (num[index5] * 5 <= num[next]) {
                index5++;
            }
            next++;

        }
        System.out.println(num[index - 1]);
    }

    private static int getSuitable(int num2, int num3, int num5) {
        int s = num2;
        if (num3 < s) {
            s = num3;
        }
        if (num5 < s) {
            s = num5;
        }
        return s;
    }


    //字符数组组成的所有字符串
    private static void swap(char[] cs, int index1, int index2) {
        char temp = cs[index1];
        cs[index1] = cs[index2];
        cs[index2] = temp;
    }

    private static void recursionSwap(char[] cs, int start, int length) {
        if (start >= length - 1) {
            print(cs);
            return;
        }
        for (int i = start; i < length; i++) {
            swap(cs, start, i);
            recursionSwap(cs, start + 1, length);
            swap(cs, start, i);
        }
    }

    private static void print(char[] cs) {
        for (int i = 0; i < cs.length; i++) {
            System.out.print(cs[i]);
        }
        System.out.println();
    }


    /**
     * 四舍五入一个数
     *
     * @param before 四舍五入前的值
     * @param scale  保留小数点
     * @return 返回double 四舍五入
     */
    public static double getNumber(double before, int scale) {
        if (scale <= 0) {
            return Math.round(before);
        } else {
            return new BigDecimal(before).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

}

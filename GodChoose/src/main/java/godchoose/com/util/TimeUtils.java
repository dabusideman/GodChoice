package godchoose.com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by peter on 15/11/9.
 */
public class TimeUtils {

    public static final int WEEKDAYS = 7;

    public static String[] WEEK = {
            "星期日",
            "星期一",
            "星期二",
            "星期三",
            "星期四",
            "星期五",
            "星期六",
};

    public static String getHHmmTime(String times){
        long time = Long.parseLong(times+"000");

        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm z");
        Date d1 = new Date(time);
        String t1=format.format(d1);

        String pattern=" ";
        Pattern pat=Pattern.compile(pattern);
        final String[] rs =pat.split(t1);

        Date d2 = new Date();
        String t2=format.format(d2);

        String nowday = t1.substring(8, 10);
        String nian = t1.substring(0, 4);
        String nian2 = t2.substring(0, 4);
        if (!nian.equals(nian2)){
            return rs[0].substring(2, rs[0].length());
        }
        String day = t2.substring(8, 10);

        String rst = rs[1].substring(0, 2);
//        LogUtil.e("rst = "+rst);
        int i = Integer.parseInt(rst);
        String s = DateToWeek(d1);

        String nowday1 = t1.substring(5, 7);

        int ri1 = Integer.parseInt(day);
        int ri2 = Integer.parseInt(nowday);
        int yue2 = Integer.parseInt(nowday1);


        int temp = Integer.parseInt(day)-Integer.parseInt(nowday);
        if (temp < 0){
            switch (yue2){
                case 1:
                    temp= temp +31;
                    break;
                case 2:
                    temp= temp +28;
                    break;
                case 3:
                    temp= temp +31;
                    break;
                case 4:
                    temp= temp +30;
                    break;
                case 5:
                    temp= temp +31;
                    break;
                case 6:
                    temp= temp +30;
                    break;
                case 7:
                    temp= temp +31;
                    break;
                case 8:
                    temp= temp +31;
                    break;
                case 9:
                    temp= temp +30;
                    break;
                case 10:
                    temp= temp +31;
                    break;
                case 11:
                    temp= temp +30;
                    break;
                case 12:
                    temp= temp +31;
                    break;
            }
        }
        switch (temp) {
            case 0:
                if (i < 12){
//                    return "上午"+rs[1];
                    return ""+rs[1];
                }else {
//                    return "下午"+rs[1];
                    return ""+rs[1];
                }
            case 1:
                return "昨天";
            case 2:
                return s;
            case 3:
                return s;
            case 4:
                return s;
            case 5:
                return s;
            case 6:
                return s;
        }

        return rs[0].substring(5, 10);
    }
    public static String getAndroidHHmmTime(String times){
        long time = Long.parseLong(times);

        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm z");
        Date d1 = new Date(time);
        String t1=format.format(d1);

        String pattern=" ";
        Pattern pat=Pattern.compile(pattern);
        final String[] rs =pat.split(t1);

        Date d2 = new Date();
        long time2 = d2.getTime();
        String t2=format.format(d2);

        String nowday = t1.substring(8, 10);
        String nowday1 = t1.substring(5, 7);
        String day = t2.substring(8, 10);
        String day1 = t2.substring(5, 7);

        String nian = t1.substring(0, 4);
        String nian2 = t2.substring(0, 4);

        if (!nian.equals(nian2)){
            return rs[0];
        }

        String rst = rs[1].substring(0, 2);
        int i = Integer.parseInt(rst);

        String s = DateToWeek(d1);
        int temp1 = Integer.parseInt(day1)-Integer.parseInt(nowday1);
        if (temp1 == 0){
            int temp = Integer.parseInt(day)-Integer.parseInt(nowday);
            switch (temp) {
                case 0:
                    if (i < 12){
                        return rs[1];
                    }else {
                        return rs[1];
                    }

                case 1:
                    return "昨天";
                case 2:
                    return s;
                case 3:
                    return s;
                case 4:
                    return s;
                case 5:
                    return s;
                case 6:
                    return s;
            }
        }else {
            int ri1 = Integer.parseInt(day);
            int ri2 = Integer.parseInt(nowday);
            int yue2 = Integer.parseInt(nowday1);

            int temp = ri1 - ri2;
            if (temp < 0){
                switch (yue2){
                    case 1:
                        temp= temp +31;
                        break;
                    case 2:
                        temp= temp +28;
                        break;
                    case 3:
                        temp= temp +31;
                        break;
                    case 4:
                        temp= temp +30;
                        break;
                    case 5:
                        temp= temp +31;
                        break;
                    case 6:
                        temp= temp +30;
                        break;
                    case 7:
                        temp= temp +31;
                        break;
                    case 8:
                        temp= temp +31;
                        break;
                    case 9:
                        temp= temp +30;
                        break;
                    case 10:
                        temp= temp +31;
                        break;
                    case 11:
                        temp= temp +30;
                        break;
                    case 12:
                        temp= temp +31;
                        break;
                }
            }

            switch (temp) {
                case 0:
                    if (i < 12){
                        return "上午"+rs[1];
                    }else {
                        return "下午"+rs[1];
                    }

                case 1:
                    return "昨天";
                case 2:
                    return s;
                case 3:
                    return s;
                case 4:
                    return s;
                case 5:
                    return s;
                case 6:
                    return s;
            }
        }



        return rs[0].substring(5, 10);
    }
    /**
     * 日期变量转成对应的星期字符串
     * @param date
     * @return
     */
    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }
    public static String DateToWeeks(long l1) {
        Date date = new Date(l1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }
}

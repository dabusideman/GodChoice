package xuezhangyouhuo.com.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * @author ty
 * @createdate 2012-6-14 下午6:09:16
 * @Description: 时间工具类
 */
public class TimeUtil {
	public static final DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	// public static final DateFormat sdf2 = new
	// SimpleDateFormat("yyyy年MM月dd日");
	// public static final DateFormat sdf3=new
	// SimpleDateFormat("yyyy年MM月dd日 E"); //E表示星期几
	// public static final DateFormat sdf4=new
	// SimpleDateFormat("yyyy-MM-dd HH"); //精确到小时
	// private static final DateFormat dfYear = new SimpleDateFormat("yyyy");
	// private static final DateFormat dfDay = new SimpleDateFormat("dd");
	// private static final DateFormat dfMonth = new
	// SimpleDateFormat("MM-dd HH:mm");
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dfHour = new SimpleDateFormat("HH:mm");
	private static final String SUNDAY = "周日";
	private static final String SUNDAYS = "星期日";
	private static final String MONDAY = "周一";
	private static final String MONDAYS = "星期一";
	private static final String TUESDAY = "周二";
	private static final String TUESDAYS = "星期二";
	private static final String WEDNESDAY = "周三";
	private static final String WEDNESDAYS = "星期三";
	private static final String THURSDAY = "周四";
	private static final String THURSDAYS = "星期四";
	private static final String FRIDAY = "周五";
	private static final String FRIDAYS = "星期五";
	private static final String SATURDAY = "周六";
	private static final String SATURDAYS = "星期六";

	/**
	 * 
	 * @author ty
	 * @createdate 2012-6-17 下午1:53:04
	 * @Description: 将字符串根据日期的格式进行格式化
	 * @param df
	 * @param date
	 * @return
	 */
	public static Date parseDate(DateFormat df, String date) {
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author ty
	 * @createdate 2012-9-23 下午3:24:34
	 * @Description: 将Long值格式化
	 * @param df
	 * @param date
	 * @return
	 */
	public static String parseDateToString(DateFormat df, Date date) {
		return df.format(date);
	}
	
	public static String parseDateToString( Date date) {
		return df.format(date);
	}

	/**
	 * @author ty
	 * @createdate 2012-9-22 下午4:40:30
	 * @Description: 指定的日期返回日期(yyyy-MM-dd)、年、月、日、星期几
	 * @param calendar
	 * @return
	 */
	public static MCalendar getDateStyle(Calendar calendar) {
		Date date = calendar.getTime();
		String datestr = parseDateToString(sdf1, date);
		String[] da = datestr.split("_");
		String[] weeks = getWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		MCalendar cal = new MCalendar();
		cal.setYear(da[0]);
		cal.setMonth(da[1]);
		cal.setDay(da[2]);
		cal.setWeek(weeks[0]);
		cal.setWeekStr(weeks[1]);
		cal.setDate(datestr);
		weeks = null;
		da = null;
		return cal;
	}

	/**
	 * @author ty
	 * @createdate 2012-9-22 下午4:47:08
	 * @Description: 获取星期几
	 * @param weekNum
	 * @return
	 */
	public static String[] getWeek(int weekNum) {
		String[] week = null;
		switch (weekNum) {
		case 0:
			week = new String[] { SUNDAY, SUNDAYS };
			break;
		case 1:
			week = new String[] { MONDAY, MONDAYS };
			break;
		case 2:
			week = new String[] { TUESDAY, TUESDAYS };
			break;
		case 3:
			week = new String[] { WEDNESDAY, WEDNESDAYS };
			break;
		case 4:
			week = new String[] { THURSDAY, THURSDAYS };
			break;
		case 5:
			week = new String[] { FRIDAY, FRIDAYS };
			break;
		case 6:
			week = new String[] { SATURDAY, SATURDAYS };
			break;
		}
		return week;
	}

	private static String getWeekDay(int weekNum) {
		String week = null;
		switch (weekNum) {
		case 0:
			week = SUNDAYS;
			break;
		case 1:
			week = MONDAYS;
			break;
		case 2:
			week = TUESDAYS;
			break;
		case 3:
			week = WEDNESDAYS;
			break;
		case 4:
			week = THURSDAYS;
			break;
		case 5:
			week = FRIDAYS;
			break;
		case 6:
			week = SATURDAYS;
			break;
		}
		return week;
	}

	/**
	 * @author ty
	 * @createdate 2012-9-28 下午3:42:07
	 * @Description: 获得日期的星期数
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return getWeekDay(w);
	}

	/**
	 * @author bo.wang
	 * @createdate 2012-10-9 下午4:46:59
	 * @Description 获取几天前的Date
	 * @param d
	 *            传递进来的Date
	 * @param day
	 *            第几天
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-9 下午4:47:14
	 * @Description 获取几天后的Date
	 * @param day
	 *            第几天
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-26 下午2:48:04
	 * @Description 将string转换成Date，String格式为“2012-10-26”
	 * @param dateString
	 *            需要转换的字符串
	 * @return 返回转换完成的Date，如果转换失败，返回null
	 */
	public static Date parseStringToDate(String dateString) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	/**
	 * @author ty
	 * @createdate 2013-4-26 下午3:51:48
	 * @Description: date转换时间戳
	 * @return
	 * @throws ParseException
	 */
	public static long getTimestamp(Date date1) throws ParseException {
		Date date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("1970/01/01 08:00:00");
		long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime() - date2.getTime() : date2.getTime() - date1.getTime();
		long rand = (int) (Math.random() * 1000);
		return rand;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-10-31 下午7:46:42
	 * @Description: 将字符串转化为日期
	 * @param dateStr
	 * @param
	 * @return
	 */
	public static Date parseStringToDate(String dateStr, DateFormat df) {
		try {
			Date date = df.parse(dateStr);
			return date;
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-26 下午3:56:55
	 * @Description 将time转换成long型
	 * @param df
	 *            格式化对象
	 * @param time
	 *            需要转换的字符串
	 * @param
	 */
	public static long parseStringToLong(DateFormat df, String time) {
		try {
			return df.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 
	 * @autor bo.wang
	 * @createdate 2012-11-26 下午05:52:57
	 * @Description 获取今天后第day天0点时间点
	 */
	public static long getTodayZeroTime(int day) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);

		LogUtil.d("getTodayZeroTime==>" + f.format(cal.getTime()));

		return cal.getTimeInMillis();
	}

	/**
	 * @author ty
	 * @createdate 2013-4-26 下午3:46:12
	 * @Description: 获取今天前第N年0点时间点
	 * @param day
	 *            前多少年
	 * @return
	 */
	public static long getTodayZeroYeay(int day) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);

		LogUtil.d("getTodayZeroTime==>" + f.format(cal.getTime()));

		return cal.getTimeInMillis();
	}

	/**
	 * @author ty
	 * @createdate 2012-12-6 下午5:46:54
	 * @Description: 获取一年后时间yyyy-MM-dd
	 * @return
	 */
	public static String aYearLater() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return TimeUtil.parseDateToString(TimeUtil.sdf1, cal.getTime());
	}

	public static String aYearLater20() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, -20);
		return TimeUtil.parseDateToString(TimeUtil.sdf1, cal.getTime());
	}

	public static String aYearBefor20() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, 20);
		return TimeUtil.parseDateToString(TimeUtil.sdf1, cal.getTime());
	}

	/**
	 * 
	 * @autor bo.wang
	 * @createdate 2013-1-8 上午10:21:22
	 * @Description 计算历时
	 */
	public static String computeTook(int time) {
		StringBuilder builder = new StringBuilder();
		if (time >= 60) { // 大于或等于一个小时
			int hours = time / 60; // 小时数
			builder.append(hours);
			builder.append("小时");
		}

		int minites = time % 60; // 分钟数
		if (minites > 0) {
			builder.append(minites);
			builder.append("分");
		}

		return builder.toString();
	}
	
	/**
	 * 
	 * @author hxp
	 * @createdate 2014-1-16 下午4:18:55
	 * @Description: 用来处理评论时间
	 * @param time
	 * @return
	 *
	 */
	public static String commentTime(String time) {
		String[] str=time.split(" ");
		String now = TimeUtil.parseDateToString(TimeUtil.sdf1, new Date());
		if(!str[0].equals(now)){
			return time;
		}
		String[] str1 = str[0].split("-");
		String[] str2 = new String[]{
				"0","0","0"
		};
		if(str[1]!=null){
			str2=str[1].split(":");
		}
		long start=System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]) - 1, Integer.parseInt(str1[2]), Integer.parseInt(str2[0]), Integer.parseInt(str2[1]));
		long end = calendar.getTimeInMillis();
		long cha = start-end;
		int day = (int) (cha / (1000 * 60 * 60 * 24));
		int hour = (int) ((cha % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		int minute = (int) (((cha % (1000 * 60 * 60 * 24)) % (1000 * 60 * 60)) / (1000 * 60))+1;
		if(hour==0){
			if(minute<=0){
				return "刚刚";
			}
			return minute+"分钟前";
		}
		return hour+"小时"+minute+"分钟前";
	}
	/**
	 * 
	 * @author wxy
	 * @createdate 2014-3-14 上午9:52:16
	 * @Description: (将时间转换成秒)
	 * @param
	 * @return
	 *
	 */
	public static String timetoSecond(String time) {
		String[] str=time.split(" ");
		String now = TimeUtil.parseDateToString(TimeUtil.sdf1, new Date());
		if(!str[0].equals(now)){
			return time;
		}
		String[] str1 = str[0].split("-");
		String[] str2 = new String[]{
				"0","0","0"
		};
		if(str[1]!=null){
			str2=str[1].split(":");
		}
		long start=System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]) - 1, Integer.parseInt(str1[2]), Integer.parseInt(str2[0]), Integer.parseInt(str2[1]));
		long end = calendar.getTimeInMillis();
		long cha = start-end;
		int day = (int) (cha / (1000 * 60 * 60 * 24));
		int hour = (int) ((cha % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		int minute = (int) (((cha % (1000 * 60 * 60 * 24)) % (1000 * 60 * 60)) / (1000 * 60))+1;
		if(hour==0){
			if(minute<=0){
				return "刚刚";
			}
			return minute+"分钟前";
		}
		return hour+"小时"+minute+"分钟前";
	}
	/**
	 * 
	 * @author wxy
	 * @createdate 2014-3-17 上午9:27:03
	 * @Description: (去掉一段时间的毫秒)
	 * @param time
	 * @return
	 *
	 */
	public static String cutMilSecond(String time){
		String[] str=time.split("\\.");
		return str[0];
	}
}

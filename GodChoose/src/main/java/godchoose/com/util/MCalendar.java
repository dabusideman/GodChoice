package godchoose.com.util;

import java.io.Serializable;


/**
 * 日历
 */
public class MCalendar implements Serializable {
	private static final long serialVersionUID = -9061712056931103718L;
	private String year; // 年
	private String month; // 月
	private String day; // 日
	private String week; // 星期
	private String weekStr; // 星期
	private String date; // yyyy-MM-dd

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String selectMonth) {
		this.month = selectMonth + "月";
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeekStr() {
		return weekStr;
	}

	public void setWeekStr(String weekStr) {
		this.weekStr = weekStr;
	}
}

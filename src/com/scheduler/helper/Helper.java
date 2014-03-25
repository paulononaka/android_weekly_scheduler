package com.scheduler.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.TypedValue;

public class Helper {
	
	public static String getStringMonth(Date date) {
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
		return month_date.format(cal.getTime());	
	}
	
	public static String getStringDayOfWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEEE");
		return sdf.format(date);
	}

	public static String getStringDayOfMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(date);
	}
	
	public static int shifMonth() {
		Calendar firstDayOfMonth = Calendar.getInstance();
		firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
		int dayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek - 1; // SUNDAY = 1..SATURDAY = 7
	}
	
	public static int numberOfWeeksWithShift() {
		Calendar calendar = Calendar.getInstance();
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return (int) Math.ceil((double) (Helper.shifMonth() + daysInMonth) / 7);
	}

	public static int dipValue(Context context, int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources()
				.getDisplayMetrics());
	}
}

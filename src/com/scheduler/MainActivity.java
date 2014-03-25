package com.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.scheduler.adapter.WeekPageAdapter;
import com.scheduler.helper.Helper;
import com.scheduler.model.ScheduledItem;

public class MainActivity extends FragmentActivity {

	Calendar calendarToday = GregorianCalendar.getInstance();
	Calendar calendarMonth = GregorianCalendar.getInstance();
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		fillPager();

		moveToThisWeek();
		
		setTitle(Helper.getStringMonth(Calendar.getInstance().getTime()));
	}

	protected void fillPager() {
		ArrayList<ScheduledItem> items = new ArrayList<ScheduledItem>();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.set(Calendar.HOUR_OF_DAY, 1);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 9);
		endDate.set(Calendar.MINUTE, 0);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_dialog_map));

		startDate.add(Calendar.DAY_OF_MONTH, 2);
		endDate.add(Calendar.DAY_OF_MONTH, 2);
		
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 4);
		endDate.set(Calendar.MINUTE, 0);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_lock_silent_mode_off));
		
		startDate.add(Calendar.DAY_OF_MONTH, 2);
		endDate.add(Calendar.DAY_OF_MONTH, 2);
		
		startDate.set(Calendar.HOUR_OF_DAY, 2);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 7);
		endDate.set(Calendar.MINUTE, 0);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_lock_idle_charging));
		
		startDate.add(Calendar.DAY_OF_MONTH, 3);
		endDate.add(Calendar.DAY_OF_MONTH, 3);
		
		startDate.set(Calendar.HOUR_OF_DAY, 5);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 13);
		endDate.set(Calendar.MINUTE, 30);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_dialog_info));

		startDate.add(Calendar.DAY_OF_MONTH, 2);
		endDate.add(Calendar.DAY_OF_MONTH, 2);
		
		startDate.set(Calendar.HOUR_OF_DAY, 8);
		startDate.set(Calendar.MINUTE, 30);
		endDate.set(Calendar.HOUR_OF_DAY, 11);
		endDate.set(Calendar.MINUTE, 00);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_dialog_map));

		startDate.add(Calendar.DAY_OF_MONTH, 2);
		endDate.add(Calendar.DAY_OF_MONTH, 2);
		
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 4);
		endDate.set(Calendar.MINUTE, 0);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_dialog_email));

		startDate.add(Calendar.DAY_OF_MONTH, 3);
		endDate.add(Calendar.DAY_OF_MONTH, 3);
		
		startDate.set(Calendar.HOUR_OF_DAY, 11);
		startDate.set(Calendar.MINUTE, 30);
		endDate.set(Calendar.HOUR_OF_DAY, 14);
		endDate.set(Calendar.MINUTE, 00);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_dialog_info));
		
		startDate.add(Calendar.DAY_OF_MONTH, 3);
		endDate.add(Calendar.DAY_OF_MONTH, 3);
		
		startDate.set(Calendar.HOUR_OF_DAY, 1);
		startDate.set(Calendar.MINUTE, 30);
		endDate.set(Calendar.HOUR_OF_DAY, 11);
		endDate.set(Calendar.MINUTE, 00);
		items.add(createMock(startDate, endDate, android.R.drawable.ic_dialog_alert));
		
		WeekPageAdapter adapter = new WeekPageAdapter(getSupportFragmentManager(), items);
		viewPager.setAdapter(adapter);
	}
	
	private ScheduledItem createMock(Calendar startDate, Calendar endDate, int ic) {
		ScheduledItem item = new ScheduledItem();
		item.setStartDate(startDate.getTime());
		item.setEndDate(endDate.getTime());
		item.setIcon(getResources().getDrawable(ic));
		return item;
	}

	private void moveToThisWeek() {
		Calendar calendar = Calendar.getInstance();
		int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
		int position = (int) (Helper.shifMonth() + thisDay) / 7;

		viewPager.setCurrentItem(position);
	}
}

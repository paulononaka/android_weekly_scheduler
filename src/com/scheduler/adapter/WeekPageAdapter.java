package com.scheduler.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scheduler.fragment.WeekFragment;
import com.scheduler.helper.Helper;
import com.scheduler.model.ScheduledItem;

public class WeekPageAdapter extends FragmentPagerAdapter {

	private ArrayList<ScheduledItem> mItems;
	private int numberOfPages;

	public WeekPageAdapter(FragmentManager manager, ArrayList<ScheduledItem> items) {
		super(manager);
		mItems = items;
		numberOfPages = Helper.numberOfWeeksWithShift();
	}

	@Override
	public int getCount() {
		return numberOfPages;
	}

	@Override
	public Fragment getItem(int position) {

		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.DAY_OF_MONTH, 1);
		int shift = Helper.shifMonth() * -1;
		startDate.add(Calendar.DAY_OF_YEAR, shift + position * 7);

		return new WeekFragment(startDate.getTime(), mItems);
	}

}

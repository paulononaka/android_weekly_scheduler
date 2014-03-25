package com.scheduler.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scheduler.R;
import com.scheduler.helper.Constants;
import com.scheduler.helper.Helper;
import com.scheduler.model.ScheduledItem;
import com.scheduler.view.WeekdayVerticalBar;

public class WeekFragment extends Fragment {

	private Date mStartDate;
	private List<ScheduledItem> mItems;

	private int widthCell;
	private int hourWidth;
	private int hourHeight;
	private int hourHeightLine;

	public WeekFragment(Date startDate, List<ScheduledItem> items) {
		mStartDate = startDate;

		if (items == null)
			mItems = new ArrayList<ScheduledItem>();
		else
			mItems = items;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		widthCell = Helper.dipValue(getActivity().getBaseContext(), 48);
		hourWidth = Helper.dipValue(container.getContext(), Constants.HOUR_WIDTH);
		hourHeight = Helper.dipValue(container.getContext(), Constants.HOUR_HEIGHT);
		hourHeightLine = Helper.dipValue(container.getContext(), Constants.HOUR_HEIGHT_LINE);
		
		View weekdayView = inflater.inflate(R.layout.weekday, null);
		LinearLayout weekdayHeader = (LinearLayout) weekdayView.findViewById(R.id.weekdayHeader);
		LinearLayout weekdayHorizontalBar = (LinearLayout) weekdayView.findViewById(R.id.weekdayHorizontalBar);

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(mStartDate);

		createHoursRuler(weekdayHorizontalBar);

		LinearLayout verticalHourCel = new LinearLayout(weekdayHorizontalBar.getContext());
		weekdayHeader.addView(verticalHourCel, 0, new RelativeLayout.LayoutParams(hourWidth,
				LayoutParams.MATCH_PARENT));

		for (int i = 0; i < 7; i++) {
			createHeader(inflater, weekdayHeader, calendar);
			createWeekdayBar(container.getContext(), calendar.getTime(), weekdayHorizontalBar);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}

		return weekdayView;
	}

	private void createHoursRuler(LinearLayout weekdayHorizontalBar) {

		LinearLayout verticalHourCell = new LinearLayout(weekdayHorizontalBar.getContext());
		verticalHourCell.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, hourHeightLine);
		LinearLayout.LayoutParams hourHeightLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, hourHeight);
		
		for (int i = Constants.INITIAL_HOUR; i <= Constants.END_HOUR; i++) {
			LinearLayout hourHeightLayout = new LinearLayout(weekdayHorizontalBar.getContext());
			TextView textView = new TextView(weekdayHorizontalBar.getContext());
			textView.setTextColor(Color.GRAY);
			textView.setText(String.format("%02d:00", i));
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			hourHeightLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			hourHeightLayout.addView(textView, textViewParams);

			verticalHourCell.addView(hourHeightLayout, hourHeightLayoutParams);
		}
		
		weekdayHorizontalBar.addView(verticalHourCell, 0, new RelativeLayout.LayoutParams(hourWidth,
				LayoutParams.MATCH_PARENT));
	}

	private void createHeader(LayoutInflater inflater, LinearLayout weekdayHeader, Calendar calDate) {

		View viewHeader = inflater.inflate(R.layout.weekday_header, null);

		TextView dayOfWeekTextView = (TextView) viewHeader.findViewById(R.id.dayOfWeek);
		dayOfWeekTextView.setText(Helper.getStringDayOfWeek(calDate.getTime()));

		TextView dayOfMonthTextView = (TextView) viewHeader.findViewById(R.id.dayOfMonth);
		dayOfMonthTextView.setText(Helper.getStringDayOfMonth(calDate.getTime()));

		if (DateUtils.isToday(calDate.getTime().getTime())) {
			dayOfMonthTextView.setBackgroundResource(R.drawable.background_oval);
			dayOfMonthTextView.setTextColor(Color.WHITE);
		}
		if (IsWeekend(calDate)) {
			dayOfWeekTextView.setTextColor(getResources().getColor(R.color.dark_gray));
			dayOfMonthTextView.setTextColor(getResources().getColor(R.color.dark_gray));
		}
		if (IsDifferentMonth(calDate)) {
			dayOfWeekTextView.setTextColor(getResources().getColor(R.color.light_gray));
			dayOfMonthTextView.setTextColor(getResources().getColor(R.color.light_gray));
		}

		weekdayHeader.addView(viewHeader);
	}

	private void createWeekdayBar(Context context, Date date, LinearLayout weekdayHorizontalBar) {
		WeekdayVerticalBar verticalBar = new WeekdayVerticalBar(context, date, mItems);
		weekdayHorizontalBar
				.addView(verticalBar, new RelativeLayout.LayoutParams(widthCell, LayoutParams.WRAP_CONTENT));
	}

	private static boolean IsWeekend(Calendar calDate) {
		if (calDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			return true;
		if (calDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return true;
		return false;
	}

	private static boolean IsDifferentMonth(Calendar calDate) {
		Calendar today = Calendar.getInstance();
		if (calDate.get(Calendar.MONTH) != today.get(Calendar.MONTH))
			return true;
		return false;
	}
}
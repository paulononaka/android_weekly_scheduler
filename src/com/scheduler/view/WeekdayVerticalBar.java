package com.scheduler.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scheduler.R;
import com.scheduler.helper.Constants;
import com.scheduler.helper.Helper;
import com.scheduler.model.ScheduledItem;

public class WeekdayVerticalBar extends RelativeLayout {

	private int HOUR_HEIGHT;
	private int HOUR_HEIGHT_LINE;

	private Date mDate;
	private List<ScheduledItem> mItems;

	public WeekdayVerticalBar(Context context, Date date, List<ScheduledItem> items) {
		super(context);
		mDate = date;

		if (items == null)
			mItems = new ArrayList<ScheduledItem>();
		else
			mItems = items;

		createDayLayout();
	}

	private void createDayLayout() {
		HOUR_HEIGHT = Helper.dipValue(getContext(), Constants.HOUR_HEIGHT);
		HOUR_HEIGHT_LINE = Helper.dipValue(getContext(), Constants.HOUR_HEIGHT_LINE);
		createHoursCell();
		createItems();
	}

	private void createHoursCell() {

		LinearLayout verticalHourCell = new LinearLayout(getContext());
		verticalHourCell.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams bg1Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, HOUR_HEIGHT_LINE/2-1);
		LinearLayout.LayoutParams bg2Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, HOUR_HEIGHT);
		LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
		LinearLayout.LayoutParams bg1LayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams bg2LayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, HOUR_HEIGHT);
		LinearLayout.LayoutParams hourHeightLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					HOUR_HEIGHT);
		
		Drawable bgResource = getResources().getDrawable(R.drawable.bg_scheduler_repeat);
		
		for (int i = Constants.INITIAL_HOUR; i <= Constants.END_HOUR; i++) {

			LinearLayout bg1Layout = new LinearLayout(getContext());
			bg1Layout.setPadding(5, 0, 5, 0);
			View bg1 = new View(getContext());
			bg1.setBackground(bgResource);
			bg1Layout.addView(bg1, bg1Params);
			
			LinearLayout bg2Layout = new LinearLayout(getContext());
			bg2Layout.setPadding(5, 0, 5, 0);
			View bg2 = new View(getContext());
			bg2.setBackground(bgResource);
			bg2Layout.addView(bg2, bg2Params);

			LinearLayout hourHeightLayout = new LinearLayout(getContext());
			hourHeightLayout.setOrientation(LinearLayout.VERTICAL);

			LinearLayout lineLayout = new LinearLayout(getContext());
			View lineView = new View(getContext());
			lineView.setBackgroundColor(Color.GRAY);
			lineLayout.addView(lineView, lineParams);

			hourHeightLayout.addView(bg1Layout, bg1LayoutParams);
			hourHeightLayout.addView(lineLayout, lineLayoutParams);
			hourHeightLayout.addView(bg2Layout, bg2LayoutParams);

			verticalHourCell.addView(hourHeightLayout, hourHeightLayoutParams);
		}

		addView(verticalHourCell, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	private void createItems() {
		
		Calendar startCalendar = GregorianCalendar.getInstance();
		startCalendar.setTime(mDate);
		startCalendar.set(Calendar.HOUR_OF_DAY, 0);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		Date startDate = startCalendar.getTime();

		Calendar endCalendar = GregorianCalendar.getInstance();
		endCalendar.setTime(mDate);
		endCalendar.set(Calendar.HOUR_OF_DAY, 23);
		endCalendar.set(Calendar.MINUTE, 59);
		endCalendar.set(Calendar.SECOND, 59);
		Date endDate = endCalendar.getTime();

		for (ScheduledItem item : mItems) {
			if ((startDate.compareTo(item.getEndDate()) < 0 && startDate.compareTo(item.getStartDate()) >= 0)
					|| (startDate.compareTo(item.getStartDate()) < 0 && endDate.compareTo(item.getStartDate()) > 0)) {

				LinearLayout linearLayout = new LinearLayout(getContext());
				linearLayout.setOrientation(LinearLayout.HORIZONTAL);
				linearLayout.setGravity(Gravity.CENTER);
				
				if (Build.VERSION.SDK_INT < 16)
					linearLayout.setBackgroundDrawable(getGradientDrawable());
				else
					linearLayout.setBackground(getGradientDrawable());

				ImageView imageView = new ImageView(getContext());
				imageView.setImageDrawable(item.getIcon());
				linearLayout.addView(imageView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						calculateHeight(item));
				layoutParams.setMargins(10, calculateRow(item), 10, 0);
				
				addView(linearLayout, layoutParams);
			}
		}
	}

	private int calculateHeight(ScheduledItem item) {
		int sizeMinutes = HOUR_HEIGHT / 60;
		int minutes = (int) ((item.getEndDate().getTime() / 60000) - (item.getStartDate().getTime() / 60000));

		return minutes * sizeMinutes;
	}

	private int calculateRow(ScheduledItem item) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(item.getStartDate());
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minutesOfHour = calendar.get(Calendar.MINUTE);

		return ((hourOfDay - Constants.INITIAL_HOUR) * HOUR_HEIGHT) + (minutesOfHour * (HOUR_HEIGHT / 60) + HOUR_HEIGHT_LINE/2);
	}

	public Date getDate() {
		return mDate;
	}

	public LayerDrawable getGradientDrawable() {
		Drawable[] layers = new Drawable[2];

		ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
		shapeDrawable.getPaint().setColor(Color.GRAY);
		shapeDrawable.getPaint().setStyle(Style.STROKE);
		shapeDrawable.getPaint().setStrokeWidth(1);
		shapeDrawable.getPaint().setPathEffect(new CornerPathEffect(20.0f));
		shapeDrawable.getPaint().setAntiAlias(true);

		GradientDrawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] {
				Color.parseColor("#eeeeee"), Color.parseColor("#d7d7d7") });
		gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		gradientDrawable.setGradientRadius(90.0f);
		gradientDrawable.setCornerRadius(20.0f);

		layers[1] = shapeDrawable;
		layers[0] = gradientDrawable;

		return new LayerDrawable(layers);
	}

}
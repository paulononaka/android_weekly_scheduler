package com.scheduler.model;

import java.util.Date;
import java.util.UUID;

import android.graphics.drawable.Drawable;

public class ScheduledItem {
	
	private UUID code;
	private Date startDate;
	private Date endDate;
	private Drawable icon;

	public ScheduledItem() {
		code = UUID.randomUUID();
	}

	public UUID getCode() {
		return code;
	}

	public void setCode(UUID code) {
		this.code = code;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
}
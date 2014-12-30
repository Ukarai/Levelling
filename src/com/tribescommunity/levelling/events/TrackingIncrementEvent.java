package com.tribescommunity.levelling.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.tribescommunity.levelling.achievements.Tracking;
import com.tribescommunity.levelling.data.user.User;

public class TrackingIncrementEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	private User user;
	private Tracking tracking;
	private int increment;

	public TrackingIncrementEvent(User user, Tracking tracking, int increment) {
		this.user = user;
		this.tracking = tracking;
		this.increment = increment;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public User getUser() {
		return user;
	}

	public Tracking getTracking() {
		return tracking;
	}

	public int getIncrement() {
		return increment;
	}

}

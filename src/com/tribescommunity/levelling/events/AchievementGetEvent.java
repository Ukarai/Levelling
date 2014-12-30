package com.tribescommunity.levelling.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.tribescommunity.levelling.achievements.Ach;
import com.tribescommunity.levelling.data.user.User;

public class AchievementGetEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	private User user;
	private Ach achievement;

	public AchievementGetEvent(User user, Ach achievement) {
		this.user = user;
		this.achievement = achievement;
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

	public Ach getAchievement() {
		return achievement;
	}

}

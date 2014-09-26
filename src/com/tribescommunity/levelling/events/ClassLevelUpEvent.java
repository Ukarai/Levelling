package com.tribescommunity.levelling.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.user.User;

public class ClassLevelUpEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private User user;
	private int level;
	private LevellingClass lClass;

	public ClassLevelUpEvent(User user, int level, LevellingClass lClass) {
		this.user = user;
		this.level = level;
		this.lClass = lClass;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public LevellingClass getlClass() {
		return lClass;
	}

	public int getLevel() {
		return level;
	}

	public User getUser() {
		return user;
	}

}

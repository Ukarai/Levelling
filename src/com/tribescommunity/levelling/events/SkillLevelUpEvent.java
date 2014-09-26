package com.tribescommunity.levelling.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

public class SkillLevelUpEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	private User user;
	private Skill skill;
	private int oldLevel;
	private int newLevel;

	public SkillLevelUpEvent(User user, Skill skill, int oldLevel, int newLevel) {
		this.user = user;
		this.skill = skill;
		this.oldLevel = oldLevel;
		this.newLevel = newLevel;
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

	public Skill getSkill() {
		return skill;
	}

	public int getOldLevel() {
		return oldLevel;
	}

	public int getNewLevel() {
		return newLevel;
	}

}

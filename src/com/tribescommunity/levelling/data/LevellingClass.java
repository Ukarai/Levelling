package com.tribescommunity.levelling.data;

import static com.tribescommunity.levelling.data.Skill.*;

import org.bukkit.ChatColor;

import com.tribescommunity.levelling.data.user.User;

public enum LevellingClass {

	ARCHER(ChatColor.GREEN, "Archer", ARCHERY, WOODCUTTING, ARCHAEOLOGY),
	THIEF(ChatColor.DARK_BLUE, "Thief", UNARMED, LOCKPICKING, PICKPOCKETING),
	CRUSADER(ChatColor.YELLOW, "Crusader", SWORDS, MINING, REPAIR),
	GATHERER(ChatColor.BLUE, "Gatherer", MINING, ARCHAEOLOGY, WOODCUTTING),
	BLACKSMITH(ChatColor.DARK_GRAY, "Blacksmith", REPAIR, SWORDS, MINING),
	CITIZEN(ChatColor.RED, "Citizen", BUILDING, COOKING, UNARMED),
	FARMER(ChatColor.GOLD, "Farmer", FARMING, GOLDPANNING, BUILDING);

	private Skill primary;
	private Skill secondary;
	private Skill teritary;
	private ChatColor colour;
	private String name;

	private LevellingClass(ChatColor colour, String name, Skill primary, Skill secondary, Skill tertiary) {
		this.colour = colour;
		this.primary = primary;
		this.secondary = secondary;
		this.teritary = tertiary;
		this.name = name;
	}

	public Skill getPrimary() {
		return primary;
	}

	public Skill getSecondary() {
		return secondary;
	}

	public Skill getTeritary() {
		return teritary;
	}

	public ChatColor getColour() {
		return colour;
	}

	public static LevellingClass getFromName(String name) {
		for (LevellingClass lc : values()) {
			if (lc.getName().equalsIgnoreCase(name)) {
				return lc;
			}
		}
		return null;
	}

	public boolean hasRequirments(User user, int level) {
		boolean prime = user.getLevel(getPrimary()) >= 15 * level;
		boolean sec = user.getLevel(getSecondary()) >= 10 * level;
		boolean tir = user.getLevel(getTeritary()) >= 5 * level;

		return prime && sec && tir;
	}

	public String getName() {
		return name;
	}
	
	public String getTag() {
		return getColour() + "[" + getName() + "]" + ChatColor.WHITE;
	}
}

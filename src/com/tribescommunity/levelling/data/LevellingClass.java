package com.tribescommunity.levelling.data;

import static com.tribescommunity.levelling.data.Skill.*;

import org.bukkit.ChatColor;

import com.tribescommunity.levelling.data.user.User;

public enum LevellingClass {
	ARCHER(ChatColor.GREEN, "Archer", "Marksman", "Deadeye", ARCHERY, WOODCUTTING, ARCHAEOLOGY),
	THIEF(ChatColor.DARK_BLUE, "Thief", "Bandit", "Rogue", UNARMED, LOCKPICKING, PICKPOCKETING),
	CRUSADER(ChatColor.YELLOW, "Crusader", "Paladin", "Champion", SWORDS, MINING, REPAIR),
	GATHERER(ChatColor.BLUE, "Gatherer", "Hoarder", "Connoisseur", MINING, ARCHAEOLOGY, WOODCUTTING),
	BLACKSMITH(ChatColor.DARK_GRAY, "Blacksmith", "Journeyman", " Mastersmith", REPAIR, SWORDS, MINING),
	CITIZEN(ChatColor.RED, "Citizen", "Settler", "Pioneer", BUILDING, COOKING, UNARMED),
	FARMER(ChatColor.GOLD, "Farmer", "Harvester", "Agronomist", FARMING, ARCHAEOLOGY, BUILDING);

	public static final int MAX_LEVEL = 9;
	public static final double PRIMARY_MODIFIER = 1.2;
	public static final double SECONDARY_MODIFIER = 1.15;
	public static final double TERTIARY_MODIFIER = 1.1;

	private Skill primary;
	private Skill secondary;
	private Skill teritary;
	private ChatColor colour;
	private String name;
	private String nameTwo;
	private String nameThree;

	private LevellingClass(ChatColor colour, String name, String nameTwo, String nameThree, Skill primary, Skill secondary, Skill tertiary) {
		this.colour = colour;
		this.primary = primary;
		this.secondary = secondary;
		this.teritary = tertiary;
		this.name = name;
		this.nameTwo = nameTwo;
		this.nameThree = nameThree;
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
			for (int i = 0; i < MAX_LEVEL; i += 3) {
				if (lc.getName(i).equalsIgnoreCase(name)) {
					return lc;
				}
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

	public String getName(int i) {
		if (i >= 0 && i < 3) {
			return name;
		} else if (i >= 3 && i < 6) {
			return nameTwo;
		} else if (i >= 6) {
			return nameThree;
		} else {
			return name;
		}
	}

	public String getNameTwo() {
		return nameTwo;
	}

	public String getNameThree() {
		return nameThree;
	}
}

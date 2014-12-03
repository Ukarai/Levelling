package com.tribescommunity.levelling.data.user;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.party.Party;

/* 
 * Date: 15 Nov 2012
 * Time: 20:10:07
 * Maker: theguynextdoor
 */
public class User {

	public Levelling plugin;
	private String name;
	private LevelStore levelStore;
	public LevellingClass levellingClass;
	private Party party;
	private Set<String> recentlyPickpocketed = new HashSet<String>();
	public boolean recentLockpickFail = false;

	public User(Levelling instance, String name) {
		plugin = instance;
		this.name = name;
		levelStore = new LevelStore(this);

		for (Skill skill : Skill.values()) {
			levelStore.level.put(skill, 0);
			levelStore.xp.put(skill, 0L);
		}

		plugin.getUsers().put(name, this);
	}

	public User(String fromFile, Levelling plugin) {
		this.plugin = plugin;
		String[] split = fromFile.split(":");
		this.name = split[0];
		levelStore = new LevelStore(this);

		for (Skill skill : Skill.values()) {
			levelStore.setXp(skill, split.length > skill.getPos() ? Long.parseLong(split[skill.getPos()]) : 0);
			levelStore.setLevel(skill, split.length > skill.getPos() ? Integer.parseInt(split[skill.getPos() + 1]) : 0);
		}

		levellingClass = LevellingClass.getFromName(split[2]);
		levelStore.setClassLevel(levellingClass != null ? Integer.parseInt(split[3]) : 0);

		if (!split[1].equals("")) {
			if (plugin.parties.containsKey(split[1])) {
				joinParty(plugin.parties.get(split[1]));
			} else {
				Party party = new Party(plugin, split[1]);
				joinParty(party);
			}
		}
	}

	public String getName() {
		return name;
	}

	/*
	 * Xp and Levels
	 */
	public long getXpForLevel(int level) {
		long points = 0;
		long output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300 * Math.pow(2, lvl / 7.0));
			output = (points / 4);
		}

		return output;
	}

	public int getLevel(Skill skill) {
		return levelStore.getLevel(skill);
	}

	public long getXp(Skill skill) {
		return levelStore.getXp(skill);
	}

	@SuppressWarnings("deprecation")
	public void addXp(Skill skill, long xp) {
		if (Bukkit.getPlayerExact(name) != null) {
			Player player = Bukkit.getPlayerExact(name);

			if (player.getGameMode() == GameMode.CREATIVE) {
				return;
			}
		}
		if (hasClass()) {
			Skill primary = levellingClass.getPrimary();
			Skill secondary = levellingClass.getSecondary();
			Skill tertiary = levellingClass.getTeritary();

			if (primary == skill || secondary == skill || tertiary == skill) {
				xp *= 1.15;
				xp = (long) Math.floor(xp);
			}
		}
		levelStore.addXp(skill, xp);
	}

	public long getXpToNextLevel(Skill skill) {
		return LevelStore.xpPerLevel[getLevel(skill) + 1];
	}

	public Integer getTotalLevel() {
		int total = 0;

		for (Skill skill : Skill.values()) {
			total += getLevel(skill);
		}

		return total;
	}

	public Set<String> getRecentlyPickpocketed() {
		return recentlyPickpocketed;
	}

	/*
	 * Levelling class
	 */
	public void checkClassLevel() {
		while (levellingClass.hasRequirments(this, levelStore.getClassLevel() + 1)) {
			levelStore.classLevelUp();
		}
	}

	public boolean hasClass() {
		return levellingClass != null;
	}

	public int getClassLevel() {
		return levelStore.getClassLevel();
	}

	public LevellingClass getLevellingClass() {
		return levellingClass;
	}

	@SuppressWarnings("deprecation")
	public void untrainClass() {
		if (hasClass()) {
			String currentPrefix = plugin.getChat().getGroupPrefix("world", plugin.getChat().getPlayerGroups("world", name)[0]);
			String removeTag = currentPrefix.replace(getLevellingClass().getTag(), "");
			OfflinePlayer op = Bukkit.getOfflinePlayer(name);

			plugin.getChat().setPlayerPrefix("world", op, removeTag);
		}
		levellingClass = null;
	}

	@SuppressWarnings("deprecation")
	public void trainClass(LevellingClass lClass) {
		String currentPrefix = plugin.getChat().getGroupPrefix("world", plugin.getChat().getPlayerGroups("world", name)[0]);
		String addTag = lClass.getTag() + currentPrefix;
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);

		plugin.getChat().setPlayerPrefix("world", op, addTag);
		levellingClass = lClass;

		for (int i = 0; i <= 8; i++) {
			if (lClass.hasRequirments(this, i)) {
				levelStore.setClassLevel(i);
			}
		}
	}

	/*
	 * Party
	 */
	public void joinParty(Party party) {
		party.addPlayer(name);
		this.party = party;
	}

	public void leaveParty() {
		party.removePlayer(name);
		this.party = null;
	}

	public boolean inParty() {
		return party != null;
	}

	public Party getParty() {
		return party;
	}

	/*
	 * Serialization
	 */
	public String getSaveString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(':');
		sb.append(party != null ? party.getName() : "");
		sb.append(':');
		sb.append(levellingClass != null ? levellingClass.getName() : "");
		sb.append(':');
		sb.append(levellingClass != null ? levelStore.getClassLevel() : 0);
		sb.append(':');

		for (int i = 0; i < Skill.values().length; i++) {
			sb.append(levelStore.getXp(Skill.values()[i]));
			sb.append(':');
			sb.append(levelStore.getLevel(Skill.values()[i]));

			if (i < Skill.values().length - 1) {
				sb.append(':');
			}
		}

		return sb.toString();
	}
}

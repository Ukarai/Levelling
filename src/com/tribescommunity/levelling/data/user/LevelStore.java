package com.tribescommunity.levelling.data.user;

import java.util.EnumMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.events.ClassLevelUpEvent;
import com.tribescommunity.levelling.events.SkillLevelUpEvent;

/* 
 * Date: 16 Nov 2012
 * Time: 23:43:37
 * Maker: theguynextdoor
 */
public class LevelStore {
	private String name;
	private User user;
	protected Map<Skill, Integer> level;
	protected Map<Skill, Long> xp;
	private int classLevel = 0;
	protected static long[] xpPerLevel = new long[361];

	static {
		long points = 0;
		long output;

		for (int lvl = 1; lvl <= 120; lvl++) {
			points += Math.floor(lvl + 300 * Math.pow(2, lvl / 7.0));
			output = (points / 4);
			xpPerLevel[lvl] = output;
		}
	}

	public LevelStore(User user) {
		this.name = user.getName();
		this.user = user;

		level = new EnumMap<Skill, Integer>(Skill.class);
		xp = new EnumMap<Skill, Long>(Skill.class);
	}

	public void addXp(Skill skill, long xp) {
		Long toAdd = this.xp.get(skill) + xp;
		this.xp.put(skill, toAdd);
		checkLevelUp(skill);
	}

	public void checkLevelUp(Skill skill) {
		if (xp.get(skill) >= xpPerLevel[level.get(skill) + 1] && level.get(skill) != Levelling.MAX_SKILL_LEVEL) {
			levelUp(skill);
		}
	}

	@SuppressWarnings("deprecation")
	public void levelUp(Skill skill) {
		level.put(skill, level.get(skill) + 1);
		xp.put(skill, xp.get(skill) - xpPerLevel[level.get(skill)]);

		Bukkit.getPluginManager().callEvent(new SkillLevelUpEvent(user, skill, level.get(skill) - 1, level.get(skill)));

		if (Bukkit.getPlayerExact(name) != null) {
			Bukkit.getPlayerExact(name).sendMessage(getLevelUpMsg(skill));
		}

		checkLevelUp(skill);
	}

	public String getLevelUpMsg(Skill skill) {
		String msg = ChatColor.GOLD + "[" + skill.getName() + "]" + ChatColor.WHITE + " Level up!";
		msg += ChatColor.GOLD + " (" + ChatColor.WHITE + level.get(skill) + ChatColor.GOLD + ")";

		return msg;
	}

	public int getLevel(Skill skill) {
		return level.get(skill);
	}

	public void setLevel(Skill skill, int level) {
		this.level.put(skill, level);
	}

	public long getXp(Skill skill) {
		return xp.get(skill);
	}

	public void setXp(Skill skill, long level) {
		this.xp.put(skill, level);
	}

	public long[] getXpPerLevel() {
		return xpPerLevel;
	}

	public int getClassLevel() {
		return classLevel;
	}

	public void setClassLevel(int classLevel) {
		this.classLevel = classLevel;

		if (this.classLevel > LevellingClass.MAX_LEVEL) {
			this.classLevel = LevellingClass.MAX_LEVEL;
		}
	}

	@SuppressWarnings("deprecation")
	public void classLevelUp() {
		classLevel++;
		ClassLevelUpEvent e = new ClassLevelUpEvent(user, getClassLevel(), user.levellingClass);
		Bukkit.getPluginManager().callEvent(e);

		if (Bukkit.getPlayerExact(name) != null) {
			Player player = Bukkit.getPlayerExact(name);
			player.sendMessage(getClassLevelUpMsg(player));
		}
	}

	public String getClassLevelUpMsg(Player player) {
		return ChatColor.GOLD + "[" + user.levellingClass.getName(classLevel) + "] " + ChatColor.WHITE + "Level up!" + ChatColor.GOLD + " (" + ChatColor.WHITE + classLevel
				+ ChatColor.GOLD + ")";
	}
}

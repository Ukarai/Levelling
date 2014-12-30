package com.tribescommunity.levelling.skills.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 20 Nov 2012
 * Time: 15:44:38
 * Maker: theguynextdoor
 */
public class Lockpicking implements LevellingSkill {

	public static final int SUCCESS_XP = 192;
	public static final int FAIL_XP = 64;

	@Override
	public String getName() {
		return Skill.LOCKPICKING.getName();
	}

	public boolean shouldUnlock(User user) {
		return Math.random() <= unlockChance(user.getLevel(Skill.LOCKPICKING));
	}

	public boolean shouldBreak(User user) {
		return Math.random() >= unlockChance(user.getLevel(Skill.LOCKPICKING));
	}

	public double unlockChance(int level) {
		double chance = 0.7 / Levelling.MAX_SKILL_LEVEL;
		int level2 = level;
		if (level2 == 0)
			level2++;
		return chance * level2;
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		table.add("Succes: " + SUCCESS_XP);
		table.add("Fail: " + FAIL_XP);

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by lockpicking an iron door with a lockpick (crafting recipe shown below)";
	}

}

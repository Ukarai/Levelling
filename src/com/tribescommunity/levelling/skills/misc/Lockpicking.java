package com.tribescommunity.levelling.skills.misc;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;

/* 
 * Date: 20 Nov 2012
 * Time: 15:44:38
 * Maker: theguynextdoor
 */
public class Lockpicking extends Skill {

	@Override
	public String getName() {
		return com.tribescommunity.levelling.data.Skill.LOCKPICKING.getName();
	}

	public boolean shouldUnlock(User user) {
		return Math.random() <= unlockChance(user.getLevel(com.tribescommunity.levelling.data.Skill.LOCKPICKING));
	}

	public boolean shouldBreak(User user) {
		return Math.random() >= unlockChance(user.getLevel(com.tribescommunity.levelling.data.Skill.LOCKPICKING));
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

}

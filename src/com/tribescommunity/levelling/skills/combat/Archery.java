package com.tribescommunity.levelling.skills.combat;

import org.bukkit.entity.EntityType;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.skills.Skill;

/* 
 * Date: 30 Nov 2012
 * Time: 23:27:33
 * Maker: theguynextdoor
 */
public class Archery extends Skill {

	@Override
	public String getName() {
		return com.tribescommunity.levelling.data.Skill.ARCHERY.getName();
	}

	public int getXp(EntityType type) {
		switch (type) {
			case PLAYER:
				return 32;
			case ZOMBIE:
				return 16;
			case SKELETON:
			case PIG_ZOMBIE:
				return 24;
			case BAT:
			case CREEPER:
				return 48;
			case ENDERMAN:
				return 64;
			case CAVE_SPIDER:
			case SPIDER:
			case SLIME:
			case MAGMA_CUBE:
			case BLAZE:
				return 32;
			default:
				return 0;
		}
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}
}

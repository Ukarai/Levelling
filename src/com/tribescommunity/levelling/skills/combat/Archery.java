package com.tribescommunity.levelling.skills.combat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.EntityType;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 30 Nov 2012
 * Time: 23:27:33
 * Maker: theguynextdoor
 */
public class Archery implements LevellingSkill {

	@Override
	public String getName() {
		return Skill.ARCHERY.getName();
	}

	public int getXp(EntityType type, int skillLevel) {
		int totalXp = 0;
		int baseXp;

		switch (type) {
		case PLAYER:
			baseXp = 32;
			break;
		case ZOMBIE:
			baseXp = 16;
			break;
		case SKELETON:
		case PIG_ZOMBIE:
			baseXp = 24;
			break;
		case BAT:
		case CREEPER:
			baseXp = 48;
			break;
		case ENDERMAN:
			baseXp = 64;
			break;
		case CAVE_SPIDER:
		case SPIDER:
		case SLIME:
		case MAGMA_CUBE:
		case BLAZE:
			baseXp = 32;
			break;
		default:
			baseXp = 0;
			break;
		}

		totalXp = (int) Math.ceil(baseXp * (1 + (skillLevel / 100)));
		return totalXp;
	}

	@Override
	public RightClickAbility getAbility() {
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		for (EntityType et : EntityType.values()) {
			if (getXp(et, level) > 0) {
				table.add(StringUtils.capitalize(et.toString().toLowerCase()) + ": " + getXp(et, level));
			}
		}

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by shooting and hitting mobs with arrows";
	}
}

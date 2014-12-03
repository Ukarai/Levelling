/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tribescommunity.levelling.skills.misc;

import org.bukkit.Material;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;

/**
 *
 * @author David
 */
public class Building extends Skill {

	public long getXp(int id) {
		switch (id) {
		case 3:
		case 4:
		case 5:
		case 12:
		case 27:
		case 28:
		case 37:
		case 38:
		case 39:
		case 40:
		case 53:
		case 54:
		case 58:
		case 61:
		case 65:
		case 67:
		case 69:
		case 70:
		case 72:
		case 77:
		case 85:
		case 87:
		case 88:
		case 96:
		case 107:
		case 121:
		case 126:
		case 128:
		case 134:
		case 135:
		case 136:
			return 4;
		case 1:
		case 6:
		case 13:
		case 18:
		case 20:
		case 33:
		case 35:
		case 44:
		case 66:
		case 80:
		case 82:
		case 84:
		case 89:
		case 98:
		case 101:
		case 102:
		case 108:
		case 109:
		case 123:
			return 8;
		case 29:
			return 12;
		case 2:
		case 17:
		case 23:
		case 24:
		case 25:
		case 30:
		case 45:
		case 47:
		case 48:
		case 49:
		case 79:
		case 110:
		case 112:
		case 113:
		case 114:
		case 116:
		case 130:
		case 145:
			return 16;
		default:
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public long getXp(Material mat) {
		return getXp(mat.getId());
	}

	public boolean shouldPreserveBlock(User user) {
		double chance = 0.5 / Levelling.MAX_SKILL_LEVEL;
		return Math.random() <= (chance * user.getLevel(com.tribescommunity.levelling.data.Skill.BUILDING));
	}

	@Override
	public String getName() {
		return "Building";
	}

	@Override
	public RightClickAbility getAbility() {
		return null;
	}
}

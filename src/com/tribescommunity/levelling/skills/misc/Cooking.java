package com.tribescommunity.levelling.skills.misc;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.skills.Skill;

public class Cooking extends Skill {

	@Override
	public String getName() {
		return "Cooking";
	}

	public int getFireKillDrops(EntityType type) {
		switch (type) {
			case COW:
				return getFurnaceCookedXp(Material.COOKED_BEEF);
			case PIG:
				return getFurnaceCookedXp(Material.PORK);
			case CHICKEN:
				return getFurnaceCookedXp(Material.COOKED_CHICKEN);
			default:
				return 0;
		}
	}

	public int getFurnaceCookedXp(Material mat) {
		switch (mat) {
			case BAKED_POTATO:
			case COOKED_CHICKEN:
				return 16;
			case COOKED_FISH:
			case COOKED_BEEF:
			case PORK:
				return 32;
			default:
				return 0;
		}
	}

	/*
	 * Cooking by Crafting:
	 * 
	 * Enchanted Golden Apple / 128 xp
	 */

	public int getCraftingBencheCookedXp(Material mat) {
		switch (mat) {
			case CAKE:
				return 128;
			case COOKIE:
			case PORK:
				return 32;
			case GOLDEN_CARROT:
			case GOLDEN_APPLE:
				return 48;
			case MUSHROOM_SOUP:
			case PUMPKIN_PIE:
				return 64;
			case BREAD:
				return 16;
			default:
				return 0;
		}
	}

	@Override
	public RightClickAbility getAbility() {
		return null;
	}

}

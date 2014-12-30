package com.tribescommunity.levelling.skills.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.skills.LevellingSkill;

public class Cooking implements LevellingSkill {

	@Override
	public String getName() {
		return Skill.COOKING.getName();
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

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		for (Material mat : Material.values()) {
			if (getCraftingBencheCookedXp(mat) > 0) {
				table.add(mat.getData().getName() + " (Crafting Bench): " + getCraftingBencheCookedXp(mat));
			}
			if (getFurnaceCookedXp(mat) > 0) {
				table.add(mat.getData().getName() + " (Furnace): " + getFurnaceCookedXp(mat));
			}
		}

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by cooking food in a furnace";
	}

}

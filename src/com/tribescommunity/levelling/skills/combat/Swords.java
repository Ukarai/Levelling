package com.tribescommunity.levelling.skills.combat;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;

/* 
 * Date: 30 Nov 2012
 * Time: 19:53:34
 * Maker: theguynextdoor
 */
public class Swords extends Skill {

	@Override
	public String getName() {
		return com.tribescommunity.levelling.data.Skill.SWORDS.getName();
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

	public double damageToBlock(User user, double damage) {
		int onePercent = 50 / 120;
		int percentToBlock = onePercent * user.getLevel(com.tribescommunity.levelling.data.Skill.SWORDS);

		return (damage / 100) * percentToBlock;
	}

	public boolean isSword(ItemStack itemInHand) {
		Material type = itemInHand.getType();

		return type == Material.WOOD_SWORD || type == Material.STONE_SWORD || type == Material.IRON_SWORD || type == Material.GOLD_SWORD || type == Material.DIAMOND_SWORD;
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}
}

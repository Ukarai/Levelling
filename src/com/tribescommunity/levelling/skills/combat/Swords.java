package com.tribescommunity.levelling.skills.combat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 30 Nov 2012
 * Time: 19:53:34
 * Maker: theguynextdoor
 */
public class Swords implements LevellingSkill {

	@Override
	public String getName() {
		return Skill.SWORDS.getName();
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
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		for (EntityType et : EntityType.values()) {
			if (getXp(et) > 0) {
				table.add(StringUtils.capitalize(et.toString().toLowerCase()) + ": " + getXp(et));
			}
		}

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by hitting certain mobs with a sword";
	}
}

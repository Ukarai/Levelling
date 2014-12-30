package com.tribescommunity.levelling.skills.gathering;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.abilities.gathering.FarmingAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 28 Nov 2012
 * Time: 08:34:31
 * Maker: theguynextdoor
 */
public class Farming implements LevellingSkill {

	RightClickAbility ability;

	public Farming(Levelling plugin) {
		ability = new FarmingAbility("Full Harvest", this, plugin);
	}

	@Override
	public String getName() {
		return Skill.FARMING.getName();
	}

	@SuppressWarnings("deprecation")
	public boolean isFullGrownTop(Block block) {
		Material type = block.getType();
		if (getXp(type) > 0) {
			byte data = block.getData();

			if ((type == Material.MELON_BLOCK || type == Material.PUMPKIN || type == Material.BROWN_MUSHROOM || type == Material.RED_MUSHROOM) && !block.hasMetadata(Levelling.USER_PLACED_META_STRING))
				return true;
			if (type == Material.CARROT || type == Material.POTATO)
				return data >= 3;
			return type == Material.NETHER_WARTS ? data >= 2 : data >= 7;
		}
		return false;
	}

	public long getXp(Material type) {
		switch (type) {
		case WHEAT:
		case VINE:
		case COCOA:
		case CROPS:
			return 32;
		case SUGAR_CANE:
		case SUGAR_CANE_BLOCK:
		case PUMPKIN:
		case MELON_BLOCK:
		case POTATO:
		case CARROT:
		case CARROT_ITEM:
		case CACTUS:
			return 64;
		case RED_MUSHROOM:
		case BROWN_MUSHROOM:
		case NETHER_WARTS:
			return 128;
		default:
			return 0;
		}
	}

	@Override
	public RightClickAbility getAbility() {
		return ability;
	}

	public boolean isHoe(ItemStack itemInHand) {
		Material m = itemInHand.getType();
		return m == Material.WOOD_HOE || m == Material.STONE_HOE || m == Material.GOLD_HOE || m == Material.IRON_HOE || m == Material.DIAMOND_HOE;
	}

	public int getFullHarvestRadius(User user) {
		int level = user.getLevel(com.tribescommunity.levelling.data.Skill.FARMING);

		if (level > 0 && level <= 20) {
			return 1;
		} else if (level > 20 && level <= 40) {
			return 2;
		} else if (level > 40 && level <= 60) {
			return 3;
		} else if (level > 60 && level <= 80) {
			return 4;
		} else if (level > 80 && level <= 100) {
			return 5;
		} else if (level > 100 && level <= 120) {
			return 6;
		} else
			return 0;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		for (Material mat : Material.values()) {
			if (getXp(mat) > 0) {
				table.add(mat.getData().getName() + ": " + getXp(mat));
			}
		}

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by harvesting crops with a hoe";
	}
}

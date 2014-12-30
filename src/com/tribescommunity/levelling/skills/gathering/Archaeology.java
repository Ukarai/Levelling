package com.tribescommunity.levelling.skills.gathering;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 18 Nov 2012
 * Time: 00:18:20
 * Maker: theguynextdoor
 */
public class Archaeology implements LevellingSkill {

	@Override
	public String getName() {
		return Skill.ARCHAEOLOGY.getName();
	}

	public long getXp(Block block) {
		return getXp(block.getType());
	}

	public long getXp(Material type) {
		switch (type) {
		case GRASS:
		case DIRT:
			return 16;
		case SAND:
		case GRAVEL:
			return 24;
		case CLAY:
		case MYCEL:
			return 48;
		default:
			return 0;
		}
	}

	public void handleDrops(User user, Block block) {
		switch (block.getType()) {
		case GRASS:
		case DIRT:
		case SAND:
		case CLAY:
		case GRAVEL:
		case MYCEL:
			double ran = Math.random();

			if (ran <= dropChanceNugget(user.getLevel(Skill.ARCHAEOLOGY))) {
				block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
			} else if (ran <= dropChanceIngot(user.getLevel(Skill.ARCHAEOLOGY))) {
				block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
			} else if (ran <= dropChanceBlock(user.getLevel(Skill.ARCHAEOLOGY))) {
				block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_BLOCK, 1));
			} else if (ran <= dropChanceBrick(user.getLevel(Skill.ARCHAEOLOGY))) {
				ItemStack stone = new ItemStack(Material.SMOOTH_BRICK, 1);
				stone.setDurability((short) 2);
				block.getLocation().getWorld().dropItem(block.getLocation(), stone);
			} else if (ran <= dropChanceBlock(user.getLevel(Skill.ARCHAEOLOGY))) {
				double random = Math.random() * 100;

				if (random > 0 && random <= 14.3)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.FEATHER, 1));
				if (random > 14.3 && random <= 28.6)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.BONE, 1));
				if (random > 28.6 && random <= 42.9)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.STRING, 1));
				if (random > 42.9 && random <= 57.2)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.GLOWSTONE_DUST, 2));
				if (random > 57.2 && random <= 71.5)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.BOWL, 2));
				if (random > 71.5 && random <= 85.8)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.BOAT, 2));
				if (random > 85.8 && random <= 100)
					block.getLocation().getWorld().dropItem(block.getLocation(), new ItemStack(Material.BRICK, 2));
			}
		default:
			break;
		}
	}

	public double dropChanceNugget(int level) {
		double chance = 0.02 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	public double dropChanceIngot(int level) {
		double chance = 0.005 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	public double dropChanceBlock(int level) {
		double chance = 0.00005 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	public double dropChanceBrick(int level) {
		double chance = 0.00175 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	public double dropTrash(int level) {
		double chance = 0.00275 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	public boolean isSpade(ItemStack is) {
		Material type = is.getType();

		return type == Material.WOOD_SPADE || type == Material.STONE_SPADE || type == Material.IRON_SPADE || type == Material.GOLD_SPADE || type == Material.DIAMOND_SPADE;
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
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
		return "Xp is gained by digging blocks with a spade";
	}
}

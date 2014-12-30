package com.tribescommunity.levelling.skills.gathering;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.abilities.gathering.MiningAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 16 Nov 2012
 * Time: 22:16:42
 * Maker: theguynextdoor
 */
public class Mining implements LevellingSkill {
	private MiningAbility ability;

	public Mining(Levelling plugin) {
		ability = new MiningAbility("Mining Ability", this, plugin);
	}

	@Override
	public String getName() {
		return Skill.MINING.getName();
	}

	public long getXp(Block block) {
		return getXp(block.getType());
	}

	public long getXp(Material type) {
		switch (type) {
		case STONE:
		case NETHERRACK:
		case ENDER_STONE:
		case SANDSTONE:
			return 16;
		case IRON_ORE:
		case COAL_ORE:
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
		case OBSIDIAN:
		case NETHER_BRICK:
		case SMOOTH_BRICK:
		case MOSSY_COBBLESTONE:
			return 32;
		case GOLD_ORE:
		case LAPIS_ORE:
			return 64;
		case DIAMOND_ORE:
			return 128;
		case EMERALD_ORE:
			return 256;
		default:
			return 0;
		}
	}

	public void xpDrop(BlockBreakEvent e, User user) {
		if (getXp(e.getBlock()) > 0) {
			if (Math.random() <= xpDropChance(user)) {
				e.setExpToDrop(e.getExpToDrop() + getExtraXpToDrop(user));
			}
		}
	}

	private double xpDropChance(User user) {
		double chance = 0.15 / Levelling.MAX_SKILL_LEVEL;
		return chance * user.getLevel(Skill.MINING);
	}

	public int getExtraXpToDrop(User user) {
		return user.getLevel(Skill.MINING) / 8;
	}

	public boolean isPick(ItemStack is) {
		Material type = is.getType();

		return type == Material.WOOD_PICKAXE || type == Material.STONE_PICKAXE || type == Material.GOLD_PICKAXE || type == Material.IRON_PICKAXE || type == Material.DIAMOND_PICKAXE;
	}

	@Override
	public RightClickAbility getAbility() {
		return ability;
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
		return "Xp is gained by mining blocks with a pickaxe";
	}

}

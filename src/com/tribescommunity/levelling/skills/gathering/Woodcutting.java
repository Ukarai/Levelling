package com.tribescommunity.levelling.skills.gathering;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 18 Nov 2012
 * Time: 15:26:52
 * Maker: theguynextdoor
 */
public class Woodcutting implements LevellingSkill {

	@Override
	public String getName() {
		return Skill.WOODCUTTING.getName();
	}

	public boolean isAxe(ItemStack is) {
		Material type = is.getType();
		return type == Material.WOOD_AXE || type == Material.STONE_AXE || type == Material.GOLD_AXE || type == Material.IRON_AXE || type == Material.DIAMOND_AXE;
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		table.add("Log: " + 64);

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by breaking logs with an axe";
	}
}

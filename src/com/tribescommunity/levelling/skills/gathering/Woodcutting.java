package com.tribescommunity.levelling.skills.gathering;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.abilities.RightClickAbility;

/* 
 * Date: 18 Nov 2012
 * Time: 15:26:52
 * Maker: theguynextdoor
 */
public class Woodcutting extends com.tribescommunity.levelling.skills.Skill {

	@Override
	public String getName() {
		return com.tribescommunity.levelling.data.Skill.WOODCUTTING.getName();
	}

	public boolean isAxe(ItemStack is) {
		Material type = is.getType();
		return type == Material.WOOD_AXE || type == Material.STONE_AXE || type == Material.GOLD_AXE || type == Material.IRON_AXE || type == Material.DIAMOND_AXE;
	}

	public void destroyTree(Block base) {

	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}
}

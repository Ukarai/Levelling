package com.tribescommunity.levelling.data.repair;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ToolMaterial {

	WOOD(Material.WOOD),
	STONE(Material.COBBLESTONE),
	GOLD(Material.GOLD_INGOT),
	IRON(Material.IRON_INGOT),
	DIAMOND(Material.DIAMOND),
	STRING(Material.STRING);

	private Material material;

	private ToolMaterial(Material madeFrom) {
		this.material = madeFrom;
	}

	public Material getMaterial() {
		return material;
	}

	public static ToolMaterial getToolMaterial(ItemStack is) {
		switch (is.getType()) {
			case WOOD_SPADE:
			case WOOD_AXE:
			case WOOD_PICKAXE:
			case WOOD_HOE:
			case WOOD_SWORD:
				return WOOD;

			case STONE_SPADE:
			case STONE_AXE:
			case STONE_PICKAXE:
			case STONE_HOE:
			case STONE_SWORD:
				return STONE;

			case GOLD_SPADE:
			case GOLD_AXE:
			case GOLD_PICKAXE:
			case GOLD_HOE:
			case GOLD_SWORD:
				return GOLD;

			case IRON_SPADE:
			case IRON_AXE:
			case IRON_PICKAXE:
			case IRON_HOE:
			case IRON_SWORD:
				return IRON;

			case DIAMOND_SPADE:
			case DIAMOND_AXE:
			case DIAMOND_PICKAXE:
			case DIAMOND_HOE:
			case DIAMOND_SWORD:
				return DIAMOND;

			case BOW:
			case FISHING_ROD:
				return STRING;

			default:
				return null;
		}
	}
}

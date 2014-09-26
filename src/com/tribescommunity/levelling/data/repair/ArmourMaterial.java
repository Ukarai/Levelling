package com.tribescommunity.levelling.data.repair;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ArmourMaterial {

	LEATHER(Material.LEATHER),
	GOLD(Material.GOLD_INGOT),
	IRON(Material.IRON_INGOT),
	CHAINMAIL(Material.IRON_INGOT),
	DIAMOND(Material.DIAMOND);

	private Material material;

	private ArmourMaterial(Material mat) {
		material = mat;
	}

	public Material getMaterial() {
		return material;
	}

	public static ArmourMaterial getArmourMaterial(ItemStack is) {
		switch (is.getType()) {
		case LEATHER_HELMET:
		case LEATHER_CHESTPLATE:
		case LEATHER_LEGGINGS:
		case LEATHER_BOOTS:
			return LEATHER;

		case GOLD_HELMET:
		case GOLD_CHESTPLATE:
		case GOLD_LEGGINGS:
		case GOLD_BOOTS:
			return GOLD;

		case IRON_HELMET:
		case IRON_CHESTPLATE:
		case IRON_LEGGINGS:
		case IRON_BOOTS:
			return IRON;

		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
			return CHAINMAIL;

		case DIAMOND_HELMET:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_LEGGINGS:
		case DIAMOND_BOOTS:
			return DIAMOND;

		default:
			return null;
		}
	}
}

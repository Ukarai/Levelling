package com.tribescommunity.levelling.skills.misc.enchanting.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchant {

	public enum EnchantType {
		WEAPON,
		ARMOUR,
		BOTH;
	}

	private String name;
	private String description;
	protected double baseChance;
	private EnchantType type;
	private int levelRequired;

	public Enchant(String name, String description, double baseChance, EnchantType type, int levelRequired) {
		this.name = name;
		this.description = description;
		this.baseChance = baseChance;
		this.type = type;
		this.levelRequired = levelRequired;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public EnchantType getType() {
		return type;
	}

	public double getBaseChance() {
		return baseChance;
	}

	public int getLevelRequired() {
		return levelRequired;
	}

	public void enchant(ItemStack is) {
		ItemMeta im = null;
		if (is.hasItemMeta())
			im = is.getItemMeta();
		else
			im = Bukkit.getServer().getItemFactory().getItemMeta(is.getType());

		if (im.hasLore()) {
			im.getLore().add(ChatColor.DARK_RED + getName());
		} else {
			List<String> lore = new ArrayList<>();

			lore.add(ChatColor.DARK_RED + getName());
			im.setLore(lore);
		}

		is.setItemMeta(im);
	}

	public boolean isApplied(ItemStack is) {
		if (is != null) {
			if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
				if (is.getItemMeta().getLore().contains(ChatColor.GRAY + getName())) {
					return true;
				}
			}
		}
		return false;
	}
}

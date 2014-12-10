package com.tribescommunity.levelling.skills.misc.enchanting.types;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface Enchant {

	public String getName();

	public List<String> getDescription();

	public boolean canEnchant(ItemStack is);

	public boolean enchant(ItemStack is);

	public boolean isApplied(ItemStack is);

	public ItemStack getIcon();

}

package com.tribescommunity.levelling.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class CustomItemManager {

	private ItemStack lockpick = new ItemStack(Material.ARROW, 1);
	private ItemStack panningBowl = new ItemStack(Material.BOWL, 1);

	public CustomItemManager() {
		lockpick.setItemMeta(setNameAndLore(lockpick, "Lockpick", "For picking locks"));
		panningBowl.setItemMeta(setNameAndLore(panningBowl, "Panning Bowl", "Used for Gold Panning"));
	}

	public ItemMeta setNameAndLore(ItemStack is, String name, String... lore) {
		ItemMeta im = is.getItemMeta();
		
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		
		return im;
	}

	public ItemStack getLockpick() {
		return lockpick;
	}

	public ItemStack getPanningBowl() {
		return panningBowl;
	}
}

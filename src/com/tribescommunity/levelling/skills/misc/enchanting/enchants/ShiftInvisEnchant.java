package com.tribescommunity.levelling.skills.misc.enchanting.enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.repair.ArmourMaterial;
import com.tribescommunity.levelling.data.repair.ToolMaterial;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.misc.enchanting.types.ArmourEnchant;

public class ShiftInvisEnchant implements ArmourEnchant {

	public List<User> hidden = new ArrayList<>();

	private ItemStack icon = new ItemStack(Material.ENCHANTED_BOOK);
	{
		ItemMeta im = icon.getItemMeta();

		im.setDisplayName(getName());
		im.setLore(getDescription());
		icon.setItemMeta(im);
	}

	@Override
	public String getName() {
		return "Shift Invis";
	}

	@Override
	public List<String> getDescription() {
		List<String> lore = new ArrayList<>();

		lore.add("Turns yourself invisible while you are sneaking");

		return lore;
	}

	@Override
	public boolean canEnchant(ItemStack is) {
		if (is != null) {
			if ((ToolMaterial.getToolMaterial(is) != null || ArmourMaterial.getArmourMaterial(is) != null) && !isApplied(is)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean enchant(ItemStack is) {
		if (canEnchant(is)) {
			ItemMeta im = null;
			if (is.hasItemMeta())
				im = is.getItemMeta();
			else
				im = Bukkit.getServer().getItemFactory().getItemMeta(is.getType());

			if (im.hasLore()) {
				im.getLore().add(ChatColor.GRAY + getName());
			} else {
				List<String> lore = new ArrayList<>();

				lore.add(ChatColor.GRAY + getName());
				im.setLore(lore);
			}

			is.setItemMeta(im);
			return true;
		}
		return false;
	}

	@Override
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

	@Override
	public ItemStack getIcon() {
		return icon;
	}

	public void applyEffect(Levelling plugin, Player player) {
		hidden.add(plugin.getUser(player.getName()));

		for (Player other : Bukkit.getOnlinePlayers()) {
			if (!other.hasPermission("levelling.admin"))
				other.hidePlayer(player);
		}
	}

	public boolean isHidden(User user) {
		return hidden.contains(user);
	}

}

package com.tribescommunity.levelling.skills.misc.enchanting.enchants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.repair.ArmourMaterial;
import com.tribescommunity.levelling.data.repair.ToolMaterial;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.misc.enchanting.types.ArmourEnchant;
import com.tribescommunity.levelling.skills.misc.enchanting.types.WeaponEnchant;

public class PoisonEnchant implements WeaponEnchant, ArmourEnchant {

	private List<UUID> effectedEntities = new ArrayList<>();

	private ItemStack icon = new ItemStack(Material.ENCHANTED_BOOK);
	{
		ItemMeta im = icon.getItemMeta();

		im.setDisplayName(getName());
		im.setLore(getDescription());
		icon.setItemMeta(im);
	}

	@Override
	public String getName() {
		return "Poison";
	}

	@Override
	public List<String> getDescription() {
		List<String> description = new ArrayList<>();

		description.add("Chance to apply a posion effect to target upon hit");

		return description;
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

	public void applyEffect(Levelling plugin, final Player player, final LivingEntity entity) {
		if (!effectedEntities.contains(entity.getUniqueId())) {
			final User user = plugin.getUser(player.getName());

			if (shouldApply(user)) {
				effectedEntities.add(entity.getUniqueId());
				player.sendMessage(ChatColor.DARK_PURPLE + "Poison applied");

				new BukkitRunnable() {

					int count = 0;

					@Override
					public void run() {
						if (!entity.isDead()) {
							entity.damage(1.0);
							count++;

							if (count >= getPoisonCounts(user)) {
								effectedEntities.remove(entity.getUniqueId());
								cancel();
							}
						} else {
							effectedEntities.remove(entity.getUniqueId());
							cancel();
						}
					}

				}.runTaskTimer(plugin, 0, 20);
			}
		}
	}

	public int getPoisonCounts(User user) {
		int level = user.getLevel(Skill.ENCHANTING);

		if (level > 0 && level <= 20)
			return 4;
		else if (level > 20 && level <= 50)
			return 6;
		else if (level > 50 && level <= 70)
			return 8;
		else if (level > 70)
			return 10;
		else
			return 4;
	}

	public boolean shouldApply(User user) {
		return Math.random() <= (0.25 / Levelling.MAX_SKILL_LEVEL * user.getLevel(Skill.ENCHANTING) * 10);
	}
}

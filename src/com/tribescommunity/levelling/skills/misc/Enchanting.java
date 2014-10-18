package com.tribescommunity.levelling.skills.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;

public class Enchanting extends Skill {
	private Levelling plugin;

	public static List<Enchantment> toolEnchants = new ArrayList<Enchantment>();
	public static List<Enchantment> weaponEnchants = new ArrayList<Enchantment>();
	public static List<Enchantment> armourEnchants = new ArrayList<Enchantment>();

	static {
		toolEnchants.add(Enchantment.DURABILITY);
		toolEnchants.add(Enchantment.DIG_SPEED);
		toolEnchants.add(Enchantment.LOOT_BONUS_BLOCKS);

		weaponEnchants.add(Enchantment.DAMAGE_ALL);
		weaponEnchants.add(Enchantment.DURABILITY);
		weaponEnchants.add(Enchantment.DAMAGE_ARTHROPODS);
		weaponEnchants.add(Enchantment.DAMAGE_UNDEAD);
		weaponEnchants.add(Enchantment.LOOT_BONUS_MOBS);
		weaponEnchants.add(Enchantment.FIRE_ASPECT);
		weaponEnchants.add(Enchantment.KNOCKBACK);

		armourEnchants.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		armourEnchants.add(Enchantment.PROTECTION_EXPLOSIONS);
		armourEnchants.add(Enchantment.PROTECTION_FALL);
		armourEnchants.add(Enchantment.PROTECTION_FIRE);
		armourEnchants.add(Enchantment.PROTECTION_PROJECTILE);
	}

	public Enchanting(Levelling instance) {
		plugin = instance;
	}

	@Override
	public String getName() {
		return com.tribescommunity.levelling.data.Skill.ENCHANTING.getName();
	}

	public void enchantEvent(EnchantItemEvent e) {
		Player player = e.getEnchanter();
		User user = plugin.getUser(player.getName());

		user.addXp(com.tribescommunity.levelling.data.Skill.ENCHANTING, e.getExpLevelCost() * 10);
	}

	@Override
	public RightClickAbility getAbility() {
		return null;
	}

}

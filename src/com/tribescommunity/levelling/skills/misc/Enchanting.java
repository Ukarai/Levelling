package com.tribescommunity.levelling.skills.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.repair.ToolMaterial;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;
import com.tribescommunity.levelling.skills.misc.enchanting.EnchantDictionary;
import com.tribescommunity.levelling.skills.misc.enchanting.types.Enchant;
import com.tribescommunity.levelling.skills.misc.enchanting.types.Enchant.EnchantType;

public class Enchanting implements LevellingSkill {
	private Levelling plugin;

	public static List<Enchantment> toolEnchants = new ArrayList<>();
	public static List<Enchantment> weaponEnchants = new ArrayList<>();
	public static List<Enchantment> armourEnchants = new ArrayList<>();

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
		return Skill.ENCHANTING.getName();
	}

	public void enchantEvent(EnchantItemEvent e) {
		Player player = e.getEnchanter();
		User user = plugin.getUser(player.getName());
		ItemStack toEnchant = e.getItem();
		EnchantType type = ToolMaterial.isSword(toEnchant) ? EnchantType.WEAPON : EnchantType.ARMOUR;
		List<Enchant> enchants = possibleEnchants(EnchantType.ARMOUR, 2);

		user.addXp(Skill.ENCHANTING, e.getExpLevelCost() * 10);

		for (Enchant enchant : EnchantDictionary.allEnchants) {
			if (user.getLevel(Skill.ENCHANTING) >= enchant.getLevelRequired()) {
			}
		}
	}

	public List<Enchant> possibleEnchants(EnchantType type, int level) {
		List<Enchant> enchants = new ArrayList<>();

		for (Enchant enchant : EnchantDictionary.allEnchants) {
			if (enchant.getType() == type || enchant.getType() == EnchantType.BOTH) {
				if (enchant.getLevelRequired() <= level) {
					enchants.add(enchant);
				}
			}
		}

		return enchants;
	}

	@Override
	public RightClickAbility getAbility() {
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		table.add("Enchanting cost * 10");

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by enchanting items";
	}

}

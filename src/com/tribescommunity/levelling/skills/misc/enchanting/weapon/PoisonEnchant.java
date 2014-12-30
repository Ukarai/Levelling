package com.tribescommunity.levelling.skills.misc.enchanting.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.misc.enchanting.types.Enchant;

public class PoisonEnchant extends Enchant {

	private List<UUID> effectedEntities = new ArrayList<>();

	public PoisonEnchant() {
		super("Poison", "Applies a poison effect to a target when you hit them", 1 / 10, EnchantType.WEAPON, 0);
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
		return Math.random() <= (baseChance / Levelling.MAX_SKILL_LEVEL * user.getLevel(Skill.ENCHANTING) * 10);
	}
}

package com.tribescommunity.levelling.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.skills.misc.enchanting.EnchantDictionary;

public class EnchantEffectListener implements Listener {

	private Levelling plugin;

	public EnchantEffectListener(Levelling instance) {
		plugin = instance;
	}

	@EventHandler
	public void poisonEnchant(EntityDamageByEntityEvent e) {
		// Attacking with a poison weapon
		if (e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();

			if (damager.getItemInHand() != null) {
				if (EnchantDictionary.POISON.isApplied(damager.getItemInHand())) {
					if (e.getEntity() instanceof LivingEntity) {
						EnchantDictionary.POISON.applyEffect(plugin, damager, (LivingEntity) e.getEntity());
					}
				}
			}
		}
	}
}

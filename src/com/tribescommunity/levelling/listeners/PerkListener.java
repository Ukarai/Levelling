package com.tribescommunity.levelling.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.user.User;

public class PerkListener implements Listener {

	private Levelling plugin;

	public PerkListener(Levelling instance) {
		plugin = instance;
	}

	@EventHandler
	public void archerPerk(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		if (user.hasClass() && user.getLevellingClass() == LevellingClass.ARCHER) {
			if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.BOW) {
				player.setWalkSpeed(0.25f);
			} else {
				player.setWalkSpeed(0.2f);
			}
		}
	}

}

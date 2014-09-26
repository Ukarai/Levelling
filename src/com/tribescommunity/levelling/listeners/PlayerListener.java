package com.tribescommunity.levelling.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Backend;
import com.tribescommunity.levelling.data.user.User;

/* 
 * Date: 15 Nov 2012
 * Time: 20:08:43
 * Maker: theguynextdoor
 */
public class PlayerListener implements Listener {

	private Levelling plugin;
	private Backend backend;

	public PlayerListener(Levelling instance) {
		plugin = instance;
		backend = plugin.getBackend();
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		User user = backend.getUser(player.getName());

		if (user.hasClass()) {
			user.checkClassLevel();

			String prefix = plugin.getChat().getGroupPrefix("world", plugin.getChat().getPlayerGroups("world", player.getName())[0]);

			if (!prefix.contains(user.getLevellingClass().getTag())) {
				String addTag = user.getLevellingClass().getTag() + prefix;

				plugin.getChat().setPlayerPrefix("world", player.getName(), addTag);
			}
		}

		plugin.getLeaderboards().update(user);
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		if (plugin.levellingGods.contains(plugin.getUser(e.getPlayer().getName()))) {
			plugin.levellingGods.remove(plugin.getUser(e.getPlayer().getName()));
		}
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		User user = backend.getUser(e.getPlayer().getName());

		if (user.hasClass()) {
			user.checkClassLevel();
		}

		if (plugin.partyChat.contains(user.getName())) {
			if (user.inParty()) {
				user.getParty().sendMessage(e.getPlayer(), e.getMessage());
				e.setCancelled(true);
			} else {
				plugin.partyChat.remove(user.getName());
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void partyProtection(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player damaged = (Player) e.getEntity();
			User uDamaged = plugin.getUser(damaged.getName());

			if (uDamaged.inParty()) {
				if (e.getDamager() instanceof Player) {
					User uDamager = plugin.getUser(((Player) e.getDamager()).getName());

					if (uDamager.inParty()) {
						if (uDamaged.getParty().getName().equals(uDamager.getParty().getName())) {
							e.setCancelled(true);
						}
					}
				} else {
					if (e.getDamager() instanceof Projectile) {
						Projectile proj = (Projectile) e.getDamager();

						if (proj.getShooter() instanceof Player) {
							User uDamager = plugin.getUser(((Player) proj.getShooter()).getName());

							if (uDamager.inParty()) {
								if (uDamaged.getParty().getName().equals(uDamager.getParty().getName())) {
									e.setCancelled(true);
								}
							}
						}
					}
				}
			}
		}
	}

}

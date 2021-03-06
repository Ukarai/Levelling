package com.tribescommunity.levelling.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Backend;
import com.tribescommunity.levelling.data.chat.ChatChannel;
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
		Player player = e.getPlayer();
		String message = e.getMessage();
		User user = backend.getUser(player.getName());
		ChatChannel currentChannel = user.getChatChannel();
		String format;
		StringBuilder logString = new StringBuilder();

		if (user.hasClass()) {
			user.checkClassLevel();
		}

		if (currentChannel == ChatChannel.PARTY) {
			if (!user.inParty()) {
				user.setChatChannel(ChatChannel.DEFAULT);
			}
		} else if (currentChannel != ChatChannel.DEFAULT && !player.hasPermission(currentChannel.getPermission())) {
			user.setChatChannel(ChatChannel.DEFAULT);
		}

		currentChannel = user.getChatChannel();

		if (currentChannel == ChatChannel.DEFAULT) {
			String title = user.getTitle().length() == 0 ? "" : user.getTitle() + " ";
			String group = plugin.getPerm().getPrimaryGroup(player);
			String permWorld = plugin.getConfigInstance().getMainPermWorldName();
			String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getChat().getGroupPrefix(permWorld, group));
			String displayName = player.getDisplayName();
			format = title + prefix + ChatColor.stripColor(displayName) + ChatColor.WHITE + ": " + message;

			e.setFormat(format);
			logString.append(player.getName() + ": " + message);
		} else {
			e.getRecipients().clear();

			for (Player online : Bukkit.getOnlinePlayers()) {
				if (currentChannel == ChatChannel.PARTY && user.getParty().inParty(online.getName())) {
					e.getRecipients().add(online);
				} else if (online.hasPermission(user.getChatChannel().getPermission())) {
					e.getRecipients().add(online);
				}
			}

			format = currentChannel.getFormat(player.getName(), e.getMessage());

			if (currentChannel == ChatChannel.PARTY) {
				String partyName = user.getParty().getName();

				logString.append("[" + partyName + "] " + player.getName() + ": " + message);
			} else {
				logString.append(player.getName() + ": " + message);
			}

			e.setFormat(format);
		}

		plugin.getChatLogger(currentChannel).info(logString.toString());
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

	@EventHandler(ignoreCancelled = true)
	public void command(PlayerCommandPreprocessEvent e) {
		plugin.getCommandLogger().info(e.getPlayer().getName() + " issued the command '" + e.getMessage() + "'");
	}

}

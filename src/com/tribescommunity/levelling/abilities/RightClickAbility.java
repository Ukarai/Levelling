package com.tribescommunity.levelling.abilities;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.skills.Skill;

public abstract class RightClickAbility {
	protected Set<String> activePlayers;
	protected Set<String> readiedPlayers;
	protected Map<String, Long> cooldownPlayers;
	protected String name;
	protected Skill skill;
	protected com.tribescommunity.levelling.data.Skill skillEnum;
	protected Levelling plugin;

	public RightClickAbility(String name, Skill skill, com.tribescommunity.levelling.data.Skill skillEnum,
			Levelling instance) {
		this.activePlayers = new HashSet<String>();
		this.readiedPlayers = new HashSet<String>();
		this.cooldownPlayers = new HashMap<String, Long>();
		this.name = name;
		this.skill = skill;
		this.skillEnum = skillEnum;
		this.plugin = instance;
	}

	public void ready(final Player player) {
		if (!isReady(player)) {
			readiedPlayers.add(player.getName());
			player.sendMessage(ChatColor.GOLD + "[" + name + "] " + ChatColor.WHITE + "Ability readied");

			new BukkitRunnable() {
				public void run() {
					unready(player);
				}
			}.runTaskLater(plugin, 60L);
		}
	}

	public void activate(final Player player) {
		if (!isActive(player)) {
			if (isReady(player)) {
				if (getRemaininCooldown(player.getName()) <= 0) {
					int activeTime = 5 * plugin.getUser(player.getName()).getLevel(skillEnum);
					final int cdTime = (300 - plugin.getUser(player.getName()).getLevel(skillEnum)) * 20;

					player.sendMessage(ChatColor.GOLD + "[" + name + "] " + ChatColor.WHITE + "Ability activated");

					readiedPlayers.remove(player.getName());
					activePlayers.add(player.getName());

					new BukkitRunnable() {
						public void run() {
							activePlayers.remove(player.getName());
							cooldownPlayers.put(player.getName(), System.nanoTime());

							player.sendMessage(ChatColor.GOLD + "[" + name + "] " + ChatColor.WHITE
									+ "Ability de-activated");
						}
					}.runTaskLater(plugin, activeTime);

					new BukkitRunnable() {
						public void run() {
							cooldownPlayers.remove(player.getName());
							player.sendMessage(ChatColor.GOLD + "[" + name + "] " + ChatColor.WHITE
									+ "Your cooldown has finished");
						}
					}.runTaskLater(plugin, activeTime + cdTime);

				}
				else {
					String rt = new DecimalFormat("#.##").format(getRemaininCooldown(player.getName()));
					player.sendMessage(ChatColor.GOLD + "[" + name + "] " + ChatColor.RED + "You need to wait " + rt
							+ " seconds before you can use this again");
				}
			}
		}
	}

	public double getRemaininCooldown(String name) {
		return cooldownPlayers.containsKey(name) ? (300 - plugin.getUser(name).getLevel(skillEnum))
				- (System.nanoTime() - cooldownPlayers.get(name)) / 1000000000.0 : 0;
	}

	public void unready(Player player) {
		if (isReady(player)) {
			readiedPlayers.remove(player.getName());
			player.sendMessage(ChatColor.GOLD + "[" + name + "] " + ChatColor.WHITE + "Ability un-readied");
		}
	}

	public boolean isReady(Player player) {
		return readiedPlayers.contains(player.getName());
	}

	public boolean isActive(Player player) {
		return activePlayers.contains(player.getName());
	}

	public abstract void doAbility(Event e);
}

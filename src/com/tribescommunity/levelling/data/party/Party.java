package com.tribescommunity.levelling.data.party;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;

/* 
 * Date: 29 Nov 2012
 * Time: 21:16:31
 * Maker: theguynextdoor
 */
public class Party {
	private String name;
	private Set<String> people;
	private Levelling plugin;

	public Party(Levelling plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		people = new HashSet<String>();
		plugin.parties.put(name, this);
	}

	public String getName() {
		return name;
	}

	public void addPlayer(String name) {
		people.add(name);

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (people.contains(player.getName())) {
				player.sendMessage(ChatColor.GREEN + name + " has joined the party");
			}
		}
	}

	public void removePlayer(String name) {
		people.remove(name);

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (people.contains(player.getName())) {
				player.sendMessage(ChatColor.GREEN + name + " has left the party");
			}
		}
	}
	
	public boolean inParty(String name) {
		return people.contains(name);
	}
}

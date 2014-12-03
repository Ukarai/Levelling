package com.tribescommunity.levelling.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.user.User;

public class PartyTeleportCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public PartyTeleportCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			if (user.inParty()) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player p = Bukkit.getPlayer(args[0]);
					User u = plugin.getUser(p.getName());

					if (u.inParty()) {
						if (u.getParty().getName().equals(user.getParty().getName())) {
							player.teleport(p);
							player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "Teleported to " + p.getName());
							p.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + player.getName() + " teleported to you");
						} else {
							player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "That player is not in your party");
						}
					} else {
						player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "That player is not in a party");
					}
				} else {
					player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "That player is not online");
				}
			} else {
				player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "You are not in a party");
			}
		}
		return true;
	}

}

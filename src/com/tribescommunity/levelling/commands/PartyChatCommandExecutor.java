package com.tribescommunity.levelling.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.user.User;

public class PartyChatCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public PartyChatCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			if (user.inParty()) {
				if (args.length == 0) {
					if (plugin.partyChat.contains(player.getName())) {
						plugin.partyChat.remove(player.getName());
						player.sendMessage(ChatColor.GREEN + "Party chat: " + ChatColor.DARK_RED + "OFF");
					} else {
						plugin.partyChat.add(player.getName());
						player.sendMessage(ChatColor.GREEN + "Party chat: " + ChatColor.WHITE + "ON");
					}
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						sb.append(args[i]);
						sb.append(' ');
					}
					user.getParty().sendMessage(player, sb.toString());
				}
			} else {
				player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "You are not in a party");
			}

		}
		return true;
	}
}

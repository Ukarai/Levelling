package com.tribescommunity.levelling.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.user.User;

/* 
 * Date: 18 Nov 2012
 * Time: 17:45:28
 * Maker: theguynextdoor
 */
public class LevellingCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public LevellingCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("levelling.command")) {
			if (args[0].equalsIgnoreCase("save")) {
				try {
					plugin.getBackend().savePlacedBlocks();
					plugin.getBackend().saveAllTxt();
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.WHITE + "Save complete!");
				return true;
			} else if (args[0].equalsIgnoreCase("debug")) {
				sender.sendMessage(ChatColor.GOLD + "Placed blocks size: " + ChatColor.WHITE + plugin.placedBlocks.size());
				sender.sendMessage(ChatColor.GOLD + "User list size: " + ChatColor.WHITE + plugin.getUsers().size());
				return true;
			} else if (args[0].equalsIgnoreCase("god")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					User user = plugin.getUser(player.getName());

					if (player.hasPermission("levelling.god")) {
						if (plugin.levellingGods.contains(user)) {
							plugin.levellingGods.remove(user);
							player.sendMessage(ChatColor.GOLD + "[Levelling God] " + ChatColor.WHITE + "God mode disabled");
						} else {
							plugin.levellingGods.add(user);
							player.sendMessage(ChatColor.GOLD + "[Levelling God] " + ChatColor.WHITE + "God mode activated");
						}
					} else {
						player.sendMessage(ChatColor.RED + "You do not have permission for this command");
					}
				}
			} else if (args[0].equalsIgnoreCase("toggle")) {
				if (args[1].equalsIgnoreCase("block")) {
					plugin.doBlockAbility = !plugin.doBlockAbility;
					sender.sendMessage("doBlockAbility = " + plugin.doBlockAbility);
				}
			}
		}
		return true;
	}
}

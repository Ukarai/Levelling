package com.tribescommunity.levelling.commands;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.user.User;

/* 
 * Date: 2 Dec 2012
 * Time: 13:28:30
 * Maker: theguynextdoor
 */
public class ClassCommandExecutor implements CommandExecutor {

	private Levelling plugin;

	public ClassCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getBackend().getUser(player.getName());

			if (args.length == 0) {
				if (!user.hasClass()) {
					for (LevellingClass lClass : LevellingClass.values()) {
						player.sendMessage(lClass.getColour() + lClass.getName() + ": " + ChatColor.WHITE + "15 " + ChatColor.GOLD + lClass.getPrimary().getName()
								+ ChatColor.WHITE + ", 10 " + ChatColor.GOLD + lClass.getSecondary().getName() + ChatColor.WHITE + ", 5 " + ChatColor.GOLD
								+ lClass.getTeritary().getName());
					}
				} else {
					player.sendMessage(ChatColor.AQUA + "Requirements for next level");
					player.sendMessage(15 * (user.getClassLevel() + 1) + " " + user.getLevellingClass().getPrimary().getName());
					player.sendMessage(10 * (user.getClassLevel() + 1) + " " + user.getLevellingClass().getSecondary().getName());
					player.sendMessage(5 * (user.getClassLevel() + 1) + " " + user.getLevellingClass().getTeritary().getName());
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					for (LevellingClass lClass : LevellingClass.values()) {
						player.sendMessage(lClass.getColour() + lClass.getName() + ": " + ChatColor.WHITE + "20 " + ChatColor.GOLD + lClass.getPrimary().getName()
								+ ChatColor.WHITE + ", 15 " + ChatColor.GOLD + lClass.getSecondary().getName() + ChatColor.WHITE + ", 10 " + ChatColor.GOLD
								+ lClass.getTeritary().getName());
					}
				} else if (args[0].equalsIgnoreCase("untrain")) {
					if (user.hasClass()) {
						if (plugin.getEconomy() != null) {
							OfflinePlayer op = Bukkit.getOfflinePlayer(player.getUniqueId());

							EconomyResponse er = plugin.getEconomy().withdrawPlayer(op, 50000);

							if (er.transactionSuccess()) {
								user.untrainClass();
								player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.WHITE + "Your class has now been removed");
							} else {
								player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + er.errorMessage);
							}
						} else {
							if (player.hasPermission("levelling.untrain")) {
								user.untrainClass();
								player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.WHITE + "Your class has now been removed");
							} else {
								player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You do not have permission for this command");
							}
						}
					} else {
						player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You never had a class you dolt");
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("train")) {
					for (LevellingClass lClass : LevellingClass.values()) {
						if (lClass.getName().equalsIgnoreCase(args[1])) {
							if (!user.hasClass()) {
								if (lClass.hasRequirments(user, 0)) {
									user.trainClass(lClass);
									player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.AQUA + "You are now a " + ChatColor.WHITE + lClass.getName());
									return true;
								} else {
									player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You do not meet the requirements for this class");
									return true;
								}
							} else {
								player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You already have a class");
								return true;
							}
						}
					}
					player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "There is no class by that name");
				} else if (args[0].equalsIgnoreCase("untrain")) {
					if (player.hasPermission("levelling.untrain")) {
						User user1 = plugin.getUser(args[1]);

						if (user1.hasClass()) {
							user1.untrainClass();
							player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.WHITE + user1.getName() + "'s class has now been removed");
						} else {
							player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + user1.getName() + " does not have a class");
						}
					} else {
						player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You do not have permission for this command");
					}
				} else if (args[0].equalsIgnoreCase("view")) {
					if (player.hasPermission("levelling.view")) {
						User user1 = plugin.getUser(args[1]);

						if (user1.hasClass()) {
							player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.WHITE + user1.getName() + " has the class " + user1.getLevellingClass().getName());
						} else {
							player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + user1.getName() + " does not have a class");
						}
					} else {
						player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You do not have permission for this command");
					}
				}
			} else {
				player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "Invalid subcommand");
			}
		}
		return true;
	}
}

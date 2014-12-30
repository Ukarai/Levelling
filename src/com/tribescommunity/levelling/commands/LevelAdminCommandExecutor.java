package com.tribescommunity.levelling.commands;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

public class LevelAdminCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public LevelAdminCommandExecutor(Levelling levelling) {
		this.plugin = levelling;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("levelling.admin")) {
			if (plugin.getUsers().containsKey(args[0])) {
				User user = plugin.getUser(args[0]);

				if (args[1].equalsIgnoreCase("add")) {
					if (args[2].equalsIgnoreCase("xp")) {
						for (Skill skill : Skill.values()) {
							if (args[3].equalsIgnoreCase(skill.getName())) {
								if (NumberUtils.isNumber(args[4])) {
									user.addXp(skill, Long.parseLong(args[4]));
									sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.AQUA + args[4] + " xp added to " + user.getName() + "'s " + skill.getName()
											+ " skill");
									return true;
								} else {
									sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + args[4] + " is not a valid number");
								}
							}
						}
						sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "No skill found with the name '" + args[3] + "'");
					}
				} else if (args[1].equalsIgnoreCase("title")) {
					if (args[2].equalsIgnoreCase("remove")) {
						user.setTitle("");
						sender.sendMessage(user.getName() + ChatColor.GOLD + "'s title has been removed");
					}
				}
			} else {
				sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "No user with the name '" + args[0] + "'");
			}
		} else {
			sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "You do not have permission for this command");
		}
		return true;
	}
}

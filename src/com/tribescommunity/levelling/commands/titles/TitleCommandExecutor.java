package com.tribescommunity.levelling.commands.titles;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.achievements.Ach;
import com.tribescommunity.levelling.achievements.AchievementManager;
import com.tribescommunity.levelling.data.user.User;

public class TitleCommandExecutor implements CommandExecutor {

	private Levelling plugin;

	public TitleCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			// /title set <title>

			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					String title = args[1];

					for (Ach achievement : AchievementManager.allAchievements) {
						if (achievement.hasTitle() && achievement.getTitle().equalsIgnoreCase(title)) {
							if (user.getAchievementTracker().hasAchievement(achievement.getId())) {
								user.setTitle(achievement.getTitle());
								player.sendMessage(ChatColor.GOLD + "Your title has been set to " + ChatColor.WHITE + user.getTitle());
								return true;
							}
						}
					}

					if (user.hasClass()) {
						for (int i = 0; i <= user.getClassLevel(); i++) {
							if (title.equalsIgnoreCase(user.getLevellingClass().getName(i))) {
								user.setTitle(user.getLevellingClass().getColour() + user.getLevellingClass().getName(i));
								player.sendMessage(ChatColor.GOLD + "Your title has been set to " + ChatColor.WHITE + user.getTitle());
								return true;
							}
						}
					}

					player.sendMessage(ChatColor.RED + "That title is not available to you");
					return true;
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (args.length == 1) {
						user.setTitle("");
						player.sendMessage(ChatColor.GOLD + "Your title has been removed");
					}
				}
			}
		}
		return true;
	}

}

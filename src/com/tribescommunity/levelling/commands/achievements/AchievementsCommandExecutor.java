package com.tribescommunity.levelling.commands.achievements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.achievements.Ach;
import com.tribescommunity.levelling.achievements.AchievementManager;
import com.tribescommunity.levelling.data.user.User;

public class AchievementsCommandExecutor implements CommandExecutor {

	private Levelling plugin;

	public AchievementsCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			if (args.length == 0) {
				StringBuilder sb = new StringBuilder();

				for (Ach achievement : AchievementManager.allAchievements) {
					if (user.getAchievementTracker().hasAchievement(achievement.getId())) {
						sb.append(ChatColor.WHITE + achievement.getTitle());
						sb.append(ChatColor.GREEN + "; ");
					}
				}

				if (sb.length() > 0) {
					player.sendMessage(ChatColor.GREEN + "======== Achievements ========");
					player.sendMessage(sb.toString());
				} else {
					player.sendMessage(ChatColor.RED + "You have no achievements");
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					for (Ach ach : AchievementManager.allAchievements) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + ach.getJSON(user.getAchievementTracker().hasAchievement(ach.getId())));
					}
				}
			}
		}
		return true;
	}
}

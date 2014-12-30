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

public class TitlesCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public TitlesCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			StringBuilder sb = new StringBuilder();

			for (Ach ach : AchievementManager.allAchievements) {
				if (user.getAchievementTracker().hasAchievement(ach.getId())) {
					if (ach.hasTitle()) {
						sb.append(ChatColor.WHITE + ach.getTitle());
						sb.append(ChatColor.GREEN + ";");
					}
				}
			}

			if (user.hasClass()) {
				String prevTitle = "";

				for (int i = 0; i <= user.getClassLevel(); i++) {
					if (!prevTitle.equalsIgnoreCase(user.getLevellingClass().getName(i))) {
						sb.append(ChatColor.WHITE + user.getLevellingClass().getName(i));
						sb.append(ChatColor.GREEN + ";");
						prevTitle = user.getLevellingClass().getName(i);
					}
				}
			}

			if (sb.length() > 0) {
				player.sendMessage(ChatColor.GREEN + "====== Titles ======");
				player.sendMessage(ChatColor.GREEN + "Current title: " + ChatColor.WHITE + user.getTitle());
				player.sendMessage(sb.toString());
				player.sendMessage(ChatColor.GREEN + "Use " + ChatColor.WHITE + "/title set <title> " + ChatColor.GREEN + "to set your title");
			} else {
				player.sendMessage(ChatColor.RED + "You do not have any titles available");
			}
		}
		return true;
	}

}

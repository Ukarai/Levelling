package com.tribescommunity.levelling.commands.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Backend;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

/* 
 * Date: 16 Nov 2012
 * Time: 22:35:21
 * Maker: theguynextdoor
 */
public class StatsCommandExecutor implements CommandExecutor {

	private Levelling plugin;
	private Backend backend;

	public StatsCommandExecutor(Levelling instance) {
		plugin = instance;
		backend = plugin.getBackend();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			handleStatsCommand(sender);
		} else if (args.length == 1) {
			handleOtherStatsCommand(sender, args);
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("xp"))
				handleXpCommand(sender, args);
			else
				handleStatsTopCommand(sender, args);
		}
		return true;
	}

	private void handleXpCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			for (Skill skill : Skill.values()) {
				if (skill.getName().equalsIgnoreCase(args[1])) {
					int totalXp = player.getTotalExperience();
					int addXp = totalXp / 2;

					if (user.hasClass()) {
						LevellingClass lClass = user.getLevellingClass();

						if (lClass.getPrimary() == skill) {
							addXp *= LevellingClass.PRIMARY_MODIFIER;
						} else if (lClass.getSecondary() == skill) {
							addXp *= LevellingClass.SECONDARY_MODIFIER;
						} else if (lClass.getTeritary() == skill) {
							addXp *= LevellingClass.TERTIARY_MODIFIER;
						}
					}

					user.addXp(skill, addXp);
					player.setTotalExperience(0);
					player.setExp(0);
					player.setLevel(0);
					player.sendMessage(totalXp + "" + ChatColor.GOLD + " xp has been converted into " + ChatColor.WHITE + addXp + " " + ChatColor.GOLD + skill.getName() + " xp");
					return;
				}
			}

			player.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "There is no skill with the name '" + args[1] + "'");
		}
	}

	private void handleStatsTopCommand(CommandSender sender, String[] args) {
		Skill skill = null;

		for (Skill skillz : Skill.values()) {
			if (args[1].equalsIgnoreCase(skillz.getName())) {
				skill = skillz;
			}
		}

		if (skill == null) {
			List<User> list = plugin.getLeaderboards().getTotalLevelBoard();
			Collections.reverse(list);
			int count = 0;

			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6TOTAL LEVEL&f&m            -"));
			for (User user : list) {
				count++;

				if (count > 10) {
					break;
				}

				sender.sendMessage(ChatColor.GOLD + "" + count + ". " + ChatColor.WHITE + user.getName() + ": " + ChatColor.GOLD + user.getTotalLevel());
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                 -"));
			return;
		}

		List<User> list = plugin.getLeaderboards().getLeaderboard(skill);
		Collections.reverse(list);
		int count = 0;

		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6" + skill.getName() + "&f&m            -"));
		for (User user : list) {
			count++;

			if (count > 10) {
				break;
			}

			sender.sendMessage(ChatColor.GOLD + "" + count + ". " + ChatColor.WHITE + user.getName() + ": " + ChatColor.GOLD + user.getLevel(skill));
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                 -"));
	}

	@SuppressWarnings("deprecation")
	private void handleOtherStatsCommand(CommandSender sender, String[] args) {
		if (args[0].equalsIgnoreCase("top")) {
			List<User> list = plugin.getLeaderboards().getTotalLevelBoard();
			Collections.reverse(list);
			int count = 0;

			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6TOTAL LEVEL&f&m            -"));
			for (User user : list) {
				count++;

				if (count > 10) {
					break;
				}

				sender.sendMessage(ChatColor.GOLD + "" + count + ". " + ChatColor.WHITE + user.getName() + ": " + ChatColor.GOLD + user.getTotalLevel());
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                 -"));
		} else if (Bukkit.getPlayer(args[0]) != null) {
			User user = plugin.getUser(Bukkit.getPlayer(args[0]).getName());

			sendStatsMessage(sender, user);
		} else if (plugin.getUsers().containsKey(args[0])) {
			User user = plugin.getUser(args[0]);

			sendStatsMessage(sender, user);
		} else {
			sender.sendMessage(ChatColor.GOLD + "[Levelling] " + ChatColor.RED + "No user with the name '" + args[0] + "'");
		}
	}

	public void handleStatsCommand(CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = backend.getUser(player.getName());

			sendStatsMessage(sender, user);
		}
	}

	public String getStatString(User user, Skill skill) {
		String msg = ChatColor.GOLD + skill.getName() + ": " + ChatColor.WHITE + user.getLevel(skill) + ChatColor.GOLD + " - " + ChatColor.WHITE + user.getXp(skill) + ChatColor.GOLD + "/"
				+ ChatColor.WHITE + user.getXpToNextLevel(skill);
		return msg;
	}

	public void sendStatsMessage(CommandSender sender, User user) {
		List<String> lines = new ArrayList<String>();

		lines.add(ChatColor.translateAlternateColorCodes('&', " &m           -&6" + user.getName() + "&f&m            -"));
		for (Skill skill : Skill.values()) {
			if (skill == Skill.GOLDPANNING) {
				if (user.getLevellingClass() == LevellingClass.GATHERER) {
					lines.add(getStatString(user, skill));
				}
			} else {
				lines.add(getStatString(user, skill));
			}
		}
		lines.add(ChatColor.translateAlternateColorCodes('&', "&m                                 -"));
		lines.add(ChatColor.GOLD + "" + ChatColor.BOLD + "TOTAL LEVEL: " + ChatColor.WHITE + user.getTotalLevel());
		lines.add(ChatColor.translateAlternateColorCodes('&', "&m                                 -"));

		for (String line : lines) {
			sender.sendMessage(line);
		}

	}
}

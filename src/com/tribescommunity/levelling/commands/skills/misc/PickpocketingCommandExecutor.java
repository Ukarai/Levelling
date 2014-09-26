package com.tribescommunity.levelling.commands.skills.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

public class PickpocketingCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public PickpocketingCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.PICKPOCKETING;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6PICKPOCKETING&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by right clicking a player while sneaking (providing cooldown is not active)");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Success + item looted: " + ChatColor.WHITE + 128 + " xp");
			player.sendMessage(ChatColor.GOLD + "Success + no item looted: " + ChatColor.WHITE + 64 + " xp");
			player.sendMessage(ChatColor.GOLD + "Fail: " + ChatColor.WHITE + 32 + " xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                    -"));
		}
		return true;
	}

}

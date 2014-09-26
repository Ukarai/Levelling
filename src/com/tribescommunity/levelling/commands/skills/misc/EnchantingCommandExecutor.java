package com.tribescommunity.levelling.commands.skills.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

public class EnchantingCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public EnchantingCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.ENCHANTING;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6UNARMED&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.RED + "THIS SKILL HAS NOT BEEN FULLY IMPLEMENTED YET");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                              -"));
		}
		return true;
	}

}

package com.tribescommunity.levelling.commands.skills.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

public class LockpickingCommandExecutor implements CommandExecutor {
	private Levelling plugin;

	public LockpickingCommandExecutor(Levelling instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.LOCKPICKING;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6LOCKPICKING&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by right clicking iron doors with a lockpick in hand");
			player.sendMessage(ChatColor.GOLD + "A success is determined by the door opening or not, along with a message");
			player.sendMessage("");
			player.sendMessage("Lockpick crafting recipe");
			player.sendMessage("AAI    A = Air");
			player.sendMessage("ACA    I = Iron ingot");
			player.sendMessage("CAA    C = Cobblestone");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Success: " + ChatColor.WHITE + 192 + " xp");
			player.sendMessage(ChatColor.GOLD + "Fail: " + ChatColor.WHITE + 64 + " xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                  -"));
		}
		return true;
	}

}

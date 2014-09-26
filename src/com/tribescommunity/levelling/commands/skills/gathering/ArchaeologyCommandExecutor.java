package com.tribescommunity.levelling.commands.skills.gathering;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.gathering.Archaeology;

public class ArchaeologyCommandExecutor implements CommandExecutor {
	private Levelling plugin;
	private Archaeology archaeology;

	public ArchaeologyCommandExecutor(Levelling instance) {
		plugin = instance;
		archaeology = plugin.getSkillHandler().getArchaeology();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.ARCHAEOLOGY;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6ARCHAEOLOGY&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by breaking the blocks listed below with a shovel in your hand");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Grass: " + ChatColor.WHITE + archaeology.getXp(Material.GRASS) + " xp");
			player.sendMessage(ChatColor.GOLD + "Dirt: " + ChatColor.WHITE + archaeology.getXp(Material.DIRT) + " xp");
			player.sendMessage(ChatColor.GOLD + "Sand: " + ChatColor.WHITE + archaeology.getXp(Material.SAND) + " xp");
			player.sendMessage(ChatColor.GOLD + "Gravel: " + ChatColor.WHITE + archaeology.getXp(Material.GRAVEL) + " xp");
			player.sendMessage(ChatColor.GOLD + "Clay: " + ChatColor.WHITE + archaeology.getXp(Material.CLAY) + " xp");
			player.sendMessage(ChatColor.GOLD + "Mycelium: " + ChatColor.WHITE + archaeology.getXp(Material.MYCEL) + "xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                       -"));

		}
		return true;
	}
}

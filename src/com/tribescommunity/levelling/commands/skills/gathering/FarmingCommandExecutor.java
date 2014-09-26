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
import com.tribescommunity.levelling.skills.gathering.Farming;

public class FarmingCommandExecutor implements CommandExecutor {
	private Levelling plugin;
	private Farming farming;

	public FarmingCommandExecutor(Levelling instance) {
		plugin = instance;
		farming = plugin.getSkillHandler().getFarming();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.FARMING;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6FARMING&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by breaking crops which are FULLY grown");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Wheat: " + ChatColor.WHITE + farming.getXp(Material.WHEAT) + " xp");
			player.sendMessage(ChatColor.GOLD + "Vines: " + ChatColor.WHITE + farming.getXp(Material.VINE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Sugar cane: " + ChatColor.WHITE + farming.getXp(Material.SUGAR_CANE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Pumpkin: " + ChatColor.WHITE + farming.getXp(Material.PUMPKIN) + " xp");
			player.sendMessage(ChatColor.GOLD + "Melon: " + ChatColor.WHITE + farming.getXp(Material.MELON_BLOCK) + " xp");
			player.sendMessage(ChatColor.GOLD + "Potato: " + ChatColor.WHITE + farming.getXp(Material.POTATO) + " xp");
			player.sendMessage(ChatColor.GOLD + "Carrot: " + ChatColor.WHITE + farming.getXp(Material.CARROT) + " xp");
			player.sendMessage(ChatColor.GOLD + "Cactus: " + ChatColor.WHITE + farming.getXp(Material.CACTUS) + " xp");
			player.sendMessage(ChatColor.GOLD + "Cocoa: " + ChatColor.WHITE + farming.getXp(Material.COCOA) + " xp");
			player.sendMessage(ChatColor.GOLD + "Red mushroom: " + ChatColor.WHITE + farming.getXp(Material.RED_MUSHROOM) + " xp");
			player.sendMessage(ChatColor.GOLD + "Brown mushroom: " + ChatColor.WHITE + farming.getXp(Material.BROWN_MUSHROOM) + " xp");
			player.sendMessage(ChatColor.GOLD + "Nether warts: " + ChatColor.WHITE + farming.getXp(Material.NETHER_WARTS) + " xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                              -"));
		}
		return true;
	}
}

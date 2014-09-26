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
import com.tribescommunity.levelling.skills.gathering.Mining;

public class MiningCommandExecutor implements CommandExecutor {

	private Levelling plugin;
	private Mining mining;

	public MiningCommandExecutor(Levelling instance) {
		plugin = instance;
		mining = plugin.getSkillHandler().getMining();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.MINING;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6MINING&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by breaking the blocks listed below with a pick in your hand");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage(ChatColor.GOLD + "Stone: " + ChatColor.WHITE + mining.getXp(Material.STONE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Netherrack: " + ChatColor.WHITE + mining.getXp(Material.NETHERRACK) + " xp");
			player.sendMessage(ChatColor.GOLD + "Endstone: " + ChatColor.WHITE + mining.getXp(Material.ENDER_STONE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Iron Ore: " + ChatColor.WHITE + mining.getXp(Material.IRON_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Coal Ore: " + ChatColor.WHITE + mining.getXp(Material.COAL_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Redstone Ore: " + ChatColor.WHITE + mining.getXp(Material.REDSTONE_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Obsidian: " + ChatColor.WHITE + mining.getXp(Material.OBSIDIAN) + " xp");
			player.sendMessage(ChatColor.GOLD + "Nether brick: " + ChatColor.WHITE + mining.getXp(Material.NETHER_BRICK) + " xp");
			player.sendMessage(ChatColor.GOLD + "Stone brick: " + ChatColor.WHITE + mining.getXp(Material.SMOOTH_BRICK) + " xp");
			player.sendMessage(ChatColor.GOLD + "Mossy cobblestone: " + ChatColor.WHITE + mining.getXp(Material.MOSSY_COBBLESTONE) + " xo");
			player.sendMessage(ChatColor.GOLD + "Gold Ore: " + ChatColor.WHITE + mining.getXp(Material.GOLD_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Lapis Ore: " + ChatColor.WHITE + mining.getXp(Material.LAPIS_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Diamond Ore: " + ChatColor.WHITE + mining.getXp(Material.DIAMOND_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Emerald Ore: " + ChatColor.WHITE + mining.getXp(Material.EMERALD_ORE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Sandstone: " + ChatColor.WHITE + mining.getXp(Material.SANDSTONE) + " xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                  -"));
		}
		return true;
	}
}

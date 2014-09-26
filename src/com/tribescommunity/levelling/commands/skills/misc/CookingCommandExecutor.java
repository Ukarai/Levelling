package com.tribescommunity.levelling.commands.skills.misc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.misc.Cooking;

public class CookingCommandExecutor implements CommandExecutor {
	private Levelling plugin;
	private Cooking cooking;

	public CookingCommandExecutor(Levelling instance) {
		plugin = instance;
		cooking = plugin.getSkillHandler().getCooking();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.COOKING;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6COOKING&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by crafting or cooking food");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Furnace cooking xp");
			player.sendMessage(ChatColor.GOLD + "Baked potato: " + cooking.getFurnaceCookedXp(Material.BAKED_POTATO) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Cooked chicken: " + cooking.getFurnaceCookedXp(Material.COOKED_CHICKEN) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Cooked fish: " + cooking.getFurnaceCookedXp(Material.COOKED_FISH) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Cooked beef: " + cooking.getFurnaceCookedXp(Material.COOKED_BEEF) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Pork: " + cooking.getFurnaceCookedXp(Material.PORK) + ChatColor.WHITE + "xp");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Crafting bench cooking xp");
			player.sendMessage(ChatColor.GOLD + "Cake: " + cooking.getCraftingBencheCookedXp(Material.CAKE) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Bread: " + cooking.getCraftingBencheCookedXp(Material.BREAD) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Cookie: " + cooking.getCraftingBencheCookedXp(Material.COOKIE) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Golden Carrot: " + cooking.getCraftingBencheCookedXp(Material.GOLDEN_CARROT) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Golden Apple: " + cooking.getCraftingBencheCookedXp(Material.GOLDEN_APPLE) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Mushroom Soup: " + cooking.getCraftingBencheCookedXp(Material.MUSHROOM_SOUP) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.GOLD + "Pumpkin Pie: " + cooking.getCraftingBencheCookedXp(Material.PUMPKIN_PIE) + ChatColor.WHITE + "xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                              -"));
		}
		return false;
	}
}

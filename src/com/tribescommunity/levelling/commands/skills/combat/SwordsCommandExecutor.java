package com.tribescommunity.levelling.commands.skills.combat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.combat.Swords;

public class SwordsCommandExecutor implements CommandExecutor {
	private Levelling plugin;
	private Swords swords;

	public SwordsCommandExecutor(Levelling instance) {
		plugin = instance;
		swords = plugin.getSkillHandler().getSwords();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.SWORDS;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6SWORDS&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by hitting the mobs listed below with a sword in your hand");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Player: " + ChatColor.WHITE + swords.getXp(EntityType.PLAYER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Zombie: " + ChatColor.WHITE + swords.getXp(EntityType.ZOMBIE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Skeleton: " + ChatColor.WHITE + swords.getXp(EntityType.SKELETON) + " xp");
			player.sendMessage(ChatColor.GOLD + "Zombie pigman: " + ChatColor.WHITE + swords.getXp(EntityType.PIG_ZOMBIE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Bat: " + ChatColor.WHITE + swords.getXp(EntityType.BAT) + " xp");
			player.sendMessage(ChatColor.GOLD + "Creeper: " + ChatColor.WHITE + swords.getXp(EntityType.CREEPER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Enderman: " + ChatColor.WHITE + swords.getXp(EntityType.ENDERMAN) + " xp");
			player.sendMessage(ChatColor.GOLD + "Cave spider: " + ChatColor.WHITE + swords.getXp(EntityType.CAVE_SPIDER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Spider: " + ChatColor.WHITE + swords.getXp(EntityType.SPIDER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Slime: " + ChatColor.WHITE + swords.getXp(EntityType.SLIME) + " xp");
			player.sendMessage(ChatColor.GOLD + "Magma cube: " + ChatColor.WHITE + swords.getXp(EntityType.MAGMA_CUBE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Blaze: " + ChatColor.WHITE + swords.getXp(EntityType.BLAZE) + " xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                             -"));

		}
		return true;
	}
}

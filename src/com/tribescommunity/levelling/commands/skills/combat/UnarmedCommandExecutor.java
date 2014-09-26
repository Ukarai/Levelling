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
import com.tribescommunity.levelling.skills.combat.Unarmed;

public class UnarmedCommandExecutor implements CommandExecutor {
	private Levelling plugin;
	private Unarmed unarmed;

	public UnarmedCommandExecutor(Levelling instance) {
		plugin = instance;
		unarmed = plugin.getSkillHandler().getUnarmed();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.UNARMED;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6UNARMED&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by hitting the mobs listed below with nothing in your hand");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Player: " + ChatColor.WHITE + unarmed.getXp(EntityType.PLAYER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Zombie: " + ChatColor.WHITE + unarmed.getXp(EntityType.ZOMBIE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Skeleton: " + ChatColor.WHITE + unarmed.getXp(EntityType.SKELETON) + " xp");
			player.sendMessage(ChatColor.GOLD + "Zombie pigman: " + ChatColor.WHITE + unarmed.getXp(EntityType.PIG_ZOMBIE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Bat: " + ChatColor.WHITE + unarmed.getXp(EntityType.BAT) + " xp");
			player.sendMessage(ChatColor.GOLD + "Creeper: " + ChatColor.WHITE + unarmed.getXp(EntityType.CREEPER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Enderman: " + ChatColor.WHITE + unarmed.getXp(EntityType.ENDERMAN) + " xp");
			player.sendMessage(ChatColor.GOLD + "Cave spider: " + ChatColor.WHITE + unarmed.getXp(EntityType.CAVE_SPIDER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Spider: " + ChatColor.WHITE + unarmed.getXp(EntityType.SPIDER) + " xp");
			player.sendMessage(ChatColor.GOLD + "Slime: " + ChatColor.WHITE + unarmed.getXp(EntityType.SLIME) + " xp");
			player.sendMessage(ChatColor.GOLD + "Magma cube: " + ChatColor.WHITE + unarmed.getXp(EntityType.MAGMA_CUBE) + " xp");
			player.sendMessage(ChatColor.GOLD + "Blaze: " + ChatColor.WHITE + unarmed.getXp(EntityType.BLAZE) + " xp");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                              -"));
		}
		return true;
	}
}

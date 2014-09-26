package com.tribescommunity.levelling.commands.skills.misc;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.repair.ArmourMaterial;
import com.tribescommunity.levelling.data.repair.ToolMaterial;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.misc.Repair;

public class RepairCommandExecutor implements CommandExecutor {
	private Levelling plugin;
	private Repair repair;

	public RepairCommandExecutor(Levelling instance) {
		plugin = instance;
		repair = plugin.getSkillHandler().getRepair();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());
			Skill skill = Skill.REPAIR;

			player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6REPAIR&f&m            -"));
			player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
			player.sendMessage(ChatColor.GOLD + "Xp is gained by right clicking an iron block with the tool which need repairing");
			player.sendMessage(ChatColor.GOLD + "If your inventory contains the respective material then the item will be repaired by 1/3 of it's max durability");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Tool repair xp values");
			for (ToolMaterial tm : ToolMaterial.values()) {
				player.sendMessage(ChatColor.GOLD + StringUtils.capitalize(tm.toString().toLowerCase()) + ChatColor.WHITE + " " + repair.getXp(tm));
			}
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Armour repair xp values");
			for (ArmourMaterial am : ArmourMaterial.values()) {
				player.sendMessage(ChatColor.GOLD + StringUtils.capitalize(am.toString().toLowerCase()) + ChatColor.WHITE + " " + repair.getXp(am));
			}
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                             -"));
		}
		return true;
	}
}

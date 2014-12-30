package com.tribescommunity.levelling.commands.skills;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

public class SkillInfoCommand implements CommandExecutor {

	private Levelling plugin;
	private Skill skill;
	private LevellingSkill levellingSkill;

	public SkillInfoCommand(Levelling instance, Skill skill) {
		plugin = instance;
		this.skill = skill;
		this.levellingSkill = plugin.getSkillHandler().getLevellingSkill(skill);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			player.sendMessage("---------- " + skill.getName() + " ----------");
			player.sendMessage(ChatColor.GOLD + "Currently Level: " + ChatColor.WHITE + user.getLevel(skill));
			player.sendMessage("---------- Xp table ----------");
			player.sendMessage(levellingSkill.getXpMethodInfo());
			player.sendMessage("");
			for (String row : levellingSkill.getXpTable(user.getLevel(skill))) {
				String[] split = row.split(":");

				player.sendMessage(ChatColor.GOLD + split[0] + ":" + ChatColor.WHITE + split[1]);
			}
		}
		return true;
	}
}

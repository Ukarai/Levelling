/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.tribescommunity.levelling.skills.misc.Building;

/**
 *
 * @author David
 */
public class BuildingCommandExecutor implements CommandExecutor {

    Levelling plugin;
    Building building;

    public BuildingCommandExecutor(Levelling instance) {
        plugin = instance;
        building = plugin.getSkillHandler().getBuilding();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = plugin.getUser(player.getName());
            Skill skill = Skill.BUILDING;

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &m           -&6BUILDING&f&m            -"));
            player.sendMessage(ChatColor.GOLD + "Your level: " + ChatColor.WHITE + user.getLevel(skill));
            player.sendMessage(ChatColor.GOLD + "Xp to next level: " + ChatColor.WHITE + (user.getXpForLevel(user.getLevel(skill) + 1) - user.getXp(skill)));
            player.sendMessage(ChatColor.GOLD + "Xp is gained by building houses, terraforming, and so on. Listed below are common blocks and the Xp gained from them.");
            player.sendMessage("");
            player.sendMessage(ChatColor.GOLD + skill.getName() + " xp table");
            player.sendMessage("");
            player.sendMessage(ChatColor.GOLD + "Stone: " + ChatColor.WHITE + building.getXp(Material.STONE) + " xp");
            player.sendMessage(ChatColor.GOLD + "Dirt: " + ChatColor.WHITE + building.getXp(Material.DIRT) + " xp");
            player.sendMessage(ChatColor.GOLD + "Cobblestone: " + ChatColor.WHITE + building.getXp(Material.COBBLESTONE) + " xp");
            player.sendMessage(ChatColor.GOLD + "Sand: " + ChatColor.WHITE + building.getXp(Material.SAND) + " xp");
            player.sendMessage(ChatColor.GOLD + "Wooden Plank: " + ChatColor.WHITE + building.getXp(Material.WOOD) + " xp");
            player.sendMessage(ChatColor.GOLD + "Gravel: " + ChatColor.WHITE + building.getXp(Material.GRAVEL) + " xp");
            player.sendMessage(ChatColor.GOLD + "Glass: " + ChatColor.WHITE + building.getXp(Material.GLASS) + " xp");
            player.sendMessage(ChatColor.GOLD + "Sandstone: " + ChatColor.WHITE + building.getXp(Material.SANDSTONE) + " xp");
            player.sendMessage(ChatColor.GOLD + "Wool: " + ChatColor.WHITE + building.getXp(Material.WOOL) + " xp");
            player.sendMessage(ChatColor.GOLD + "Stone Brick: " + ChatColor.WHITE + building.getXp(Material.SMOOTH_BRICK) + " xp");
            player.sendMessage(ChatColor.GOLD + "Stone Slab: " + ChatColor.WHITE + building.getXp(Material.STEP) + " xp");
            player.sendMessage(ChatColor.GOLD + "Brick: " + ChatColor.WHITE + building.getXp(Material.BRICK) + " xp");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m                                  -"));
        }
        return true;
    }
}

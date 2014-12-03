package com.tribescommunity.levelling.skills.misc;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

/* 
 * Date: 20 Nov 2012
 * Time: 15:44:46
 * Maker: theguynextdoor
 */
public class Pickpocketing extends com.tribescommunity.levelling.skills.Skill {

	public int xpGainedForSuccess;
	public int xpGainedForFailOne;
	public int xpGainedForFailTwo;

	public Pickpocketing() {
		xpGainedForSuccess = 512;
		xpGainedForFailOne = 256;
		xpGainedForFailTwo = 128;
	}

	@Override
	public String getName() {
		return com.tribescommunity.levelling.data.Skill.PICKPOCKETING.getName();
	}

	@SuppressWarnings("deprecation")
	public void handlePickpocketing(Levelling plugin, Player player, final User user, final Player clicked) {
		if (clicked.hasMetadata("NPC")) {
			return;
		}

		if (plugin.levellingGods.contains(plugin.getUser(clicked.getName()))) {
			player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.RED + "That person exempt from being pickpocketed at this time");
			player.sendMessage(ChatColor.RED + "If you believe this player is abusing this command, please report it to theguynextdoor");
			return;
		}
		if (plugin.levellingGods.contains(user)) {
			player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.RED + "You cannot pickpocket people while you have levelling god active");
			return;
		}
		if (clicked.getGameMode() == GameMode.CREATIVE) {
			player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.RED + "You cannot pickpocket people who are in creative mode");
			return;
		}
		Vector bv = player.getLocation().getDirection();
		Vector fv = clicked.getLocation().getDirection();

		if (Math.toDegrees(bv.angle(fv)) <= 45) {
			if (!user.getRecentlyPickpocketed().contains(clicked.getName())) {
				Random random = new Random();
				ItemStack is = clicked.getInventory().getItem(random.nextInt(clicked.getInventory().getSize()));

				user.getRecentlyPickpocketed().add(clicked.getName());
				boolean fail = true;

				if (is != null) {
					if (Math.random() <= successChance(user.getLevel(Skill.PICKPOCKETING))) {
						player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.WHITE + "Pickpocket successful");
						player.getInventory().addItem(is);
						clicked.getInventory().removeItem(is);

						if (sendMessageChance(user.getLevel(Skill.PICKPOCKETING))) {
							clicked.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.WHITE + "Better watch yo pockets dawg");
						}

						fail = false;

						// May be deprecated, but it still functions and is
						// necessary
						player.updateInventory();
						clicked.updateInventory();

						user.addXp(Skill.PICKPOCKETING, xpGainedForSuccess);
					} else {
						user.addXp(Skill.PICKPOCKETING, xpGainedForFailOne);
						player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.WHITE + "So close, yet so far");
					}
				} else {
					user.addXp(Skill.PICKPOCKETING, xpGainedForFailTwo);
					player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.WHITE + "Aww shit son, better luck next time");
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						user.getRecentlyPickpocketed().remove(clicked.getName());
					}
				}.runTaskLaterAsynchronously(plugin, 20 * cooldown(fail));
			} else {
				player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.RED + "Woah, you gotta wait before you pick their pocket again!");
			}
		} else {
			player.sendMessage(ChatColor.GOLD + "[Pickpocketing] " + ChatColor.RED + "You must be behind the player to pickpocket them");
		}
	}

	public double successChance(int level) {
		double chance = 0.75 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	public boolean sendMessageChance(int level) {
		return Math.random() >= (1 / 360) * level;
	}

	public int cooldown(boolean fail) {
		return !fail ? 30 : 90;
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}
}

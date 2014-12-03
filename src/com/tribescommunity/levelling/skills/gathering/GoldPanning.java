/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tribescommunity.levelling.skills.gathering;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;

/**
 * 
 * @author David
 */
public class GoldPanning extends Skill {

	public int SUCCESS_XP = 128;
	public int FAIL_XP = 64;

	@SuppressWarnings("deprecation")
	public void pan(User user, PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack inHand = player.getItemInHand();
		long xpToGive = 0;

		if (inHand != null && inHand.hasItemMeta() && inHand.getItemMeta().hasDisplayName() && inHand.getItemMeta().getDisplayName().equals("Panning Bowl")) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = e.getClickedBlock().getRelative(BlockFace.UP);

				if (block.getType() == Material.STATIONARY_WATER) {
					if (block.getData() > 0) {
						if (shouldDropLoot(user.getLevel(com.tribescommunity.levelling.data.Skill.GOLDPANNING))) {
							block.getWorld().dropItemNaturally(block.getRelative(BlockFace.UP).getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
							xpToGive += SUCCESS_XP * (1 + (user.getLevel(com.tribescommunity.levelling.data.Skill.GOLDPANNING) / 100));
						} else {
							xpToGive += FAIL_XP * (1 + (user.getLevel(com.tribescommunity.levelling.data.Skill.GOLDPANNING) / 200));
						}

						if (shouldRemoveBowl(user.getLevel(com.tribescommunity.levelling.data.Skill.GOLDPANNING)))
							player.setItemInHand(removeBowl(player, inHand));
					}
				}
			}
		}

		user.addXp(com.tribescommunity.levelling.data.Skill.GOLDPANNING, xpToGive);
	}

	private boolean shouldRemoveBowl(int level) {
		double random = Math.random();
		return random <= 0.75 / (Levelling.MAX_SKILL_LEVEL * level) * 100;
	}

	private ItemStack removeBowl(Player player, ItemStack inHand) {
		int amount = inHand.getAmount();
		amount--;

		if (amount == 0) {
			inHand = null;
		} else {
			inHand.setAmount(amount);
		}

		return inHand;
	}

	@Override
	public String getName() {
		return "Gold Panning";
	}

	@Override
	public RightClickAbility getAbility() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean shouldDropLoot(int level) {
		return Math.random() <= ((0.4 / Levelling.MAX_SKILL_LEVEL) * level);
	}
}

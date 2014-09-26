package com.tribescommunity.levelling.abilities.gathering;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;

public class FarmingAbility extends RightClickAbility {

	public FarmingAbility(String name, Skill skill, Levelling plugin) {
		super(name, skill, com.tribescommunity.levelling.data.Skill.FARMING, plugin);
	}

	@Override
	public void doAbility(Event ev) {
		if (ev instanceof BlockBreakEvent) {
			BlockBreakEvent e = (BlockBreakEvent) ev;
			Player player = e.getPlayer();
			User user = plugin.getUser(player.getName());
			int radius = plugin.getSkillHandler().getFarming().getFullHarvestRadius(user);

			if (plugin.getSkillHandler().getFarming().getXp(e.getBlock().getType()) > 0) {
				int blockX = e.getBlock().getLocation().getBlockX();
				int blockY = e.getBlock().getLocation().getBlockY();
				int blockZ = e.getBlock().getLocation().getBlockZ();

				for (int xx = -radius; xx < radius; xx++) {
					for (int zz = -radius; zz < radius; zz++) {
						Block block = player.getWorld().getBlockAt(blockX + xx, blockY, blockZ + zz);

						if (plugin.getSkillHandler().getFarming().getXp(block.getType()) > 0) {
							BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
							
							if (!blockBreakEvent.isCancelled()) {
								for (ItemStack is : block.getDrops()) {
									block.getWorld().dropItemNaturally(block.getLocation(), is);
								}
								block.breakNaturally();
								user.addXp(com.tribescommunity.levelling.data.Skill.FARMING, 16);
							}
						}
					}
				}
			}
		}
	}
}

package com.tribescommunity.levelling.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.tribescommunity.levelling.Levelling;

/* 
 * Date: 15 Nov 2012
 * Time: 20:09:00
 * Maker: theguynextdoor
 */
public class BlockListener implements Listener {

	private Levelling plugin;

	public BlockListener(Levelling instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockBreak(BlockBreakEvent e) {
		Block block = e.getBlock();

		if (block.hasMetadata(Levelling.USER_PLACED_META_STRING)) {
			block.removeMetadata(Levelling.USER_PLACED_META_STRING, plugin);
			plugin.placedBlocks.remove(plugin.locToString(block.getLocation()));
		}

		for (int y = block.getLocation().getBlockY(); y < block.getWorld().getMaxHeight(); y++) {
			Block relative = block.getRelative(block.getLocation().getBlockX(), y, block.getLocation().getBlockZ());
			boolean sandOrGravel = relative.getType() == Material.SAND || relative.getType() == Material.GRAVEL;

			if (sandOrGravel) {
				relative.removeMetadata(Levelling.USER_PLACED_META_STRING, plugin);
			} else {
				break;
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void blockPlace(BlockPlaceEvent e) {
		Block block = e.getBlockPlaced();
		Material type = block.getType();
		boolean sandOrGravel = type == Material.SAND || type == Material.GRAVEL;
		boolean willFall = block.getRelative(BlockFace.DOWN).getType() == Material.AIR;

		if (sandOrGravel) {
			if (!willFall) {
				block.setMetadata(Levelling.USER_PLACED_META_STRING, new FixedMetadataValue(plugin, true));
				plugin.placedBlocks.put(plugin.locToString(block.getLocation()), true);
			}
		} else {
			block.setMetadata(Levelling.USER_PLACED_META_STRING, new FixedMetadataValue(plugin, true));
			plugin.placedBlocks.put(plugin.locToString(block.getLocation()), true);
		}
	}

	@EventHandler
	public void pistonExtend(BlockPistonExtendEvent e) {
		List<Block> toSetData = new ArrayList<Block>();

		for (Block block : e.getBlocks()) {
			if (block.hasMetadata(Levelling.USER_PLACED_META_STRING)) {
				block.removeMetadata(Levelling.USER_PLACED_META_STRING, plugin);
				toSetData.add(block.getRelative(e.getDirection()));
			}
		}

		for (Block block : toSetData) {
			block.setMetadata(Levelling.USER_PLACED_META_STRING, new FixedMetadataValue(plugin, true));
		}
	}

	@EventHandler
	public void pistonRetract(final BlockPistonRetractEvent e) {
		if (e.isSticky()) {
			new BukkitRunnable() {
				@Override
				public void run() {
					if (e.getRetractLocation().getBlock().hasMetadata(Levelling.USER_PLACED_META_STRING)) {
						e.getRetractLocation().getBlock().removeMetadata(Levelling.USER_PLACED_META_STRING, plugin);
						e.getRetractLocation().getBlock().getRelative(e.getDirection().getOppositeFace())
								.setMetadata(Levelling.USER_PLACED_META_STRING, new FixedMetadataValue(plugin, true));
					}
				}
			}.runTask(plugin);

		}
	}
}

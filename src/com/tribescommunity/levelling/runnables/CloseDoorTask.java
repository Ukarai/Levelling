package com.tribescommunity.levelling.runnables;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/* 
 * Date: 23 Nov 2012
 * Time: 19:58:16
 * Maker: theguynextdoor
 */
public class CloseDoorTask implements Runnable {
	private Block block;

	public CloseDoorTask(Block block) {
		this.block = block;
	}

	@Override
	public void run() {
		if (block != null) {
			if (block.getState().getType() == Material.IRON_DOOR_BLOCK) {
				if (!isDoorClosed(block)) {
					closeDoor(block);
				}
			}
		}
	}

	static void closeDoor(Block block) {
		byte data = block.getData();
		if ((data & 0x8) == 0x8) {
			block = block.getRelative(BlockFace.DOWN);
			data = block.getData();
		}
		if (!isDoorClosed(block)) {
			data = (byte) (data & 0xb);
			block.setData(data, true);
			block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
		}
	}

	static boolean isDoorClosed(Block block) {
		byte data = block.getData();
		if ((data & 0x8) == 0x8) {
			block = block.getRelative(BlockFace.DOWN);
			data = block.getData();
		}
		return ((data & 0x4) == 0);
	}

}

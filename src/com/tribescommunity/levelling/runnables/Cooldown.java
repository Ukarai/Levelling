package com.tribescommunity.levelling.runnables;

import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown extends BukkitRunnable {

	int time;

	public Cooldown(int time) {
		this.time = time;
	}

	@Override
	public void run() {
		if (time > 0)
			time--;
	}
}

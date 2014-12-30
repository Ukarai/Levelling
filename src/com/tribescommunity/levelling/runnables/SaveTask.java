package com.tribescommunity.levelling.runnables;

import java.io.IOException;

import com.tribescommunity.levelling.data.Backend;

/* 
 * Date: 17 Nov 2012
 * Time: 01:06:22
 * Maker: theguynextdoor
 */
public class SaveTask implements Runnable {
	private Backend backend;
	private int timesRan = 0;

	public SaveTask(Backend backend) {
		this.backend = backend;
	}

	@Override
	public void run() {
		backend.saveAllTxt();
		try {
			backend.saveAchievements();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		timesRan++;

		if (timesRan % 6 == 0) {
			try {
				backend.savePlacedBlocks();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

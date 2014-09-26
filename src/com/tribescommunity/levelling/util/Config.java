package com.tribescommunity.levelling.util;

import org.bukkit.configuration.file.FileConfiguration;

import com.tribescommunity.levelling.Levelling;

public class Config {

	private Levelling plugin;
	private FileConfiguration config;

	public Config(Levelling instance) {
		plugin = instance;
		config = plugin.getConfig();
	}

	public void init() {
		config.addDefault("Economy.Costs.Class.Train", 50000);
		config.addDefault("Economy.Costs.Class.Untrain", 50000);
		plugin.saveConfig();
	}
	
	public int getUntrainCost() {
		return config.getInt("Economy.Costs.Class.Untrain");
	}

}

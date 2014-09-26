package com.tribescommunity.levelling.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.events.SkillLevelUpEvent;

public class SelfListener implements Listener {
	private Levelling plugin;

	public SelfListener(Levelling instance) {
		plugin = instance;
	}

	@EventHandler
	public void skillLevelUp(SkillLevelUpEvent e) {
		User user = e.getUser();

		plugin.getLeaderboards().update(user);

		if (user.hasClass()) {
			user.checkClassLevel();
		}
	}

}

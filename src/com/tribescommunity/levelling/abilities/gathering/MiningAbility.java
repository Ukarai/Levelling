package com.tribescommunity.levelling.abilities.gathering;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;
import com.tribescommunity.levelling.skills.gathering.Mining;

public class MiningAbility extends RightClickAbility {

	private Mining mining;

	public MiningAbility(String name, LevellingSkill skill, Levelling plugin) {
		super(name, skill, Skill.MINING, plugin);

		mining = (Mining) skill;
	}

	@Override
	public void doAbility(Event e) {
		if (e instanceof BlockBreakEvent) {
			BlockBreakEvent ev = (BlockBreakEvent) e;
			Player player = ev.getPlayer();
			User user = plugin.getUser(player.getName());

			if (isActive(player) && mining.getXp(ev.getBlock()) > 0 && !ev.getBlock().hasMetadata(Levelling.USER_PLACED_META_STRING)) {
				ev.setExpToDrop(1 + ((Mining) skill).getExtraXpToDrop(user));
			}
		}
	}
}

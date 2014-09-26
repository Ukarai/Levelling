package com.tribescommunity.levelling.abilities.gathering;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.Skill;
import com.tribescommunity.levelling.skills.gathering.Mining;

public class MiningAbility extends RightClickAbility {

	public MiningAbility(String name, Skill skill, Levelling plugin) {
		super(name, skill, com.tribescommunity.levelling.data.Skill.MINING, plugin);
	}

	@Override
	public void doAbility(Event e) {
		if (e instanceof BlockBreakEvent) {
			BlockBreakEvent ev = (BlockBreakEvent) e;
			Player player = ev.getPlayer();
			User user = plugin.getUser(player.getName());

			if (isActive(player) && plugin.getSkillHandler().getMining().getXp(ev.getBlock()) > 0 && !ev.getBlock().hasMetadata(Levelling.USER_PLACED_META_STRING)) {
				ev.setExpToDrop(1 + ((Mining) skill).getExtraXpToDrop(user));
			}
		}
	}
}

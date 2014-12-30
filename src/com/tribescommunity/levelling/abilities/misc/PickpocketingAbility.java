package com.tribescommunity.levelling.abilities.misc;

import org.bukkit.event.Event;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.skills.LevellingSkill;

public class PickpocketingAbility extends RightClickAbility {

	public PickpocketingAbility(String name, LevellingSkill skill, com.tribescommunity.levelling.data.Skill skillEnum, Levelling instance) {
		super(name, skill, skillEnum, instance);
	}

	@Override
	public void doAbility(Event e) {

	}

}

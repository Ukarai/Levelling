package com.tribescommunity.levelling.skills;

import java.util.HashMap;
import java.util.Map;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.skills.combat.Archery;
import com.tribescommunity.levelling.skills.combat.Swords;
import com.tribescommunity.levelling.skills.combat.Unarmed;
import com.tribescommunity.levelling.skills.gathering.Archaeology;
import com.tribescommunity.levelling.skills.gathering.Farming;
import com.tribescommunity.levelling.skills.gathering.GoldPanning;
import com.tribescommunity.levelling.skills.gathering.Mining;
import com.tribescommunity.levelling.skills.gathering.Woodcutting;
import com.tribescommunity.levelling.skills.misc.Building;
import com.tribescommunity.levelling.skills.misc.Cooking;
import com.tribescommunity.levelling.skills.misc.Enchanting;
import com.tribescommunity.levelling.skills.misc.Lockpicking;
import com.tribescommunity.levelling.skills.misc.Pickpocketing;
import com.tribescommunity.levelling.skills.misc.Repair;

/* 
 * Date: 16 Nov 2012
 * Time: 22:27:19
 * Maker: theguynextdoor
 */
public class SkillHandler {

	private Levelling plugin;
	private Map<Skill, LevellingSkill> skills;

	public SkillHandler(Levelling instance) {
		plugin = instance;
		skills = new HashMap<>();

		skills.put(Skill.MINING, new Mining(plugin));
		skills.put(Skill.ARCHAEOLOGY, new Archaeology());
		skills.put(Skill.WOODCUTTING, new Woodcutting());
		skills.put(Skill.LOCKPICKING, new Lockpicking());
		skills.put(Skill.PICKPOCKETING, new Pickpocketing());
		skills.put(Skill.FARMING, new Farming(plugin));
		skills.put(Skill.SWORDS, new Swords());
		skills.put(Skill.UNARMED, new Unarmed());
		skills.put(Skill.ARCHERY, new Archery());
		skills.put(Skill.REPAIR, new Repair());
		skills.put(Skill.COOKING, new Cooking());
		skills.put(Skill.BUILDING, new Building());
		skills.put(Skill.GOLDPANNING, new GoldPanning());
		skills.put(Skill.ENCHANTING, new Enchanting(plugin));
	}

	public LevellingSkill getLevellingSkill(Skill skill) {
		return skills.get(skill);
	}

}

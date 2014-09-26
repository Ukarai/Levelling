package com.tribescommunity.levelling.data;

import com.tribescommunity.levelling.skills.combat.Archery;
import com.tribescommunity.levelling.skills.combat.Swords;
import com.tribescommunity.levelling.skills.combat.Unarmed;
import com.tribescommunity.levelling.skills.gathering.Archaeology;
import com.tribescommunity.levelling.skills.gathering.Farming;
import com.tribescommunity.levelling.skills.gathering.GoldPanning;
import com.tribescommunity.levelling.skills.gathering.Mining;
import com.tribescommunity.levelling.skills.gathering.Woodcutting;
import com.tribescommunity.levelling.skills.misc.Enchanting;
import com.tribescommunity.levelling.skills.misc.Lockpicking;
import com.tribescommunity.levelling.skills.misc.Pickpocketing;
import com.tribescommunity.levelling.skills.misc.Repair;

/* 
 * Date: 15 Nov 2012
 * Time: 20:12:23
 * Maker: theguynextdoor
 */
public enum Skill {

	MINING("Mining", 4),
	ARCHAEOLOGY("Archaeology", 6),
	WOODCUTTING("Woodcutting", 8),
	FARMING("Farming", 10),
	LOCKPICKING("Lockpicking", 12),
	PICKPOCKETING("Pickpocketing", 14),
	SWORDS("Swords", 16),
	UNARMED("Unarmed", 18),
	ARCHERY("Archery", 20),
	REPAIR("Smithing", 22),
	ENCHANTING("Enchanting", 24),
	COOKING("Cooking", 26),
	GOLDPANNING("Gold Panning", 28),
	BUILDING("Building", 30);

	private String name;
	private int pos;

	private Skill(String name, int pos) {
		this.name = name;
		this.pos = pos;
	}

	public String getName() {
		return name;
	}

	public static com.tribescommunity.levelling.data.Skill getSkill(com.tribescommunity.levelling.skills.Skill skill) {
		if (skill instanceof Mining) {
			return MINING;
		}
		if (skill instanceof Archaeology) {
			return ARCHAEOLOGY;
		}
		if (skill instanceof Lockpicking) {
			return LOCKPICKING;
		}
		if (skill instanceof Woodcutting) {
			return WOODCUTTING;
		}
		if (skill instanceof Pickpocketing) {
			return PICKPOCKETING;
		}
		if (skill instanceof Farming) {
			return FARMING;
		}
		if (skill instanceof Swords) {
			return SWORDS;
		}
		if (skill instanceof Unarmed) {
			return UNARMED;
		}
		if (skill instanceof Archery) {
			return ARCHERY;
		}
		if (skill instanceof Repair) {
			return REPAIR;
		}
		if (skill instanceof Enchanting) {
			return ENCHANTING;
		}
		if (skill instanceof GoldPanning) {
			return GOLDPANNING;
		}
		return null;
	}

	public int getPos() {
		return pos;
	}
}

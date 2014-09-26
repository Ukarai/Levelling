package com.tribescommunity.levelling.skills;

import com.tribescommunity.levelling.Levelling;
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
	protected Mining mining;
	protected Archaeology archaeology;
	protected Woodcutting woodcutting;
	protected Lockpicking lockpicking;
	protected Pickpocketing pickpocketing;
	protected Farming farming;
	protected Swords swords;
	protected Unarmed unarmed;
	protected Archery archery;
	protected Repair repair;
	protected Cooking cooking;
	protected Building building;
	protected GoldPanning goldPanning;
	protected Enchanting enchanting;

	public SkillHandler(Levelling instance) {
		plugin = instance;

		mining = new Mining(plugin);
		archaeology = new Archaeology();
		woodcutting = new Woodcutting();
		lockpicking = new Lockpicking();
		pickpocketing = new Pickpocketing();
		farming = new Farming(plugin);
		swords = new Swords();
		unarmed = new Unarmed();
		archery = new Archery();
		repair = new Repair();
		cooking = new Cooking();
		building = new Building();
		goldPanning = new GoldPanning();
		enchanting = new Enchanting(plugin);
	}

	public Mining getMining() {
		return mining;
	}

	public Archaeology getArchaeology() {
		return archaeology;
	}

	public Woodcutting getWoodcutting() {
		return woodcutting;
	}

	public Lockpicking getLockpicking() {
		return lockpicking;
	}

	public Pickpocketing getPickpocketing() {
		return pickpocketing;
	}

	public Farming getFarming() {
		return farming;
	}

	public Swords getSwords() {
		return swords;
	}

	public Unarmed getUnarmed() {
		return unarmed;
	}

	public Archery getArchery() {
		return archery;
	}

	public Repair getRepair() {
		return repair;
	}

	public Cooking getCooking() {
		return cooking;
	}

	public Building getBuilding() {
		return building;
	}

	public GoldPanning getGoldPanning() {
		return goldPanning;
	}

	public Enchanting getEnchanting() {
		return enchanting;
	}
}

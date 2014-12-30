package com.tribescommunity.levelling.skills.misc.enchanting;

import java.util.ArrayList;
import java.util.List;

import com.tribescommunity.levelling.skills.misc.enchanting.types.Enchant;
import com.tribescommunity.levelling.skills.misc.enchanting.weapon.PoisonEnchant;

public class EnchantDictionary {

	public static List<Enchant> allEnchants = new ArrayList<>();
	public static final PoisonEnchant POISON = new PoisonEnchant();

	static {
		allEnchants.add(POISON);
	}

	private EnchantDictionary() {
	}

}

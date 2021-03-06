package com.tribescommunity.levelling.skills.combat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 30 Nov 2012
 * Time: 23:15:48
 * Maker: theguynextdoor
 */
public class Unarmed implements LevellingSkill {

	@Override
	public String getName() {
		return Skill.UNARMED.getName();
	}

	public boolean isUnarmed(Player player) {
		return player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR;
	}

	public int getXp(EntityType type) {
		switch (type) {
		case PLAYER:
			return 32;
		case ZOMBIE:
			return 16;
		case SKELETON:
		case PIG_ZOMBIE:
			return 24;
		case BAT:
		case CREEPER:
			return 48;
		case ENDERMAN:
			return 64;
		case CAVE_SPIDER:
		case SPIDER:
		case SLIME:
		case MAGMA_CUBE:
		case BLAZE:
			return 32;
		default:
			return 0;
		}
	}

	public void uppercut(Player player, User user, Entity entity) {
		if (Math.random() <= upercutChance(user.getLevel(com.tribescommunity.levelling.data.Skill.UNARMED))) {
			if (entity instanceof Player) {
				Location loc = entity.getLocation();

				loc.setPitch(-90);
				entity.teleport(loc);
				player.sendMessage(ChatColor.GOLD + "[Unarmed] " + ChatColor.WHITE + "Uppercut!");
			}
		}
	}

	public double upercutChance(int level) {
		double chance = 0.35 / Levelling.MAX_SKILL_LEVEL;
		return chance * level;
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		for (EntityType et : EntityType.values()) {
			if (getXp(et) > 0) {
				table.add(StringUtils.capitalize(et.toString().toLowerCase()) + ": " + getXp(et));
			}
		}

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by attacking mobs with your fists";
	}

}

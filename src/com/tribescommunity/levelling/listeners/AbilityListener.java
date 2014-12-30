package com.tribescommunity.levelling.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.skills.SkillHandler;
import com.tribescommunity.levelling.skills.gathering.Farming;
import com.tribescommunity.levelling.skills.gathering.Mining;

public class AbilityListener implements Listener {
	private Levelling plugin;
	private SkillHandler skillHandler;

	private Mining mining;
	private Farming farming;

	private Set<Material> dontActivateOn;

	public AbilityListener(Levelling instance) {
		plugin = instance;
		skillHandler = plugin.getSkillHandler();
		mining = (Mining) skillHandler.getLevellingSkill(Skill.MINING);
		farming = (Farming) skillHandler.getLevellingSkill(Skill.FARMING);
		dontActivateOn = new HashSet<Material>();

		dontActivateOn.add(Material.IRON_BLOCK);
		dontActivateOn.add(Material.CHEST);
		dontActivateOn.add(Material.FURNACE);
		dontActivateOn.add(Material.ENCHANTMENT_TABLE);
		dontActivateOn.add(Material.ANVIL);
		dontActivateOn.add(Material.DISPENSER);
		dontActivateOn.add(Material.WORKBENCH);
		dontActivateOn.add(Material.ENDER_CHEST);
		dontActivateOn.add(Material.BEACON);
		dontActivateOn.add(Material.BREWING_STAND);
		dontActivateOn.add(Material.COMMAND);
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && dontActivateOn.contains(e.getClickedBlock().getType()))
				return;
			if (mining.isPick(player.getItemInHand())) {
				if (!mining.getAbility().isActive(player)) {
					mining.getAbility().ready(player);
				}
			}
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && farming.getXp(e.getClickedBlock().getType()) > 0) {
				if (farming.isHoe(player.getItemInHand())) {
					if (!farming.getAbility().isActive(player)) {
						farming.getAbility().ready(player);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void miningAbility(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (mining.getAbility().isReady(player) && mining.isPick(player.getItemInHand())) {
			mining.getAbility().activate(player);
		}

		if (mining.getAbility().isActive(player))
			mining.getAbility().doAbility(e);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void farmingAbility(BlockBreakEvent e) {
		Player player = e.getPlayer();

		if (farming.getAbility().isReady(player) && farming.isHoe(player.getItemInHand())) {
			farming.getAbility().activate(player);
		}

		if (farming.getAbility().isActive(player))
			farming.getAbility().doAbility(e);
	}

}

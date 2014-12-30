package com.tribescommunity.levelling.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.achievements.Ach;
import com.tribescommunity.levelling.achievements.AchievementManager;
import com.tribescommunity.levelling.achievements.Tracking;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.events.AchievementGetEvent;
import com.tribescommunity.levelling.events.SkillLevelUpEvent;
import com.tribescommunity.levelling.events.TrackingIncrementEvent;

public class AchievementListener implements Listener {

	private Levelling plugin;

	public AchievementListener(Levelling instance) {
		plugin = instance;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		if (!e.getTo().getBlock().equals(e.getFrom().getBlock())) {
			user.getAchievementTracker().increment(user, Tracking.BLOCKS_TRAVELLED);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		user.getAchievementTracker().increment(user, Tracking.MESSAGES_SENT);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerChat(EntityDeathEvent e) {
		if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
			Entity damager = event.getDamager();
			LivingEntity entity = e.getEntity();
			boolean wasPlayer = damager instanceof Player || (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player);

			if (wasPlayer) {
				Player player;

				if (damager instanceof Player)
					player = (Player) damager;
				else
					player = (Player) ((Projectile) damager).getShooter();

				User user = plugin.getUser(player.getName());

				if (isAnimal(entity.getType())) {
					user.getAchievementTracker().increment(user, Tracking.ANIMALS_KILLED);
				} else if (isHostile(entity.getType())) {
					user.getAchievementTracker().increment(user, Tracking.HOSTILE_MOBS_KILLED);
				} else if (entity.getType() == EntityType.PLAYER) {
					user.getAchievementTracker().increment(user, Tracking.PLAYERS_KILLED);
				}
			}
		}

	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerSkillLevelUp(SkillLevelUpEvent e) {
		User user = e.getUser();

		user.getAchievementTracker().increment(user, Tracking.TOTAL_SKILL_LEVELS_GAINED);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		User user = plugin.getUser(player.getName());

		user.getAchievementTracker().increment(user, Tracking.DEATHS);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		user.getAchievementTracker().increment(user, Tracking.LOGINS);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void blockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		user.getAchievementTracker().increment(user, Tracking.BLOCKS_BROKEN);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void blockPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		user.getAchievementTracker().increment(user, Tracking.BLOCKS_PLACED);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void playerTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		user.getAchievementTracker().increment(user, Tracking.TELEPORTS);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void itemCrafted(CraftItemEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			User user = plugin.getUser(player.getName());

			user.getAchievementTracker().increment(user, Tracking.ITEMS_CRAFTED);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void arrowShoot(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
			Player player = (Player) e.getEntity().getShooter();
			User user = plugin.getUser(player.getName());

			if (e.getEntityType() == EntityType.ARROW) {
				user.getAchievementTracker().increment(user, Tracking.ARROWS_SHOT);
			} else if (e.getEntityType() == EntityType.SNOWBALL) {
				user.getAchievementTracker().increment(user, Tracking.SNOWBALLS_THROWN);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true)
	public void killStreak(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		User uKilled = plugin.getUser(killed.getName());

		uKilled.getAchievementTracker().reset(Tracking.KILLSTREAK);

		if (killed.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) killed.getLastDamageCause();

			if (ev.getDamager() instanceof Player) {
				Player killer = (Player) ev.getDamager();
				User uKill = plugin.getUser(killer.getName());

				if ((uKill.inParty() && uKilled.inParty()) && !uKilled.getParty().getName().equals(uKill.getParty().getName()))
					uKill.getAchievementTracker().increment(uKill, Tracking.KILLSTREAK);
			} else if (ev.getDamager() instanceof Projectile) {
				if (((Projectile) ev.getDamager()).getShooter() instanceof Player) {
					Player killer = (Player) ev.getDamager();
					User uKill = plugin.getUser(killer.getName());

					if ((uKill.inParty() && uKilled.inParty()) && !uKilled.getParty().getName().equals(uKill.getParty().getName()))
						uKill.getAchievementTracker().increment(uKill, Tracking.KILLSTREAK);
				}
			}
		}
	}

	@EventHandler
	public void trackerIncrement(TrackingIncrementEvent e) {
		User user = e.getUser();

		for (Ach achievement : AchievementManager.allAchievements) {
			if (!user.getAchievementTracker().hasAchievement(achievement.getId())) {
				if (achievement.hasMetCriteria(user.getAchievementTracker())) {
					if (achievement.isAttainable()) {
						Bukkit.getPluginManager().callEvent(new AchievementGetEvent(e.getUser(), achievement));
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void achievementGet(AchievementGetEvent e) {
		User user = e.getUser();
		Ach achievement = e.getAchievement();
		String json = user.getName() + " has earned the achievement " + achievement.getJSON();

		user.getAchievementTracker().addAchievement(achievement);

		for (Player player : Bukkit.getOnlinePlayers())
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + json);
		if (achievement.hasTitle()) {
			Bukkit.getPlayerExact(user.getName()).sendMessage(ChatColor.GOLD + "You have unlocked the title " + ChatColor.WHITE + achievement.getTitle());
		}
	}

	public boolean isAnimal(EntityType type) {
		switch (type) {
		case CHICKEN:
		case COW:
		case MUSHROOM_COW:
		case PIG:
		case SHEEP:
			return true;
		default:
			return false;
		}
	}

	public boolean isHostile(EntityType type) {
		switch (type) {
		case ENDERMAN:
		case CREEPER:
		case BLAZE:
		case GHAST:
		case ENDER_DRAGON:
		case MAGMA_CUBE:
		case SILVERFISH:
		case SKELETON:
		case SLIME:
		case SPIDER:
		case CAVE_SPIDER:
		case ZOMBIE:
		case WITHER:
		case PIG_ZOMBIE:
		case WITCH:
			return true;
		default:
			return false;
		}
	}
}

package com.tribescommunity.levelling.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.user.User;

public class PerkListener implements Listener {

	private Levelling plugin;
	private Map<User, Long> perkCooldowns;

	public PerkListener(Levelling instance) {
		plugin = instance;
		perkCooldowns = new HashMap<>();
	}

	@EventHandler
	public void archerPerk(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		final User user = plugin.getUser(player.getName());

		if (user.hasClass() && user.getLevellingClass() == LevellingClass.ARCHER) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.ARROW) {
					if (!perkCooldowns.containsKey(user) || (System.nanoTime() - perkCooldowns.get(user) >= TimeUnit.MINUTES.toNanos(1))) {
						player.setWalkSpeed(0.5f);
						player.sendMessage(ChatColor.GREEN + "The wind helps you in your travels");

						new BukkitRunnable() {

							@Override
							public void run() {
								player.setWalkSpeed(0.2f);
								perkCooldowns.put(user, System.nanoTime());
								player.sendMessage(ChatColor.GREEN + "The wind fades");

								new BukkitRunnable() {
									public void run() {
										player.sendMessage(ChatColor.GREEN + "You feel the wind pick up again");
									};
								}.runTaskLater(plugin, 20 * 60);
							}

						}.runTaskLater(plugin, 20 * 5);
					} else {
						player.sendMessage(ChatColor.GREEN + "You do not feel a breeze");
					}
				}
			}
		}
	}

	@EventHandler
	public void thiefPerk(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player player = (Player) e.getDamager();
			User user = plugin.getUser(player.getName());
			Entity entity = e.getEntity();

			if (user.getLevellingClass() == LevellingClass.THIEF) {
				Vector bv = player.getLocation().getDirection();
				Vector fv = entity.getLocation().getDirection();

				if (Math.toDegrees(bv.angle(fv)) <= 45 && player.isSneaking())
					e.setDamage(e.getDamage() + 1);
			}
		}
	}

	@EventHandler
	public void crusaderPerk(EntityDeathEvent e) {
		Entity en = e.getEntity();

		if (en instanceof Player) {
			Player dead = (Player) en;

			if (dead.getKiller() instanceof Player) {
				Player killer = (Player) dead.getKiller();
				User user = plugin.getUser(killer.getName());

				if (user.getLevellingClass() == LevellingClass.CRUSADER) {
					killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 1));
				}
			}
		}
	}

	@EventHandler
	public void farmerPerk(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		if (user.getLevellingClass() == LevellingClass.FARMER) {
			if (isFood(e.getItem())) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20 * 5, 1));
			}
		}
	}

	public boolean isFood(ItemStack is) {
		if (is != null) {
			switch (is.getType()) {
			case BAKED_POTATO:
			case BREAD:
			case CAKE:
			case CARROT:
			case COOKED_CHICKEN:
			case COOKED_FISH:
			case COOKED_BEEF:
			case COOKIE:
			case GOLDEN_APPLE:
			case GOLDEN_CARROT:
			case MELON:
			case MUSHROOM_SOUP:
			case POISONOUS_POTATO:
			case POTATO:
			case PUMPKIN_PIE:
			case APPLE:
			case RAW_BEEF:
			case RAW_CHICKEN:
			case RAW_FISH:
			case PORK:
			case GRILLED_PORK:
			case ROTTEN_FLESH:
			case SPIDER_EYE:
				return true;
			default:
				return false;
			}
		}
		return false;
	}
}

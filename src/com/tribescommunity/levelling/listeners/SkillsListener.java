package com.tribescommunity.levelling.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.bukkit.scheduler.BukkitRunnable;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.Backend;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.runnables.CloseDoorTask;
import com.tribescommunity.levelling.skills.SkillHandler;
import com.tribescommunity.levelling.skills.combat.Archery;
import com.tribescommunity.levelling.skills.combat.Swords;
import com.tribescommunity.levelling.skills.combat.Unarmed;
import com.tribescommunity.levelling.skills.gathering.Archaeology;
import com.tribescommunity.levelling.skills.gathering.Farming;
import com.tribescommunity.levelling.skills.gathering.Mining;
import com.tribescommunity.levelling.skills.gathering.Woodcutting;
import com.tribescommunity.levelling.skills.misc.Building;
import com.tribescommunity.levelling.skills.misc.Cooking;
import com.tribescommunity.levelling.skills.misc.Lockpicking;
import com.tribescommunity.levelling.skills.misc.Pickpocketing;
import com.tribescommunity.levelling.skills.misc.Repair;

/* 
 * Date: 23 Nov 2012
 * Time: 19:51:08
 * Maker: theguynextdoor
 */
@SuppressWarnings("deprecation")
public class SkillsListener implements Listener {

	private Levelling plugin;
	private Backend backend;
	private SkillHandler skillHandler;
	private Mining mining;
	private Archaeology archaeology;
	private Lockpicking lockpicking;
	private Woodcutting woodcutting;
	private Farming farming;
	private Swords swords;
	private Unarmed unarmed;
	private Archery archery;
	private Pickpocketing pickpocketing;
	private Repair repair;
	private Cooking cooking;
	private Building building;

	public SkillsListener(Levelling instance) {
		plugin = instance;
		backend = plugin.getBackend();
		skillHandler = plugin.getSkillHandler();

		mining = skillHandler.getMining();
		archaeology = skillHandler.getArchaeology();
		lockpicking = skillHandler.getLockpicking();
		woodcutting = skillHandler.getWoodcutting();
		farming = skillHandler.getFarming();
		swords = skillHandler.getSwords();
		unarmed = skillHandler.getUnarmed();
		archery = skillHandler.getArchery();
		pickpocketing = skillHandler.getPickpocketing();
		repair = skillHandler.getRepair();
		cooking = skillHandler.getCooking();
		building = skillHandler.getBuilding();
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void mining(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (!block.hasMetadata(Levelling.USER_PLACED_META_STRING) && mining.isPick(player.getItemInHand())) {
			User user = backend.getUser(player.getName());
			user.addXp(Skill.MINING, mining.getXp(block));
			mining.xpDrop(e, user);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void archaeology(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (!block.hasMetadata(Levelling.USER_PLACED_META_STRING) && archaeology.isSpade(player.getItemInHand())) {
			User user = backend.getUser(player.getName());

			user.addXp(Skill.ARCHAEOLOGY, archaeology.getXp(block));
			archaeology.handleDrops(user, block);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void lockpicking(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		final User user = plugin.getUsers().get(player.getName());

		if (!user.recentLockpickFail) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block b = e.getClickedBlock();

				if (b.getState().getType() == Material.IRON_DOOR_BLOCK) {
					if (player.getItemInHand() != null && plugin.isLockpick(player.getItemInHand())) {
						Door door = (Door) b.getType().getNewData(b.getData());

						if (!door.isOpen()) {
							ItemStack inHand = player.getItemInHand();

							if (lockpicking.shouldBreak(user)) {
								if (inHand.getAmount() > 1) {
									inHand.setAmount(player.getItemInHand().getAmount() - 1);
								} else {
									player.setItemInHand(null);
								}
							}

							if (lockpicking.shouldUnlock(user)) {
								player.sendMessage(ChatColor.GOLD + "[Lockpicking] " + ChatColor.WHITE + "Lockpick success!");
								user.addXp(com.tribescommunity.levelling.data.Skill.LOCKPICKING, 192);
								b.setData((byte) (door.getData() ^ 4));

								Block relative = b.getRelative(BlockFace.DOWN);

								if (relative.getState().getType() == Material.IRON_DOOR_BLOCK) {
									relative.setData((byte) (b.getRelative(BlockFace.DOWN).getData() ^ 4));
								}

								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new CloseDoorTask(b), 20 * 5);
							} else {
								user.addXp(com.tribescommunity.levelling.data.Skill.LOCKPICKING, 64);
								player.sendMessage(ChatColor.GOLD + "[Lockpicking] " + ChatColor.WHITE + "Lockpick failed");
								user.recentLockpickFail = true;

								new BukkitRunnable() {
									@Override
									public void run() {
										user.recentLockpickFail = false;
									}
								}.runTaskLaterAsynchronously(plugin, 20 * 3);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void woodcutting(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		System.out.println(block.getType().toString());

		if ((block.getType() == Material.LOG || block.getType() == Material.LOG_2) && !block.hasMetadata(Levelling.USER_PLACED_META_STRING)) {
			if (woodcutting.isAxe(player.getItemInHand())) {
				User user = plugin.getUsers().get(player.getName());

				user.addXp(Skill.WOODCUTTING, 64);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void farming(BlockBreakEvent e) {
		Player player = e.getPlayer();

		if (farming.isFullGrownTop(e.getBlock())) {
			User user = plugin.getUsers().get(player.getName());

			user.addXp(Skill.FARMING, 64);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void swords(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();

			if (swords.isSword(player.getItemInHand())) {
				User user = plugin.getUsers().get(player.getName());

				user.addXp(Skill.SWORDS, swords.getXp(e.getEntityType()));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void unarmed(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();

			if (unarmed.isUnarmed(player)) {
				User user = plugin.getUsers().get(player.getName());

				user.addXp(Skill.UNARMED, unarmed.getXp(e.getEntityType()));
				unarmed.uppercut(player, user, e.getEntity());
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void archery(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();

			if (arrow.getShooter() instanceof Player) {
				User user = plugin.getUsers().get(((Player) arrow.getShooter()).getName());

				double dif = arrow.getLocation().getY() - e.getEntity().getLocation().getY() - 1.5;

				if (dif > 0.1 && dif < 0.5) {
					e.setDamage(e.getDamage() + 2);
					user.addXp(Skill.ARCHERY, archery.getXp(e.getEntityType()) + 64);
				} else {
					user.addXp(Skill.ARCHERY, archery.getXp(e.getEntityType()));
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void pickpocket(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player player = e.getPlayer();

			if (player.isSneaking()) {
				Player clicked = (Player) e.getRightClicked();
				User user = plugin.getUser(player.getName());
				pickpocketing.handlePickpocketing(plugin, player, user, clicked);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void repair(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();

			if (block.getType() == Material.IRON_BLOCK) {
				repair.repair(plugin.getUser(e.getPlayer().getName()), e.getPlayer(), e.getItem());
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void enchant(EnchantItemEvent e) {
		Player player = e.getEnchanter();
		User user = plugin.getUser(player.getName());

		plugin.getSkillHandler().getEnchanting().enchantEvent(e);
	}

	@EventHandler(ignoreCancelled = true)
	public void swordsBlock(EntityDamageByEntityEvent e) {
		if (plugin.doBlockAbility) {
			if (e.getEntity() instanceof Player) {
				Player player = (Player) e.getEntity();
				User user = plugin.getUser(player.getName());

				if (player.isBlocking()) {
					e.setDamage(e.getDamage() - swords.damageToBlock(user, e.getDamage()));
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void furnace(FurnaceExtractEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());
		user.addXp(Skill.COOKING, cooking.getFurnaceCookedXp(e.getItemType()) * e.getItemAmount());
		user.addXp(Skill.REPAIR, repair.getXp(e.getItemType()) * e.getItemAmount());
	}

	@EventHandler(ignoreCancelled = true)
	public void cookingCraftingBench(CraftItemEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			User user = plugin.getUser(e.getWhoClicked().getName());
			user.addXp(Skill.COOKING, cooking.getCraftingBencheCookedXp(e.getRecipe().getResult().getType()));
		}
	}

	@EventHandler
	public void cookingSword(EntityDeathEvent e) {
		EntityType type = e.getEntityType();

		if (type == EntityType.CHICKEN || type == EntityType.COW || type == EntityType.PIG) {
			LivingEntity killed = e.getEntity();

			if (killed.getKiller() instanceof Player) {
				Player player = killed.getKiller();

				if (player.getItemInHand() != null) {
					ItemStack is = player.getItemInHand();
					Material t = is.getType();

					if (t == Material.WOOD_SWORD || t == Material.STONE_SWORD || t == Material.GOLD_SWORD || t == Material.IRON_SWORD || t == Material.DIAMOND_SWORD) {
						if (is.containsEnchantment(Enchantment.FIRE_ASPECT)) {
							User user = plugin.getUser(player.getName());

							user.addXp(Skill.COOKING, cooking.getFireKillDrops(type));
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void buildingPlace(BlockPlaceEvent e) {
		Block block = e.getBlock();
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		user.addXp(Skill.BUILDING, building.getXp(block.getTypeId()));

		if (building.shouldPreserveBlock(user) && player.getItemInHand() != null && building.getXp(player.getItemInHand().getType()) > 0) {
			if (block.getType() != Material.ANVIL && block.getType() != Material.ENCHANTMENT_TABLE && block.getType() != Material.SMOOTH_BRICK) {
				player.getItemInHand().setAmount(player.getItemInHand().getAmount() + 1);
			}
		}
	}

	@EventHandler
	public void panning(PlayerInteractEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			skillHandler.getGoldPanning().pan(plugin.getUser(e.getPlayer().getName()), e);
	}

	@EventHandler(ignoreCancelled = true)
	public void archerPerk(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();

			if (arrow.getShooter() instanceof Player && e.getEntity().getType() != EntityType.PLAYER) {
				Player player = (Player) arrow.getShooter();
				User user = plugin.getUser(player.getName());

				if (user.hasClass() && user.getLevellingClass() == LevellingClass.ARCHER) {
					e.setDamage(e.getDamage() * 2);
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void crusaderPerk(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			User user = plugin.getUser(player.getName());

			if (user.hasClass() && user.getLevellingClass() == LevellingClass.CRUSADER) {
				if (e.getEntity().getType() != EntityType.PLAYER) {
					if (player.getItemInHand() != null && repair.isSword(player.getItemInHand())) {
						e.setDamage(e.getDamage() * 2);
					}
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void gathererPerk(BlockBreakEvent e) {
		Player player = e.getPlayer();
		User user = plugin.getUser(player.getName());

		if (user.hasClass() && user.getLevellingClass() == LevellingClass.GATHERER) {
			Block block = e.getBlock();
			Material type = block.getType();

			if (!block.hasMetadata(Levelling.USER_PLACED_META_STRING)) {
				if (Math.random() <= 0.25) {
					if (type == Material.STONE) {
						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COBBLESTONE, 1));
					} else if (type == Material.SAND) {
						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SAND, 1));
					} else if (type == Material.GRAVEL) {
						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GRAVEL, 1));
					} else if (type == Material.DIRT) {
						block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIRT, 1));
					}
				}
			}
		}
	}

}

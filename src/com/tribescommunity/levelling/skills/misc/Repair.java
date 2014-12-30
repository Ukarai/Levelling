package com.tribescommunity.levelling.skills.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.tribescommunity.levelling.abilities.RightClickAbility;
import com.tribescommunity.levelling.data.LevellingClass;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.repair.ArmourMaterial;
import com.tribescommunity.levelling.data.repair.ToolMaterial;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.LevellingSkill;

/* 
 * Date: 2 Dec 2012
 * Time: 19:18:26
 * Maker: theguynextdoor
 */
public class Repair implements LevellingSkill {

	private Map<Material, Short> maxDurabilities;

	public Repair() {
		maxDurabilities = new HashMap<Material, Short>();

		/*
		 * Tools
		 */
		maxDurabilities.put(Material.WOOD_SPADE, (short) 60);
		maxDurabilities.put(Material.WOOD_AXE, (short) 60);
		maxDurabilities.put(Material.WOOD_PICKAXE, (short) 60);
		maxDurabilities.put(Material.WOOD_HOE, (short) 60);
		maxDurabilities.put(Material.WOOD_SWORD, (short) 60);

		maxDurabilities.put(Material.STONE_SPADE, (short) 132);
		maxDurabilities.put(Material.STONE_AXE, (short) 132);
		maxDurabilities.put(Material.STONE_PICKAXE, (short) 132);
		maxDurabilities.put(Material.STONE_HOE, (short) 132);
		maxDurabilities.put(Material.STONE_SWORD, (short) 132);

		maxDurabilities.put(Material.IRON_SPADE, (short) 251);
		maxDurabilities.put(Material.IRON_AXE, (short) 251);
		maxDurabilities.put(Material.IRON_PICKAXE, (short) 251);
		maxDurabilities.put(Material.IRON_HOE, (short) 251);
		maxDurabilities.put(Material.IRON_SWORD, (short) 251);

		maxDurabilities.put(Material.GOLD_SPADE, (short) 33);
		maxDurabilities.put(Material.GOLD_AXE, (short) 33);
		maxDurabilities.put(Material.GOLD_PICKAXE, (short) 33);
		maxDurabilities.put(Material.GOLD_HOE, (short) 33);
		maxDurabilities.put(Material.GOLD_SWORD, (short) 33);

		maxDurabilities.put(Material.DIAMOND_SPADE, (short) 1562);
		maxDurabilities.put(Material.DIAMOND_AXE, (short) 1562);
		maxDurabilities.put(Material.DIAMOND_PICKAXE, (short) 1562);
		maxDurabilities.put(Material.DIAMOND_HOE, (short) 1562);
		maxDurabilities.put(Material.DIAMOND_SWORD, (short) 1562);

		maxDurabilities.put(Material.BOW, (short) 385);
		maxDurabilities.put(Material.FISHING_ROD, (short) 65);
		/*
		 * Armour
		 */
		maxDurabilities.put(Material.LEATHER_HELMET, (short) 56);
		maxDurabilities.put(Material.LEATHER_CHESTPLATE, (short) 82);
		maxDurabilities.put(Material.LEATHER_LEGGINGS, (short) 76);
		maxDurabilities.put(Material.LEATHER_BOOTS, (short) 66);

		maxDurabilities.put(Material.GOLD_HELMET, (short) 78);
		maxDurabilities.put(Material.GOLD_CHESTPLATE, (short) 114);
		maxDurabilities.put(Material.GOLD_LEGGINGS, (short) 106);
		maxDurabilities.put(Material.GOLD_BOOTS, (short) 92);

		maxDurabilities.put(Material.IRON_HELMET, (short) 166);
		maxDurabilities.put(Material.IRON_CHESTPLATE, (short) 242);
		maxDurabilities.put(Material.IRON_LEGGINGS, (short) 226);
		maxDurabilities.put(Material.IRON_BOOTS, (short) 196);

		maxDurabilities.put(Material.CHAINMAIL_HELMET, (short) 166);
		maxDurabilities.put(Material.CHAINMAIL_CHESTPLATE, (short) 242);
		maxDurabilities.put(Material.CHAINMAIL_LEGGINGS, (short) 226);
		maxDurabilities.put(Material.CHAINMAIL_BOOTS, (short) 196);

		maxDurabilities.put(Material.DIAMOND_HELMET, (short) 364);
		maxDurabilities.put(Material.DIAMOND_CHESTPLATE, (short) 529);
		maxDurabilities.put(Material.DIAMOND_LEGGINGS, (short) 496);
		maxDurabilities.put(Material.DIAMOND_BOOTS, (short) 430);

	}

	@Override
	public String getName() {
		return Skill.REPAIR.getName();
	}

	public void repair(User user, Player player, ItemStack is) {
		if (is != null) {
			Material type = is.getType();

			if (maxDurabilities.containsKey(type)) {
				short max = maxDurabilities.get(type);
				Inventory inv = player.getInventory();
				int mats = 0;
				Material want = null;

				if (ToolMaterial.getToolMaterial(is) != null) {
					want = ToolMaterial.getToolMaterial(is).getMaterial();
				} else {
					want = ArmourMaterial.getArmourMaterial(is).getMaterial();
				}

				ItemStack itemStack = new ItemStack(want);

				for (ItemStack is2 : inv.getContents()) {
					if (is2 != null) {
						if (is2.hasItemMeta()) {
							ItemMeta im = is2.getItemMeta();

							if (!im.getDisplayName().equals(StringUtils.capitalize(itemStack.getType().toString().toLowerCase()))) {
								continue;
							}
						}
						if (is2.getType() == want) {
							mats += is2.getAmount();
						}
					}
				}

				int cost = getRepairCost(is);

				if (mats >= cost) {
					itemStack.setAmount(cost);

					if (is.getDurability() <= 0) {
						player.sendMessage(ChatColor.GOLD + "[Repair] " + ChatColor.RED + "That item is already at full durability");
					} else {
						if (isSpade(is)) {
							is.setDurability((short) 0);
						} else if (isHoe(is) || isSword(is) || isFishingRod(is)) {
							if ((is.getDurability() - (max / 2) < 0)) {
								is.setDurability((short) 0);
							} else {
								is.setDurability((short) (is.getDurability() - (max / 2)));
							}
						} else if ((is.getDurability() - (max / 3) < 0)) {
							is.setDurability((short) 0);
						} else {
							is.setDurability((short) (is.getDurability() - (max / 3)));
						}

						if (is.getDurability() <= 0) {
							is.setDurability((short) 0);
						}

						user.addXp(com.tribescommunity.levelling.data.Skill.REPAIR, getXp(is));

						player.getInventory().removeItem(itemStack);

						if (is.getEnchantments().size() > 0) {
							if (user.getLevellingClass() != LevellingClass.BLACKSMITH) {
								if (Math.random() > 0.5) {
									ItemMeta im = is.getItemMeta();

									for (Enchantment enchant : im.getEnchants().keySet()) {
										im.removeEnchant(enchant);
									}

									is.setItemMeta(im);

									if (ToolMaterial.getToolMaterial(is) != null) {
										player.sendMessage("The magic fades from your weapon");
									} else {
										player.sendMessage("The magic fades from your armour");
									}
								}
							}
						}

						player.sendMessage(ChatColor.GOLD + "[Repair] " + ChatColor.WHITE + "Your " + ChatColor.AQUA
								+ StringUtils.capitalize(is.getType().toString().toLowerCase().replaceAll("_", " ")) + ChatColor.WHITE + " has been repaired");
					}

					player.updateInventory();
				} else {
					player.sendMessage(ChatColor.GOLD + "[Repair] " + ChatColor.RED + "You do not have the required materials");
					player.sendMessage(ChatColor.RED + "Required mats = " + cost + "x " + want);
				}
			}
		}
	}

	public int getRepairCost(ItemStack is) {
		switch (is.getType()) {
		case WOOD_SPADE:
		case STONE_SPADE:
		case GOLD_SPADE:
		case IRON_SPADE:
		case DIAMOND_SPADE:
			return 1;

		case WOOD_AXE:
		case STONE_AXE:
		case GOLD_AXE:
		case IRON_AXE:
		case DIAMOND_AXE:
			return 1;

		case WOOD_PICKAXE:
		case STONE_PICKAXE:
		case GOLD_PICKAXE:
		case IRON_PICKAXE:
		case DIAMOND_PICKAXE:
			return 1;

		case WOOD_HOE:
		case STONE_HOE:
		case GOLD_HOE:
		case IRON_HOE:
		case DIAMOND_HOE:
			return 1;

		case WOOD_SWORD:
		case STONE_SWORD:
		case GOLD_SWORD:
		case IRON_SWORD:
		case DIAMOND_SWORD:
			return 1;

		case LEATHER_HELMET:
		case GOLD_HELMET:
		case IRON_HELMET:
		case CHAINMAIL_HELMET:
		case DIAMOND_HELMET:
			return 2;

		case LEATHER_CHESTPLATE:
		case GOLD_CHESTPLATE:
		case IRON_CHESTPLATE:
		case CHAINMAIL_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
			return 2;

		case LEATHER_LEGGINGS:
		case GOLD_LEGGINGS:
		case IRON_LEGGINGS:
		case CHAINMAIL_LEGGINGS:
		case DIAMOND_LEGGINGS:
			return 2;

		case LEATHER_BOOTS:
		case GOLD_BOOTS:
		case IRON_BOOTS:
		case CHAINMAIL_BOOTS:
		case DIAMOND_BOOTS:
			return 1;

		case BOW:
			return 1;

		case FISHING_ROD:
			return 1;

		default:
			return Integer.MAX_VALUE;
		}
	}

	public boolean isSpade(ItemStack is) {
		Material type = is.getType();
		return type == Material.WOOD_SPADE || type == Material.STONE_SPADE || type == Material.GOLD_SPADE || type == Material.IRON_SPADE || type == Material.DIAMOND_SPADE;
	}

	public boolean isHoe(ItemStack is) {
		Material type = is.getType();
		return type == Material.WOOD_HOE || type == Material.STONE_HOE || type == Material.GOLD_HOE || type == Material.IRON_HOE || type == Material.DIAMOND_HOE;
	}

	public boolean isSword(ItemStack is) {
		Material type = is.getType();
		return type == Material.WOOD_SWORD || type == Material.STONE_SWORD || type == Material.GOLD_SWORD || type == Material.IRON_SWORD || type == Material.DIAMOND_SWORD;
	}

	public boolean isFishingRod(ItemStack is) {
		Material type = is.getType();
		return type == Material.FISHING_ROD;
	}

	public long getXp(ItemStack is) {
		if (ToolMaterial.getToolMaterial(is) != null) {
			ToolMaterial tm = ToolMaterial.getToolMaterial(is);

			return getXp(tm);
		} else if (ArmourMaterial.getArmourMaterial(is) != null) {
			ArmourMaterial am = ArmourMaterial.getArmourMaterial(is);

			return getXp(am);
		}

		return 0;
	}

	public long getXp(ToolMaterial tm) {
		switch (tm) {
		case STRING:
		case WOOD:
			return 16;
		case STONE:
			return 32;
		case GOLD:
			return 64;
		case IRON:
			return 128;
		case DIAMOND:
			return 192;
		default:
			return 0;
		}
	}

	public long getXp(ArmourMaterial am) {
		switch (am) {
		case LEATHER:
			return 16;
		case GOLD:
			return 64;
		case IRON:
		case CHAINMAIL:
			return 128;
		case DIAMOND:
			return 192;
		default:
			return 0;
		}
	}

	public long getXp(Material mat) {
		switch (mat) {
		case IRON_INGOT:
		case GOLD_INGOT:
			return 16;
		default:
			return 0;
		}
	}

	@Override
	public RightClickAbility getAbility() {
		// TODO ADD THIS
		return null;
	}

	@Override
	public List<String> getXpTable(int level) {
		List<String> table = new ArrayList<>();

		for (ToolMaterial tm : ToolMaterial.values()) {
			table.add(tm.getMaterial().getData().getName() + " (Tool): " + getXp(tm));
		}
		for (ArmourMaterial am : ArmourMaterial.values()) {
			table.add(am.getMaterial().getData().getName() + " (Armour): " + getXp(am));
		}
		for (Material m : Material.values()) {
			if (getXp(m) > 0)
				table.add(m.getData().getName() + " (Smelting): " + getXp(m));
		}

		return table;
	}

	@Override
	public String getXpMethodInfo() {
		return "Xp is gained by repairing an item (Right click an iron block with the item in hand and the required items in your inv)";
	}
}

package com.tribescommunity.levelling.skills.misc.enchanting;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.skills.misc.enchanting.types.Enchant;

public class EnchantMenu implements Listener {

	private Inventory menu;
	private Map<Integer, Enchant> enchantMap;

	public EnchantMenu(Levelling plugin) {
		enchantMap = new HashMap<>();

		enchantMap.put(0, EnchantDictionary.POISON);

		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void open(User user, Player player) {
		menu = Bukkit.createInventory(player, 27, user.getName() + " Enchanting Skill");

		for (int slot : enchantMap.keySet())
			menu.setItem(slot, enchantMap.get(slot).getIcon());

		player.openInventory(menu);
	}

	@EventHandler
	public void itemClick(InventoryClickEvent e) {
		if (e.getInventory().equals(menu)) {
			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();

				if (getEnchant(e.getRawSlot()) != null) {
					if (player.getItemInHand() != null) {
						Enchant enchantment = getEnchant(e.getRawSlot());

						enchantment.enchant(player.getItemInHand());
					}
				}
			}
			e.setCancelled(true);
		}
	}

	public Enchant getEnchant(int slot) {
		if (enchantMap.containsKey(slot)) {
			return enchantMap.get(slot);
		}
		return null;
	}
}

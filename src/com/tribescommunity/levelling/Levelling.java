package com.tribescommunity.levelling;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server.Spigot;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.tribescommunity.levelling.commands.ClassCommandExecutor;
import com.tribescommunity.levelling.commands.LevelAdminCommandExecutor;
import com.tribescommunity.levelling.commands.LevellingCommandExecutor;
import com.tribescommunity.levelling.commands.achievements.AchievementsCommandExecutor;
import com.tribescommunity.levelling.commands.chat.ChatChannelCommandExecutor;
import com.tribescommunity.levelling.commands.party.PartyCommandExecutor;
import com.tribescommunity.levelling.commands.party.PartyTeleportCommandExecutor;
import com.tribescommunity.levelling.commands.skills.SkillInfoCommand;
import com.tribescommunity.levelling.commands.skills.StatsCommandExecutor;
import com.tribescommunity.levelling.commands.titles.TitleCommandExecutor;
import com.tribescommunity.levelling.commands.titles.TitlesCommandExecutor;
import com.tribescommunity.levelling.data.Backend;
import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.chat.ChatChannel;
import com.tribescommunity.levelling.data.party.Party;
import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.listeners.AbilityListener;
import com.tribescommunity.levelling.listeners.AchievementListener;
import com.tribescommunity.levelling.listeners.BlockListener;
import com.tribescommunity.levelling.listeners.EnchantEffectListener;
import com.tribescommunity.levelling.listeners.PerkListener;
import com.tribescommunity.levelling.listeners.PlayerListener;
import com.tribescommunity.levelling.listeners.SelfListener;
import com.tribescommunity.levelling.listeners.SkillsListener;
import com.tribescommunity.levelling.runnables.SaveTask;
import com.tribescommunity.levelling.skills.SkillHandler;
import com.tribescommunity.levelling.util.Config;
import com.tribescommunity.levelling.util.CustomItemManager;
import com.tribescommunity.levelling.util.Leaderboards;
import com.tribescommunity.levelling.util.logging.ChatLogger;
import com.tribescommunity.levelling.util.logging.LevellingLogger;

/* 
 * Date: 15 Nov 2012
 * Time: 20:07:32
 * Maker: theguynextdoor
 */
public class Levelling extends JavaPlugin {

	public static final String USER_PLACED_META_STRING = "levelling-user-placed";
	public static final int MAX_SKILL_LEVEL = 130;

	private TreeMap<String, User> users;
	public Map<String, Boolean> placedBlocks;
	private Backend backend;
	private SkillHandler skillHandler;
	// Party
	public Map<String, Party> parties;
	public Map<String, Party> partyInvites;

	public Set<User> levellingGods;
	private Leaderboards leaderboards;
	private CustomItemManager cim;
	private File userTextFile;
	public boolean doBlockAbility = false;
	private Config config;
	// Vault
	private Chat chat;
	private Economy econ;
	private Permission perm;

	private Map<ChatChannel, ChatLogger> chatLoggers;
	private LevellingLogger commandLogger;

	@Override
	public void onEnable() {
		getDataFolder().mkdirs();

		initialise();

		registerRecipes();
		registerListeners();
		registerCommands();

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(backend), 20 * 60 * 10, 20 * 60 * 10);
		backend.saveAllTxt();
	}

	private void registerRecipes() {
		ShapedRecipe lockpickRecipe = new ShapedRecipe(cim.getLockpick());
		lockpickRecipe.shape("XXI", "XCX", "CXX");
		lockpickRecipe.setIngredient('I', Material.IRON_INGOT);
		lockpickRecipe.setIngredient('C', Material.COBBLESTONE);
		Bukkit.getServer().addRecipe(lockpickRecipe);

		ShapedRecipe panningBowlRecipe = new ShapedRecipe(cim.getPanningBowl());
		panningBowlRecipe.shape("AAA", "WAW", "ABA");
		panningBowlRecipe.setIngredient('W', Material.STICK);
		panningBowlRecipe.setIngredient('B', Material.BOWL);
		Bukkit.getServer().addRecipe(panningBowlRecipe);
	}

	public void initialise() {
		cim = new CustomItemManager();
		skillHandler = new SkillHandler(this);
		users = new TreeMap<String, User>();
		parties = new HashMap<String, Party>();
		backend = new Backend(this);
		levellingGods = new HashSet<User>();
		placedBlocks = backend.loadPlacedBlocks();
		partyInvites = new HashMap<String, Party>();
		config = new Config(this);
		chatLoggers = new HashMap<>();

		leaderboards = new Leaderboards();
		userTextFile = new File(getDataFolder(), "Users.txt");

		if (!userTextFile.exists()) {
			try {
				userTextFile.createNewFile();
			} catch (IOException e) {
			}
		}
		try {
			users = backend.loadFromFile(userTextFile);
		} catch (IOException e) {
		}

		for (ChatChannel channel : ChatChannel.values()) {
			chatLoggers.put(channel, new ChatLogger(this, channel));
		}
		commandLogger = new LevellingLogger(this, "Commands");

		setupChat();
		setupEconomy();
		setupPermissions();
	}

	public void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new SkillsListener(this), this);
		pm.registerEvents(new SelfListener(this), this);
		pm.registerEvents(new AbilityListener(this), this);
		pm.registerEvents(new PerkListener(this), this);
		pm.registerEvents(new EnchantEffectListener(this), this);
		pm.registerEvents(new AchievementListener(this), this);
	}

	public void registerCommands() {
		getCommand("levels").setExecutor(new StatsCommandExecutor(this));
		getCommand("levelling").setExecutor(new LevellingCommandExecutor(this));
		getCommand("class").setExecutor(new ClassCommandExecutor(this));
		getCommand("party").setExecutor(new PartyCommandExecutor(this));
		getCommand("ptp").setExecutor(new PartyTeleportCommandExecutor(this));
		getCommand("leveladmin").setExecutor(new LevelAdminCommandExecutor(this));

		getCommand("achievements").setExecutor(new AchievementsCommandExecutor(this));
		getCommand("title").setExecutor(new TitleCommandExecutor(this));
		getCommand("titles").setExecutor(new TitlesCommandExecutor(this));

		for (Skill skill : Skill.values()) {
			getCommand(skill.getName().toLowerCase().replaceAll(" ", "")).setExecutor(new SkillInfoCommand(this, skill));
		}
		for (ChatChannel channel : ChatChannel.values()) {
			if (channel != ChatChannel.DEFAULT) {
				getCommand(channel.getCommand()).setExecutor(new ChatChannelCommandExecutor(this, channel));
			}
		}
	}

	@Override
	public void onDisable() {
		try {
			backend.saveAllTxt();
			backend.savePlacedBlocks();
			backend.saveAchievements();
		} catch (IOException e) {
			e.printStackTrace();
		}
		backend.deinit();

		for (ChatLogger chatLogger : chatLoggers.values()) {
			chatLogger.deinit();
		}
		commandLogger.deinit();
	}

	public String locToString(Location loc) {
		return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
	}

	public Location getLocFromString(String s) {
		String[] split = s.split(",");
		World world = Bukkit.getWorld(split[0]);
		Double x = Double.parseDouble(split[1]);
		Double y = Double.parseDouble(split[2]);
		Double z = Double.parseDouble(split[3]);

		return new Location(world, x, y, z);
	}

	public Backend getBackend() {
		return backend;
	}

	public TreeMap<String, User> getUsers() {
		return users;
	}

	public User getUser(String name) {
		return backend.getUser(name);
	}

	public SkillHandler getSkillHandler() {
		return skillHandler;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			econ = economyProvider.getProvider();
		}

		return (econ != null);
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perm = rsp.getProvider();
		return perm != null;
	}

	public Chat getChat() {
		return chat;
	}

	public boolean isLockpick(ItemStack item) {
		if (item == null || item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
			return false;
		}
		return item.getItemMeta().getDisplayName().equalsIgnoreCase("Lockpick");
	}

	public CustomItemManager getCustomItemManager() {
		return cim;
	}

	public Leaderboards getLeaderboards() {
		return leaderboards;
	}

	public File getUsersFile() {
		return userTextFile;
	}

	public Economy getEconomy() {
		return econ;
	}

	public Config getConfigInstance() {
		return config;
	}

	public Permission getPerm() {
		return perm;
	}

	public ChatLogger getChatLogger(ChatChannel channel) {
		return chatLoggers.get(channel);
	}

	public LevellingLogger getCommandLogger() {
		return commandLogger;
	}
}

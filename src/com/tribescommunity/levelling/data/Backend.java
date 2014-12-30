package com.tribescommunity.levelling.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.achievements.AchievementTracker;
import com.tribescommunity.levelling.data.user.User;

/* 
 * Date: 16 Nov 2012
 * Time: 21:43:11
 * Maker: theguynextdoor
 */
public class Backend {

	public Levelling plugin;
	private List<File> saveFiles = new ArrayList<File>();
	public Map<String, BufferedWriter> bws;
	private File achievementTrackFile;
	private File achievementGotFile;

	public Backend(Levelling instance) {
		plugin = instance;
		File blocksFolder = new File(plugin.getDataFolder() + File.separator + "Placed Blocks");
		blocksFolder.mkdirs();

		for (World world : Bukkit.getWorlds()) {
			File file = new File(plugin.getDataFolder() + File.separator + "Placed Blocks", world.getName());

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			saveFiles.add(file);
		}

		File achievementTrackFolder = new File(plugin.getDataFolder() + File.separator + "Achievements");
		achievementTrackFolder.mkdirs();

		achievementTrackFile = new File(plugin.getDataFolder() + File.separator + "Achievements", "Achievement Tracking.txt");
		if (!achievementTrackFile.exists()) {
			try {
				achievementTrackFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		achievementGotFile = new File(plugin.getDataFolder() + File.separator + "Achievements", "Achievement Gotten.txt");
		if (!achievementGotFile.exists()) {
			try {
				achievementGotFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			initBws();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deinit() {
		for (BufferedWriter bw : bws.values()) {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public User getUser(String name) {
		if (plugin.getUsers().containsKey(name))
			return plugin.getUsers().get(name);
		else {
			User user = new User(plugin, name);

			plugin.getUsers().put(name, user);
			return user;
		}
	}

	public void saveAllTxt() {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(plugin.getUsersFile()));

			for (User user : plugin.getUsers().values()) {
				bw.append(user.getSaveString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveAchievements() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(achievementTrackFile));
		for (User user : plugin.getUsers().values()) {
			bw.append(user.getAchievementTracker().trackingStatsToString(user));
			bw.newLine();
		}
		bw.close();

		bw = new BufferedWriter(new FileWriter(achievementGotFile));
		for (User user : plugin.getUsers().values()) {
			bw.append(user.getAchievementTracker().achievementsGottenToString(user));
			bw.newLine();
		}

		bw.close();
	}

	public void loadAchievements(User user) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(achievementTrackFile));
		String achievementTrackString = "";
		String achievementGotString = "";
		String line;

		while ((line = br.readLine()) != null) {
			String[] split = line.split(":");

			if (split[0].equals(user.getName())) {
				achievementTrackString = line.substring(user.getName().length() + 1);
			}
		}
		br.close();

		br = new BufferedReader(new FileReader(achievementGotFile));

		while ((line = br.readLine()) != null) {
			String[] split = line.split(":");

			if (split[0].equals(user.getName())) {
				achievementGotString = line.substring(user.getName().length() + 1);
			}
		}

		br.close();

		if (achievementTrackString.length() == 0 || achievementGotString.length() == 0)
			user.setAchievementTracker(new AchievementTracker());
		else
			user.setAchievementTracker(new AchievementTracker(achievementTrackString, achievementGotString));
	}

	public void initBws() throws IOException {
		bws = new HashMap<String, BufferedWriter>();

		for (World world : Bukkit.getWorlds()) {
			bws.put(world.getName(), new BufferedWriter(new FileWriter(new File(plugin.getDataFolder() + File.separator + "Placed Blocks", world.getName()), true)));
		}

	}

	public void savePlacedBlocks() throws IOException {
		for (File file : saveFiles) {
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.flush();
			writer.close();
		}
		for (String loc : plugin.placedBlocks.keySet()) {
			BufferedWriter bw = bws.get(loc.split(",")[0]);
			String[] s = loc.split(",");
			String save = s[1] + "," + s[2] + "," + s[3];

			bw.append(save);
			bw.newLine();
			bw.flush();
		}
	}

	public Map<String, Boolean> loadPlacedBlocks() {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		BufferedReader br = null;
		for (File file : saveFiles) {
			try {
				br = new BufferedReader(new FileReader(file));
				String line;

				while ((line = br.readLine()) != null) {
					map.put(file.getName() + "," + line, true);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	public TreeMap<String, User> loadFromFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		TreeMap<String, User> map = new TreeMap<String, User>();
		String line;

		while ((line = br.readLine()) != null) {
			User user = new User(line, plugin);
			map.put(user.getName(), user);
			plugin.getLeaderboards().addUser(user);
		}

		br.close();
		saveAllTxt();
		return map;
	}
}

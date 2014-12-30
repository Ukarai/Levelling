package com.tribescommunity.levelling.achievements;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

import com.tribescommunity.levelling.data.user.User;
import com.tribescommunity.levelling.events.TrackingIncrementEvent;

public class AchievementTracker {
	private Map<Tracking, Integer> tracking;
	private Set<Integer> achievements;

	public AchievementTracker() {
		tracking = new EnumMap<>(Tracking.class);
		achievements = new HashSet<>();

		for (Tracking track : Tracking.values()) {
			tracking.put(track, 0);
		}
	}

	// Note: The user name shall be removed from the string before parsed into the constructor
	public AchievementTracker(String trackingString, String gottenString) {
		tracking = new EnumMap<>(Tracking.class);
		achievements = new HashSet<>();

		for (Tracking track : Tracking.values()) {
			tracking.put(track, 0);
		}

		int length = trackingString.split(":").length;
		for (int i = 0; i < length; i++) {
			tracking.put(Tracking.values()[i], Integer.parseInt(trackingString.split(":")[i]));
		}
		for (String id : gottenString.split(":")) {
			achievements.add(Integer.parseInt(id));
		}
	}

	public void increment(User user, Tracking track) {
		tracking.put(track, tracking.get(track) + 1);

		Bukkit.getPluginManager().callEvent(new TrackingIncrementEvent(user, track, 1));
	}

	public void addAchievement(Ach achievement) {
		achievements.add(achievement.getId());
	}

	public int getTracked(Tracking track) {
		return tracking.get(track);
	}

	public boolean hasAchievement(int achievementId) {
		return achievements.contains(achievementId);
	}

	public String trackingStatsToString(User user) {
		StringBuilder sb = new StringBuilder();

		sb.append(user.getName());

		for (Tracking track : Tracking.values()) {
			sb.append(":");
			sb.append(tracking.get(track));
		}

		return sb.toString();
	}

	public String achievementsGottenToString(User user) {
		return user.getName() + ":" + StringUtils.join(achievements.toArray(), ':');
	}

	public void reset(Tracking tracking) {
		this.tracking.put(tracking, 0);
	}

}

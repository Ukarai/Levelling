package com.tribescommunity.levelling.achievements;

import java.util.ArrayList;
import java.util.List;

import com.tribescommunity.levelling.achievements.Ach.AchievementBuilder;

public class AchievementManager {

	public static final Ach COMMUTER = new AchievementBuilder("Commuter").id(0).title("Commuter").description("Walk 10,000 blocks").criteria(Tracking.BLOCKS_TRAVELLED, 10000).build();
	public static final Ach TOURIST = new AchievementBuilder("Tourist").id(1).title("Tourist").description("Walk 25,000 blocks").criteria(Tracking.BLOCKS_TRAVELLED, 25000).build();
	public static final Ach DRIFTER = new AchievementBuilder("Drifter").id(2).title("Drifter").description("Walk 50,000 blocks").criteria(Tracking.BLOCKS_TRAVELLED, 50000).build();
	public static final Ach TRAVELLER = new AchievementBuilder("Traveller").id(3).title("Traveller").description("Walk 100,000 blocks").criteria(Tracking.BLOCKS_TRAVELLED, 100000).build();
	public static final Ach EXPLORER = new AchievementBuilder("Explorer").id(4).title("Explorer").description("Walk 250,000 blocks").criteria(Tracking.BLOCKS_TRAVELLED, 250000).build();
	public static final Ach NAVIGATOR = new AchievementBuilder("Navigator").id(5).title("Navigator").description("Walk 500,000 blocks").criteria(Tracking.BLOCKS_TRAVELLED, 500000).build();

	public static final Ach DOUBLE_KILL = new AchievementBuilder("Double Kill").id(6).description("Kill 2 players without getting killed").criteria(Tracking.KILLSTREAK, 2).build();
	public static final Ach KILLING_SPREE = new AchievementBuilder("Killing Spree").id(7).description("Kill 5 players without getting killed").criteria(Tracking.KILLSTREAK, 5).build();
	public static final Ach ALMIGHTY = new AchievementBuilder("Almighty").id(8).description("Kill 10 players without getting killed").criteria(Tracking.KILLSTREAK, 10).build();
	public static final Ach INVINCIBLE = new AchievementBuilder("Invincible").id(9).description("Kill 15 players without getting killed").criteria(Tracking.KILLSTREAK, 15).build();
	public static final Ach IMMORTAL = new AchievementBuilder("Immortal").id(10).description("Kill 20 players without getting killed").criteria(Tracking.KILLSTREAK, 20).build();
	public static final Ach GOD_LIKE = new AchievementBuilder("God Like").title("God-like").id(11).description("Kill 30 players without getting killed").criteria(Tracking.KILLSTREAK, 30).build();

	// Tool Maker, make a set of diamond tools

	public static List<Ach> allAchievements = new ArrayList<>();

	static {
		allAchievements.add(COMMUTER);
		allAchievements.add(TOURIST);
		allAchievements.add(DRIFTER);
		allAchievements.add(TRAVELLER);
		allAchievements.add(EXPLORER);
		allAchievements.add(NAVIGATOR);

		allAchievements.add(DOUBLE_KILL);
		allAchievements.add(KILLING_SPREE);
		allAchievements.add(ALMIGHTY);
		allAchievements.add(INVINCIBLE);
		allAchievements.add(IMMORTAL);
		allAchievements.add(GOD_LIKE);
	}
}

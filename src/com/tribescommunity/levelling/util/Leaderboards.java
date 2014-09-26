package com.tribescommunity.levelling.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tribescommunity.levelling.data.Skill;
import com.tribescommunity.levelling.data.user.User;

public class Leaderboards {

	private Map<Skill, Map<User, Integer>> leaderboards;
	private Map<User, Integer> totalLevelBoard;
	private Map<Skill, StatLevelComparitor> comparators;

	public Leaderboards() {
		leaderboards = new EnumMap<Skill, Map<User, Integer>>(Skill.class);
		comparators = new HashMap<Skill, StatLevelComparitor>();
		totalLevelBoard = new HashMap<User, Integer>();
		initLeaderBoards();
	}

	public void initLeaderBoards() {
		for (Skill skill : Skill.values()) {
			leaderboards.put(skill, new HashMap<User, Integer>());
			comparators.put(skill, new StatLevelComparitor(leaderboards.get(skill)));
		}
	}

	public void addUser(User user) {
		for (Skill skill : Skill.values()) {
			if (!leaderboards.get(skill).containsKey(user)) {
				leaderboards.get(skill).put(user, user.getLevel(skill));
				comparators.get(skill).setMap(leaderboards.get(skill));
				totalLevelBoard.put(user, user.getTotalLevel());
			}
		}
	}

	public List<User> getLeaderboard(Skill skill) {
		final Map<User, Integer> map = leaderboards.get(skill);

		List<User> users = new ArrayList<User>(map.keySet());
		Collections.sort(users, comparators.get(skill));

		return users;
	}

	public List<User> getTotalLevelBoard() {
		List<User> list = new ArrayList<User>(totalLevelBoard.keySet());
		Collections.sort(list, new IntegerComparator());
		return list;
	}

	public void update(User user) {
		for (Skill skill : Skill.values()) {
			update(user, skill);
		}

		totalLevelBoard.put(user, user.getTotalLevel());
	}

	public void update(User user, Skill skill) {
		leaderboards.get(skill).put(user, user.getLevel(skill));
		comparators.get(skill).setMap(leaderboards.get(skill));
	}

	public class StatLevelComparitor implements Comparator<User> {
		private Map<User, Integer> map = new HashMap<User, Integer>();

		public StatLevelComparitor(Map<User, Integer> map) {
			this.map = map;
		}

		public void setMap(Map<User, Integer> map) {
			this.map = map;
		}

		@Override
		public int compare(User u1, User u2) {
			Integer popularity1 = map.get(u1);
			Integer popularity2 = map.get(u2);
			return popularity1.compareTo(popularity2);
		}
	}

	public class IntegerComparator implements Comparator<User> {
		@Override
		public int compare(User u, User u2) {
			return u.getTotalLevel().compareTo(u2.getTotalLevel());
		}

	}

}

package com.tribescommunity.levelling.achievements;

import java.util.HashMap;
import java.util.Map;

public class Ach {

	private int id;
	private String name;
	private String title;
	private String description;
	private Map<Tracking, Integer> criteria;
	private boolean attainable;

	public Ach(AchievementBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.title = builder.title;
		this.description = builder.description;
		this.criteria = builder.criteria;
		this.attainable = builder.attainable;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Map<Tracking, Integer> getCriteria() {
		return criteria;
	}

	public boolean isAttainable() {
		return attainable;
	}

	public boolean hasTitle() {
		return title.length() > 0;
	}

	public String getJSON() {
		return "{\"text\":\"[" + name + "]\",\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + description + "\"}}";
	}

	public String getJSON(boolean has) {
		String colour = has ? "green" : "red";
		return "{\"text\":\"[" + name + "]\",\"color\":\"" + colour + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + description + "\"}}";
	}

	public boolean hasMetCriteria(AchievementTracker tracker) {
		for (Tracking tracking : criteria.keySet()) {
			if (tracker.getTracked(tracking) < criteria.get(tracking)) {
				return false;
			}
		}
		return true;
	}

	public static class AchievementBuilder {

		private int id;
		private String name;
		private String title;
		private String description;
		private Map<Tracking, Integer> criteria;
		private boolean attainable;

		public AchievementBuilder(String name) {
			this.name = name;
			criteria = new HashMap<>();
			attainable = true;
		}

		public AchievementBuilder id(int id) {
			this.id = id;
			return this;
		}

		public AchievementBuilder title(String title) {
			this.title = title;
			return this;
		}

		public AchievementBuilder description(String description) {
			this.description = description;
			return this;
		}

		public AchievementBuilder criteria(Tracking tracking, int total) {
			this.criteria.put(tracking, total);
			return this;
		}

		public AchievementBuilder attainable(boolean attainable) {
			this.attainable = attainable;
			return this;
		}

		public Ach build() {
			return new Ach(this);
		}
	}

}

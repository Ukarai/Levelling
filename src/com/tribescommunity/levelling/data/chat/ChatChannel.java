package com.tribescommunity.levelling.data.chat;

import org.bukkit.ChatColor;

public enum ChatChannel {

	ADMIN("Admin", ChatColor.DARK_RED, ChatColor.RED, ChatColor.RED, "channel.admin", "adminchat"),
	MOD("Mod", ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE, "channel.mod", "modchat"),
	VIP("VIP", ChatColor.DARK_BLUE, ChatColor.BLUE, ChatColor.BLUE, "channel.vip", "vipchat"),
	PARTY("Party", ChatColor.GREEN, ChatColor.GREEN, ChatColor.GREEN, "", "partychat"),
	DEFAULT("Default", null, null, null, null, null);

	private String name;
	private ChatColor bracketColour;
	private ChatColor nameColour;
	private ChatColor textColour;
	private String permission;
	private String command;

	private ChatChannel(String name, ChatColor bracketColour, ChatColor nameColour, ChatColor textColour, String permission, String command) {
		this.name = name;
		this.bracketColour = bracketColour;
		this.nameColour = nameColour;
		this.textColour = textColour;
		this.permission = permission;
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public String getPermission() {
		return permission;
	}

	public String getFormat(String playerName, String message) {
		return ChatColor.GRAY + "[" + name() + "] " + bracketColour + "(" + nameColour + playerName + bracketColour + ") " + textColour + message;
	}

	public String getName() {
		return name;
	}

}

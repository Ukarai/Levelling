package com.tribescommunity.levelling.data.chat;

import org.bukkit.ChatColor;

public enum ChatChannel {

	ADMIN(ChatColor.DARK_RED, ChatColor.RED, ChatColor.RED, "channel.admin", "adminchat"),
	MOD(ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE, "channel.mod", "modchat"),
	VIP(ChatColor.DARK_BLUE, ChatColor.BLUE, ChatColor.BLUE, "channel.vip", "vipchat"),
	PARTY(ChatColor.GREEN, ChatColor.GREEN, ChatColor.GREEN, "", "partychat"),
	DEFAULT(null, null, null, null, null);

	private ChatColor bracketColour;
	private ChatColor nameColour;
	private ChatColor textColour;
	private String permission;
	private String command;

	private ChatChannel(ChatColor bracketColour, ChatColor nameColour, ChatColor textColour, String permission, String command) {
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
		StringBuilder sb = new StringBuilder();

		sb.append(ChatColor.GRAY + "[" + name() + "] ");
		sb.append(bracketColour + "(");
		sb.append(nameColour + playerName);
		sb.append(bracketColour + ") ");
		sb.append(textColour + message);

		return sb.toString();
	}

}

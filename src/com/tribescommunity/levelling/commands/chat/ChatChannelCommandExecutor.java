package com.tribescommunity.levelling.commands.chat;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.chat.ChatChannel;
import com.tribescommunity.levelling.data.user.User;

public class ChatChannelCommandExecutor implements CommandExecutor {

	private Levelling plugin;
	private ChatChannel channel;

	public ChatChannelCommandExecutor(Levelling instance, ChatChannel channel) {
		this.plugin = instance;
		this.channel = channel;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = plugin.getUser(player.getName());

			if (!player.hasPermission(channel.getPermission())) {
				player.sendMessage(ChatColor.RED + "You do not have permission for this command");
				return true;
			}

			if (channel == ChatChannel.PARTY) {
				if (!user.inParty()) {
					player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "You are not in a party");
					return true;
				}
			}

			if (args.length == 0) {
				ChatChannel current = user.getChatChannel();

				if (current == channel) {
					user.setChatChannel(ChatChannel.DEFAULT);
					player.sendMessage(StringUtils.capitalize(channel.name()) + " chat: OFF");
				} else if (current == ChatChannel.DEFAULT) {
					user.setChatChannel(channel);
					player.sendMessage(StringUtils.capitalize(channel.name()) + " chat: ON");
				} else {
					player.sendMessage(StringUtils.capitalize(user.getChatChannel().name()) + " chat: OFF");
					user.setChatChannel(channel);
					player.sendMessage(StringUtils.capitalize(channel.name()) + " chat: ON");
				}

				return true;
			}
		} else {
			if (channel == ChatChannel.PARTY) {
				sender.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.RED + "You are not in a party");
				return true;
			}
		}

		if (args.length > 0) {
			String message = StringUtils.join(args);
			String format = channel.getFormat(sender.getName(), message);

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (channel == ChatChannel.PARTY) {
					if (sender instanceof Player) {
						User user = plugin.getUser(sender.getName());

						if (user.inParty() && user.getParty().inParty(player.getName())) {
							player.sendMessage(format);
						}
					}
				} else if (player.hasPermission(channel.getPermission())) {
					player.sendMessage(format);
				}
			}

			plugin.getLogger().info(format);
		} else {
			sender.sendMessage(ChatColor.RED + "Please enter a message");
		}

		return true;
	}
}

package com.tribescommunity.levelling.util.logging;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.chat.ChatChannel;

public class ChatLogger extends LevellingLogger {

	public ChatLogger(Levelling plugin, ChatChannel channel) {
		super(plugin, channel.getName());
	}

}

package com.tribescommunity.levelling.commands.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tribescommunity.levelling.Levelling;
import com.tribescommunity.levelling.data.party.Party;
import com.tribescommunity.levelling.data.user.User;

public class PartyCommandExecutor implements CommandExecutor {

    private Levelling plugin;

    public PartyCommandExecutor(Levelling instance) {
        plugin = instance;
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            User user = plugin.getUser(player.getName());

            if (args.length == 0) {
                if (user.inParty()) {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You are in the party " + user.getParty().getName());
                    String list = "";

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        User u = plugin.getUser(p.getName());

                        if (u.inParty()) {
                            if (plugin.getUser(p.getName()).getParty().getName().equals(user.getParty().getName())) {
                                list += p.getName() + ", ";
                            }
                        }
                    }
                    player.sendMessage(list);
                } else {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You are not in a party");
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (user.inParty()) {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You have left the party " + user.getParty().getName());
                    user.leaveParty();
                } else {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You were never in a party");
                }
            } else if (args[0].equalsIgnoreCase("invite")) {
                if (user.inParty()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player invited = Bukkit.getPlayer(args[1]);

                        if (plugin.getUser(invited.getName()).inParty()) {
                            player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "That person is already in a party");
                        } else if (plugin.partyInvites.containsKey(invited.getName())) {
                            player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "That person already has an invite");
                        } else {
                            plugin.partyInvites.put(invited.getName(), user.getParty());
                            player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "Invite sent!");
                            invited.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You have been invited to the party " + user.getParty().getName());
                        }
                    } else {
                        player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "That player is not online");
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You are not in a party");
                }
            } else if (args[0].equalsIgnoreCase("accept")) {
                if (!user.inParty()) {
                    if (plugin.partyInvites.containsKey(player.getName())) {
                        plugin.getUser(player.getName()).joinParty(plugin.partyInvites.get(player.getName()));
                        plugin.partyInvites.remove(player.getName());
                        player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "Invite accepted");
                    } else {
                        player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You do not have any invites at this time");
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You are already in a party");
                }
            } else if (args[0].equalsIgnoreCase("decline")) {
                if (!user.inParty()) {
                    if (plugin.partyInvites.containsKey(player.getName())) {
                        player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "Invite declined");
                        plugin.partyInvites.remove(player.getName());
                    } else {
                        player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You do not have any invites at this time");
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You are already in a party");
                }
            } else {
                if (plugin.parties.containsKey(args[0])) {
                    if (user.inParty()) {
                        user.leaveParty();
                    }

                    user.joinParty(plugin.parties.get(args[0]));
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You have joined the party " + user.getParty().getName());
                } else {
                    if (user.inParty()) {
                        user.leaveParty();
                    }

                    Party party = new Party(plugin, args[0]);
                    user.joinParty(party);
                    player.sendMessage(ChatColor.GOLD + "[Party] " + ChatColor.WHITE + "You have joined the party " + user.getParty().getName());
                }
            }
        }
        return true;
    }
}

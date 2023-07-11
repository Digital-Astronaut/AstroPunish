package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.SQLGetter;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class BlacklistCommand extends SubCommand {

    @Override
    public String getName() {
        return "blacklist";
    }

    @Override
    public String getDescription() {
        return "Blacklists the specified player";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("bl", "blist", "blackl");
    }

    @Override
    public String getSyntax() {
        return "/ap blacklist <Player> [Remove | Add]";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {

            if (!p.isOp()) return;

            if (args.length <= 1) {
                p.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap bl <Player> [Remove | Add]");
            } else if (args.length == 2) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).getIsBlacklisted()) {
                        p.sendMessage(ChatColor.RED + "That player is already blacklisted!");
                        p.sendMessage(ChatColor.YELLOW + "To remove the player from the blacklist, try /ap bl <Player> remove");
                        return;
                    }
                    SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).setIsBlacklisted(true);
                    p.sendMessage(ChatColor.GREEN + "Successfully added " + args[1] + " to the blacklist!");
                } else {
                    p.sendMessage(ChatColor.RED + "That player does not exist!");
                }
            } else if (args.length == 3) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    if (args[2].equalsIgnoreCase("add")) {

                        if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).getIsBlacklisted()) {

                            SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).setIsBlacklisted(true);
                            p.sendMessage(ChatColor.GREEN + "Successfully added " + args[1] + " to the blacklist!");
                        } else {
                            p.sendMessage(ChatColor.RED + "That player is already blacklisted!");
                            p.sendMessage(ChatColor.YELLOW + "To remove the player from the blacklist, try /ap bl <Player> remove");
                        }
                    } else if (args[2].equalsIgnoreCase("remove")) {

                        if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).getIsBlacklisted()) {

                            SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).setIsBlacklisted(false);
                            p.sendMessage(ChatColor.GREEN + "Successfully removed " + args[1] + " from the blacklist!");
                        } else {
                            p.sendMessage(ChatColor.RED + "That player is not blacklisted!");
                            p.sendMessage(ChatColor.YELLOW + "To add the player to the blacklist, try /ap bl <Player> add");
                        }
                    }
                }
            } else {
                p.sendMessage(ChatColor.RED + "Invalid usage! Try /ap bl <Player> [Remove | Add]");
            }
        } else if (commandSender instanceof ConsoleCommandSender ccs) {

            if (args.length <= 1) {
                ccs.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap bl <Player> [Remove | Add]");
            } else if (args.length == 2) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).getIsBlacklisted()) {
                        ccs.sendMessage(ChatColor.RED + "That player is already blacklisted!");
                        ccs.sendMessage(ChatColor.YELLOW + "To remove the player from the blacklist, try /ap bl <Player> remove");
                        return;
                    }
                    SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).setIsBlacklisted(true);
                    ccs.sendMessage(ChatColor.GREEN + "Successfully added " + args[1] + " to the blacklist!");
                } else {
                    ccs.sendMessage(ChatColor.RED + "That player does not exist!");
                }
            } else if (args.length == 3) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    if (args[2].equalsIgnoreCase("add")) {

                        if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).getIsBlacklisted()) {

                            SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).setIsBlacklisted(true);
                            ccs.sendMessage(ChatColor.GREEN + "Successfully added " + args[1] + " to the blacklist!");
                        } else {
                            ccs.sendMessage(ChatColor.RED + "That player is already blacklisted!");
                            ccs.sendMessage(ChatColor.YELLOW + "To remove the player from the blacklist, try /ap bl <Player> remove");
                        }
                    } else if (args[2].equalsIgnoreCase("remove")) {

                        if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).getIsBlacklisted()) {

                            SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).setIsBlacklisted(false);
                            ccs.sendMessage(ChatColor.GREEN + "Successfully removed " + args[1] + " from the blacklist!");
                        } else {
                            ccs.sendMessage(ChatColor.RED + "That player is not blacklisted!");
                            ccs.sendMessage(ChatColor.YELLOW + "To add the player to the blacklist, try /ap bl <Player> add");
                        }
                    }
                }
            } else {
                ccs.sendMessage(ChatColor.RED + "Invalid usage! Try /ap bl <Player> [Remove | Add]");
            }
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] strings) {
        return null;
    }

    @Nullable
    @Override
    public TextComponent getSubCommandHelpLayout() {
        return null;
    }

    @Nullable
    @Override
    public HoverEvent getSubCommandHoverEvent() {
        return null;
    }

    @Nullable
    @Override
    public ClickEvent getSubCommandClickEvent() {
        return null;
    }
}

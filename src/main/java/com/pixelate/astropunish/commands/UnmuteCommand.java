package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.SQLGetter;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class UnmuteCommand extends SubCommand {

    @Override
    public String getName() {
        return "unmute";
    }

    @Override
    public String getDescription() {
        return "Unmutes the specified player";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("unm", "um");
    }

    @Override
    public String getSyntax() {
        return "/ap unmute <Player>";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            if (args.length <= 1) {
                p.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap unmute <Player>");
            } else if (args.length == 2) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

                if (SQLGetter.getPunishedPlayer(player).getCurrentMute() == null) {
                    SQLGetter.getPunishedPlayer(player).unmute();
                    p.sendMessage(ChatColor.RED + "That player is not muted.");
                } else {
                    SQLGetter.getPunishedPlayer(player).unmute();
                    p.sendMessage(ChatColor.GREEN + "Successfully unmuted " + args[1] + "!");

                    if (player.isOnline()) {
                        player.getPlayer().sendMessage(ChatColor.GREEN + "You have been unmuted!");
                    }
                }
            } else {
                p.sendMessage("Invalid arguments. Try /ap unmute <Player>");
            }
        } else if (commandSender instanceof ConsoleCommandSender ccs) {

            if (args.length <= 1) {
                ccs.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap unmute <Player>");
            } else if (args.length == 2) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);

                if (SQLGetter.getPunishedPlayer(player).getCurrentMute() == null) {
                    SQLGetter.getPunishedPlayer(player).unmute();
                    ccs.sendMessage(ChatColor.RED + "That player is not muted.");
                } else {
                    SQLGetter.getPunishedPlayer(player).unmute();
                    ccs.sendMessage(ChatColor.GREEN + "Successfully unmuted " + args[1] + "!");

                    if (player.isOnline()) {
                        player.getPlayer().sendMessage(ChatColor.GREEN + "You have been unmuted!");
                    }
                }
            } else {
                ccs.sendMessage("Invalid arguments. Try /ap unmute <Player>");
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

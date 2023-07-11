package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.AstroBanEntry;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PunishmentHistoryCommand extends SubCommand {

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "Shows a player's punishment history";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("phistory", "ph");
    }

    @Override
    public String getSyntax() {
        return "/ap history <Player> [Page]";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            if (args.length <= 1) {
                p.sendMessage(ChatColor.RED + "Please specify a player");
                p.sendMessage(ChatColor.AQUA + "/ap history <Player> [Page]");
                return;
            } else if (args.length == 2) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {

                    p.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");

//                    for (AstroBanEntry entry : AstroBanEntry.getPlayerBans(Bukkit.getOfflinePlayer(args[1]))) {
//
//                        p.sendMessage(ChatColor.AQUA + "Banned for " + entry.getBanLength() + " " + entry.getBanUnit() + " - Reason: " + entry.getBanReason() + " | Unbanned on: " + entry.getUnbanDate());
//                        p.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");
//                    }
                }
            }
            p.sendMessage(ChatColor.BLUE + "Showing history");
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

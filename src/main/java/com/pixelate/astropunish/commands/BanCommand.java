package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.AstroBanEntry;
import com.pixelate.astropunish.database.SQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.mcjustice.astroapi.Commands.SubCommand;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class BanCommand extends SubCommand {

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Bans the specified player";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("b", "exile");
    }

    @Override
    public String getSyntax() {
        return "/ap ban <Player> [Time] [Unit] [Reason]";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {


        if (commandSender instanceof Player p) {

            if (!p.isOp())
                return;

            if (args.length <= 1) {
                p.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap ban <Player> [Time] [Unit] [Reason]");
                p.sendMessage(ChatColor.RED + "Example: /ap ban Notch 1 week I don't like you");
            }

            if (args.length == 2) {

                if (Bukkit.getServer().getOfflinePlayer(args[1]) != null) {

                    OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                    if (SQLGetter.getPunishedPlayer(offlinePlayer) != null) {

                        if (SQLGetter.getPunishedPlayer(offlinePlayer).isBanned()) {
                            p.sendMessage(ChatColor.RED + "That player is already banned!");
                            return;
                        }

                        SQLGetter.getPunishedPlayer(offlinePlayer).addEntryToBans(new AstroBanEntry(offlinePlayer, null, dateFormatter.format(Calendar.getInstance().getTime()), Long.MAX_VALUE, null, "No reason given", null, true));

                        SQLGetter.getPunishedPlayer(offlinePlayer).setBanned(true);

                        if (offlinePlayer.isOnline()) {
                            offlinePlayer.getPlayer().kick(Component.text("You have been banned forever", NamedTextColor.RED));
                        }
                    } else {
                        p.sendMessage(args[1] + " is null?");
                    }
                }
            } else if (args.length == 3) {

                p.sendMessage(ChatColor.RED + "Improper usage! Try /ap ban <Player> [Time] [Unit] [Reason]");

            } else if (args.length == 4) {

                // /ap ban <Player> [Time] [Unit] [Reason]

                if (Bukkit.getServer().getOfflinePlayer(args[1]) != null) {

                    OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);

                    if (SQLGetter.getPunishedPlayer(offlinePlayer) != null) {

                        if (SQLGetter.getPunishedPlayer(offlinePlayer).isBanned()) {
                            p.sendMessage(ChatColor.RED + "That player is already banned!");
                            return;
                        }

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                        if (NumberUtils.isCreatable(args[2])) {

                            Calendar cal = calendarStuff(args[2], args[3]);

                            if (calendarStuff(args[2], args[3]) == null) {
                                p.sendMessage(ChatColor.RED + "Improper unit! Try week, month, year, second, hour, day, or minute");
                                return;
                            }

                            SQLGetter.getPunishedPlayer(offlinePlayer).addEntryToBans(new AstroBanEntry(offlinePlayer, null, dateFormatter.format(Calendar.getInstance().getTime()), Long.parseLong(args[2]), getDateUnitText(args[3]), "No reason given", dateFormatter.format(cal.getTime()), true));

                            SQLGetter.getPunishedPlayer(offlinePlayer).setBanned(true);

                            if (offlinePlayer.isOnline()) {
                                offlinePlayer.getPlayer().kick(Component.text(ChatColor.RED + "You have been banned for no reason"));
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Invalid number!");
                        }
                    }
                }
            } else if (args.length >= 5) {

                // /ap ban <Player> [Time] [Unit] [Reason]

                if (Bukkit.getServer().getOfflinePlayer(args[1]) != null) {

                }
                OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);

                if (SQLGetter.getPunishedPlayer(offlinePlayer) != null) {

                    if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).isBanned()) {
                        p.sendMessage(ChatColor.RED + "That player is already banned!");
                        return;
                    }


                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                    if (NumberUtils.isCreatable(args[2])) {

                        if (calendarStuff(args[2], args[3]) == null) {
                            p.sendMessage(ChatColor.RED + "Improper unit! Try week, month, year, second, hour, day, or minute");
                            return;
                        }

                        Calendar cal = calendarStuff(args[2], args[3]);

                        String[] reason = new String[args.length - 4];

                        for (int i = 4; i < args.length; i++) {

                            reason[i - 4] = args[i];
                        }

                        String concatednatedReason = String.join(" ", reason);

                        Component banText = Component.text("You have been banned for reason: ", NamedTextColor.RED, TextDecoration.BOLD)
                                .appendNewline()
                                .append(LegacyComponentSerializer.legacy('&').deserialize(concatednatedReason))
                                .appendNewline()
                                .appendNewline()
                                .append(Component.text("For " + args[2] + " " + getDateUnitText(args[3]), NamedTextColor.AQUA))
                                .appendNewline()
                                .appendNewline()
                                .append(Component.text("Try again on this date: ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                .append(Component.text(dateFormatter.format(cal.getTime()), NamedTextColor.AQUA));

                        SQLGetter.getPunishedPlayer(offlinePlayer).addEntryToBans(new AstroBanEntry(offlinePlayer, null, dateFormatter.format(Calendar.getInstance().getTime()), Long.parseLong(args[2]), getDateUnitText(args[3]), concatednatedReason, dateFormatter.format(cal.getTime()), true));

                        SQLGetter.getPunishedPlayer(offlinePlayer).setBanned(true);

                        p.sendMessage(ChatColor.GREEN + "Ban entry successfully created");

                        if (offlinePlayer.isOnline()) {

                            offlinePlayer.getPlayer().kick(banText);
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Invalid number!");
                    }
                }
            }
        } else if (commandSender instanceof ConsoleCommandSender ccs) {

            if (args.length <= 1) {
                ccs.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap ban <Player> [Time] [Unit] [Reason]");
                ccs.sendMessage(ChatColor.RED + "Example: /ap ban Notch 1 week I don't like you");
            }

            if (args.length == 2) {

                if (Bukkit.getServer().getOfflinePlayer(args[1]) != null) {

                    OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                    if (SQLGetter.getPunishedPlayer(offlinePlayer) != null) {

                        if (SQLGetter.getPunishedPlayer(offlinePlayer).isBanned()) {
                            ccs.sendMessage(ChatColor.RED + "That player is already banned!");
                            return;
                        }

                        SQLGetter.getPunishedPlayer(offlinePlayer).addEntryToBans(new AstroBanEntry(offlinePlayer, null, dateFormatter.format(Calendar.getInstance().getTime()), Long.MAX_VALUE, null, "No reason given", null, true));

                        SQLGetter.getPunishedPlayer(offlinePlayer).setBanned(true);

                        if (offlinePlayer.isOnline()) {
                            offlinePlayer.getPlayer().kick(Component.text("You have been banned forever", NamedTextColor.RED));
                        }
                    }
                }
            } else if (args.length == 3) {

                ccs.sendMessage(ChatColor.RED + "Improper usage! Try /ap ban <Player> [Time] [Unit] [Reason]");

            } else if (args.length == 4) {

                // /ap ban <Player> [Time] [Unit] [Reason]

                if (Bukkit.getServer().getOfflinePlayer(args[1]) != null) {

                    OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);

                    if (SQLGetter.getPunishedPlayer(offlinePlayer) != null) {

                        if (SQLGetter.getPunishedPlayer(offlinePlayer).isBanned()) {
                            ccs.sendMessage(ChatColor.RED + "That player is already banned!");
                            return;
                        }

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                        if (NumberUtils.isCreatable(args[2])) {

                            Calendar cal = calendarStuff(args[2], args[3]);

                            if (calendarStuff(args[2], args[3]) == null) {
                                ccs.sendMessage(ChatColor.RED + "Improper unit! Try week, month, year, second, hour, day, or minute");
                                return;
                            }

                            SQLGetter.getPunishedPlayer(offlinePlayer).addEntryToBans(new AstroBanEntry(offlinePlayer, null, dateFormatter.format(Calendar.getInstance().getTime()), Long.parseLong(args[2]), getDateUnitText(args[3]), "No reason given", dateFormatter.format(cal.getTime()), true));

                            SQLGetter.getPunishedPlayer(offlinePlayer).setBanned(true);

                            if (offlinePlayer.isOnline()) {
                                offlinePlayer.getPlayer().kick(Component.text(ChatColor.RED + "You have been banned for no reason"));
                            }
                        } else {
                            ccs.sendMessage(ChatColor.RED + "Invalid number!");
                        }
                    }
                }
            } else if (args.length >= 5) {

                // /ap ban <Player> [Time] [Unit] [Reason]

                if (Bukkit.getServer().getOfflinePlayer(args[1]) != null) {

                    OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);

                    if (SQLGetter.getPunishedPlayer(offlinePlayer) != null) {

                        if (SQLGetter.getPunishedPlayer(Bukkit.getOfflinePlayer(args[1])).isBanned()) {
                            ccs.sendMessage(ChatColor.RED + "That player is already banned!");
                            return;
                        }


                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                        if (NumberUtils.isCreatable(args[2])) {

                            if (calendarStuff(args[2], args[3]) == null) {
                                ccs.sendMessage(ChatColor.RED + "Improper unit! Try week, month, year, second, hour, day, or minute");
                                return;
                            }

                            Calendar cal = calendarStuff(args[2], args[3]);

                            String[] reason = new String[args.length - 4];

                            for (int i = 4; i < args.length; i++) {

                                reason[i - 4] = args[i];
                            }

                            String concatednatedReason = String.join(" ", reason);

                            Component banText = Component.text("You have been banned for reason: ", NamedTextColor.RED, TextDecoration.BOLD)
                                    .appendNewline()
                                    .append(LegacyComponentSerializer.legacy('&').deserialize(concatednatedReason))
                                    .appendNewline()
                                    .appendNewline()
                                    .append(Component.text("For " + args[2] + " " + getDateUnitText(args[3]), NamedTextColor.AQUA))
                                    .appendNewline()
                                    .appendNewline()
                                    .append(Component.text("Try again on this date: ", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                                    .append(Component.text(dateFormatter.format(cal.getTime()), NamedTextColor.AQUA));

                            SQLGetter.getPunishedPlayer(offlinePlayer).addEntryToBans(new AstroBanEntry(offlinePlayer, null, dateFormatter.format(Calendar.getInstance().getTime()), Long.parseLong(args[2]), getDateUnitText(args[3]), concatednatedReason, dateFormatter.format(cal.getTime()), true));

                            SQLGetter.getPunishedPlayer(offlinePlayer).setBanned(true);

                            ccs.sendMessage(ChatColor.GREEN + "Ban entry successfully created");

                            if (offlinePlayer.isOnline()) {

                                offlinePlayer.getPlayer().kick(banText);
                            }
                        } else {
                            ccs.sendMessage(ChatColor.RED + "Invalid number!");
                        }
                    }
                }
            }
        }
    }

    public String getDateUnitText(String banUnit) {

        switch (banUnit) {
            case "week", "w", "weeks", "we", "wk", "wks":

                return "week(s)";
            case "day", "d", "da", "days", "dy", "dys":

                return "day(s)";
            case "month", "mo", "mon", "months", "mth", "mths", "mts":

                return "month(s)";
            case "year", "y", "years", "ye", "yr", "yrs":

                return "year(s)";
            case "hour", "h", "hou", "hours", "hr", "hrs":

                return "hour(s)";
            case "min", "minute", "minutes", "mi", "mins", "mis":

                return "minute(s)";
            case "second", "seconds", "sec", "secs", "s", "se":

                return "second(s)";
            default:
                return null;
        }
    }

    public Calendar calendarStuff(String timeToBan, String banUnit) {

        Calendar cal = Calendar.getInstance();

        long banTime = Long.parseLong(timeToBan);

        switch (banUnit.toLowerCase()) {

            case "week", "w", "weeks", "we", "wk", "wks":
                cal.add(Calendar.WEEK_OF_YEAR, (int) banTime);

                return cal;
            case "day", "d", "da", "days", "dy", "dys":
                cal.add(Calendar.DAY_OF_MONTH, (int) banTime);
                return cal;
            case "month", "mo", "mon", "months", "mth", "mths", "mts":
                cal.add(Calendar.MONTH + 1, (int) banTime);
                return cal;
            case "year", "y", "years", "ye", "yr", "yrs":
                cal.add(Calendar.YEAR, (int) banTime);
                return cal;
            case "hour", "h", "hou", "hours", "hr", "hrs":
                cal.add(Calendar.HOUR, (int) banTime);
                return cal;
            case "min", "minute", "minutes", "mi", "mins", "mis":
                cal.add(Calendar.MINUTE, (int) banTime);
                return cal;
            case "second", "seconds", "sec", "secs", "s", "se":
                cal.add(Calendar.SECOND, (int) banTime);
                return cal;
            default:
                return null;
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] strings) {
        return null;
    }

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

package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.AstroKickEntry;
import com.pixelate.astropunish.database.AstroMuteEntry;
import com.pixelate.astropunish.database.SQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.mcjustice.astroapi.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class KickCommand extends SubCommand {

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kicks the specified player";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("k", "boot", "kik", "remove");
    }

    @Override
    public String getSyntax() {
        return "/ ap kick <Player> [Reason]";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            if (args.length <= 1) {
                p.sendMessage(ChatColor.RED + "Insufficient arguments. Try /ap kick <Player> [Reason]");
                p.sendMessage(ChatColor.RED + "Example: /ap kick Notch I don't like you");
            }

            if (args.length == 2) {

                if (Bukkit.getPlayer(args[1]) != null) {
                    Player playerToKick = Bukkit.getPlayer(args[1]);

                    if (playerToKick.isOnline()) {
                        // Mute player forever
                        playerToKick.kick(Component.text("You have been kicked for no reason given"));

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                        SQLGetter.getPunishedPlayer(playerToKick).addEntryToKicks(new AstroKickEntry(playerToKick, null, dateFormatter.format(Calendar.getInstance().getTime()), "No reason given"));
                    } else {
                        p.sendMessage(ChatColor.RED + "That player is not online");
                    }
                }
            } else if (args.length >= 3) {

                // /ap kick <Player> [Reason]

                if (Bukkit.getServer().getPlayer(args[1]) != null) {

                    Player playerToKick = Bukkit.getPlayer(args[1]);

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

                    String[] reason = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {

                        reason[i - 2] = args[i];
                    }

                    String concatednatedReason = String.join(" ", reason);

                    Component kickText = Component.text("You have been kicked for reason: ", NamedTextColor.RED, TextDecoration.BOLD)
                            .appendNewline()
                            .append(LegacyComponentSerializer.legacy('&').deserialize(concatednatedReason));

                    SQLGetter.getPunishedPlayer(playerToKick).addEntryToKicks(new AstroKickEntry(playerToKick, null, dateFormatter.format(Calendar.getInstance().getTime()), concatednatedReason));

                    if (playerToKick.getPlayer().isOnline()) {
                        playerToKick.getPlayer().kick(kickText);
                        p.sendMessage(ChatColor.GREEN + "Kick entry successfully created");
                    } else {
                        p.sendMessage(ChatColor.RED + "That player is not online!");
                    }
                }
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

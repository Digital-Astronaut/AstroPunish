package com.pixelate.astropunish.commands;

import net.mcjustice.astroapi.Commands.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class MuteCommand extends SubCommand {

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public String getDescription() {
        return "Mutes the specified player";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("m", "mu", "silence", "sil");
    }

    @Override
    public String getSyntax() {
        return "/ap mute <Player> [Reason] [Time in seconds]";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            p.sendMessage(ChatColor.GREEN + "Oi!");
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

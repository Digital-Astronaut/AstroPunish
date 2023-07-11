package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.SQLUpdater;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.examination.Examinable;
import net.mcjustice.astroapi.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class APUpdateCommand extends SubCommand {

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Updates the database to reflect memory values";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("u", "up", "upload");
    }

    @Override
    public String getSyntax() {
        return "/ap update";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            if (args.length == 1) {
                SQLUpdater.uploadDataToDatabase();
                p.sendMessage(ChatColor.GREEN + "Successfully updated database!");
            } else {
                p.sendMessage(ChatColor.RED + "Invalid usage. Try /ap update");
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
    public HoverEvent<? extends Examinable> getSubCommandHoverEvent() {
        return null;
    }

    @Nullable
    @Override
    public ClickEvent getSubCommandClickEvent() {
        return null;
    }
}

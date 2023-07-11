package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.database.SQLGetter;
import com.pixelate.astropunish.database.SQLInit;
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

public class APReloadCommand extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload values from the database into memory (overriding memory values)";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "re", "rel", "override");
    }

    @Override
    public String getSyntax() {
        return "/ap reload";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            SQLGetter.getPunishedPlayers().forEach(pl -> pl.getBans().clear());
            SQLGetter.getPunishedPlayers().forEach(pl -> pl.getMutes().clear());
            SQLGetter.getPunishedPlayers().forEach(pl -> pl.getKicks().clear());
            SQLGetter.getPunishedPlayers().clear();

            SQLInit.initPunishedPlayers();
            p.sendMessage(ChatColor.GREEN + "Successfully reloaded memory values from database values!");
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

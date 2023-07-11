package com.pixelate.astropunish.commands;

import com.pixelate.astropunish.inventories.PunishmentsMainMenu;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.Commands.SubCommand;
import net.mcjustice.astroapi.Inventory.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PunishmentMenuCommand extends SubCommand {

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Displays the punishment menu";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("pmen", "punishmenu", "punishmentmenu");
    }

    @Override
    public String getSyntax() {
        return "/ap menu";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) return;

            new PunishmentsMainMenu(MenuManager.getPlayerMenuUtility(p)).open();
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

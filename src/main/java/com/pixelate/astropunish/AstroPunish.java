package com.pixelate.astropunish;

import com.pixelate.astropunish.commands.*;
import net.mcjustice.astroapi.AstroAPI;
import net.mcjustice.astroapi.Commands.CommandManager;
import net.mcjustice.astroapi.Inventory.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class AstroPunish extends JavaPlugin {

    public static final AstroAPI api = (AstroAPI) Bukkit.getServer().getPluginManager().getPlugin("AstroAPI");

    public static AstroPunish ASTROPUNISH;

    @Override
    public void onEnable() {

        if (api == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Couldn't find AstroAPI. Shutting down.");
            return;
        }

        if (api.isEnabled()) {

            ASTROPUNISH = this;

            MenuManager.setupListener(getServer(), this);

            try {
                createCommands();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully registered AstroPunish!");
    }

    public void createCommands() throws NoSuchFieldException, IllegalAccessException {
        CommandManager.createCommand(this, "ap", "Punishes ppl", "/ap", PunishmentMenuCommand.class, null, BanCommand.class, MuteCommand.class, KickCommand.class, UnbanCommand.class, UnmuteCommand.class, BlacklistCommand.class, PunishmentHistoryCommand.class, PunishmentMenuCommand.class);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(net.md_5.bungee.api.ChatColor.GREEN + "Successfully disabled AstroPunish");
    }
}

package com.pixelate.astropunish;

import com.pixelate.astropunish.commands.*;
import com.pixelate.astropunish.database.*;
import com.pixelate.astropunish.listeners.ChatListener;
import com.pixelate.astropunish.listeners.PunishLogin;
import net.mcjustice.astroapi.AstroAPI;
import net.mcjustice.astroapi.Commands.CommandManager;
import net.mcjustice.astroapi.Inventory.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class AstroPunish extends JavaPlugin {

    public static final AstroAPI api = (AstroAPI) Bukkit.getServer().getPluginManager().getPlugin("AstroAPI");

    public static AstroPunish ASTROPUNISH;
    public MySQL SQL;

    private String host = "Not available to you";
    private String port = "Not available to you";
    private String database = "Not available to you";
    private String username = "Not available to you";
    private String password = "Not available to you";

    @Override
    public void onEnable() {

        if (api == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Couldn't find AstroAPI. Shutting down.");
            return;
        }

        if (api.isEnabled()) {

            ASTROPUNISH = this;

            this.SQL = new MySQL();

            MenuManager.setupListener(getServer(), this);

            try {
                createCommands();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            Bukkit.getPluginManager().registerEvents(new PunishLogin(), this);
            Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

            try {
                SQL.connect(host, port, database, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                Bukkit.getLogger().info("Database not connected.");
            }

            if (SQL.isConnected()) {
                Bukkit.getLogger().info("Database is connected!");
                SQLCreator.createTables();
                SQLInit.initPunishedPlayers();
            }
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully registered AstroPunish!");
    }

    public void createCommands() throws NoSuchFieldException, IllegalAccessException {
        CommandManager.createCommand(this, "ap", "Punishes ppl", "/ap", PunishmentMenuCommand.class, null,
                BanCommand.class,
                MuteCommand.class,
                KickCommand.class,
                UnbanCommand.class,
                UnmuteCommand.class,
                BlacklistCommand.class,
//                PunishmentHistoryCommand.class,
                PunishmentMenuCommand.class,
                APUpdateCommand.class,
                APReloadCommand.class);
    }

    @Override
    public void onDisable() {
        SQLUpdater.uploadDataToDatabase();
        SQLGetter.getPunishedPlayers().forEach(p -> p.getBans().clear());
        SQLGetter.getPunishedPlayers().forEach(p -> p.getMutes().clear());
        SQLGetter.getPunishedPlayers().forEach(p -> p.getKicks().clear());
        SQLGetter.getPunishedPlayers().clear();
        SQL.disconnect();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully disconnected from database");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully disabled AstroPunish");
    }
}

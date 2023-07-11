package com.pixelate.astropunish.listeners;

import com.pixelate.astropunish.database.SQLCreator;
import com.pixelate.astropunish.database.SQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.ParseException;

public class PunishLogin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e) {

        SQLCreator.createPlayer(e.getPlayer());

        try {
            SQLGetter.getPunishedPlayer(e.getPlayer()).setIsBanned();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        if (SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentBan() != null) {


            String unbanDate = "";
            Component banText = null;

            if (SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentBan().getUnbanDate() != null) {

                unbanDate = SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentBan().getUnbanDate();

                banText = Component.text("You are banned for reason: ", NamedTextColor.RED, TextDecoration.BOLD)
                        .appendNewline()
                        .append(LegacyComponentSerializer.legacy('&').deserialize(SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentBan().getBanReason())
                                .appendNewline()
                                .appendNewline()
                                .append(Component.text("Until " + unbanDate, NamedTextColor.AQUA)));
            } else {
                banText = Component.text("You are banned for reason: ", NamedTextColor.RED, TextDecoration.BOLD)
                        .appendNewline()
                        .append(LegacyComponentSerializer.legacy('&').deserialize(SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentBan().getBanReason())
                                .appendNewline()
                                .appendNewline()
                                .append(Component.text("Forever", NamedTextColor.RED)));
            }

            if (SQLGetter.getPunishedPlayer(e.getPlayer()).isBanned()) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, banText);
            }
        } else if (SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentBan() == null) {

            SQLGetter.getPunishedPlayer(e.getPlayer()).setBanned(false);
        }

        if (SQLGetter.getPunishedPlayer(e.getPlayer()).getIsBlacklisted()) {

            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You are blacklisted from this server");
        }
    }
}

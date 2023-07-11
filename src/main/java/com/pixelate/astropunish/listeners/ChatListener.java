package com.pixelate.astropunish.listeners;

import com.pixelate.astropunish.database.SQLGetter;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.ParseException;

public class ChatListener implements Listener {

    @EventHandler
    public void onMutedPlayerChat(AsyncChatEvent e) {

        if (SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentMute() != null) {

            try {
                SQLGetter.getPunishedPlayer(e.getPlayer()).setIsMuted();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            if (SQLGetter.getPunishedPlayer(e.getPlayer()).isMuted()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You cannot chat, you are muted until " + SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentMute().getUnmuteDate());
            }
        } else if (SQLGetter.getPunishedPlayer(e.getPlayer()).getCurrentMute() == null) {

            SQLGetter.getPunishedPlayer(e.getPlayer()).setMuted(false);
        }
    }
}

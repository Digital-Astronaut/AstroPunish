package com.pixelate.astropunish.database;

import org.bukkit.OfflinePlayer;

public class SQLInit {

    public static void initPunishedPlayers() {

        populatePunishedPlayers();
    }
    public static void populatePunishedPlayers() {

        for (OfflinePlayer p : SQLGetter.getPlayersInAPPlayers()) {

            SQLGetter.getPunishedPlayers().add(new PunishedPlayer(p.getName(),
                    p.getUniqueId(),
                    SQLGetter.getNumberOfPunishment(p, "BanAmount"),
                    SQLGetter.getNumberOfPunishment(p, "MuteAmount"),
                    SQLGetter.getNumberOfPunishment(p, "KickAmount"),
                    SQLGetter.getIsPunished(p, "IsBanned"),
                    SQLGetter.getIsPunished(p, "IsMuted"),
                    SQLGetter.getIsPunished(p, "IsBlacklisted"),
                    SQLGetter.getPlayerBans(p),
                    SQLGetter.getPlayerMutes(p),
                    SQLGetter.getPlayerKicks(p)));
        }
    }
}

package com.pixelate.astropunish.database;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class AstroKickEntry {

    private OfflinePlayer p;
    private UUID kickId;
    private String kickDate;
    private String kickReason;

    public AstroKickEntry(OfflinePlayer p, UUID kickId, String kickDate, String kickReason) {

        if (kickId == null)
            this.kickId = UUID.randomUUID();
        else
            this.kickId = kickId;
        this.p = p;
        this.kickDate = kickDate;
        this.kickReason = kickReason;
    }

    public UUID getKickId() {
        return kickId;
    }

    public String getKickDate() {
        return kickDate;
    }

    public String getKickReason() {
        return kickReason;
    }
}

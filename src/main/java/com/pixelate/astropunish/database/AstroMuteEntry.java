package com.pixelate.astropunish.database;

import org.bukkit.OfflinePlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AstroMuteEntry {

    private OfflinePlayer p;
    private UUID muteId;
    private String muteDate;
    private long muteLength;
    private String muteUnit;
    private String muteReason;
    private String unmuteDate;

    private boolean isActive;

    public AstroMuteEntry(OfflinePlayer p, UUID muteId, String muteDate, long muteLength, String muteUnit, String muteReason, String unmuteDate, boolean isActive) {

        if (muteId == null)
            this.muteId = UUID.randomUUID();
        else
            this.muteId = muteId;
        this.p = p;
        this.muteDate = muteDate;
        this.muteLength = muteLength;
        this.muteUnit = muteUnit;
        this.muteReason = muteReason;

        if (unmuteDate == null) {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");


            this.unmuteDate = dateFormatter.format(new Date(Long.MAX_VALUE));
        } else {
            this.unmuteDate = unmuteDate;
        }

        this.isActive = isActive;
    }

    public UUID getMuteId() {
        return muteId;
    }

    public String getMuteDate() {
        return muteDate;
    }

    public long getMuteLength() {
        return muteLength;
    }

    public String getMuteUnit() {
        return muteUnit;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public String getUnmuteDate() {
        return unmuteDate;
    }
    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

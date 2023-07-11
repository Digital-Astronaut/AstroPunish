package com.pixelate.astropunish.database;

import org.bukkit.OfflinePlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AstroBanEntry {


    private OfflinePlayer p;
    private UUID banId;
    private String banDate;
    private long banLength;
    private String banUnit;
    private String banReason;
    private String unbanDate;

    private boolean isActive;

    public AstroBanEntry(OfflinePlayer p, UUID banId, String banDate, long banLength, String banUnit, String banReason, String unbanDate, boolean isActive) {

        if (banId == null)
            this.banId = UUID.randomUUID();
        else
            this.banId = banId;
        this.banDate = banDate;
        this.p = p;
        this.banLength = banLength;
        this.banUnit = banUnit;
        this.banReason = banReason;

        if (unbanDate == null) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

            this.unbanDate = dateFormatter.format(new Date(Long.MAX_VALUE));
        } else {
            this.unbanDate = unbanDate;
        }

        this.isActive = isActive;
    }

    public String getBanDate() {
        return banDate;
    }

    public UUID getBanId() {
        return banId;
    }

    public long getBanLength() {
        return banLength;
    }

    public String getBanUnit() {
        return banUnit;
    }

    public String getBanReason() {
        return banReason;
    }

    public String getUnbanDate() {
        return unbanDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

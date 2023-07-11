package com.pixelate.astropunish.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PunishedPlayer {

    private String playerName;
    private UUID playerUUID;
    private int playerBanAmount;
    private int playerMuteAmount;
    private int playerKickAmount;
    private boolean isBanned;
    private boolean isMuted;
    private boolean isBlacklisted;

    private List<AstroBanEntry> bans;
    private List<AstroMuteEntry> mutes;
    private List<AstroKickEntry> kicks;

    public PunishedPlayer(String playerName, UUID playerUUID, int playerBanAmount, int playerMuteAmount, int playerKickAmount, boolean isBanned, boolean isMuted, boolean isBlacklisted, List<AstroBanEntry> bans, List<AstroMuteEntry> mutes, List<AstroKickEntry> kicks) {

        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerBanAmount = playerBanAmount;
        this.playerMuteAmount = playerMuteAmount;
        this.playerKickAmount = playerKickAmount;
        this.isBanned = isBanned;
        this.isMuted = isMuted;
        this.isBlacklisted = isBlacklisted;

        this.bans = bans;
        this.mutes = mutes;
        this.kicks = kicks;
    }

    public void setIsBanned() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

        Date current = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));

        if (getCurrentBan() != null) {

            Date date = dateFormat.parse(getCurrentBan().getUnbanDate());

            if (date.after(current)) {
                System.out.println("Date: " + date + " is greater than " + current);
                setBanned(true);
            } else {
                System.out.println("Date: " + date + " is less than " + current);
                setBanned(false);
                getCurrentBan().setActive(false);
            }
        }
    }

    public void unmute() {

        for (AstroMuteEntry entry : getMutes()) {
            entry.setActive(false);
        }

        setMuted(false);
    }

    public void unban() {

        for (AstroBanEntry entry : getBans()) {
            entry.setActive(false);
        }
        setBanned(false);
    }

    public void setIsMuted() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");

        Date current = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));

        if (getCurrentMute() != null) {

            Date date = dateFormat.parse(getCurrentMute().getUnmuteDate());

            if (date.after(current)) {
                System.out.println("Date: " + date + " is greater than " + current);
                setMuted(true);
            } else {
                System.out.println("Date: " + date + " is less than " + current);
                setMuted(false);
                getCurrentMute().setActive(false);
            }
        }
    }

    public AstroBanEntry getCurrentBan() {

        for (AstroBanEntry entry : getBans()) {

            if (entry.getIsActive())
                return entry;
        }
        return null;
    }

    public AstroMuteEntry getCurrentMute() {

        for (AstroMuteEntry entry : getMutes()) {

            if (entry.getIsActive())
                return entry;
        }
        return null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getPlayerBanAmount() {

        if (playerBanAmount != getBans().size())
            setPlayerBanAmount(getBans().size());

        return playerBanAmount;
    }

    public void setPlayerBanAmount(int playerBanAmount) {
        this.playerBanAmount = playerBanAmount;
    }

    public int getPlayerMuteAmount() {

        if (playerMuteAmount != getMutes().size())
            setPlayerMuteAmount(getMutes().size());

        return playerMuteAmount;
    }

    public void setPlayerMuteAmount(int playerMuteAmount) {
        this.playerMuteAmount = playerMuteAmount;
    }

    public int getPlayerKickAmount() {

        if (playerKickAmount != getKicks().size())
            setPlayerKickAmount(getKicks().size());

        return playerKickAmount;
    }

    public void setPlayerKickAmount(int playerKickAmount) {
        this.playerKickAmount = playerKickAmount;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isMuted() {

        return isMuted;
    }

    public boolean getIsBlacklisted() {

        return isBlacklisted;
    }

    public void setIsBlacklisted(boolean isBlacklisted) {

        this.isBlacklisted = isBlacklisted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public List<AstroBanEntry> getBans() {
        return bans;
    }

    public List<AstroMuteEntry> getMutes() {
        return mutes;
    }

    public List<AstroKickEntry> getKicks() {
        return kicks;
    }

    public void setBans(List<AstroBanEntry> bans) {
        this.bans = bans;
    }

    public void addEntryToBans(AstroBanEntry ban) {

        bans.add(ban);
        playerBanAmount = bans.size();
    }

    public void addEntryToMutes(AstroMuteEntry mute) {

        mutes.add(mute);
        playerMuteAmount = mutes.size();
    }

    public void addEntryToKicks(AstroKickEntry kick) {

        kicks.add(kick);
        playerKickAmount = kicks.size();
    }
}

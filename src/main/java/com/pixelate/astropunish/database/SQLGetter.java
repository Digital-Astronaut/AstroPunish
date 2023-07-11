package com.pixelate.astropunish.database;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.pixelate.astropunish.AstroPunish.ASTROPUNISH;

public class SQLGetter {

    private static List<PunishedPlayer> punishedPlayers = new ArrayList<>();


    public static boolean exists(String tableToCheck, String uuidName, UUID uuid) {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT * FROM " + tableToCheck + " WHERE " + uuidName + "=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // TODO: I don't think this is properly checking names
    public static boolean playerNameMatchesUUID(UUID uuid, Player p) {
        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT * FROM APPlayers WHERE Name=?");
            ps.setString(1, p.getName());

            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<OfflinePlayer> getPlayersInAPPlayers() {

        List<OfflinePlayer> players = new ArrayList<>();

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT * FROM APPlayers");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                UUID uuid = UUID.fromString(rs.getString("UUID"));

                players.add(Bukkit.getOfflinePlayer(uuid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }


    public static List<AstroBanEntry> getPlayerBans(OfflinePlayer p) {

        List<AstroBanEntry> bansList = new ArrayList<>();

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT * FROM APBans WHERE PlayerUUID=?");
            ps.setString(1, p.getUniqueId().toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                bansList.add(new AstroBanEntry(p, UUID.fromString(rs.getString("BanId")),
                        rs.getString("BanDate"),
                        rs.getLong("BanLength"),
                        rs.getString("BanUnit"),
                        rs.getString("BanReason"),
                        rs.getString("UnbanDate"),
                        rs.getBoolean("IsActive")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bansList;
    }

    public static List<AstroMuteEntry> getPlayerMutes(OfflinePlayer p) {

        List<AstroMuteEntry> mutesList = new ArrayList<>();

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT * FROM APMutes WHERE PlayerUUID=?");
            ps.setString(1, p.getUniqueId().toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                mutesList.add(new AstroMuteEntry(p, UUID.fromString(rs.getString("MuteId")),
                        rs.getString("MuteDate"),
                        rs.getLong("MuteLength"),
                        rs.getString("MuteUnit"),
                        rs.getString("MuteReason"),
                        rs.getString("UnmuteDate"),
                        rs.getBoolean("IsActive")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mutesList;
    }

    public static List<AstroKickEntry> getPlayerKicks(OfflinePlayer p) {

        List<AstroKickEntry> kicksList = new ArrayList<>();

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT * FROM APKicks WHERE PlayerUUID=?");
            ps.setString(1, p.getUniqueId().toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                kicksList.add(new AstroKickEntry(p, UUID.fromString(rs.getString("KickId")),
                        rs.getString("KickDate"),
                        rs.getString("KickReason")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kicksList;
    }

    public static boolean getIsPunished(OfflinePlayer p, String typeOfPunishment) {

        String type = "";

        switch (typeOfPunishment.toUpperCase()) {
            case "BAN", "ISBANNED", "BANNED":
                type = "IsBanned";
                break;
            case "MUTE", "MUTED", "ISMUTED":
                type = "IsMuted";
                break;
            case "BLACKLISTED", "BL", "ISBLACKLISTED":
                type = "IsBlacklisted";
                break;
            default:
                break;
        }

        boolean isPunished = false;

        try {

            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT " + type + " FROM APPlayers WHERE UUID=?");

            ps.setString(1, p.getUniqueId().toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                isPunished = rs.getBoolean(type);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isPunished;
    }

    public static int getNumberOfPunishment(OfflinePlayer p, String punishment) {

        String typeOfPunishment = "";

        switch (punishment.toUpperCase()) {
            case "BANS", "BAN", "BANAMOUNT":
                typeOfPunishment = "APBans";
                break;
            case "MUTES", "MUTE":
                typeOfPunishment = "APMutes";
                break;
            default:
                typeOfPunishment = "APKicks";
                break;
        }

        int punishmentAmount = 0;

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT count(*) FROM " + typeOfPunishment + " WHERE PlayerUUID=?");
            ps.setString(1, p.getUniqueId().toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                punishmentAmount = rs.getInt("count(*)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return punishmentAmount;
    }

//    public static int getPunishmentAmountForPlayer(OfflinePlayer p, String punishment) {
//
//        String typeOfPunishment = "";
//
//        switch (punishment.toUpperCase()) {
//            case "MUTES", "MUTEAMOUNT":
//                typeOfPunishment = "MuteAmount";
//                break;
//            case "KICKS", "KICKAMOUNT":
//                typeOfPunishment = "KickAmount";
//                break;
//            default:
//                typeOfPunishment = "BanAmount";
//                break;
//        }
//
//        int punishmentAmount = 0;
//
//        try {
//
//            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("SELECT " + typeOfPunishment + " FROM APPlayers WHERE UUID=?");
//
//            ps.setString(1, p.getUniqueId().toString());
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//
//                punishmentAmount = rs.getInt(typeOfPunishment);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return punishmentAmount;
//    }

    public static PunishedPlayer getPunishedPlayer(OfflinePlayer op) {

        PunishedPlayer playerToGet = null;

        for (PunishedPlayer pp : getPunishedPlayers()) {

            if (pp.getPlayerUUID().toString().equals(op.getUniqueId().toString())) {
                playerToGet = pp;
            }
        }

        return playerToGet;
    }

    public static List<PunishedPlayer> getPunishedPlayers() {

        return punishedPlayers;
    }

    public static void setPunishedPlayers(List<PunishedPlayer> punishedPlayers) {

        SQLGetter.punishedPlayers = punishedPlayers;
    }
}

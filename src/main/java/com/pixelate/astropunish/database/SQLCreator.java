package com.pixelate.astropunish.database;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static com.pixelate.astropunish.AstroPunish.ASTROPUNISH;

public class SQLCreator {

    public static void createTables() {
        createPlayersTable();
        createBansTable();
        createMutesTable();
        createKicksTable();
//        createBlacklistsTable();
    }

    public static void createBansTable() {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS APBans(Name varchar(100), PlayerUUID varchar(100), BanID varchar(100), BanDate varchar(100), BanLength bigint NOT NULL, BanUnit varchar(100), BanReason varchar(100), UnbanDate varchar(100), IsActive boolean, PRIMARY KEY (BanID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createKicksTable() {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS APKicks(Name varchar(100), PlayerUUID varchar(100), KickID varchar(100), KickDate varchar(100), KickReason varchar(100), PRIMARY KEY (KickID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMutesTable() {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS APMutes(Name varchar(100), PlayerUUID varchar(100), MuteID varchar(100), MuteDate varchar(100), MuteLength bigint NOT NULL, MuteUnit varchar(100), MuteReason varchar(100), UnmuteDate varchar(100), IsActive boolean, PRIMARY KEY (MuteID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void createBlacklistsTable() {
//
//        try {
//            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement(
//                    "CREATE TABLE IF NOT EXISTS APBlacklists(Name varchar(100), PlayerUUID varchar(100), BlacklistReason varchar(100), PRIMARY KEY (PlayerUUID))");
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void createPlayersTable() {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS APPlayers(Name varchar(100), UUID varchar(100), BanAmount integer NOT NULL, MuteAmount integer NOT NULL, KickAmount integer NOT NULL, IsBanned boolean, IsMuted boolean, IsBlacklisted boolean, PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createPlayer(Player p) {

        try {
            UUID uuid = p.getUniqueId();

            if (!SQLGetter.exists("APPlayers", "UUID", uuid)) {

                PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("INSERT IGNORE INTO APPlayers" + "(Name,UUID,BanAmount,MuteAmount,KickAmount,IsBanned,IsMuted,IsBlacklisted) VALUES (?,?,?,?,?,?,?,?)");

                ps.setString(1, p.getName());
                ps.setString(2, uuid.toString());
                ps.setInt(3, 0);
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.setBoolean(6, false);
                ps.setBoolean(7, false);
                ps.setBoolean(8, false);
                ps.executeUpdate();

                SQLGetter.getPunishedPlayers().add(new PunishedPlayer(p.getName(), p.getUniqueId(), 0, 0, 0, false, false, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

            } else if (SQLGetter.exists("APPlayers", "UUID", uuid)) {
                if (!SQLGetter.playerNameMatchesUUID(uuid, p)) {
                    SQLUpdater.updatePlayerNameToMatchUUID(p);
                } else {
                    System.out.println("Username matches UUID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createBanEntry(PunishedPlayer p, UUID banId, String banDate, long banLength, String banUnit, String banReason, String unbanDate, boolean isActive) {

        try {

            if (!SQLGetter.exists("APBans", "BanID", banId)) {

                PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("INSERT IGNORE INTO APBans" + "(Name,PlayerUUID,BanID,BanDate,BanLength,BanUnit,BanReason,UnbanDate,IsActive) VALUES (?,?,?,?,?,?,?,?,?)");

                ps.setString(1, p.getPlayerName());
                ps.setString(2, p.getPlayerUUID().toString());
                ps.setString(3, banId.toString());
                ps.setString(4, banDate);
                ps.setLong(5, banLength);
                ps.setString(6, banUnit);
                ps.setString(7, banReason);
                ps.setString(8, unbanDate);
                ps.setBoolean(9, isActive);
                ps.executeUpdate();
            }
            // Add check here for making sure no BanIds collide

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createMuteEntry(PunishedPlayer p, UUID muteId, String muteDate, long muteLength, String muteUnit, String muteReason, String unmuteDate, boolean isActive) {

        try {

            if (!SQLGetter.exists("APMutes", "MuteID", muteId)) {

                PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("INSERT IGNORE INTO APMutes" + "(Name,PlayerUUID,MuteID,MuteDate,MuteLength,MuteUnit,MuteReason,UnmuteDate,IsActive) VALUES (?,?,?,?,?,?,?,?,?)");

                ps.setString(1, p.getPlayerName());
                ps.setString(2, p.getPlayerUUID().toString());
                ps.setString(3, muteId.toString());
                ps.setString(4, muteDate);
                ps.setLong(5, muteLength);
                ps.setString(6, muteUnit);
                ps.setString(7, muteReason);
                ps.setString(8, unmuteDate);
                ps.setBoolean(9, isActive);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createKickEntry(PunishedPlayer p, UUID kickId, String kickDate, String kickReason) {

        try {

            if (!SQLGetter.exists("APKicks", "KickID", kickId)) {

                PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("INSERT IGNORE INTO APKicks" + "(Name,PlayerUUID,KickID,KickDate,KickReason) VALUES (?,?,?,?,?)");

                ps.setString(1, p.getPlayerName());
                ps.setString(2, p.getPlayerUUID().toString());
                ps.setString(3, kickId.toString());
                ps.setString(4, kickDate);
                ps.setString(5, kickReason);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

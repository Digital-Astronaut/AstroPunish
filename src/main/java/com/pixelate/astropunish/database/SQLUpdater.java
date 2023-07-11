package com.pixelate.astropunish.database;

import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.pixelate.astropunish.AstroPunish.ASTROPUNISH;

public class SQLUpdater {

    public static void updatePlayerNameToMatchUUID(OfflinePlayer p) {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("UPDATE APPlayers SET Name=? WHERE UUID=?");

            ps.setString(1, p.getName());
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();

            SQLGetter.getPunishedPlayer(p).setPlayerName(p.getName());

            System.out.println("Successfully updated player name to match UUID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setPunishmentAmountForPlayer(String UUID, String punishment, int amount) {

        try {

            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("UPDATE APPlayers SET " + punishment + "=" + amount + " WHERE UUID=?");

            ps.setString(1, UUID);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setIsPunishedForPlayer(String UUID, String punishment, boolean isPunished) {

        try {
            PreparedStatement ps = ASTROPUNISH.SQL.getConnection().prepareStatement("UPDATE APPlayers SET " + punishment + "=" + isPunished + " WHERE UUID=?");

            ps.setString(1, UUID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void uploadDataToDatabase() {

        for (PunishedPlayer pp : SQLGetter.getPunishedPlayers()) {

            for (AstroBanEntry entry : pp.getBans()) {

                SQLCreator.createBanEntry(pp, entry.getBanId(), entry.getBanDate(), entry.getBanLength(), entry.getBanUnit(), entry.getBanReason(), entry.getUnbanDate(), entry.getIsActive());
            }

            for (AstroMuteEntry entry : pp.getMutes()) {
                SQLCreator.createMuteEntry(pp, entry.getMuteId(), entry.getMuteDate(), entry.getMuteLength(), entry.getMuteUnit(), entry.getMuteReason(), entry.getUnmuteDate(), entry.getIsActive());
            }

            for (AstroKickEntry entry : pp.getKicks()) {
                SQLCreator.createKickEntry(pp, entry.getKickId(), entry.getKickDate(), entry.getKickReason());
            }

            setPunishmentAmountForPlayer(pp.getPlayerUUID().toString(), "BanAmount", pp.getPlayerBanAmount());
            setIsPunishedForPlayer(pp.getPlayerUUID().toString(), "IsBanned", pp.isBanned());
            setPunishmentAmountForPlayer(pp.getPlayerUUID().toString(), "MuteAmount", pp.getPlayerMuteAmount());
            setIsPunishedForPlayer(pp.getPlayerUUID().toString(), "IsMuted", pp.isMuted());
            setPunishmentAmountForPlayer(pp.getPlayerUUID().toString(), "KickAmount", pp.getPlayerKickAmount());
            setIsPunishedForPlayer(pp.getPlayerUUID().toString(), "IsBlacklisted", pp.getIsBlacklisted());
        }
    }
}

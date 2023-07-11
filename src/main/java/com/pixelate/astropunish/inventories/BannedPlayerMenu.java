package com.pixelate.astropunish.inventories;

import com.pixelate.astropunish.database.AstroBanEntry;
import com.pixelate.astropunish.database.SQLGetter;
import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class BannedPlayerMenu extends PaginatedMenu {

    private HashMap<Integer, AstroBanEntry> banEntriesMap = new HashMap<>();

    private OfflinePlayer p;

    public BannedPlayerMenu(PlayerMenuUtility playerMenuUtility, OfflinePlayer p, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        this.p = p;

        setMainMenu(new PunishmentsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {

        SQLGetter.getPunishedPlayer(p).getBans().sort(Comparator.comparing(AstroBanEntry::getBanDate).reversed());

        return SQLGetter.getPunishedPlayer(p).getBans();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof AstroBanEntry abe) {

            ItemStack banEntry = new ItemStack(Material.PAPER);

            ItemMeta banMeta = banEntry.getItemMeta();

            banMeta.setDisplayName(ChatColor.YELLOW + "Ban Date: " + abe.getBanDate());
            List<String> lore = new ArrayList<>();

            if (abe.getBanLength() == Long.MAX_VALUE) {
                lore.add(ChatColor.YELLOW + "Unban Date: Never");
            } else {
                lore.add(ChatColor.YELLOW + "Unban Date: " + abe.getUnbanDate());
            }

            if (abe.getBanLength() == Long.MAX_VALUE) {
                lore.add(ChatColor.LIGHT_PURPLE + "Ban Length: Forever");
            } else if (abe.getBanLength() < Long.MAX_VALUE && abe.getBanUnit() == null) {
                lore.add(ChatColor.LIGHT_PURPLE + "Ban Length: " + abe.getBanLength() + " years");
            } else if (abe.getBanLength() < Long.MAX_VALUE && abe.getBanUnit() != null) {
                lore.add(ChatColor.LIGHT_PURPLE + "Ban Length: " + abe.getBanLength() + " " + abe.getBanUnit());
            }

            if (abe.getBanReason() == null) {
                lore.add(ChatColor.AQUA + "Ban Reason: No reason given");
            } else {
                lore.add(ChatColor.AQUA + "Ban Reason: " + ChatColor.translateAlternateColorCodes('&', abe.getBanReason()));
            }

            banMeta.setLore(lore);
            banEntry.setItemMeta(banMeta);

            banEntriesMap.put(inventory.firstEmpty(), abe);

            inventory.setItem(inventory.firstEmpty(), banEntry);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW + "" + p.getName() + " Bans";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

//                if (e.getClick() == ClickType.RIGHT) {
//                    SQLGetter.getPlayerBans(p).remove(banEntriesMap.get(e.getRawSlot()));
//                    getData().remove(banEntriesMap.get(e.getRawSlot()));
//                    banEntriesMap.remove(e.getRawSlot());
//
//                    open();
//                }
            }
        }
    }
}

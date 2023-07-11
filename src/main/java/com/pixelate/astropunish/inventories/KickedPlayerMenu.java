package com.pixelate.astropunish.inventories;

import com.pixelate.astropunish.database.AstroBanEntry;
import com.pixelate.astropunish.database.AstroKickEntry;
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

public class KickedPlayerMenu extends PaginatedMenu {

    private HashMap<Integer, AstroKickEntry> kickEntryMap = new HashMap<>();

    private OfflinePlayer p;

    public KickedPlayerMenu(PlayerMenuUtility playerMenuUtility, OfflinePlayer p, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        this.p = p;

        setMainMenu(new PunishmentsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        SQLGetter.getPunishedPlayer(p).getKicks().sort(Comparator.comparing(AstroKickEntry::getKickDate).reversed());

        return SQLGetter.getPunishedPlayer(p).getKicks();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof AstroKickEntry ake) {

            ItemStack kickEntry = new ItemStack(Material.PAPER);

            ItemMeta kickMeta = kickEntry.getItemMeta();

            kickMeta.setDisplayName(ChatColor.YELLOW + "Kick Date: " + ake.getKickDate());
            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.AQUA + "Kick Reason: " + ChatColor.translateAlternateColorCodes('&', ake.getKickReason()));

            kickMeta.setLore(lore);
            kickEntry.setItemMeta(kickMeta);

            kickEntryMap.put(inventory.firstEmpty(), ake);

            inventory.setItem(inventory.firstEmpty(), kickEntry);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW + "" + p.getName() + " Kicks";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

//                if (e.getClick() == ClickType.RIGHT) {
//                    SQLGetter.getPlayerKicks(p).remove(kickEntryMap.get(e.getRawSlot()));
//                    getData().remove(kickEntryMap.get(e.getRawSlot()));
//                    kickEntryMap.remove(e.getRawSlot());
//
//                    open();
//                }
            }
        }
    }
}

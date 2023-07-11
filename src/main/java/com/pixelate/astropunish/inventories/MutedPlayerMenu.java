package com.pixelate.astropunish.inventories;

import com.pixelate.astropunish.database.AstroMuteEntry;
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

public class MutedPlayerMenu extends PaginatedMenu {

    private HashMap<Integer, AstroMuteEntry> muteEntryMap = new HashMap<>();

    private OfflinePlayer p;

    public MutedPlayerMenu(PlayerMenuUtility playerMenuUtility, OfflinePlayer p, Menu previousMenu) {

        super(playerMenuUtility, previousMenu);

        this.p = p;

        setMainMenu(new PunishmentsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        SQLGetter.getPunishedPlayer(p).getMutes().sort(Comparator.comparing(AstroMuteEntry::getMuteDate).reversed());

        return SQLGetter.getPunishedPlayer(p).getMutes();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof AstroMuteEntry ame) {

            ItemStack muteEntry = new ItemStack(Material.PAPER);

            ItemMeta muteMeta = muteEntry.getItemMeta();

            muteMeta.setDisplayName(ChatColor.YELLOW + "Mute Date: " + ame.getMuteDate());
            List<String> lore = new ArrayList<>();

            if (ame.getMuteLength() == Long.MAX_VALUE) {
                lore.add(ChatColor.YELLOW + "Unmute Date: Never");
            } else {
                lore.add(ChatColor.YELLOW + "Unmute Date: " + ame.getUnmuteDate());
            }
            if (ame.getMuteLength() == Long.MAX_VALUE) {
                lore.add(ChatColor.LIGHT_PURPLE + "Mute Length: Forever");
            } else if (ame.getMuteLength() < Long.MAX_VALUE && ame.getMuteUnit() == null) {
                lore.add(ChatColor.LIGHT_PURPLE + "Mute Length: " + ame.getMuteLength() + " years");
            } else if (ame.getMuteLength() < Long.MAX_VALUE && ame.getMuteUnit() != null) {

                lore.add(ChatColor.LIGHT_PURPLE + "Mute Length: " + ame.getMuteLength() + " " + ame.getMuteUnit());
            }

            if (ame.getMuteReason() == null)
                lore.add(ChatColor.AQUA + "Mute Reason: No reason given");
            else
                lore.add(ChatColor.AQUA + "Mute Reason: " + ChatColor.translateAlternateColorCodes('&', ame.getMuteReason()));

            muteMeta.setLore(lore);
            muteEntry.setItemMeta(muteMeta);

            muteEntryMap.put(inventory.firstEmpty(), ame);

            inventory.setItem(inventory.firstEmpty(), muteEntry);
        }

    }

    @Override
    public int getMaxAmountOfDisplayedItems() {

        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW + "" + p.getName() + " Mutes";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

//                if (e.getClick() == ClickType.RIGHT) {
//                    SQLGetter.getPlayerMutes(p).remove(muteEntryMap.get(e.getRawSlot()));
//                    getData().remove(muteEntryMap.get(e.getRawSlot()));
//                    muteEntryMap.remove(e.getRawSlot());
//
//                    open();
//                }
            }
        }
    }
}

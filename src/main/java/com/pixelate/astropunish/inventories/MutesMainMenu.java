package com.pixelate.astropunish.inventories;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MutesMainMenu extends Menu {

    public MutesMainMenu(PlayerMenuUtility playerMenuUtility, Menu previousMenu) {

        super(playerMenuUtility, previousMenu);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "Mutes Menu";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

        ItemStack createMute = new ItemStack(Material.PAPER);
        ItemStack removeMute = new ItemStack(Material.PAPER);
        ItemStack viewMutes = new ItemStack(Material.BOOK);

        ItemMeta createMeta = createMute.getItemMeta();
        ItemMeta removeMeta = removeMute.getItemMeta();
        ItemMeta viewMeta = viewMutes.getItemMeta();

        createMeta.setDisplayName(ChatColor.GREEN + "Mute a player");
        viewMeta.setDisplayName(ChatColor.GREEN + "View mute history");

        createMute.setItemMeta(createMeta);
        removeMute.setItemMeta(removeMeta);
        viewMutes.setItemMeta(viewMeta);

//        inventory.setItem(11, createBan);
        inventory.setItem(13, viewMutes);
//        inventory.setItem(15, removeBan);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getSlot() == 13) {

            new MuteListMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
        }
    }
}

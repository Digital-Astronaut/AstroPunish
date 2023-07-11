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
import org.jetbrains.annotations.Nullable;

public class KicksMainMenu extends Menu {

    public KicksMainMenu(PlayerMenuUtility playerMenuUtility, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "Kicks Menu";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

        ItemStack createBan = new ItemStack(Material.PAPER);
        ItemStack removeBan = new ItemStack(Material.PAPER);
        ItemStack viewKicks = new ItemStack(Material.BOOK);

        ItemMeta createMeta = createBan.getItemMeta();
        ItemMeta removeMeta = removeBan.getItemMeta();
        ItemMeta viewMeta = viewKicks.getItemMeta();
//
//        createMeta.setDisplayName(ChatColor.GREEN + "Create a ban entry");
//        removeMeta.setDisplayName(ChatColor.GREEN + "Remove a ban entry");
        viewMeta.setDisplayName(ChatColor.GREEN + "View kicks");

        createBan.setItemMeta(createMeta);
        removeBan.setItemMeta(removeMeta);
        viewKicks.setItemMeta(viewMeta);

//        inventory.setItem(11, createBan);
        inventory.setItem(13, viewKicks);
//        inventory.setItem(15, removeBan);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getSlot() == 13) {

            new KickListMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
        }
    }
}

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

public class BansMainMenu extends Menu {
    public BansMainMenu(PlayerMenuUtility playerMenuUtility, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        // No point in setting main menu if the main menu is just the previous one
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "Bans Menu";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

        ItemStack createBan = new ItemStack(Material.PAPER);
        ItemStack removeBan = new ItemStack(Material.PAPER);
        ItemStack viewBans = new ItemStack(Material.BOOK);

        ItemMeta createMeta = createBan.getItemMeta();
        ItemMeta removeMeta = removeBan.getItemMeta();
        ItemMeta viewMeta = viewBans.getItemMeta();

        createMeta.setDisplayName(ChatColor.GREEN + "Create a ban entry");
        removeMeta.setDisplayName(ChatColor.GREEN + "Remove a ban entry");
        viewMeta.setDisplayName(ChatColor.GREEN + "View bans");

        createBan.setItemMeta(createMeta);
        removeBan.setItemMeta(removeMeta);
        viewBans.setItemMeta(viewMeta);

//        inventory.setItem(11, createBan);
        inventory.setItem(13, viewBans);
//        inventory.setItem(15, removeBan);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getSlot() == 13) {

            new BanListMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
        }
    }
}

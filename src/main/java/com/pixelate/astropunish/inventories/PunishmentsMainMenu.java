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

public class PunishmentsMainMenu extends Menu {

    public PunishmentsMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "Punish Menu";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void setMenuItems() {

        ItemStack bans = new ItemStack(Material.GREEN_WOOL);
        ItemStack mutes = new ItemStack(Material.RED_WOOL);
        ItemStack kicks = new ItemStack(Material.PURPLE_WOOL);

        ItemMeta bansMeta = bans.getItemMeta();
        ItemMeta mutesMeta = mutes.getItemMeta();
        ItemMeta kicksMeta = kicks.getItemMeta();

        bansMeta.setDisplayName(ChatColor.GREEN + "Bans");
        mutesMeta.setDisplayName(ChatColor.RED + "Mutes");
        kicksMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Kicks");

        bans.setItemMeta(bansMeta);
        mutes.setItemMeta(mutesMeta);
        kicks.setItemMeta(kicksMeta);

        inventory.setItem(11, bans);
        inventory.setItem(13, mutes);
        inventory.setItem(15, kicks);


    }

    @Override
    public void handleMenu(InventoryClickEvent e) {


        if (e.getSlot() == 11) {
            new BanListMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
        } else if (e.getSlot() == 13) {
            new MuteListMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
        } else if (e.getSlot() == 15) {
            new KickListMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
        }
    }
}

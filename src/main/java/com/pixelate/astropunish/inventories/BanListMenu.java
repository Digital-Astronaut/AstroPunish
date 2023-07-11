package com.pixelate.astropunish.inventories;

import com.pixelate.astropunish.database.PunishedPlayer;
import com.pixelate.astropunish.database.SQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class BanListMenu extends PaginatedMenu {

    public BanListMenu(PlayerMenuUtility playerMenuUtility, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setMainMenu(new PunishmentsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {

        List<PunishedPlayer> punished = new ArrayList<>();

        for (PunishedPlayer p : SQLGetter.getPunishedPlayers()) {
            if (p.getPlayerBanAmount() > 0) {
                punished.add(p);
            }
        }
        return punished;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof PunishedPlayer p) {

            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

            SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

            meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getPlayerUUID()));

            meta.displayName(Component.text(p.getPlayerName(), NamedTextColor.YELLOW));
            List<Component> lore = new ArrayList<>();

            lore.add(Component.text("Currently banned: " + p.isBanned(), NamedTextColor.LIGHT_PURPLE));
            lore.add(Component.text("Amount of bans: " + p.getPlayerBanAmount(), NamedTextColor.AQUA));

            meta.lore(lore);

            playerHead.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), playerHead);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.RED + "Ban History";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                new BannedPlayerMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), Bukkit.getOfflinePlayer(
                        PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize(
                                e.getCurrentItem().getItemMeta().getDisplayName()))), this).open();
            }
        }
    }
}

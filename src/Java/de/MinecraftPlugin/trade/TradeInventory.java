package de.MinecraftPlugin.trade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


public class TradeInventory implements Listener {

    Inventory inv1;

    Player p1;
    Player p2;

    List<Integer> p1Slots = Arrays.asList(5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44, 50, 53); // rechte Seite
    List<Integer> p2Slots = Arrays.asList(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 38, 39, 45, 48); // linke Seite
    List<Integer> blockedSlots = Arrays.asList(4, 13, 22, 31, 40, 49); // mittlere Wand

    ItemStack confirmButton1;
    ItemStack confirmButton2;
    ItemStack declineButton1;
    ItemStack declineButton2;

    Map<Player, List<ItemStack>> tradeItems = new HashMap<>();

    boolean p1Accepted = false;
    boolean p2Accepted = false;

    public void openTradeInventory(Player p1, Player p2) {
        tradeItems.clear();

        this.p1 = p1;
        this.p2 = p2;

        inv1 = Bukkit.createInventory(null, 54, "Lege hier deine Items für den Handel rein");

        p1.openInventory(inv1);

        confirmButton1 = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta meta = confirmButton1.getItemMeta();
        meta.setDisplayName("§aTrade Akzeptieren");

        declineButton1 = new ItemStack(Material.RED_CONCRETE);
        ItemMeta meta2 = declineButton1.getItemMeta();
        meta2.setDisplayName("Trade Ablehnen");

        confirmButton1.setItemMeta(meta);
        declineButton1.setItemMeta(meta2);

        inv1.setItem(47, confirmButton1);
        inv1.setItem(48, declineButton1);

        confirmButton2 = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta meta3 = confirmButton1.getItemMeta();
        meta3.setDisplayName("§aTrade Akzeptieren");

        declineButton2 = new ItemStack(Material.RED_CONCRETE);
        ItemMeta meta4 = declineButton1.getItemMeta();
        meta4.setDisplayName("Trade Ablehnen");

        confirmButton2.setItemMeta(meta3);
        declineButton2.setItemMeta(meta4);

        inv1.setItem(52, confirmButton2);
        inv1.setItem(53, declineButton2);

        ItemStack mid = new ItemStack(Material.BLUE_CONCRETE);

        inv1.setItem(49, mid);
        inv1.setItem(40, mid);
        inv1.setItem(31, mid);
        inv1.setItem(22, mid);
        inv1.setItem(13, mid);
        inv1.setItem(4, mid);

        p1.openInventory(inv1);
        p2.openInventory(inv1);
    }
    private void giveInventory() {
        for (ItemStack item : tradeItems.get(p2)) {
            p1.getInventory().addItem(item);
        }

        for (ItemStack item : tradeItems.get(p1)) {
            p2.getInventory().addItem(item);
        }

        p1.closeInventory();
        p2.closeInventory();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (player.equals(p1) && p1Slots.contains(slot)) {
            e.setCancelled(false);
        } else if (player.equals(p2) && p2Slots.contains(slot)) {
            e.setCancelled(false);
        }

        if (slot == 47) {
            if (p2Accepted) {
                giveInventory();
            } else {
                p1Accepted = true;
            }
        } else if (slot == 52) {
            if (p1Accepted) {
                giveInventory();
            } else  {
                p2Accepted = true;
            }
        } else if (slot == 48 || slot == 53) {

            for (ItemStack item : tradeItems.get(p1)) {
                p1.getInventory().addItem(item);
            }

            for (ItemStack item : tradeItems.get(p2)) {
                p2.getInventory().addItem(item);
            }

            p1.closeInventory();
            p2.closeInventory();
        }
    }
}

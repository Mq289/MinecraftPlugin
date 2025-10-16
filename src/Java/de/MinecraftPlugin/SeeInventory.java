package de.MinecraftPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SeeInventory implements CommandExecutor, Listener {

    final Main plugin;
    public SeeInventory(Main plugin) {
        this.plugin = plugin;
    }

    Inventory inv;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /invsee <Player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null ) {
            player.sendMessage(ChatColor.RED + "Theres no player with that name.");
            return true;
        }

        inv = Bukkit.createInventory(null, target.getInventory().getSize(), "Inventory from: " + target.getName());
        for (int i = 0; i < target.getInventory().getSize(); i++) {
            ItemStack item = target.getInventory().getItem(i);
            if (item != null) inv.setItem(i, item.clone());
        }
        player.openInventory(inv);

        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().startsWith("Inventory from:")) {
            e.setCancelled(true);
        }
    }
}

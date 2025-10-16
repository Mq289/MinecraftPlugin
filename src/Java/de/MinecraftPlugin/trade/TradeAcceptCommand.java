package de.MinecraftPlugin.trade;

import de.MinecraftPlugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TradeAcceptCommand implements CommandExecutor {

    private final de.MinecraftPlugin.Main plugin;

    public TradeAcceptCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;
        Player accepter = (Player) sender;

        if (args.length < 1) {
            accepter.sendMessage(ChatColor.RED + "Benutzung: /tradeaccept <Spieler>");
            return true;
        }

        Player requester = Bukkit.getPlayer(args[0]);
        if (requester == null) {
            accepter.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht online.");
            return true;
        }

        // Prüfen ob Anfrage existiert
        if (!plugin.tradeRequests.containsKey(accepter.getUniqueId()) ||
                !plugin.tradeRequests.get(accepter.getUniqueId()).equals(requester.getUniqueId())) {
            accepter.sendMessage(ChatColor.RED + "Du hast keine Handelsanfrage von diesem Spieler.");
            return true;
        }

        plugin.tradeRequests.remove(accepter.getUniqueId());

        TradeInventory tmp = new TradeInventory();
        tmp.openTradeInventory(accepter, requester);

        return true;
    }
}

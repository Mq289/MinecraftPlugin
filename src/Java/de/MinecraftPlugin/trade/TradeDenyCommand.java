package de.MinecraftPlugin.trade;

import de.MinecraftPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TradeDenyCommand implements CommandExecutor {

    private final Main plugin;

    public TradeDenyCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;
        Player denier = (Player) sender;

        if (args.length < 1) {
            denier.sendMessage(ChatColor.RED + "Benutzung: /tradedeny <Spieler>");
            return true;
        }

        Player requester = Bukkit.getPlayer(args[0]);
        if (requester == null) {
            denier.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht Online.");
            return true;
        }

        if (!plugin.tradeRequests.containsKey(denier.getUniqueId()) ||
                !plugin.tradeRequests.get(denier.getUniqueId()).equals(requester.getUniqueId())) {
            denier.sendMessage(ChatColor.RED + "Du hast keine Handelsanfrage von diesem Spieler.");
            return true;
        }

        plugin.tradeRequests.remove(denier.getUniqueId());
        denier.sendMessage(ChatColor.YELLOW + "Du hast die Handelsanfrage von " + requester.getName() + " abgelehnt.");
        requester.sendMessage(ChatColor.RED + denier.getName() + " hat deine Handelsanfrage abgelehnt.");

        return true;
    }
}

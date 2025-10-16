package de.MinecraftPlugin.trade;

import de.MinecraftPlugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TradeDelete implements CommandExecutor {

    final Main plugin;

    public TradeDelete(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Nur spieler können diesen Befehl nutzen.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /de.MinecraftPlugin.trade delete");
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("delete")) {
            if (plugin.getTradeRequests().containsKey(player.getName())) {
                plugin.getTradeRequests().remove(player.getName());
                player.sendMessage("Du kannst jetzt eine neue Trade anfrage senden.");
            } else {
                player.sendMessage("Du hast derzeitig keine Trade Anfrage versendet.");
            }
        }

        return true;
    }
}

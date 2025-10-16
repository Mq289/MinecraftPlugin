package de.MinecraftPlugin.trade;

import de.MinecraftPlugin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TradeCommand implements CommandExecutor {

    final Main plugin;

    public TradeCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (plugin.tradeRequests.containsValue(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Du hast bereits eine offene Handelsanfrage gesendet!");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Benutzung: /de.MinecraftPlugin.trade <Spieler>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null|| !target.isOnline()) {
            player.sendMessage(ChatColor.RED + "Spieler " + args[0] + " wurde nicht gefunden oder ist offline.");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(ChatColor.RED + "Du kannst dir selbst keine Anfrage senden!");
            return true;
        }

        player.sendMessage(ChatColor.YELLOW + "Du hast " + target.getName() + " eine Handelsanfrage gesendet.");

        target.sendMessage(ChatColor.GOLD + player.getName() + " möchte mit dir handeln! "
                + ChatColor.GREEN + "[Annehmen] "
                + ChatColor.RED + "[Ablehnen]");

        plugin.tradeRequests.put(target.getUniqueId(), player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (plugin.tradeRequests.containsKey(target.getUniqueId())
                    && plugin.tradeRequests.get(target.getUniqueId()).equals(player.getUniqueId())) {

                plugin.tradeRequests.remove(target.getUniqueId());
                player.sendMessage(ChatColor.RED + "Die Handelsanfrage an " + target.getName() + " ist abgelaufen.");
                target.sendMessage(ChatColor.RED + "Die Handelsanfrage von " + player.getName() + " ist abgelaufen.");
            }
        }, 20L * 60); // 20 Ticks = 1 Sekunde → 60 Sekunden = 1200 Ticks


        return  true;
    }
}

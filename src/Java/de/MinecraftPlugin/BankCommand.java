package de.MinecraftPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    private final Main plugin;

    public BankCommand(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /bank <balance|deposit|withdraw|set|help>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "balance":
                double balance = plugin.getBalance(player);
                player.sendMessage("Balance: " + ChatColor.GREEN + balance);
                break;
            case "deposit":
                if (args.length < 2) {
                    player.sendMessage("§cPlease enter a correct Ammount: /bank deposit <Ammount>");
                    return true;
                }
                try {
                    double deposit = Double.parseDouble(args[1]);
                    plugin.addBalance(player, deposit);
                    player.sendMessage("Deposited: " + ChatColor.GREEN + deposit + " to your Bank Account");
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Amount");
                }
                break;
            case "withdraw":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /bank withdraw <amount>");
                    return true;
                }
                try {
                    double withdraw = Double.parseDouble(args[1]);
                    if (plugin.getBalance(player) >= withdraw) {
                        plugin.addBalance(player, -withdraw);
                        player.sendMessage("§aYou withdraw §e" + withdraw + "§a Coins");
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have enough Coins");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Amount");
                }
                break;
            case "set":
                if (!player.hasPermission("P.admin")) {
                    player.sendMessage("§cYou don't have permission for this command!");
                    return true;
                }
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Usage: /bank set <Player> <amount>");
                }
                Player target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                try {
                    double amount = Double.parseDouble(args[2]);
                    plugin.setBalance(target,amount);
                    player.sendMessage("You added " + amount + " to " + target.getDisplayName() + "  Bank Account");
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid Amount");
                }
                break;
            case "help":
                player.sendMessage("§6===== §eBank Commands §6=====");
                player.sendMessage("§e/bank balance §7- Show your balance");
                player.sendMessage("§e/bank deposit <Amount> §7- deposit");
                player.sendMessage("§e/bank withdraw <Amount> §7- withdraw");
                if (player.hasPermission("P.admin"))
                    player.sendMessage("§e/bank set <Player> <Amount> §7- set the balance of the target Player ");
                break;

            default:
                player.sendMessage("§Unknown Command. Use: /bank help");
                break;
        }

        return true;
    }
}

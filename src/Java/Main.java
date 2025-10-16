import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Main extends JavaPlugin {

    private File bankFile;
    private FileConfiguration bankConfig;

    @Override
    public void onEnable() {
        getLogger().info("MeinPlugin wurde aktiviert!");

        // Bank-Datei laden oder erstellen
        createBankFile();

        // Command registrieren
        getCommand("bank").setExecutor(new BankCommand(this));
    }

    private void createBankFile() {
        bankFile = new File(getDataFolder(), "bank.yml");
        if (!bankFile.exists()) {
            bankFile.getParentFile().mkdirs();
            saveResource("bank.yml", false);
        }
        bankConfig = YamlConfiguration.loadConfiguration(bankFile);
    }

    public FileConfiguration getBankConfig() {
        return bankConfig;
    }

    public void saveBankConfig() {
        try {
            bankConfig.save(bankFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(Player player) {
        return bankConfig.getDouble("Balance");
    }

    public void setBalance(Player player, double amount) {
        UUID uuid = player.getUniqueId();
        bankConfig.set("balances." + uuid,amount);
        saveBankConfig();
    }

    public void addBalance(Player player, double amount) {
        double current = getBalance(player);
        setBalance(player,current + amount);
    }

    @Override
    public void onDisable() {
        saveBankConfig();
        getLogger().info("MeinPlugin wurde deaktiviert!");
    }
}

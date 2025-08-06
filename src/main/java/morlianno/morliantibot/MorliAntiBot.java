package morlianno.morliantibot;

import morlianno.morliantibot.bot.DetectBot;
import morlianno.morliantibot.commands.MABCommand;
import morlianno.morliantibot.managers.PermissionManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public final class MorliAntiBot extends JavaPlugin {
    private FileConfiguration messagesConfig;

    private LuckPerms luckPerms;

    public static final Logger LOGGER = Logger.getLogger("MorliAntiBot");

    private final Set<UUID> alertsEnabled = new HashSet<>();

    public Set<UUID> getAlertsEnabled() {
        return alertsEnabled;
    }

    @Override
    public void onEnable() {
        String version = getDescription().getVersion();

        LOGGER.info("Plugin enabled!");
        LOGGER.info("Plugin made by MorliannoStudio");
        LOGGER.info("Telegram: @MorliannoStudio");
        LOGGER.info("Version: " + version);
        LOGGER.info("------------------------------");
        LOGGER.info("PLUGIN STILL IN BETA! BUGS MAY OCCUR!");
        LOGGER.info("https://github.com/MorliannoStudio/MorliAntiBot/issues");
        LOGGER.info("------------------------------");

        saveDefaultConfig();
        loadMessages();

        RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
            LOGGER.info("[MorliAntiBot] Hooked into LuckPerms.");
        } else {
            LOGGER.warning("[MorliAntiBot] LuckPerms not found! Alerts won't work correctly!");
        }

        PermissionManager permissionManager = luckPerms != null ? new PermissionManager(luckPerms) : null;

        DetectBot detectBot = new DetectBot(getConfig());
        getServer().getPluginManager().registerEvents(detectBot, this);
        getCommand("mab").setExecutor(new MABCommand(this, detectBot, permissionManager));
    }

    @Override
    public void onDisable() {
        LOGGER.info("Plugin disabled!");
    }

    public void loadMessages() {
        String lang = getConfig().getString("language", "ru").toLowerCase(Locale.ROOT);
        String fileName = lang + ".yml";
        File messagesFile = new File(getDataFolder(), fileName);

        if (!messagesFile.exists()) {
            saveResource(fileName, false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        LOGGER.info("[MorliAntiBot] Loaded locale " + fileName);
    }

    public String getMessage(String key) {
        String raw = messagesConfig.getString(key);
        if (raw == null) return "§c[MorliAntiBot] Can't find localization key: " + key;
        return raw.replace('&', '§');
    }
}

package morlianno.morliantibot;

import morlianno.morliantibot.bot.DetectBot;
import morlianno.morliantibot.commands.MABCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class MorliAntiBot extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("MorliAntiBot");

    public String version = getDescription().getVersion();

    @Override
    public void onEnable() {
        LOGGER.info("Плагин включён!");
        LOGGER.info("Сделано студией MorliannoStudio");
        LOGGER.info("Telegram: @MorliannoStudio");
        LOGGER.info("Версия: " + version);

        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("------------------------------");
        LOGGER.info("ПЛАГИН В БЕТА СТАДИИ! ВОЗМОЖНЫ БАГИ!");
        LOGGER.info("ПРОВЕРЯЙТЕ ПЛАГИН НА ОБНОВЛЕНИЯ В БОТЕ/MODRINTH/GITHUB RELEASES!");
        LOGGER.info("СООБЩАЙТЕ О БАГАХ НАМ В БОТА/GITHUB ISSUES!");
        LOGGER.info("------------------------------");
        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("");

        DetectBot detectBot = new DetectBot(getConfig());

        getServer().getPluginManager().registerEvents(detectBot, this);
        getCommand("mab").setExecutor(new MABCommand(this, detectBot));

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        LOGGER.info("Плагин выключен!");
    }
}

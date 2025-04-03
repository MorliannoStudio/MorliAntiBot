package morlianno.morliantibot.bot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static morlianno.morliantibot.MorliAntiBot.LOGGER;

import java.util.List;

public class DetectBot implements Listener {
    private FileConfiguration config;

    public DetectBot(FileConfiguration config) {
        this.config = config;
    }

    public void reloadConfig(FileConfiguration newConfig) {
        this.config = newConfig;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName().toLowerCase();
        List<String> disallowedContains = config.getStringList("disallowed-contains");

        for (String disallowed : disallowedContains) {
            if (playerName.contains(disallowed)) {
                String punishment = config.getString("punishment");
                Integer punishmentDuration = config.contains("punishment-duration") ? config.getInt("punishment-duration") : null;
                String punishmentReason = config.contains("punishment-reason") ? config.getString("punishment-reason") : null;

                if (punishmentDuration == null) {
                    LOGGER.severe("§cОшибка: В config.yml не указан параметр punishment-duration! Боты не будут наказаны!");
                }

                if (punishmentReason == null) {
                    LOGGER.severe("§cОшибка: В config.yml не указан параметр punishment-reason! Боты не будут наказаны!");
                }

                if (punishmentDuration == null || punishmentReason == null) {
                    return;
                }

                if ("tempban".equalsIgnoreCase(punishment)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + playerName + " " + punishmentDuration + "m " + punishmentReason);
                    LOGGER.info("§eБот " + playerName + " попытался зайти на сервер, но был забанен на " + punishmentDuration + " минут");
                } else if ("tempbanip".equalsIgnoreCase(punishment)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempbanip " + playerName + " " + punishmentDuration + "m " + punishmentReason);
                    LOGGER.info("§eБот " + playerName + " попытался зайти на сервер, но был забанен по IP на " + punishmentDuration + " минут");
                } else if ("ban".equalsIgnoreCase(punishment)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + playerName + " " + punishmentReason);
                    LOGGER.info("§eБот " + playerName + " попытался зайти на сервер, но был забанен навсегда");
                } else if ("banip".equalsIgnoreCase(punishment)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "banip " + playerName + " " + punishmentReason);
                    LOGGER.info("§eБот " + playerName + " попытался зайти на сервер, но был забанен по IP навсегда");
                } else if ("deny".equalsIgnoreCase(punishment)) {
                    Player player = event.getPlayer();
                    player.kickPlayer(punishmentReason);
                    LOGGER.info("§eБот " + playerName + " попытался зайти на сервер, но был кикнут");
                } else {
                    LOGGER.info("§eБот " + playerName + " зашёл на сервер, наказания не было, так как в config.yml указан неверный тип наказания.");
                }
                break;
            }
        }
    }
}

package morlianno.morliantibot.bot;

import morlianno.morliantibot.managers.AlertManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
        Player player = event.getPlayer();
        String playerName = player.getName().toLowerCase();
        List<String> disallowedContains = config.getStringList("disallowed-contains");

        for (String disallowed : disallowedContains) {
            if (playerName.contains(disallowed.toLowerCase())) {
                String punishment = config.getString("punishment", "");
                int punishmentDuration = config.getInt("punishment-duration", -1);
                String punishmentReason = config.getString("punishment-reason", "");

                if (punishmentDuration == -1 || punishmentReason.isEmpty()) {
                    if (punishmentDuration == -1) {
                        LOGGER.severe("§cОшибка: В config.yml не указан параметр punishment-duration! Боты не будут наказаны!");
                    }
                    if (punishmentReason.isEmpty()) {
                        LOGGER.severe("§cОшибка: В config.yml не указан параметр punishment-reason! Боты не будут наказаны!");
                    }
                    return;
                }

                switch (punishment.toLowerCase()) {
                    case "tempban":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + playerName + " " + punishmentDuration + "m " + punishmentReason);
                        LOGGER.info("§eБот " + player.getName() + " забанен временно на " + punishmentDuration + " минут");
                        AlertManager.tempbanned(player.getName(), punishmentDuration);
                        break;

                    case "tempbanip":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempbanip " + playerName + " " + punishmentDuration + "m " + punishmentReason);
                        LOGGER.info("§eБот " + player.getName() + " забанен по IP временно на " + punishmentDuration + " минут");
                        AlertManager.tempbannedip(player.getName(), punishmentDuration);
                        break;

                    case "ban":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + playerName + " " + punishmentReason);
                        LOGGER.info("§eБот " + player.getName() + " забанен навсегда");
                        AlertManager.banned(player.getName());
                        break;

                    case "banip":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "banip " + playerName + " " + punishmentReason);
                        LOGGER.info("§eБот " + player.getName() + " забанен по IP навсегда");
                        AlertManager.bannedip(player.getName());
                        break;

                    case "deny":
                        player.kickPlayer(punishmentReason);
                        LOGGER.info("§eБот " + player.getName() + " кикнут");
                        AlertManager.kicked(player.getName());
                        break;

                    default:
                        LOGGER.info("§eБот " + player.getName() + " зашёл на сервер, наказание не применено — неверный тип punishment.");
                        AlertManager.noAction(player.getName());
                }
                break;
            }
        }
    }
}

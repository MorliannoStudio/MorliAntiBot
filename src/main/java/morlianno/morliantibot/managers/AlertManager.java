package morlianno.morliantibot.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AlertManager {

    public static void tempbanned(String playerName, int punishmentDuration) {
        sendPunishmentAlert(playerName, "§c[!] Бот " + playerName + " попытался войти на сервер, но  временно забанен на " + punishmentDuration + " минут.");
    }

    public static void tempbannedip(String playerName, int punishmentDuration) {
        sendPunishmentAlert(playerName, "§c[!] Бот " + playerName + " попытался войти на сервер, но был временно забанен по IP на " + punishmentDuration + " минут.");
    }

    public static void banned(String playerName) {
        sendPunishmentAlert(playerName, "§c[!] Бот " + playerName + " попытался войти на сервер, но был забанен навсегда.");
    }

    public static void bannedip(String playerName) {
        sendPunishmentAlert(playerName, "§c[!] Бот " + playerName + " попытался войти на сервер, но был забанен по IP навсегда.");
    }

    public static void kicked(String playerName) {
        sendPunishmentAlert(playerName, "§c[!] Бот " + playerName + " попытался войти на сервер, но был кикнут с сервера.");
    }

    public static void noAction(String playerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("morlianno.antibot.alert")) {
                player.sendMessage("§c[!] Бот " + playerName + " зашел на сервер, но наказания не было, так как в config.yml указан неверный тип наказания.");
            }
        }
    }

    private static void sendPunishmentAlert(String playerName, String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("morlianno.antibot.alert")) {
                player.sendMessage(message);
            }
        }
    }
}


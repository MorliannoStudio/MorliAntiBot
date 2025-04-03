package morlianno.morliantibot.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import morlianno.morliantibot.bot.DetectBot;

public class MABCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final DetectBot detectBot;

    public MABCommand(JavaPlugin plugin, DetectBot detectBot) {
        this.plugin = plugin;
        this.detectBot = detectBot;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            detectBot.reloadConfig(plugin.getConfig());
            sender.sendMessage("[MorliAntiBot] §aКонфиг перезагружен.");

            FileConfiguration config = plugin.getConfig();

            if (!config.contains("punishment") || config.getString("punishment").isEmpty()) {
                sender.sendMessage("[MorliAntiBot] §cОшибка: В config.yml не указан параметр punishment! Боты не будут наказаны!");
            }

            int punishmentDuration = config.getInt("punishment-duration", -1);
            if (punishmentDuration == -1 && !config.getString("punishment").equalsIgnoreCase("ban") && !config.getString("punishment").equalsIgnoreCase("banip") && !config.getString("punishment").equalsIgnoreCase("deny")) {
                sender.sendMessage("[MorliAntiBot] §cОшибка: В config.yml не указан параметр punishment-duration! Боты не будут наказаны!");
            }

            if (!config.contains("punishment-reason") || config.getString("punishment-reason").isEmpty()) {
                sender.sendMessage("[MorliAntiBot] §cОшибка: В config.yml не указан параметр punishment-reason! Боты не будут наказаны!");
            }
            return true;
        } else {
            sender.sendMessage("[MorliAntiBot] §cИспользование: /mab reload");
            return false;
        }
    }
}

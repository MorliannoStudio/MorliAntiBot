package morlianno.morliantibot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import morlianno.morliantibot.bot.DetectBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MABCommand implements CommandExecutor, TabCompleter {
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
        } else if (args.length == 1 && args[0].equalsIgnoreCase("alerts")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("[MorliAntiBot] §cЭту команду может использовать только игрок.");
                return true;
            }

            Player player = (Player) sender;
            String permission = "morlianno.antibot.alert";
            String commandToRun;

            if (player.hasPermission(permission)) {
                commandToRun = "lp user " + player.getName() + " permission set " + permission + " false";
                player.sendMessage("[MorliAntiBot] §cОповещения отключены.");
            } else {
                commandToRun = "lp user " + player.getName() + " permission set " + permission + " true";
                player.sendMessage("[MorliAntiBot] §aОповещения включены.");
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToRun);
            return true;
        } else {
            sender.sendMessage("[MorliAntiBot] §cИспользование: /mab reload | alerts");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "alerts");
        }
        return new ArrayList<>();
    }
}

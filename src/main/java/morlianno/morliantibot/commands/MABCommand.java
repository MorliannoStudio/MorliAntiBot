package morlianno.morliantibot.commands;

import morlianno.morliantibot.MorliAntiBot;
import morlianno.morliantibot.bot.DetectBot;
import morlianno.morliantibot.managers.PermissionManager;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.Objects;
import java.util.*;

public class MABCommand implements CommandExecutor, TabCompleter {
    private final MorliAntiBot plugin;
    private final DetectBot detectBot;
    private final PermissionManager permissionManager;

    public MABCommand(MorliAntiBot plugin, DetectBot detectBot, PermissionManager permissionManager) {
        this.plugin = plugin;
        this.detectBot = detectBot;
        this.permissionManager = permissionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String sub = args[0].toLowerCase();

            switch (sub) {
                case "reload":
                    plugin.reloadConfig();
                    plugin.loadMessages();
                    detectBot.reloadConfig(plugin.getConfig());
                    FileConfiguration config = plugin.getConfig();

                    sender.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("config-reloaded"));

                    if (!config.contains("punishment") || Objects.requireNonNull(config.getString("punishment")).isEmpty()) {
                        sender.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("no-punishment"));
                    }

                    if (config.getInt("punishment-duration", -1) == -1
                            && !config.getString("punishment", "").equalsIgnoreCase("ban")
                            && !config.getString("punishment", "").equalsIgnoreCase("banip")
                            && !config.getString("punishment", "").equalsIgnoreCase("deny")) {
                        sender.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("no-punishment-duration"));
                    }

                    if (!config.contains("punishment-reason") || config.getString("punishment-reason").isEmpty()) {
                        sender.sendMessage(plugin.getMessage("no-punishment-reason"));
                    }

                    return true;

                case "alerts":
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(plugin.getMessage("usedbyconsole"));
                        return true;
                    }

                    Player player = (Player) sender;

                    if (permissionManager == null) {
                        player.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("luckperms-not-found"));
                        return true;
                    }

                    String permission = "morlianno.antibot.alert";
                    Set<UUID> alertsEnabled = plugin.getAlertsEnabled();

                    if (!alertsEnabled.contains(player.getUniqueId())) {
                        alertsEnabled.add(player.getUniqueId());
                        permissionManager.addPermission(player.getUniqueId(), permission);
                        player.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("alerts-enabled"));
                    } else {
                        alertsEnabled.remove(player.getUniqueId());
                        permissionManager.removePermission(player.getUniqueId(), permission);
                        player.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("alerts-disabled"));
                    }
                    return true;

                case "antiraid":
                    sender.sendMessage(plugin.getMessage("prefix"));
                    return true;
            }
        }

        sender.sendMessage(plugin.getMessage("prefix") + plugin.getMessage("usage"));
        sender.sendMessage("/mab reload - " + plugin.getMessage("reload-usage"));
        sender.sendMessage("/mab alerts - " + plugin.getMessage("alerts-usage"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "alerts");
        }
        return new ArrayList<>();
    }
}

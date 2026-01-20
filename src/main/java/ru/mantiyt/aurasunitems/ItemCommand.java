package ru.mantiyt.aurasunitems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand implements CommandExecutor {

    private final AuraSunItems plugin;

    public ItemCommand(AuraSunItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "helmet":
            case "give":
                handleGiveHelmet(sender, args);
                break;
            case "boots":
                handleGiveBoots(sender, args);
                break;
            case "reload":
                handleReload(sender);
                break;
            default:
                sendUsage(sender);
                break;
        }

        return true;
    }

    private void handleGiveHelmet(CommandSender sender, String[] args) {
        if (!sender.hasPermission("holysunitems.give")) {
            sendMessage(sender, "no-permission");
            return;
        }

        Player target = getTarget(sender, args);
        if (target == null) return;

        giveHelmet(target);

        if (target.equals(sender)) {
            sendMessage(sender, "received-helmet");
        } else {
            sendMessage(sender, "helmet-given", "%player%", target.getName());
        }
    }

    private void giveHelmet(Player player) {
        ItemStack helmet = plugin.getItemManager().getSunHelmet();
        if (helmet == null) {
            return;
        }
        giveItem(player, helmet);
    }

    private void handleGiveBoots(CommandSender sender, String[] args) {
        if (!sender.hasPermission("holysunitems.give")) {
            sendMessage(sender, "no-permission");
            return;
        }

        Player target = getTarget(sender, args);
        if (target == null) return;

        giveBoots(target);

        if (target.equals(sender)) {
            sendMessage(sender, "received-boots");
        } else {
            sendMessage(sender, "boots-given", "%player%", target.getName());
        }
    }

    private void giveBoots(Player player) {
        ItemStack boots = plugin.getItemManager().getSunBoots();
        if (boots == null) {
            return;
        }
        giveItem(player, boots);
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("holysunitems.reload")) {
            sendMessage(sender, "no-permission");
            return;
        }

        plugin.reloadConfig();
        plugin.getItemManager().reload();
        sendMessage(sender, "reloaded-config");
    }

    private Player getTarget(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sendMessage(sender, "player-only");
                return null;
            }
            return (Player) sender;
        }

        String targetName = args[1];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sendMessage(sender, "player-not-found", "%player%", targetName);
            return null;
        }

        return target;
    }

    private void giveItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    private void sendUsage(CommandSender sender) {
        sendMessage(sender, "usage");
    }

    private void sendMessage(CommandSender sender, String configPath, String... replacements) {
        String message = plugin.getConfig().getString("messages." + configPath, "");
        if (message.isEmpty()) return;

        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }

        sender.sendMessage(ColorUtil.translate(message));
    }
}
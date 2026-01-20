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
            case "give":
                handleGive(sender, args);
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

    private void handleGive(CommandSender sender, String[] args) {
        if (!sender.hasPermission("aurasunitems.admin")) {
            sendMessage(sender, "no-permission");
            return;
        }

        if (args.length < 2) {
            sendMessage(sender, "usage-give");
            return;
        }

        String itemType = args[1].toLowerCase();
        ItemStack item = null;
        String receivedKey = null;
        String givenKey = null;

        if (itemType.equals("helmet")) {
            item = plugin.getItemManager().getSunHelmet();
            receivedKey = "received-helmet";
            givenKey = "helmet-given";
        } else if (itemType.equals("boots")) {
            item = plugin.getItemManager().getSunBoots();
            receivedKey = "received-boots";
            givenKey = "boots-given";
        } else {
            sendMessage(sender, "invalid-item");
            return;
        }

        if (item == null) {
            sendMessage(sender, "item-not-configured");
            return;
        }

        Player target;
        if (args.length >= 3) {
            target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sendMessage(sender, "player-not-found", "%player%", args[2]);
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                sendMessage(sender, "player-only");
                return;
            }
            target = (Player) sender;
        }

        giveItem(target, item.clone());

        if (target.equals(sender)) {
            sendMessage(sender, receivedKey);
        } else {
            sendMessage(sender, givenKey, "%player%", target.getName());
        }
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("aurasunitems.admin")) {
            sendMessage(sender, "no-permission");
            return;
        }

        plugin.reloadConfig();
        plugin.getItemManager().reload();
        sendMessage(sender, "reloaded-config");
    }

    private void giveItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ColorUtil.translate("AuraSunItems"));
        sender.sendMessage(ColorUtil.translate("&f/sunitems give <helmet|boots> [игрок] &7— выдать предмет"));
        sender.sendMessage(ColorUtil.translate("&f/sunitems reload &7— перезагрузить конфиг"));
    }

    private void sendMessage(CommandSender sender, String configPath, String... replacements) {
        String message = plugin.getConfig().getString("messages." + configPath, "");
        if (message == null || message.isEmpty()) {
            switch (configPath) {
                case "usage-give":
                    message = "&cИспользование: /sunitems give <helmet|boots> [игрок]";
                    break;
                case "invalid-item":
                    message = "&cПредмет не найден! Используй: helmet или boots";
                    break;
                case "item-not-configured":
                    message = "&cПредмет не настроен в конфиге!";
                    break;
                default:
                    return;
            }
        }

        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }

        sender.sendMessage(ColorUtil.translate(message));
    }
}
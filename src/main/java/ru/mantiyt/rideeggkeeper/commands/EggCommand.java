package ru.mantiyt.rideeggkeeper.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.mantiyt.rideeggkeeper.RideEggKeeper;
import ru.mantiyt.rideeggkeeper.menu.Menu;
import java.util.ArrayList;
import java.util.List;

public class EggCommand implements CommandExecutor, TabCompleter {
    private final Menu menu = new Menu();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Данную команду может писать только игрок!");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("eggkeeper")) {
            menu.openMenu(player);
            return true;
        }

        if (command.getName().equalsIgnoreCase("rideeggkeeper")) {
            if (!player.hasPermission("rideeggkeeper.admin")) {
                return true;
            }

            if (args.length == 0) {
                sendHelp(sender);
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "open":
                    menu.openMenu(player);
                    return true;
                case "give":
                    return handleGive(sender, args);
                default:
                    sendHelp(sender);
                    return true;
            }
        }

        menu.openMenu(player);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(" ");
        sender.sendMessage("/rideeggkeeper open - открыть меню");
        sender.sendMessage("/rideeggkeeper give [ник] [тип (oskol, ender)] [кол-во]");
        sender.sendMessage(" ");
    }

    private boolean handleGive(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("/rideeggkeeper give [ник] [тип (oskol, ender)] [кол-во]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("Игрок не найден!");
            return true;
        }

        int count;
        try {
            count = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Количество должно быть числом!");
            return true;
        }

        ItemStack item;
        String typeName;
        switch (args[2].toLowerCase()) {
            case "oskol":
                item = RideEggKeeper.getInstance().getItemEgg().getOskolEgg(count);
                typeName = "осколочных яиц";
                break;
            case "ender":
                item = RideEggKeeper.getInstance().getItemEgg().getEnderEgg(count);
                typeName = "эндер яиц";
                break;
            default:
                sender.sendMessage("Тип может быть: oskol, ender");
                return true;
        }

        addItemToInventory(target.getInventory(), item, target.getLocation());
        sender.sendMessage("Выдал " + count + " " + typeName + " для " + target.getName());
        return true;
    }

    private void addItemToInventory(Inventory inventory, ItemStack itemStack, Location location) {
        int remaining = itemStack.getAmount();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                inventory.setItem(i, itemStack.clone());
                return;
            }

            if (item.isSimilar(itemStack)) {
                int space = item.getMaxStackSize() - item.getAmount();
                if (space > 0) {
                    int add = Math.min(remaining, space);
                    item.setAmount(item.getAmount() + add);
                    remaining -= add;
                    if (remaining <= 0) return;
                }
            }
        }

        final int finalRemaining = remaining;
        Bukkit.getScheduler().runTask(RideEggKeeper.getInstance(), () -> {
            ItemStack drop = itemStack.clone();
            drop.setAmount(finalRemaining);
            location.getWorld().dropItemNaturally(location, drop);
        });
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!command.getName().equalsIgnoreCase("rideeggkeeper") || !sender.hasPermission("rideeggkeeper.admin")) {
            return completions;
        }

        switch (args.length) {
            case 1:
                completions.add("open");
                completions.add("give");
                break;
            case 2:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
                break;
            case 3:
                completions.add("oskol");
                completions.add("ender");
                break;
            case 4:
                for (int i = 1; i <= 64; i++) {
                    completions.add(String.valueOf(i));
                }
                break;
        }

        return completions;
    }
}
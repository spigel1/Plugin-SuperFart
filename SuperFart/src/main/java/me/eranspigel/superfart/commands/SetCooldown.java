package me.eranspigel.superfart.commands;

import me.eranspigel.superfart.SuperFart;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetCooldown implements CommandExecutor {

    private final SuperFart plugin;

    public SetCooldown(SuperFart plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length > 0){
            int newCoolodown = Integer.parseInt(args[0]);
            this.plugin.getConfig().set("cooldown-in-milliseconds", newCoolodown);
            this.plugin.saveConfig();

            sender.sendMessage("cooldown changed to: " + ChatColor.RED  + newCoolodown + " seconds");
        }else{
            sender.sendMessage("You must provide a number!");
        }

        return true;
    }
}

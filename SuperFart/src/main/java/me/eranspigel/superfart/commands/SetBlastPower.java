package me.eranspigel.superfart.commands;

import me.eranspigel.superfart.SuperFart;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBlastPower implements CommandExecutor {

    private final SuperFart plugin;

    public SetBlastPower(SuperFart plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length > 0){
            int newBlastPower = Integer.parseInt(args[0]);
            this.plugin.getConfig().set("blast-power", newBlastPower);
            this.plugin.saveConfig();

            sender.sendMessage("Blast power changed to: " + ChatColor.RED  + newBlastPower);
        }else{
            sender.sendMessage("You must provide a number!");
        }

        return true;
    }
}

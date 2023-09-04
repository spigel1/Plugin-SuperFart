package me.eranspigel.superfart;

import me.eranspigel.superfart.commands.FartCommand;
import me.eranspigel.superfart.commands.SetBlastPower;
import me.eranspigel.superfart.commands.SetCooldown;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.common.returnsreceiver.qual.This;

public final class SuperFart extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig(); //saves the default config.yml
        getCommand("superfart").setExecutor(new FartCommand(this));
        getCommand("setblastpower").setExecutor(new SetBlastPower(this));
        getCommand("setcooldown").setExecutor(new SetCooldown(this));


    }

}
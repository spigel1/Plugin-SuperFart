package me.eranspigel.superfart.commands;

import me.eranspigel.superfart.SuperFart;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Particle;


import java.util.HashMap;
import java.util.UUID;

public class FartCommand implements CommandExecutor {
    private final SuperFart plugin;


    //The key is the Player, and the long is the epoch time of the last time they ran the command
    private final HashMap<UUID, Long> cooldown;

    public FartCommand(SuperFart plugin) {
        this.plugin = plugin;
        this.cooldown = new HashMap<>();
    }
    private void spawnParticlesInExplosionRange(Location center, double radius, Particle.DustOptions dustOptions) {
        for (double x = center.getX() - radius; x <= center.getX() + radius; x++) {
            for (double y = center.getY() - radius; y <= center.getY() + radius; y++) {
                for (double z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
                    Location location = new Location(center.getWorld(), x, y, z);
                    if (location.distanceSquared(center) <= radius * radius) {
                        // Spawn particles at 'location' with the specified color.
                        location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, dustOptions);                    }
                }
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //if the sender is not a player, return false
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        long cooldownTime = Long.parseLong(this.plugin.getConfig().getString("cooldown-in-milliseconds")) * 1000;

        //if the player is not in the cooldown map, add them to the map and run the code
        //if the player is in the cooldown map, check if the cooldown is over(10 secs)
        if (!cooldown.containsKey(player.getUniqueId()) || System.currentTimeMillis() - cooldown.get(player.getUniqueId()) > cooldownTime) {
            cooldown.put(player.getUniqueId(), System.currentTimeMillis());

            player.sendMessage("You farted! Oopsies!");

            // Specify the brown particle color.
            Color brownColor = Color.fromRGB(139, 69, 19);

            // Create a Particle.DustOptions object with the brown color.
            Particle.DustOptions dustOptions = new Particle.DustOptions(brownColor, 1);

            float blastpower = Float.parseFloat(this.plugin.getConfig().getString("blast-power"));

            player.setInvulnerable(true);

            Location explosionLocation = player.getLocation();
            player.getWorld().createExplosion(player.getLocation(), blastpower, false, false); // The last two arguments are 'fire' and 'breakBlocks';
            spawnParticlesInExplosionRange(explosionLocation, blastpower, dustOptions);

            player.setInvulnerable(false);

        } else {

            //if the cooldown is not over, send the player a message
            player.sendMessage("You can't fart again for another " + (cooldownTime - (System.currentTimeMillis() - cooldown.get(player.getUniqueId()))) / 1000 + " seconds!");
        }

        return true;
    }


}
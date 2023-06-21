package dev.didnt.wmal.events;

import dev.didnt.wmal.Main;
import dev.didnt.wmal.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class onPlayerInteraction implements Listener {
    Main plugin;
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ConfigUtil config = new ConfigUtil(plugin, "config.yml");
        ConfigUtil lang = new ConfigUtil(plugin, "lang.yml");
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && b != null && b.getType() == Material.WALL_SIGN) {
            Sign s = (Sign) b.getState();
            String[] l = s.getLines();

            if (l[0].equalsIgnoreCase("[Teleport]")) {
                String n = l[1];

                if (p.hasPermission(config.getString(n + ".permission-required"))) {
                    try {
                        //tp
                        int x = Integer.parseInt(config.getString(n + ".x"));
                        int y = Integer.parseInt(config.getString(n + ".y"));
                        int z = Integer.parseInt(config.getString(n + ".z"));
                        float yaw = Float.parseFloat(config.getString(n + ".yaw"));
                        float pitch = Float.parseFloat(config.getString(n + ".pitch"));
                        World world = Bukkit.getWorld(config.getString(n + ".world"));

                        Location location = new Location( world, x, y, z, yaw, pitch);
                        p.teleport(location);
                        p.sendMessage(lang.getString("successful"));

                        //command
                        if(config.getString(n + ".console-command") != ""){
                            p.performCommand(config.getString(n + ".command")
                              .replace("%player%", p.getName()));
                        }

                        //console command
                        if(config.getString(n + ".console-command") != ""){
                            ConsoleCommandSender c = Bukkit.getServer().getConsoleSender();
                            Bukkit.dispatchCommand(c,config.getString(n + ".console-command")
                              .replace("%player%", p.getName()));
                        }
                    } catch (Exception er) {
                        p.sendMessage(lang.getString("error"));
                    }
                } else {
                    p.sendMessage(lang.getString("no-permission"));
                }
            }
        }
    }
}

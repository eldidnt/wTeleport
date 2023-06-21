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
    Main plugin = Main.getInstance();
    ConfigUtil config = new ConfigUtil(plugin, "config.yml");
    ConfigUtil lang = new ConfigUtil(plugin, "lang.yml");
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
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
                        double x = Double.parseDouble(config.getString("Signs." + n + ".x"));
                        double y = Double.parseDouble(config.getString("Signs." + n + ".y"));
                        double z = Double.parseDouble(config.getString("Signs." + n + ".z"));
                        float yaw = Float.parseFloat(config.getString("Signs." + n + ".yaw"));
                        float pitch = Float.parseFloat(config.getString("Signs." + n + ".pitch"));
                        World world = Bukkit.getWorld(config.getString("Signs." + n + ".world"));
                        Location location = new Location(world, x, y, z, yaw, pitch);

                        p.teleport(location);
                        p.sendMessage(lang.getString("successful"));
                        //command
                        if(config.getString("Signs." + n + ".command") != "" || config.getString("Signs." + n + ".command") != null
                           || !config.getString("Signs." + n + ".command").isEmpty()){
                            p.performCommand(config.getString("Signs." + n + ".command")
                              .replace("%player%", p.getName()));
                        }
                        //console command
                        if(config.getString("Signs." + n + ".console-command") != "" ||
                           config.getString("Signs." + n + ".console-command") != null ||
                           !config.getString("Signs." + n + ".console-command").isEmpty()){
                            ConsoleCommandSender c = Bukkit.getServer().getConsoleSender();
                            Bukkit.dispatchCommand(c,config.getString("Signs." + n + ".console-command")
                              .replace("%player%", p.getName()));
                        }
                    } catch (Exception er) {
                        p.sendMessage(lang.getString("error"));
                        Bukkit.getServer().getConsoleSender().sendMessage(er.getMessage());
                        Bukkit.getServer().getConsoleSender().sendMessage(er.getLocalizedMessage());
                    }
                } else {
                    p.sendMessage(lang.getString("no-permission"));
                }
            }
        }
    }
}

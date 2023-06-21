package dev.didnt.wmal.commands;

import dev.didnt.wmal.Main;
import dev.didnt.wmal.utils.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class wTeleport implements CommandExecutor {
    Main plugin;
    public wTeleport(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        Player p = (Player) s;
        FileConfiguration config = plugin.getConfig();
        ConfigUtil lang = new ConfigUtil(plugin, "lang.yml");

        if (!(s instanceof Player)) {
            s.sendMessage(lang.getString("no-console"));
            return false;
        } else if (!s.hasPermission("wteleport.admin")) {
            s.sendMessage(lang.getString("no-permission"));
            return false;
        }

        if(args.length == 1){
            Location loc = ((Player) s).getLocation();
            config.set(args[0] + ".x", loc.getX());
            config.set(args[0] + ".y", loc.getY());
            config.set(args[0] + ".z", loc.getZ());
            config.set(args[0] + ".yaw", loc.getYaw());
            config.set(args[0] + ".pitch", loc.getPitch());
            config.set(args[0] + ".world", loc.getWorld().getName());
            config.set(args[0] + ".permission-required", "wteleport.use");
            config.set(args[0] + ".command", "");
            config.set(args[0]+ ".console-command", "");

            plugin.saveConfig();
            return true;
        }
        lang.getStringList("usage").forEach(s::sendMessage);
        return false;
    }
}

package dev.didnt.wmal.commands;

import dev.didnt.wmal.Main;
import dev.didnt.wmal.utils.ConfigUtil;
import dev.didnt.wmal.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class wTeleport implements CommandExecutor {
    Main plugin;
    public wTeleport(Main plugin) {
        this.plugin = plugin;
    }
    TextUtil color = new TextUtil();

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        Player p = (Player) s;
        FileConfiguration config = plugin.getConfig();
        ConfigUtil lang = new ConfigUtil(plugin, "lang.yml");

        if (!(s instanceof Player)) {
            Bukkit.getServer().getConsoleSender().sendMessage(lang.getString("no-console"));
            return false;
        } else if (!s.hasPermission("wteleport.admin")) {
            p.sendMessage(lang.getString("no-permission"));
            return false;
        }

        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("set")){
                if(args.length == 2){
                    Location loc = p.getLocation();
                    config.set("Signs." + args[1] + ".x", loc.getX());
                    config.set("Signs." + args[1] + ".y", loc.getY());
                    config.set("Signs." + args[1] + ".z", loc.getZ());
                    config.set("Signs." + args[1] + ".yaw", loc.getYaw());
                    config.set("Signs." + args[1] + ".pitch", loc.getPitch());
                    config.set("Signs." + args[1] + ".world", loc.getWorld().getName());
                    config.set("Signs." + args[1] + ".permission-required", "wteleport.use");
                    config.set("Signs." + args[1] + ".command", "");
                    config.set("Signs." + args[1]+ ".console-command", "");

                    plugin.saveConfig();
                    p.sendMessage(lang.getString("set-successfully"));
                    return true;
                }else{
                    lang.getStringList("set-usage").forEach(p::sendMessage);
                    return false;
                }
            }else if(args[0].equalsIgnoreCase("deleteall")) {
                p.sendMessage("paso 0");
                ConfigurationSection s1 = config.getConfigurationSection("Signs");
                p.sendMessage("paso 1");
                if (s1 != null) {
                    for (String sign : s1.getKeys(false)) {
                        s1.set(sign, null);
                        p.sendMessage("paso 2");
                    }
                    config.set("signs", null);
                    p.sendMessage("paso 3");
                    plugin.saveConfig();
                    p.sendMessage("paso 4");
                    p.sendMessage(lang.getString("delete-all"));
                    return true;
                } else {
                    p.sendMessage(lang.getString("no-exists"));
                    return false;
                }
            }else if(args[0].equalsIgnoreCase("list")){
                p.sendMessage("paso 0");
                ConfigurationSection s1 = config.getConfigurationSection("Signs");
                p.sendMessage("paso 1");
                if (s1 != null) {
                    p.sendMessage(lang.getString("available"));
                    p.sendMessage("paso 2");
                    for (String sign : s1.getKeys(false)) {
                        p.sendMessage("paso 3");
                        p.sendMessage(color.translate("&e- &f" + sign));
                    }
                    p.sendMessage("paso 4");
                    return true;
                } else {
                    p.sendMessage(lang.getString("no-available"));
                    return false;
                }
            }else if(args[0].equalsIgnoreCase("delete")){
                if(args.length == 2){
                    String n = args[1];
                    ConfigurationSection path = config.getConfigurationSection("Signs");
                    if (path != null) {
                        path.set(n, null);
                        config.set("Signs", path);
                        plugin.saveConfig();

                        p.sendMessage(lang.getString("delete").replace("%name%", n));
                        return true;
                    } else {
                        p.sendMessage(lang.getString("no-exists"));
                        return false;
                    }
                }
                lang.getStringList("delete-usage").forEach(p::sendMessage);
                return false;
            }else{
                lang.getStringList("usage").forEach(p::sendMessage);
                return false;
            }

        }
        lang.getStringList("usage").forEach(p::sendMessage);
        return false;
    }
}

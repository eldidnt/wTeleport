package dev.didnt.wmal;

import dev.didnt.wmal.commands.wTeleport;
import dev.didnt.wmal.events.onPlayerInteraction;
import dev.didnt.wmal.utils.ConfigUtil;
import dev.didnt.wmal.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public static Main getInstance() { return instance; }

    private PluginDescriptionFile pl = this.getDescription();
    public ConfigUtil lang = new ConfigUtil(this, "lang.yml");

    public void onEnable(){
        instance = this;
        registerCommands();
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(TextUtil.translate("&cMal te odio hijoeputa ya prendio tu mierda"));
    }
    public void onDisable(){
    }


    public void registerCommands() {
        this.getCommand("wteleport").setExecutor(new wTeleport(this));
    }
    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new onPlayerInteraction(), this);
    }
}
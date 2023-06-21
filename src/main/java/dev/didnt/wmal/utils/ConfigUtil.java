package dev.didnt.wmal.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigUtil extends YamlConfiguration {
    private File file;
    private final JavaPlugin plugin;
    private final String name;
    public ConfigUtil(JavaPlugin plugin, String name) {
        this.file = new File(plugin.getDataFolder(), name);
        this.plugin = plugin;
        this.name = name;

        if(!this.file.exists()) {
            plugin.saveResource(name, false);
        }

        try {
            this.load(this.file);
        } catch(IOException | InvalidConfigurationException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void load() {
        this.file = new File(plugin.getDataFolder(), name);

        if(!this.file.exists()) {
            plugin.saveResource(name, false);
        }
        try {
            this.load(this.file);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path, 0);
    }

    @Override
    public double getDouble(String path) {
        return super.getDouble(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }

    public String getString(String path, boolean ignored) {
        return super.getString(path, null);
    }

    @Override
    public String getString(String path) {

        return TextUtil.translate(super.getString(path, "&bString at path &7'&3" + path + "&7' &bnot found."));
    }


    public void setString(String path, String newValue) {
        this.file = new File(plugin.getDataFolder(), name);
        FileConfiguration f = YamlConfiguration.loadConfiguration(this.file);
        f.set(path, newValue);
        try {
            save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(TextUtil::translate).collect(Collectors.toList());
    }

    public List<String> getStringList(String path, boolean ignored) {
        if (!super.contains(path)) return null;
        return super.getStringList(path).stream().map(TextUtil::translate).collect(Collectors.toList());
    }

    public File getFile() {
        return file;
    }
}
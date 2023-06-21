package dev.didnt.wmal.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.stream.Collectors;

public class TextUtil {
    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> translate(List<String> s) {
        return s.stream().map(TextUtil::translate).collect(Collectors.toList());
    }

    public static void messageToPlayer(Player player, String args){
        player.sendMessage(TextUtil.translate(args));
    }
}
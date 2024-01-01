package ez.iELib.utils.colorUtils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
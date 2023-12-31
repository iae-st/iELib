package ez.iELib;

import ez.iELib.utils.ColorUtils;
import org.bukkit.Bukkit;

public class Logger {
    private static String pfx;

    public Logger(String prefix) {
        pfx = prefix;
    }

    public void log(String message) {
        log(message, false);
    }

    public void log(String message, boolean prefix) {
        String prfx = "";
        if (prefix)
            prfx = pfx;
        Bukkit.getConsoleSender().sendMessage(ColorUtils.color(prfx + " &f" + message));
    }

    public void warn(String message, boolean prefix) {
        log("&e" + message.replaceAll("&[a-zA-Z0-9]", "&e"), prefix);
    }

    public void warn(String message) {
        warn(message, false);
    }

    public void error(String message, boolean prefix) {
        log("&c" + message.replaceAll("&[a-zA-Z0-9]", "&c"), prefix);
    }

    public void error(String message) {
        error(message, false);
    }
}

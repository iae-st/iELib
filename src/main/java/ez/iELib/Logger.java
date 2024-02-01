package ez.iELib;

import ez.iELib.utils.colorUtils.ColorUtils;
import org.bukkit.Bukkit;

public class Logger {
    private static String pfx;

    /**
     * The Logger class is used to log messages to the console with optional prefixes.
     *
     * @param prefix The prefix to be used
     */
    public Logger(String prefix) {
        pfx = prefix;
        log("§asuccessfully hooked into iELib", true);
    }

    /**
     * Logs a message to the console.
     *
     * @param message The message to be logged.
     */
    public void log(String message) {
        log(message, false);
    }

    /**
     * Logs a message to the console.
     *
     * @param message The message to be logged.
     * @param prefix  Specifies whether to include the prefix in the log message.
     */
    public void log(String message, boolean prefix) {
        String prfx = "";
        if (prefix)
            prfx = pfx;
        Bukkit.getConsoleSender().sendMessage(ColorUtils.color(prfx + " &f" + message));
    }

    /**
     * Logs a warning message to the console.
     *
     * @param message The message to be logged.
     * @param prefix  Specifies whether to include the prefix in the log message.
     */
    public void warn(String message, boolean prefix) {
        log("&e" + message.replaceAll("&[a-zA-Z0-9]", "&e"), prefix);
    }

    /**
     * Logs a warning message to the console.
     *
     * @param message The message to be logged.
     */
    public void warn(String message) {
        warn(message, false);
    }

    /**
     * Logs an error message to the console.
     *
     * @param message The error message to be logged.
     * @param prefix  Specifies whether to include the prefix in the log message.
     */
    public void error(String message, boolean prefix) {
        log("&c" + message.replaceAll("&[a-zA-Z0-9]", "&c"), prefix);
    }

    /**
     * Logs an error message to the console.
     *
     * @param message The error message to be logged.
     */
    public void error(String message) {
        error(message, false);
    }
}

package ez.iELib.utils;

import ez.iELib.Logger;
import lombok.extern.java.Log;
import org.bukkit.plugin.Plugin;

public class TimeTracker {
    private long startTime;
    private Plugin plugin;
    private Logger logger;

    public TimeTracker(Plugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void endTime(String initialized) {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String color = "§a";
        if (totalTime > 20000) {
            color = "§c";
        }

        logger.log("§7Initializing §a" + initialized + " §7took " + color + totalTime + "§7 milliseconds", true);
    }

    public void endTimeFullString(String fullString) {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String color = "§a";
        if (totalTime > 20000) {
            color = "§c";
        }

        logger.log(fullString + " §7took " + color + totalTime + "§7 milliseconds", true);
    }

    public String endTimeAsString(String task) {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String color = "§a";
        if (totalTime > 20000) {
            color = "§c";
        }

        return "§a" + plugin.getName() + "§6⊱ §7Task §a" + task + " §7took " + color + totalTime + "§7 milliseconds to complete!";
    }
}

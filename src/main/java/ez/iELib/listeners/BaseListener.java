package ez.iELib.listeners;

import ez.iELib.iELib;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class BaseListener implements Listener {
    private final boolean shouldRegister;

    public BaseListener(boolean shouldRegister) {
        this.shouldRegister = shouldRegister;
        if(shouldRegister) {
            iELib.getPlugin().getServer().getPluginManager().registerEvents(this, iELib.getPlugin());
        }
    }

    public BaseListener(boolean shouldRegister, String pluginRequired) {
        this.shouldRegister = shouldRegister;
        if(shouldRegister && isPluginEnabled(pluginRequired)) {
            iELib.getPlugin().getServer().getPluginManager().registerEvents(this, iELib.getPlugin());
        }
    }


    protected boolean isPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }
}
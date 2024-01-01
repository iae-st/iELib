package ez.iELib;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Optional;

public class iELib {

    @Getter
    protected static Plugin plugin;

    public static void assignPlugin(Plugin plugin) {
        iELib.plugin = plugin;
    }

}


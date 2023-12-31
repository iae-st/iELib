package ez.iELib;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Optional;

public class iELib {

    @Getter
    private static Plugin plugin;

    public iELib(Plugin plugin) {
        iELib.plugin = plugin;
    }

}


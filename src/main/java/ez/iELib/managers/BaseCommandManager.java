package ez.iELib.managers;

import co.aikar.commands.BukkitCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
public abstract class BaseCommandManager {

    protected final BukkitCommandManager cmdManager;

    public BaseCommandManager(Plugin plugin) {
        cmdManager = new BukkitCommandManager(plugin);
        registerCommands();
        createCompletions();
    }

    protected abstract void registerCommands();

    protected abstract void createCompletions();

    protected List<String> sortAndConvertToList(Set<String> set) {
        return List.of(sortSetAlphabetically(set).toArray(new String[0]));
    }

    public static List<String> sortSetAlphabetically(Set<String> set) {
        List<String> list = new ArrayList<>(set);

        Collections.sort(list);

        return list;
    }

}
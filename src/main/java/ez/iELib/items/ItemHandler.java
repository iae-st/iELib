package ez.iELib.items;

import ez.iELib.debug.TypeNotAcceptedException;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.ParameterizedType;

public abstract class ItemHandler<E> {
    private final JavaPlugin plugin;
    @Getter
    private final EventPriority priority;


    public ItemHandler(JavaPlugin plugin, EventPriority priority) {
        this.plugin = plugin;
        this.priority = priority;
        ParameterizedType p = (ParameterizedType) getClass().getGenericSuperclass();
        Class c = (Class) p.getActualTypeArguments()[0];
        if (!Event.class.isAssignableFrom(c)) {
            try {
                throw new TypeNotAcceptedException(Event.class, p.getActualTypeArguments()[0]);
            } catch (TypeNotAcceptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public abstract void onEvent(E event);

    public Plugin getPlugin() {
        return plugin;
    }

    public Class<? extends Event> getEventClass() {
        ParameterizedType p = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<? extends Event>) p.getActualTypeArguments()[0];
    }
}



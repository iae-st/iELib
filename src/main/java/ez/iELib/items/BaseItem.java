package ez.iELib.items;

import ez.iELib.iELib;
import ez.iELib.utils.PersistentDataUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class BaseItem<T extends Event> extends BaseCustomItemStack implements Listener {
    private final ItemStack armorItem;
    private static final Set<BaseItem<?>> registeredListeners = new HashSet<>();

    public BaseItem(String ID, ItemStack itemStack) {
        super(ID, itemStack);
        this.armorItem = itemStack;

        registerListener(this);

        assignItem();
    }

    public static boolean hasArmorEquipped(Player player, BaseItem<?> baseItem) {
        ItemStack itemStack = player.getItemInHand();
        if (!itemStack.hasItemMeta()) return false;
        if (!PersistentDataUtils.hasKey(itemStack, "ID")) return false;
        String ID = (String) PersistentDataUtils.getKey(itemStack, "ID", PersistentDataType.STRING);
        return ID.equals(baseItem.getID());
    }

    @EventHandler
    private void logic(T event) {
        if (preLogic(event)) {
            specificLogic(event);
        }
    }

    private boolean preLogic(T event) {
        try {
            if (event instanceof PlayerInteractEvent playerInteractEvent) {
                return hasArmorEquipped(playerInteractEvent.getPlayer(), this);
            }

            if (event instanceof EntityDamageByBlockEvent entityDamageByBlockEvent) {
                if (!(entityDamageByBlockEvent.getEntity() instanceof Player)) return false;
                return hasArmorEquipped((Player) entityDamageByBlockEvent.getEntity(), this);
            }

            if (event instanceof PlayerMoveEvent playerMoveEvent) {
                return hasArmorEquipped(playerMoveEvent.getPlayer(), this);
            }

        } catch (Exception ignored) {
        }
        return false;
    }

    public abstract void specificLogic(T event);

    public abstract void methodToRun(T event);

    private void registerListener(BaseItem baseItem) {
        if (registeredListeners.contains(baseItem)) {
            return;
        }
        registeredListeners.add(baseItem);

        Listener l = new Listener() {
        };
        EventExecutor executor = (ignored, event) -> {
            baseItem.logic(event);
        };
        iELib.getPlugin().getServer().getPluginManager().registerEvent(baseItem.getEventClass(), l, EventPriority.NORMAL, executor, iELib.getPlugin());
    }

    private Class<? extends Event> getEventClass() {
        ParameterizedType p = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<? extends Event>) p.getActualTypeArguments()[0];
    }
}
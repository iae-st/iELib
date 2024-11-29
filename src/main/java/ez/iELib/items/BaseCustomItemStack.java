package ez.iELib.items;

import ez.iELib.utils.PersistentDataUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.EventExecutor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;


public class BaseCustomItemStack extends ItemStack implements ConfigurationSerializable {

    @Getter
    private static final List<BaseCustomItemStack> allItems = new ArrayList<>();
    @Getter
    private static final HashMap<String, BaseCustomItemStack> customItemIDs = new HashMap<>();

    @Getter
    @NonNull
    private final String ID;
    @Setter
    @Getter
    private boolean isPlaceable;

    public BaseCustomItemStack(@NonNull String ID, @Nonnull ItemStack item, boolean isPlaceable) {
        super(item);
        this.ID = ID;
        this.isPlaceable = isPlaceable;

        PersistentDataUtils.addKey(this, "ID", PersistentDataType.STRING, ID);
    }

    public BaseCustomItemStack(@NonNull String ID, @Nonnull ItemStack item) {
        this(ID, item, false);
    }

    public BaseCustomItemStack(@NonNull String ID, @Nonnull ItemStack itemStack, @Nonnull Consumer<ItemMeta> consumer) {
        this(ID, itemStack);
        ItemMeta im = this.getItemMeta();
        consumer.accept(im);
        this.setItemMeta(im);
    }

    public BaseCustomItemStack(@NonNull String ID, ItemStack itemStack, String name, String... lore) {
        this(ID, itemStack, (im) -> {
            if (name != null) {
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }

            if (lore.length > 0) {
                List<String> lines = new ArrayList<>();

                for (String line : lore) {
                    lines.add(ChatColor.translateAlternateColorCodes('&', line));
                }

                im.setLore(lines);
            }

        });
    }


    public BaseCustomItemStack(@NonNull ItemStack item) {
        super(item);
        this.ID = "";
    }

    public BaseCustomItemStack(@Nonnull ItemStack itemStack, @Nonnull Consumer<ItemMeta> consumer) {
        this(itemStack);
        ItemMeta im = this.getItemMeta();
        consumer.accept(im);
        this.setItemMeta(im);
    }

    public BaseCustomItemStack(ItemStack itemStack, String name, String... lore) {
        this(itemStack, (im) -> {
            if (name != null) {
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            }

            if (lore.length > 0) {
                List<String> lines = new ArrayList<>();

                for (String line : lore) {
                    lines.add(ChatColor.translateAlternateColorCodes('&', line));
                }

                im.setLore(lines);
            }

        });
    }

    public static void assignItem(BaseCustomItemStack customItemStack, boolean isPlaceable) {
        if (customItemStack == null) return;
        customItemStack.setPlaceable(isPlaceable);
        allItems.add(customItemStack);
        customItemIDs.put(customItemStack.getID(), customItemStack);
    }

    public BaseCustomItemStack assignItem() {
        allItems.add(this);
        customItemIDs.put(this.getID(), this);
        return this;
    }


    public BaseCustomItemStack addHandler(ItemHandler handler) {
        Listener l = new Listener() {
        };
        EventExecutor executor = (ignored, event) -> {
            handler.onEvent(event);
        };
        handler.getPlugin().getServer().getPluginManager().registerEvent(handler.getEventClass(), l, handler.getPriority(), executor, handler.getPlugin());
        return this;
    }

    public static List<ItemStack> toItemStackList(List<? extends BaseCustomItemStack> customItems) {
        return new ArrayList<>(customItems);
    }


}

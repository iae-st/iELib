package ez.iELib.items.enchants;


import ez.iELib.iELib;
import ez.iELib.items.BaseCustomItemStack;
import ez.iELib.items.ItemBuilder;
import ez.iELib.utils.PersistentDataUtils;
import ez.iELib.utils.Utils;
import ez.iELib.utils.colorUtils.RGBColor;
import ez.iELib.utils.itemUtils.LoreUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public abstract class BaseEnchant<T extends Event> implements Listener {

    public static HashMap<String, BaseEnchant<?>> enchantNameList = new HashMap<>();
    private final String name;
    @Getter
    private final String[] description;
    private final int maxLevel;
    private final RGBColor color;
    public List<BaseEnchant<?>> enchantList = new ArrayList<>();

    public BaseEnchant(String name, String[] description, int maxLevel, RGBColor color) {
        this.name = name.replace(" ", "_").toUpperCase();
        this.maxLevel = maxLevel;
        this.color = color;
        this.description = description;

        registerListener(this);

        enchantList.add(this);
        enchantNameList.put(this.name, this);
        this.getBook().assignItem();
    }

    public static void apply(ItemStack itemStack, BaseEnchant<?> baseEnchant) {
        if (PersistentDataUtils.hasKey(itemStack, baseEnchant.getName())) {
            int currentLevel = (int) PersistentDataUtils.getKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER);
            if (currentLevel < baseEnchant.maxLevel) {
                PersistentDataUtils.addKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER, currentLevel + 1);
                String targetLore = Utils.formatString(baseEnchant.getName()) + " " + currentLevel;
                String newLore = baseEnchant.getColor().getColor() + Utils.formatString(baseEnchant.getName()) + " " + (currentLevel + 1);
                LoreUtils.updateItemLore(itemStack, targetLore, newLore);
            }
        } else {
            PersistentDataUtils.addKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER, 1);
            String lore = baseEnchant.getColor().getColor() + Utils.formatString(baseEnchant.getName()) + " " + 1;
            LoreUtils.addLore(itemStack, lore);
        }
    }

    public static boolean canApply(ItemStack itemStack, BaseEnchant<?> baseEnchant) {
        if (PersistentDataUtils.hasKey(itemStack, baseEnchant.getName())) {
            int currentLevel = (int) PersistentDataUtils.getKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER);
            return currentLevel < baseEnchant.maxLevel;
        } else {
            return true;
        }
    }

    public static void setEnchantLevel(ItemStack itemStack, BaseEnchant<?> baseEnchant, int level) {
        if (PersistentDataUtils.hasKey(itemStack, baseEnchant.getName())) {
            int currentLevel = (int) PersistentDataUtils.getKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER);
            if (currentLevel < level) {
                PersistentDataUtils.addKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER, level);
                String targetLore = Utils.formatString(baseEnchant.getName()) + " " + currentLevel;
                String newLore = baseEnchant.getColor().getColor() + Utils.formatString(baseEnchant.getName()) + " " + level;
                LoreUtils.updateItemLore(itemStack, targetLore, newLore);
            }
        } else {
            PersistentDataUtils.addKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER, level);
            String lore = baseEnchant.getColor().getColor() + Utils.formatString(baseEnchant.getName()) + " " + level;
            LoreUtils.addLore(itemStack, lore);
        }
    }

    public static void removeEnchant(ItemStack itemStack, BaseEnchant<?> baseEnchant) {
        PersistentDataUtils.removeKey(itemStack, baseEnchant.getName());
        LoreUtils.removeLore(itemStack, Utils.formatString(baseEnchant.getName()) + " ");
    }

    public BaseCustomItemStack getBook() {
        String ID = name.toUpperCase().replace(" ", "_") + "_BOOK";
        BaseCustomItemStack customItemStack = new BaseCustomItemStack(
                ID,
                ItemBuilder.customEnchantedItemUsingStack(
                        new ItemStack(Material.ENCHANTED_BOOK),
                        color.getColor() + Utils.formatString(ID),
                        ""
                ));

        for (String s : description) {
            LoreUtils.addLore(customItemStack, s);
        }
        return customItemStack;
    }

    @EventHandler
    private void logic(T event) {
        if (preLogic(event)) {
            specificLogic(event);
        }
    }

    public abstract void specificLogic(T event);

    public abstract void methodToRun(T event);

    private boolean preLogic(T event) {
        try {
            if (event instanceof PlayerInteractEvent playerInteractEvent) {
                return handlePlayerInteractEvent(playerInteractEvent);
            }
            if (event instanceof PlayerFishEvent) {
                // Handle PlayerFishEvent logic here
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public abstract boolean handlePlayerInteractEvent(PlayerInteractEvent event);

    public boolean hasEnchant(ItemStack itemStack) {
        return itemStack != null && itemStack.hasItemMeta() && PersistentDataUtils.hasKey(itemStack, this.getName(), PersistentDataType.INTEGER);
    }

    public int getLevel(ItemStack itemStack, BaseEnchant<?> baseEnchant) {
        return (int) PersistentDataUtils.getKey(itemStack, baseEnchant.getName(), PersistentDataType.INTEGER);
    }

    private void registerListener(BaseEnchant enchant) {
        Listener l = new Listener() {
        };
        EventExecutor executor = (ignored, event) -> enchant.logic(event);
        iELib.getPlugin().getServer().getPluginManager().registerEvent(enchant.getEventClass(), l, EventPriority.NORMAL, executor, iELib.getPlugin());
    }

    private Class<? extends Event> getEventClass() {
        ParameterizedType p = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<? extends Event>) p.getActualTypeArguments()[0];
    }
}
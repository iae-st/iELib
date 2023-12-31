package ez.iELib.utils;

import com.jeff_media.customblockdata.CustomBlockData;
import ez.iELib.iELib;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtils {


    private static final PersistentDataType<?, ?>[] PRIMITIVE_DATA_TYPES = new PersistentDataType<?, ?>[]{
            PersistentDataType.BYTE,
            PersistentDataType.SHORT,
            PersistentDataType.INTEGER,
            PersistentDataType.LONG,
            PersistentDataType.FLOAT,
            PersistentDataType.DOUBLE,
            PersistentDataType.STRING,
            PersistentDataType.BYTE_ARRAY,
            PersistentDataType.INTEGER_ARRAY,
            PersistentDataType.LONG_ARRAY,
            PersistentDataType.TAG_CONTAINER_ARRAY,
            PersistentDataType.TAG_CONTAINER};


    /**
     * Retrieves the data type of key in the given PersistentDataContainer.
     *
     * @param pdc The PersistentDataContainer to search.
     * @param key The key to get the data type for.
     * @return The data type associated with the key, or null if the key does not exist in the container.
     */
    public static PersistentDataType<?, ?> getDataType(PersistentDataContainer pdc, NamespacedKey key) {
        for (PersistentDataType<?, ?> dataType : PRIMITIVE_DATA_TYPES) {
            if (pdc.has(key, dataType)) return dataType;
        }
        return null;
    }

    /**
     * It adds a key to an itemstack
     *
     * @param itemStack The itemstack you want to add the key to.
     * @param key       The key of the data you want to add.
     * @param type      The type of data you want to store.
     * @param value     The value you want to set the key to.
     * @return The itemStack with the key added to it.
     */
    public static ItemStack addKey(ItemStack itemStack, String key, PersistentDataType type, Object value) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(iELib.getPlugin(), key), type, value);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Adds a key-value pair to a PersistentDataContainer.
     *
     * @param dataContainer The PersistentDataContainer to add the key-value pair to.
     * @param key           The key for the data you want to add.
     * @param type          The type of data you want to store.
     * @param value         The value you want to set the key to.
     */
    public static void addKeyToBlock(PersistentDataContainer dataContainer, String key, PersistentDataType type, Object value) {
        dataContainer.set(new NamespacedKey(iELib.getPlugin(), key), type, value);
    }

    /**
     * Removes a specific key from the given item stack's persistent data container.
     *
     * @param itemStack The ItemStack to remove the key from.
     * @param key       The key to be removed.
     * @return The modified ItemStack with the key removed.
     */
    public static ItemStack removeKey(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.remove(new NamespacedKey(iELib.getPlugin(), key));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Removes a specific key from the given block's persistent data container.
     *
     * @param block The block to remove the key from.
     * @param key   The key to be removed.
     */
    public static void removeKeyFromBlock(Block block, String key) {
        PersistentDataContainer data = new CustomBlockData(block, iELib.getPlugin());
        data.remove(new NamespacedKey(iELib.getPlugin(), key));
    }

    /**
     * It gets the key from the itemstack
     *
     * @param itemStack The itemStack you want to get the key from.
     * @param key       The key of the data you want to get.
     * @param type      The type of data you want to get.
     * @return The value of the key.
     */
    public static Object getKey(ItemStack itemStack, String key, PersistentDataType type) {
        NamespacedKey key1 = new NamespacedKey(iELib.getPlugin(), key);
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(key1, type);
    }

    /**
     * Retrieves the value associated with the given key from the given PersistentDataContainer.
     *
     * @param dataContainer The PersistentDataContainer to retrieve the value from.
     * @param key           The key to retrieve the value for.
     * @param type          The type of the value to retrieve.
     * @return The value associated with the key, or null if the key does not exist or the value cannot be cast to the specified type.
     */
    public static Object getKeyFromBlock(PersistentDataContainer dataContainer, String key, PersistentDataType type) {
        NamespacedKey key1 = new NamespacedKey(iELib.getPlugin(), key);
        return dataContainer.get(key1, type);
    }

    /**
     * This function checks if the item has a key with the given key and type.
     *
     * @param itemStack The itemstack you want to check
     * @param key       The key of the tag you want to check for.
     * @param type      The type of data you want to get.
     * @return A boolean value.
     */
    public static boolean hasKey(ItemStack itemStack, String key, PersistentDataType type) {
        NamespacedKey namespacedKey = new NamespacedKey(iELib.getPlugin(), key);
        return itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey, type);
    }

    /**
     * Checks if the given PersistentDataContainer has a key with the given key.
     *
     * @param dataContainer The PersistentDataContainer to check.
     * @param key           The key of the tag you want to check for.
     * @return True if the PersistentDataContainer has the key, false otherwise.
     */
    public static boolean blockHasKey(PersistentDataContainer dataContainer, String key) {
        return dataContainer.has(new NamespacedKey(iELib.getPlugin(), key));
    }

    /**
     * Checks if the given ItemStack has an ID key.
     *
     * @param itemStack The ItemStack to check.
     * @return True if the ItemStack has an ID key, false otherwise.
     */
    public static boolean hasID(ItemStack itemStack) {
        if(itemStack.getItemMeta() == null) return false;
        return hasKey(itemStack, "ID", PersistentDataType.STRING);
    }

    /**
     * If the item stack is not null, and the item stack's meta is not null, and the item stack's meta's persistent data
     * container has the key, then return true
     *
     * @param itemStack The itemstack you want to check
     * @param key       The key to check for
     * @return A boolean value.
     */
    public static boolean hasKey(ItemStack itemStack, String key) {
        if (itemStack == null) return false;
        if (itemStack.getItemMeta() == null) return false;
        NamespacedKey namespacedKey = new NamespacedKey(iELib.getPlugin(), key);
        return itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey);
    }
}

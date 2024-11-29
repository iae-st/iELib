package ez.iELib.utils;

import com.jeff_media.customblockdata.CustomBlockData;
import ez.iELib.iELib;
import org.bukkit.Chunk;
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
     * Adds a key-value pair to an ItemStack's PersistentDataContainer.
     *
     * @param itemStack The ItemStack to add the key-value pair to.
     * @param key       The key for the data you want to add.
     * @param type      The type of data you want to store.
     * @param value     The value you want to set the key to.
     * @return The ItemStack with the key added to it.
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
     * Adds a key-value pair to a Block's PersistentDataContainer.
     *
     * @param block The Block to add the key-value pair to.
     * @param key   The key for the data you want to add.
     * @param type  The type of data you want to store.
     * @param value The value you want to set the key to.
     */
    public static void addKeyToBlock(Block block, String key, PersistentDataType type, Object value) {
        CustomBlockData customBlockData = new CustomBlockData(block, iELib.getPlugin());
        customBlockData.set(new NamespacedKey(iELib.getPlugin(), key), type, value);
    }

    /**
     * Removes a specific key from the given ItemStack's PersistentDataContainer.
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
     * Removes a specific key from the given Block's PersistentDataContainer.
     *
     * @param block The Block to remove the key from.
     * @param key   The key to be removed.
     */
    public static void removeKeyFromBlock(Block block, String key) {
        PersistentDataContainer data = new CustomBlockData(block, iELib.getPlugin());
        data.remove(new NamespacedKey(iELib.getPlugin(), key));
    }

    /**
     * Retrieves the value associated with the given key from the given ItemStack's PersistentDataContainer.
     *
     * @param itemStack The ItemStack to retrieve the value from.
     * @param key       The key to retrieve the value for.
     * @param type      The type of the value to retrieve.
     * @return The value associated with the key, or null if the key does not exist or the value cannot be cast to the specified type.
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
     * Retrieves the value associated with the given key from the given Block's PersistentDataContainer.
     *
     * @param block The Block to retrieve the value from.
     * @param key   The key to retrieve the value for.
     * @param type  The type of the value to retrieve.
     * @return The value associated with the key, or null if the key does not exist or the value cannot be cast to the specified type.
     */
    public static Object getKeyFromBlock(Block block, String key, PersistentDataType type) {
        CustomBlockData customBlockData = new CustomBlockData(block, iELib.getPlugin());
        return getKeyFromBlock(customBlockData, key, type);
    }

    /**
     * Checks if the given ItemStack has a key with the given key and type.
     *
     * @param itemStack The ItemStack to check.
     * @param key       The key of the tag you want to check for.
     * @param type      The type of data you want to check for.
     * @return True if the ItemStack has the key, false otherwise.
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
     * Checks if the given Block's PersistentDataContainer has a key with the given key.
     *
     * @param block The Block to check.
     * @param key   The key of the tag you want to check for.
     * @return True if the Block's PersistentDataContainer has the key, false otherwise.
     */
    public static boolean blockHasKey(Block block, String key) {
        CustomBlockData customBlockData = new CustomBlockData(block, iELib.getPlugin());
        return customBlockData.has(new NamespacedKey(iELib.getPlugin(), key));
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
     * Checks if the given ItemStack has a key with the given key.
     *
     * @param itemStack The ItemStack to check.
     * @param key       The key to check for.
     * @return True if the ItemStack has the key, false otherwise.
     */
    public static boolean hasKey(ItemStack itemStack, String key) {
        if (itemStack == null) return false;
        if (itemStack.getItemMeta() == null) return false;
        NamespacedKey namespacedKey = new NamespacedKey(iELib.getPlugin(), key);
        return itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey);
    }

    /**
     * Adds a key-value pair to a Chunk's PersistentDataContainer.
     *
     * @param chunk The Chunk to add the key-value pair to.
     * @param key   The key for the data you want to add.
     * @param type  The type of data you want to store.
     * @param value The value you want to set the key to.
     */
    public static void addKeyToChunk(Chunk chunk, String key, PersistentDataType type, Object value) {
        NamespacedKey key1 = new NamespacedKey(iELib.getPlugin(), key);
        chunk.getPersistentDataContainer().set(key1, type, value);
    }

    /**
     * Checks if the given Chunk's PersistentDataContainer has a key with the given key.
     *
     * @param chunk The Chunk to check.
     * @param key   The key to check for.
     * @return True if the Chunk's PersistentDataContainer has the key, false otherwise.
     */
    public static boolean HasKeyInChunk(Chunk chunk, String key) {
        NamespacedKey key1 = new NamespacedKey(iELib.getPlugin(), key);
        return chunk.getPersistentDataContainer().has(key1);
    }

    /**
     * Retrieves the value associated with the given key from the given Chunk's PersistentDataContainer.
     *
     * @param chunk The Chunk to retrieve the value from.
     * @param key   The key to retrieve the value for.
     * @param type  The type of the value to retrieve.
     * @return The value associated with the key, or null if the key does not exist or the value cannot be cast to the specified type.
     */
    public static Object GetKeyInChunk(Chunk chunk, String key, PersistentDataType type) {
        NamespacedKey key1 = new NamespacedKey(iELib.getPlugin(), key);
        return chunk.getPersistentDataContainer().get(key1, type);
    }
}
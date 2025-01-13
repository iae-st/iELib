package ez.iELib.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public static ItemStack customItemName(Material mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(legacySerializer.deserialize(name));
        meta.lore(Arrays.stream(lore)
                .map(legacySerializer::deserialize)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(legacySerializer.deserialize(name));
        meta.lore(Arrays.stream(lore)
                .map(legacySerializer::deserialize)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(legacySerializer.deserialize(name));
        meta.lore(lore.stream()
                .map(legacySerializer::deserialize)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customUnbreakableItem(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(legacySerializer.deserialize(name));
        meta.lore(Arrays.stream(lore)
                .map(legacySerializer::deserialize)
                .collect(Collectors.toList()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customTrimTemplate(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(legacySerializer.deserialize(name));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.lore(Arrays.stream(lore)
                .map(legacySerializer::deserialize)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customEnchantedItemUsingStack(ItemStack itemStack, String s, String... lore) {
        ItemStack item = new ItemStack(itemStack);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(legacySerializer.deserialize(s));
        meta.lore(Arrays.stream(lore)
                .map(legacySerializer::deserialize)
                .collect(Collectors.toList()));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return item;
    }

    public static ItemStack enchantItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return itemStack;
    }
}
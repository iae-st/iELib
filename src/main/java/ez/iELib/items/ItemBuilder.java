package ez.iELib.items;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    public static ItemStack customItemName(Material mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        meta.lore(Arrays.stream(lore)
                .map(line -> Component.text(ChatColor.translateAlternateColorCodes('&', line)))
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        meta.lore(Arrays.stream(lore)
                .map(line -> Component.text(ChatColor.translateAlternateColorCodes('&', line)))
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        meta.lore(lore.stream()
                .map(line -> Component.text(ChatColor.translateAlternateColorCodes('&', line)))
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customUnbreakableItem(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        meta.lore(Arrays.stream(lore)
                .map(line -> Component.text(ChatColor.translateAlternateColorCodes('&', line)))
                .collect(Collectors.toList()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customTrimTemplate(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.lore(Arrays.stream(lore)
                .map(line -> Component.text(ChatColor.translateAlternateColorCodes('&', line)))
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customEnchantedItemUsingStack(ItemStack itemStack, String s, String... lore) {
        ItemStack item = new ItemStack(itemStack);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', s)));
        meta.lore(Arrays.stream(lore)
                .map(line -> Component.text(ChatColor.translateAlternateColorCodes('&', line)))
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
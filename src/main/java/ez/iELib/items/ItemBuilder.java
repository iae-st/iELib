package ez.iELib.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {
    public static ItemStack customItemName(Material mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ComponentBuilder.createComponent(name));
        meta.lore(Arrays.stream(lore)
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ComponentBuilder.createComponent(name));
        meta.lore(Arrays.stream(lore)
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, Component name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        meta.lore(Arrays.stream(lore)
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customItemUsingStack(ItemStack mat, String name, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ComponentBuilder.createComponent(name));
        meta.lore(lore.stream()
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customUnbreakableItem(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ComponentBuilder.createComponent(name));
        meta.lore(Arrays.stream(lore)
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customTrimTemplate(ItemStack mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(ComponentBuilder.createComponent(name));
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.lore(Arrays.stream(lore)
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack customEnchantedItemUsingStack(ItemStack itemStack, String s, String... lore) {
        ItemStack item = new ItemStack(itemStack);
        ItemMeta meta = item.getItemMeta();
        item.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
        meta.displayName(ComponentBuilder.createComponent(s));
        meta.lore(Arrays.stream(lore)
                .map(ComponentBuilder::createComponent)
                .collect(Collectors.toList()));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enchantItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        itemStack.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
        return itemStack;
    }

}
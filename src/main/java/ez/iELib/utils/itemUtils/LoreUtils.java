package ez.iELib.utils.itemUtils;

import ez.iELib.utils.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreUtils {
    public static ItemStack updateItemLore(ItemStack itemStack, String targetLore, String newLore) {
        if (itemStack != null && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                List<String> newLoreList = new ArrayList<>();

                for (String loreLine : lore) {
                    if (loreLine.contains(targetLore)) {
                        newLoreList.add(newLore);
                    } else {
                        newLoreList.add(loreLine);
                    }
                }

                itemMeta.setLore(newLoreList);
                itemStack.setItemMeta(itemMeta);
            }
        }

        return itemStack;
    }

    public static void setItemLore(ItemStack itemStack, String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(itemMeta);
    }

    public static void setItemName(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);

        itemStack.setItemMeta(itemMeta);
    }

    public static ItemStack insertLoreLine(ItemStack itemStack, int index, String newLoreLine) {
        if (itemStack != null && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore;

            if (itemMeta.hasLore()) {
                lore = itemMeta.getLore();
            } else {
                lore = new ArrayList<>();
            }

            if (index < 0) {
                index = 0;
            } else if (index > lore.size()) {
                index = lore.size();
            }

            lore.add(index, newLoreLine);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }


    public static void setLoreLine(ItemStack itemStack, int lineIndex, String newLine) {
        if (itemStack == null || newLine == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        if (itemMeta.hasLore()) {
            List<String> lore = itemMeta.getLore();

            if (lineIndex >= 0 && lineIndex < lore.size()) {
                lore.set(lineIndex, newLine);

                itemMeta.setLore(lore);

                itemStack.setItemMeta(itemMeta);
            }
        }
    }

    public static ItemStack addLore(ItemStack item, String lore) {
        if (lore == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lores = meta.getLore();
            if (lores == null) {
                lores = new ArrayList<>();
            }
            lores.add(Utils.color(lore));
            meta.setLore(lores);
            item.setItemMeta(meta);
        }
        return item;
    }



    public static ItemStack assignLoreWithMeta(ItemStack item, ItemMeta meta, String lore) {
        if (lore == null) {
            return null;
        }
        if (meta != null) {
            List<String> lores = meta.getLore();
            if (lores == null) {
                lores = new ArrayList<>();
            }
            lores.add(Utils.color(lore));
            meta.setLore(lores);
        }
        return item;
    }

    public static String applyStars(int amount) {
        StringBuilder star = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            star.append("§e✯");
        }

        return star.toString();
    }


    public static void removeLore(ItemStack itemStack, String loreToRemove) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null && itemMeta.hasLore()) {
            List<String> lore = itemMeta.getLore();
            lore.removeIf(line -> line.contains(loreToRemove));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
    }
}

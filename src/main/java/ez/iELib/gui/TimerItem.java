package ez.iELib.gui;

import ez.iELib.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class TimerItem {

    private long seconds;
    private ItemStack timeItem;
    private BukkitTask task;
    private ItemStack displayItem;

    public TimerItem(long seconds, ItemStack displayItem) {
        this.seconds = seconds;
        this.displayItem = displayItem;
    }

    public ItemStack getTimeItem() {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        long d = (seconds / (60 * 60 * 24));
        String timeFormat = String.format("%02d:%02d:%02d:%02d", d, h, m, s);

        // Assuming you've an item factory to make a new item
        // replace this with your own item creation logic
        timeItem = ItemBuilder.customEnchantedItemUsingStack(displayItem, "Time remaining", timeFormat);

        return timeItem;
    }

    public void startCountdown(Plugin plugin, Inventory inventory, int itemSlot) {
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (seconds > 0) {
                seconds--;
                inventory.setItem(itemSlot, getTimeItem());
            } else {
                task.cancel(); // stop the task when seconds reach zero
            }
        }, 0L, 20L);
    }
}
package ez.iELib.gui;

import ez.iELib.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class TimerItem {

    private long seconds;
    private long seconds_to_loop;
    private ItemStack timeItem;
    private BukkitTask task;
    private ItemStack displayItem;

    public TimerItem(long seconds, ItemStack displayItem) {
        this.seconds = seconds;
        this.seconds_to_loop = seconds;
        this.displayItem = displayItem;
    }

    public ItemStack getTimeItem() {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        long d = (seconds / (60 * 60 * 24));
        String timeFormat = String.format("%02d:%02d:%02d:%02d", d, h, m, s);

        timeItem = ItemBuilder.customEnchantedItemUsingStack(displayItem, displayItem.getItemMeta().getDisplayName(), ChatColor.GRAY + "Time remaining: " + ChatColor.GOLD + timeFormat);

        return timeItem;
    }

    public void startCountdown(Plugin plugin, Consumer<?> consumer) {
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (seconds > 0) {
                seconds--;
            } else {
                consumer.accept(null);
                seconds = seconds_to_loop;
            }
        }, 0L, 20L);
    }

    public void stopCountdown() {
        task.cancel();
    }
}
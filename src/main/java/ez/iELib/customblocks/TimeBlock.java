package ez.iELib.customblocks;

import com.jeff_media.customblockdata.CustomBlockData;
import ez.iELib.iELib;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;
import java.util.Objects;

public interface TimeBlock {
    String TIME_KEY = "Time";

    /**
     * Set the time data on the specified block.
     *
     * @param block    The block to set the time data on.
     * @param timeData The time data to be set.
     */
    default void setTimeData(Block block, String timeData) {
        if (block != null) {
            CustomBlockData customBlockData = new CustomBlockData(block, iELib.getPlugin());
            customBlockData.set(new NamespacedKey(iELib.getPlugin(), TIME_KEY), PersistentDataType.STRING, timeData);
        }
    }

    /**
     * Get the time data from the specified block.
     *
     * @param block The block to retrieve time data from.
     * @return The time data as a string, or null if not found.
     */
    default Date getTimeData(Block block) {
        if (block != null) {
            CustomBlockData customBlockData = new CustomBlockData(block, iELib.getPlugin());
            if (customBlockData.has(new NamespacedKey(iELib.getPlugin(), TIME_KEY), PersistentDataType.STRING)) {
                return new Date(Objects.requireNonNull(customBlockData.get(new NamespacedKey(iELib.getPlugin(), TIME_KEY), PersistentDataType.STRING)));
            }
        }
        return null;
    }

    /**
     * Set the current date as the "Time" data on the specified block.
     *
     * @param block The block to set the current date as "Time" data.
     */
    default void setCurrentDateAsTimeData(Block block) {
        if (block != null) {
            String timeData = getCurrentDateString();
            setTimeData(block, timeData);
        }
    }

    default long calculateDaysPassed(Block block) {
        Date storedTimeData = getTimeData(block);
        if (storedTimeData != null) {
            Date currentDate = new Date();


            long timeDelay = (currentDate.getTime() - storedTimeData.getTime()) / 1000;
            return timeDelay / 1200; // 1200
        }
        return 0;
    }


    /**
     * Get the current date as a string.
     *
     * @return The current date as a string.
     */
    private String getCurrentDateString() {
        Date currentDate = new Date();
        return currentDate.toString();
    }
}

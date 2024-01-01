package ez.iELib.utils;

import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuiUtils {
    public static int getSnakeSlot(int slot) {
        final int horizontalLength = 2;
        final int verticalLength = 5;

        final int patternLength = 2 * (horizontalLength + verticalLength);
        final int patternNo = slot / (patternLength + 1); // 10 -> 1, 8/10 -> 1, 12/10 -> 2
        int slotNoInPattern = slot % (patternLength + 1); // 8/10 -> 8, 12/10 -> 2

        final boolean isInReversedPattern = slotNoInPattern > patternLength / 2;

        int roadTakenHorizontal, roadTakenVertical;
        if (isInReversedPattern) {
            slotNoInPattern -= patternLength / 2; // 6 -> 1

            roadTakenHorizontal = Math.min(horizontalLength, slotNoInPattern) + horizontalLength;
            roadTakenVertical = verticalLength - Math.max(0, slotNoInPattern - horizontalLength);
        } else {
            roadTakenHorizontal = Math.min(horizontalLength, slotNoInPattern);
            roadTakenVertical = Math.max(0, slotNoInPattern - horizontalLength);
        }

        return roadTakenHorizontal + patternNo * horizontalLength * 2 + roadTakenVertical * 9;
    }

    public static List<Item> itemStackToItemList(List<ItemStack> itemStacks) {
        return Arrays.stream(itemStacks.toArray())
                .filter(Objects::nonNull)
                .map(item -> new SimpleItem((ItemStack) item))
                .collect(Collectors.toList());
    }
}
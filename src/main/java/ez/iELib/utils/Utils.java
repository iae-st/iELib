package ez.iELib.utils;

import com.google.common.base.Strings;
import ez.iELib.items.SkullCreator;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;

public class Utils {
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static boolean isBoosted = false;
    private static BossBar bossBar;
    private static long min;

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static boolean isEmpty(Player player) {
        ItemStack getMainHand = player.getItemInHand();
        return getMainHand.getType() != Material.AIR;
    }


    public static void spawnFireworks(Location location, int offsetX, int offsetY, int offsetZ, Color color, int amount) {
        Location loc = location.add(offsetX, offsetY, offsetZ);
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(color).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        for (int i = 0; i < amount; i++) {
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK_ROCKET);
            fw2.setFireworkMeta(fwm);
        }
    }

    public static ItemStack getPlayerSkull(Player paramPlayer) {
        ItemStack skull = SkullCreator.itemFromUuid(paramPlayer.getUniqueId());

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(paramPlayer.getName());
        meta.setDisplayName("§6" + paramPlayer.getName() + "'s stats");
        skull.setItemMeta(meta);
        return skull;
    }


    /**
     * It returns a string of a progress bar
     *
     * @param current           The current value
     * @param max               The maximum value of the progress bar.
     * @param totalBars         The total number of bars to display.
     * @param symbol            The symbol to use for the progress bar.
     * @param completedColor    The color of the completed bars
     * @param notCompletedColor The color of the progress bar that hasn't been completed yet.
     * @return A string of symbols that represents a progress bar.
     */
    public static String getProgressBar(int current, int max, int totalBars, String symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat(completedColor + symbol, progressBars)
                + Strings.repeat(notCompletedColor + symbol, totalBars - progressBars);
    }

    public static String getProgressBar(long current, long max, int totalBars, String symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat(completedColor + symbol, progressBars)
                + Strings.repeat(notCompletedColor + symbol, totalBars - progressBars);
    }

    /**
     * It takes a string and returns a TextComponent
     *
     * @param s The string to convert to a TextComponent
     * @return A TextComponent object.
     */
    public static TextComponent stringToComponent(String s) {

        return new TextComponent(s);
    }

    /**
     * It returns the display name of the item stack
     *
     * @param itemStack The item you want to get the name of.
     * @return The name of the item.
     */
    public static String getName(ItemStack itemStack) {
        return itemStack.getItemMeta().getDisplayName();
    }


    public static boolean hasItemWithID(Player player, String id) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (PersistentDataUtils.hasKey(item, "ID")) {
                    String itemID = (String) PersistentDataUtils.getKey(item, "ID", PersistentDataType.STRING);
                    if (itemID.equals(id)) {
                        return true;
                    }
                }
            }
        }
        // Player does not have an item with the specified ID
        return false;
    }

    /**
     * It takes a string, makes it lowercase, replaces all underscores with spaces, and then capitalizes the first letter
     * of each word
     *
     * @param ID The ID of the item.
     * @return A string of the translated ID.
     */
    public static String formatString(String ID) {
        String id = ID.toLowerCase().replace("_", " ");
        char[] charArray = id.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        return String.valueOf(charArray);
    }

    /**
     * It returns a random BlockFace.
     *
     * @return A random BlockFace
     */
    public static BlockFace randomBlockFace() {
        int pick = new Random().nextInt(BlockFace.values().length);
        return BlockFace.values()[pick];
    }

    public static BlockFace getOppositeBlockFace(BlockFace blockFace) {
        switch (blockFace) {
            case EAST:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.EAST;
            case NORTH:
                return BlockFace.SOUTH;
            case SOUTH:
                return BlockFace.NORTH;
        }
        return null;
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static Location getCenter(Location loc) {
        double centerX = loc.getBlockX() + 0.5;
        double centerY = loc.getBlockY() + 0.5;
        double centerZ = loc.getBlockZ() + 0.5;
        return new Location(loc.getWorld(), centerX, centerY, centerZ);
    }

    public static Material treeDisplayPot(Material material) {
        switch (material) {
            case DARK_OAK_SAPLING:
                return Material.POTTED_DARK_OAK_SAPLING;
            case OAK_SAPLING:
                return Material.POTTED_OAK_SAPLING;
            case BIRCH_SAPLING:
                return Material.POTTED_BIRCH_SAPLING;
            case JUNGLE_SAPLING:
                return Material.POTTED_JUNGLE_SAPLING;
            case ACACIA_SAPLING:
                return Material.POTTED_ACACIA_SAPLING;
            case CHERRY_SAPLING:
                return Material.POTTED_CHERRY_SAPLING;
            default:
                return Material.POTTED_OAK_SAPLING;

        }
    }

    public static int getAvailableInventorySpace(Player player, ItemStack itemStack) {
        ItemStack[] playerInventory = player.getInventory().getStorageContents();
        int availableSpace = 0;

        for (ItemStack slotItem : playerInventory) {
            if (slotItem == null) {
                availableSpace += itemStack.getMaxStackSize();
            } else if (slotItem.isSimilar(itemStack)) {
                int spaceInSlot = itemStack.getMaxStackSize() - slotItem.getAmount();
                availableSpace += spaceInSlot;
            }
        }

        return availableSpace;
    }

    public static int availableInventorySpaces(Player player) {
        ItemStack[] playerInventory = player.getInventory().getStorageContents();
        int emptySpaces = 0;

        for (ItemStack slotItem : playerInventory) {
            if (slotItem == null || slotItem.getType() == Material.AIR) {
                emptySpaces++;
            }
        }

        return emptySpaces;
    }

    public static boolean generateYesWithPercentage(double percentage) {
        if (percentage < 0.0001 || percentage > 100.0) {
            throw new IllegalArgumentException("Percentage must be between 0.0001 and 100.0");
        }

        Random random = new Random();
        double randomValue = random.nextDouble() * 100.0;

        return randomValue <= percentage;
    }

    public static ItemStack enchantItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.UNBREAKING, 1, false);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public static boolean containsBlock(Block block1, Material material) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block currentBlock = block1.getRelative(x, y, z);
                    if (currentBlock.getType() == material) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static Block[] getAdjunct(Block block, Material material, BlockFace[] faces) {
        Block[] blockArray = new Block[faces.length];
        for (BlockFace face : faces) {
            int i = 0;
            Block relativeBlock = block.getRelative(face);
            if (relativeBlock.getType() == material) {
                blockArray[i] = relativeBlock;
                i++;
            }
        }
        return blockArray;
    }


}

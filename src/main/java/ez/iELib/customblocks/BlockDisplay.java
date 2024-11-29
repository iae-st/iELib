package ez.iELib.customblocks;

import ez.iELib.iELib;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;

import java.util.*;

/**
 * Class representing a display of text above a block using ArmorStands.
 */
public class BlockDisplay {
    public static List<BlockDisplay> blockDisplayList = new ArrayList<>();
    private List<ArmorStand> displayArmorStands = new ArrayList<>();
    private BukkitTask distanceCheckTask;
    private Set<Player> playersLookingAtBlock = new HashSet<>();
    private List<String> displayTexts;

    /**
     * Constructor for BlockDisplay.
     *
     * @param displayTexts List of strings to display above the block.
     */
    public BlockDisplay(List<String> displayTexts) {
        this.displayTexts = displayTexts;
        blockDisplayList.add(this);
    }

    public void addText(String text) {
        displayTexts.add(text);
    }

    /**
     * Shows the display to the player if they are looking at the specified block.
     *
     * @param player The player to show the display to.
     * @param block The block to display the text above.
     */
    public void showDisplay(Player player, Block block) {
        RayTraceResult rayTraceResult = player.rayTraceBlocks(7.0);
        if (rayTraceResult == null || !rayTraceResult.getHitBlock().equals(block)) {
            return;
        }

        World world = player.getWorld();
        if (isPlayerNearBlock(player, block.getLocation(), 7)) {
            Location location = block.getLocation().add(0.5, 0.4, 0.5);

            for (int i = 0; i < displayTexts.size(); i++) {
                Location lineLocation = location.clone().add(0, 0.25 * i, 0);
                ArmorStand armorStand = lineLocation.getWorld().spawn(lineLocation, ArmorStand.class);
                setupArmorStand(armorStand);
                player.showEntity(iELib.getPlugin(), armorStand);
                armorStand.setCustomNameVisible(true);
                armorStand.setCustomName(displayTexts.get(i));
                displayArmorStands.add(armorStand);
            }
        }
    }

    /**
     * Configures the properties of the ArmorStand.
     *
     * @param armorStand The ArmorStand to configure.
     */
    void setupArmorStand(ArmorStand armorStand) {
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setBasePlate(false);
        armorStand.setHeadPose(new EulerAngle(Math.toRadians(180), 0, 0));
        armorStand.setBodyPose(new EulerAngle(Math.toRadians(180), 0, 0));
        armorStand.setArms(false);
        armorStand.setRightLegPose(new EulerAngle(Math.toRadians(180), 0, 0));
        armorStand.setLeftLegPose(new EulerAngle(Math.toRadians(180), 0, 0));
        armorStand.setCollidable(false);
        armorStand.setVisibleByDefault(false);
    }

    /**
     * Checks if the player is near the specified block within a given radius.
     *
     * @param player The player to check.
     * @param location The location of the block.
     * @param radius The radius to check within.
     * @return True if the player is within the radius, false otherwise.
     */
    public static boolean isPlayerNearBlock(Player player, Location location, double radius) {
        Location playerLocation = player.getLocation();
        return playerLocation.distance(location) <= radius;
    }

    /**
     * Starts a task to periodically check if the player is near and looking at the block.
     *
     * @param player The player to check.
     * @param block The block to check.
     */
    public void startDistanceCheck(Player player, Block block) {
        distanceCheckTask = new BukkitRunnable() {
            @Override
            public void run() {
                checkAndShowDisplay(player, block);
            }
        }.runTaskTimer(iELib.getPlugin(), 0, 10); // Check every second
    }

    /**
     * Checks if the player is near and looking at the block, and shows or hides the display accordingly.
     *
     * @param player The player to check.
     * @param block The block to check.
     */
    public void checkAndShowDisplay(Player player, Block block) {
        RayTraceResult rayTraceResult = player.rayTraceBlocks(5.0);
        boolean isLookingAtBlock = rayTraceResult != null && rayTraceResult.getHitBlock().equals(block);

        if (isPlayerNearBlock(player, block.getLocation(), 5) && isLookingAtBlock) {
            if (!playersLookingAtBlock.contains(player)) {
                playersLookingAtBlock.add(player);
                if (displayArmorStands.isEmpty() || displayArmorStands.stream().noneMatch(ArmorStand::isValid)) {
                    if (isValidBlock(block)) {
                        showDisplay(player, block);
                    } else {
                        kill();
                    }
                }
            }
        } else {
            if (playersLookingAtBlock.contains(player)) {
                playersLookingAtBlock.remove(player);
                if (displayArmorStands.stream().allMatch(ArmorStand::isValid) && playersLookingAtBlock.isEmpty()) {
                    displayArmorStands.forEach(ArmorStand::remove);
                    displayArmorStands.clear();
                }
            }
        }
    }

    /**
     * Checks if the block is valid for displaying text.
     *
     * @param block The block to check.
     * @return True if the block is valid, false otherwise.
     */
    protected boolean isValidBlock(Block block) {
        // Default implementation, can be overridden
        return true;
    }

    /**
     * Cancels the distance check task.
     */
    public void cancelDistanceCheck() {
        if (distanceCheckTask != null) {
            distanceCheckTask.cancel();
        }
    }

    /**
     * Removes all ArmorStands and cancels the distance check task.
     */
    public void kill() {
        displayArmorStands.forEach(ArmorStand::remove);
        displayArmorStands.clear();
        this.cancelDistanceCheck();
        blockDisplayList.remove(this);
    }

    /**
     * Removes all BlockDisplays and their associated ArmorStands.
     */
    public static void killAll() {
        Iterator<BlockDisplay> iterator = blockDisplayList.iterator();
        while (iterator.hasNext()) {
            BlockDisplay blockDisplay = iterator.next();
            blockDisplay.kill();
            iterator.remove();
        }
    }
}
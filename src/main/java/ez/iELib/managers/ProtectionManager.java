package ez.iELib.managers;

import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import ez.iELib.Logger;
import ez.iELib.iELib;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ProtectionManager {

    private final Plugin pluginInstance;
    private Plugin towny;
    private Plugin griefPrevention;

    private Plugin slimefun;
    private Plugin worldGuard;
    private Logger logger;

    public ProtectionManager(Logger logger) {
        this.pluginInstance = iELib.getPlugin();
        this.logger = logger;
        load();
    }

    private void load() {
        towny = pluginInstance.getServer().getPluginManager().getPlugin("Towny");
        griefPrevention = pluginInstance.getServer().getPluginManager().getPlugin("GriefPrevention");
        slimefun = pluginInstance.getServer().getPluginManager().getPlugin("Slimefun");
        worldGuard = pluginInstance.getServer().getPluginManager().getPlugin("Worldguard");
        if (towny != null) {
            logger.log("§7Integration with §bTowny §7Successfully Established!", true);
        }

        if (griefPrevention != null) {
            logger.log("§7Integration with §9GriefPrevention §7Successfully Established!", true);
        }

        if (slimefun != null) {
            logger.log("§7Integration with §aSlimefun §7Successfully Established!", true);
        }
        if (worldGuard != null) {
            logger.log("§7Integration with §eWorldGuard §7Successfully Established!", true);
        }
    }

    public boolean hasSlimefun() {
        return slimefun != null;
    }


    public boolean playerCanInteract(Block block, Player player) {
        boolean canInteract = true;
        if(player.isOp()) return true;
        if (griefPrevention != null) {
            if (isPlayerNotInClaim(block.getLocation())) {
                canInteract = true;
            } else {
                canInteract = isPlayerInOwnOrTrustedClaim(player, block);
            }
        }

        if (towny != null) {
            canInteract = PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(), TownyPermission.ActionType.SWITCH);
        }

        if (worldGuard != null) {
            if (!hasWorldGuardPermissions(block.getLocation(), player, Flags.INTERACT)) {
                canInteract = false;
            }
        }

        return canInteract;
    }

    public boolean playerCanBreak(Block block, Player player) {
        boolean canBreak = true;
        if(player.isOp()) return true;
        if (griefPrevention != null) {
            if (isPlayerNotInClaim(block.getLocation())) {
                canBreak = true;
            } else {
                canBreak = isPlayerInOwnOrTrustedClaim(player, block);
            }
        }

        if (towny != null) {
            canBreak = PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(), TownyPermission.ActionType.DESTROY);
        }

        if (worldGuard != null) {
            if (!hasWorldGuardPermissions(block.getLocation(), player, Flags.BLOCK_BREAK)) {
                canBreak = false;
            }
        }

        return canBreak;
    }

    public boolean playerCanPlace(Block block, Player player) {
        boolean canPlace = true;
        if(player.isOp()) return true;
        if (griefPrevention != null) {
            if (isPlayerNotInClaim(block.getLocation())) {
                canPlace = true;
            } else {
                canPlace = isPlayerInOwnOrTrustedClaim(player, block);
            }
        }

        if (towny != null) {
            canPlace = PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(), TownyPermission.ActionType.BUILD);
        }

        if (worldGuard != null) {
            if (!hasWorldGuardPermissions(block.getLocation(), player, Flags.BLOCK_PLACE)) {
                canPlace = false;
            }
        }

        return canPlace;
    }

    public boolean hasTownyBreakPerms(Block block, Player player) {
        if(towny == null) return true;
        return PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(), TownyPermission.ActionType.DESTROY);
    }


    private boolean isPlayerInOwnOrTrustedClaim(Player player, Block block) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(block.getLocation(), true, null);

        if (claim != null) {
            if (claim.getOwnerID().equals(player.getUniqueId())) {
                return true;
            } else return claim.allowAccess(player) == null;
        }

        return false;
    }

    private boolean isPlayerNotInClaim(Location location) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);

        return claim == null;
    }


    public boolean hasWorldGuardPermissions(Location location, Player player, StateFlag flags) {
        if(worldGuard == null) return true;
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
        return query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(player), flags);
    }
}
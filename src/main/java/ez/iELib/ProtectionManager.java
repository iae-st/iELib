package ez.iELib;

import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.extern.java.Log;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
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

    public ProtectionManager(Plugin plugin, Logger logger) {
        this.pluginInstance = plugin;
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
        if (griefPrevention != null) {
            if (isPlayerNotInClaim(block.getLocation())) {
                return true;
            } else return isPlayerInOwnOrTrustedClaim(player, block);
        }

        if (towny != null) {
            return PlayerCacheUtil.getCachePermission(player, block.getLocation(), block.getType(), TownyPermission.ActionType.SWITCH);
        }

        return true;
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

    public boolean hasWorldGuardPermissions(Location location, Player player) {
        if(worldGuard == null) return true;
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
        return query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(player), Flags.BUILD);
    }
}
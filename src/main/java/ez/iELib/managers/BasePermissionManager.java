package ez.iELib.managers;

import ez.iELib.iELib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;


public abstract class BasePermissionManager {

    public BasePermissionManager() {
        registerPermissions();
    }

    protected abstract void registerPermissions();

    protected void addPermission(String permission) {
        Permission perm = Bukkit.getPluginManager().getPermission(permission);
        if (perm == null) {
            iELib.getPlugin().getServer().getPluginManager().addPermission(new Permission(permission, PermissionDefault.FALSE));
        }
    }

    public void addPermissionToPlayer(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(iELib.getPlugin());
        attachment.setPermission(permission, true);
    }

    public void removePermissionFromPlayer(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(iELib.getPlugin());
        attachment.setPermission(permission, false);
    }
}
package ez.iELib.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import ez.iELib.iELib;
import ez.iELib.items.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;


public abstract class GuiTemplate implements Listener {
    public static ItemStack FILLER_GLASS = ItemBuilder.customItemName(Material.BLACK_STAINED_GLASS_PANE, " ");
    private final int rows;
    @Getter
    public ChestGui gui;
    @Getter
    public Player player;
    @Getter
    public OfflinePlayer offlinePlayer;
    private boolean registered;

    public GuiTemplate(int rows, String title, Player player) {
        this.gui = new ChestGui(rows, title);
        this.player = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        populateGUI();

        unregisterEvents(true);
        registerEvents();
    }

    public GuiTemplate(int rows, String title, OfflinePlayer player) {
        this.gui = new ChestGui(rows, title);
        this.offlinePlayer = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        populateGUI();

        unregisterEvents(true);
        registerEvents();
    }


    public GuiTemplate(int rows, String title, Player player, boolean willUnregister) {
        this.gui = new ChestGui(rows, title);
        this.player = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        populateGUI();

        unregisterEvents(willUnregister);
        registerEvents();
    }

    public GuiTemplate(int rows, String title, Player player, boolean isClickable, boolean willUnregister) {
        this.gui = new ChestGui(rows, title);
        this.player = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(isClickable));
        populateGUI();

        registerEvents();
        unregisterEvents(willUnregister);
    }

    protected abstract void populateGUI();


    public void openGUI() {
        gui.show(player);
    }

    public void applyBackground(int length, int height) {
        OutlinePane background = new OutlinePane(0, 0, length, height, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(ItemBuilder.customItemUsingStack(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), " ")));
        background.setRepeat(true);

        gui.addPane(background);
    }

    public void applyBackground(Material material, int length, int height) {
        OutlinePane background = new OutlinePane(0, 0, length, height, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(ItemBuilder.customItemUsingStack(new ItemStack(material), " ")));
        background.setRepeat(true);

        gui.addPane(background);
    }

    public void applyBorder() {

        StaticPane staticPane = new StaticPane(0, 0, 9 * rows, rows);
        GuiItem guiItem = new GuiItem(ItemBuilder.customItemUsingStack(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), " "), event -> event.setCancelled(true));

        for (int x = 0; x < 9; x++) {
            staticPane.addItem(guiItem, x, 0);
            staticPane.addItem(guiItem, x, rows - 1);
        }

        for (int y = 0; y < rows; y++) {
            staticPane.addItem(guiItem, 0, y);
            staticPane.addItem(guiItem, 9 - 1, y);
        }

        gui.addPane(staticPane);
    }


    public void unregisterEvents(Boolean willUnregister) {
        if (willUnregister) {
            gui.setOnClose(inventoryCloseEvent -> {
                HandlerList.unregisterAll(this);
            });
        }
    }



    public void registerEvents() {
        if (!registered) {
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(this, iELib.getPlugin());
            registered = true;
        }
    }
}

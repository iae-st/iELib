package ez.iELib.gui;

import com.github.stefvanschie.inventoryframework.adventuresupport.TextHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import ez.iELib.iELib;
import ez.iELib.items.ItemBuilder;
import ez.iELib.items.SkullCreator;
import ez.iELib.utils.FormatUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;


public abstract class GuiTemplate implements Listener {
    private final int rows;
    @Getter
    public ChestGui gui;
    @Getter
    public Player player;
    @Getter
    public OfflinePlayer offlinePlayer;
    private boolean registered;
    @Setter
    @Getter
    private double[][] stepOptions = {
            {0.1, 1, 10, 100, 1000, 10000, 100000}
    };

    @Getter
    private int[] currentStepIndex = {0};

    public GuiTemplate(int rows, String title, Player player) {
        title = title.replaceAll("&", "§");
        this.gui = new ChestGui(rows, title);
        this.player = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        populateGUI();

        unregisterEvents(true);
        registerEvents();
    }

    public GuiTemplate(int rows, String title, OfflinePlayer player) {
        title = title.replaceAll("&", "§");
        this.gui = new ChestGui(rows, title);
        this.offlinePlayer = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        populateGUI();

        unregisterEvents(true);
        registerEvents();
    }


    public GuiTemplate(int rows, String title, Player player, boolean willUnregister) {
        title = title.replaceAll("&", "§");
        this.gui = new ChestGui(rows, title);
        this.player = player;
        this.rows = rows;
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        populateGUI();

        unregisterEvents(willUnregister);
        registerEvents();
    }

    public GuiTemplate(int rows, String title, Player player, boolean isClickable, boolean willUnregister) {
        title = title.replaceAll("&", "§");
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
        OutlinePane background = new OutlinePane(0, 0, length, height, Pane.Priority.LOW);
        background.addItem(new GuiItem(ItemBuilder.customItemUsingStack(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), " ")));
        background.setRepeat(true);

        gui.addPane(background);
    }

    public void applyBackground(Material material, int length, int height) {
        OutlinePane background = new OutlinePane(0, 0, length, height, Pane.Priority.LOW);
        background.addItem(new GuiItem(ItemBuilder.customItemUsingStack(new ItemStack(material), " ")));
        background.setRepeat(true);

        gui.addPane(background);
    }

    public void applyBorder() {

        StaticPane staticPane = new StaticPane(0, 0, 9 * rows, rows, Pane.Priority.LOWEST);
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

    public void forceUnregister() {
        HandlerList.unregisterAll(this);
    }



    public void registerEvents() {
        if (!registered) {
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(this, iELib.getPlugin());
            registered = true;
        }
    }

    @FunctionalInterface
    private interface ValueSetter {
        void setValue(double value);
    }

    @FunctionalInterface
    private interface ValueGetter {
        double getValue();
    }

    protected List<String> getStepSizeDisplay(double[][] stepOptions, int currentStepIndex) {
        List<String> display = new ArrayList<>();
        display.add("&aAmount:");
        for (int i = 0; i < stepOptions[0].length; i++) {
            if (i == currentStepIndex) {
                display.add("&b⏩&7" + FormatUtils.formatNumber(stepOptions[0][i]) + "&b⏪");
            } else {
                display.add("&7" + FormatUtils.formatNumber(stepOptions[0][i]));
            }
        }
        return display;
    }

    public double getStep() {
        return stepOptions[0][currentStepIndex[0]];
    }




    public GuiItem getStepItem() {
        GuiItem stepSizeChanger = new GuiItem(ItemBuilder.customItemUsingStack(new ItemStack(Material.STONE_BUTTON), "&eStep Size Changer", getStepSizeDisplay(stepOptions, currentStepIndex[0])));

        stepSizeChanger.setAction(event -> {
            if (event.isLeftClick()) {
                currentStepIndex[0] = (currentStepIndex[0] + 1) % stepOptions[0].length;
                stepSizeChanger.setItem(ItemBuilder.customItemUsingStack(new ItemStack(Material.STONE_BUTTON), "&eStep Size Changer", getStepSizeDisplay(stepOptions, currentStepIndex[0])));
                gui.update();
            }
        });
        return stepSizeChanger;
    }
}
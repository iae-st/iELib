package ez.iELib.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import ez.iELib.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ConfirmationGUITemplate extends GuiTemplate {
    private final ItemStack yesItem;
    private final ItemStack noItem;

    public ConfirmationGUITemplate(Player player, ItemStack yesItem, ItemStack noItem) {
        super(3, "§aAre you sure?", player);
        this.yesItem = yesItem;
        this.noItem = noItem;
        applyBackground(9, 3);
        this.gui.addPane(outlinePane());
    }

    public ConfirmationGUITemplate(Player player) {
        super(3, "§aAre you sure?", player);
        this.yesItem = ItemBuilder.customItemUsingStack(new ItemStack(Material.LIME_WOOL), "§aYes!");
        this.noItem = ItemBuilder.customItemUsingStack(new ItemStack(Material.RED_WOOL), "§cNo!");;
        applyBackground(9, 3);
        this.gui.addPane(outlinePane());
    }

    @Override
    protected void populateGUI() {
    }

    private OutlinePane outlinePane() {
        OutlinePane outlinePane = new OutlinePane(3, 1, 3, 1);
        outlinePane.addItem(new GuiItem(yesItem, inventoryClickEvent -> {
            onYesClick();
        }));
        outlinePane.addItem(new GuiItem(FILLER_GLASS));
        outlinePane.addItem(new GuiItem(noItem, inventoryClickEvent -> {
            onNoClick();
        }));
        return outlinePane;
    }

    protected abstract void onYesClick();

    protected abstract void onNoClick();
}

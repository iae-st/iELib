package ez.iELib.wrappers.inventoryWrapper.scrollWrapper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.structure.Marker;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;

public class ScrollGui {
    private Player player;
    private List<Item> itemList;
    @Getter
    private Gui gui;
    private String title;
    @Setter
    private Marker marker = Markers.CONTENT_LIST_SLOT_HORIZONTAL;
    private Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"));
    public ScrollGui(Player player, String title, List<Item> itemList) {
        this.player = player;
        this.title = title;
        this.itemList = itemList;
    }

    public Gui build() {
        this.gui = xyz.xenondevs.invui.gui.ScrollGui.items()
                .setStructure(
                        "x x x x x x x x u",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x d")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('u', new ScrollUpItem())
                .addIngredient('d', new ScrollDownItem())
                .setContent(itemList)
                .build();
        return this.gui;
    }


    public void open() {
        Window window = Window.single()
                .setViewer(player)
                .setTitle(title)
                .setGui(gui)
                .build();

        window.open();
    }
}
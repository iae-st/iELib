package ez.iELib.wrappers.inventoryWrapper.pageWrapper;

import ez.iELib.wrappers.inventoryWrapper.scrollWrapper.ScrollDownItem;
import ez.iELib.wrappers.inventoryWrapper.scrollWrapper.ScrollUpItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Marker;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PageGui {

    public enum PageType { GUI, ITEM }
    private Player player;
    @Getter
    private Gui gui;
    private String title;
    @Setter
    private PageType pageType;
    @Setter
    private Marker marker = Markers.CONTENT_LIST_SLOT_HORIZONTAL;
    private Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"));
    @Setter
    private List<Gui> pagedGui = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    public PageGui(Player player, String title, List<?> list) {
        this.player = player;
        this.title = title;

        if(list.isEmpty()) return;
        if(list.get(0) instanceof Gui) {
            this.pagedGui = (List<Gui>) list;
            this.pageType = PageType.GUI;
        }

        if(list.get(0) instanceof Item) {
            this.itemList = (List<Item>) list;
            this.pageType = PageType.ITEM;
        }
    }

    public String[] getStructure() {
        return new String[]{
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                ". . < . . . > . ."
        };
    }

    public Gui build() {

        if(pageType == PageType.GUI) {
            this.gui = PagedGui.guis()
                    .setStructure(
                            getStructure())
                    .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL) // where paged items should be put (in this case: the parts of the nested GUI)
                    .addIngredient('<', new BackItem())
                    .addIngredient('>', new ForwardItem())
                    .setContent(pagedGui)
                    .build();
        } else if(pageType == PageType.ITEM) {
            this.gui = PagedGui.items()
                    .setStructure(
                            getStructure())
                    .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL) // where paged items should be put (in this case: the parts of the nested GUI)
                    .addIngredient('<', new BackItem())
                    .addIngredient('>', new ForwardItem())
                    .setContent(itemList)
                    .build();
        }
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
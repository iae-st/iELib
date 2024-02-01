package ez.iELib.wrappers.inventoryWrapper.items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

import java.util.function.Consumer;

public class ForwardEventItem extends PageItem {
    private final ItemStack itemStack;
    private final Consumer<InventoryClickEvent> clickEventConsumer;
    public ForwardEventItem(ItemStack itemStack, Consumer<InventoryClickEvent> clickEventConsumer) {
        super(true);
        this.itemStack = itemStack;
        this.clickEventConsumer = clickEventConsumer;
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {
        return new ItemBuilder(itemStack);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        getGui().goForward();
//        clickEventConsumer.accept(event);
    }

}
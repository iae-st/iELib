package ez.iELib.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.jetbrains.annotations.NotNull;

public class SimpleStaticPane extends StaticPane {
    public SimpleStaticPane(Slot slot, int length, int height, @NotNull Pane.Priority priority) {
        super(slot, length, height, priority);
    }

    public SimpleStaticPane(int x, int y, int length, int height, @NotNull Pane.Priority priority) {
        super(x, y, length, height, priority);
    }

    public SimpleStaticPane(Slot slot, int length, int height) {
        super(slot, length, height);
    }

    public SimpleStaticPane(int x, int y, int length, int height) {
        super(x, y, length, height);
    }

    public SimpleStaticPane(int length, int height) {
        super(length, height);
    }

    public void setBackground(GuiItem item){
        for (int x = 0; x < this.length; x++)   {
            for (int y = 0; y < this.height; y++)   {
                this.addItem(item, x, y);
            }
        }
    }


    public void addBorder(GuiItem item) {

        for (int x = 0; x < this.length; x++) {
            this.addItem(item, x, 0);
            this.addItem(item, x, 4 - 1);
        }

        for (int y = 0; y < this.height; y++) {
            this.addItem(item, 0, y);
            this.addItem(item, 9 - 1, y);
        }
    }
}
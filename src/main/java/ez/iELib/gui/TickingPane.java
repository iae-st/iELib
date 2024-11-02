package ez.iELib.gui;


import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import ez.iELib.ticking.TickingAction;
import org.jetbrains.annotations.NotNull;

public abstract class TickingPane extends SimpleStaticPane {


    private TickingAction onTick;

    public TickingPane(Slot slot, int length, int height, @NotNull Priority priority) {
        super(slot, length, height, priority);
    }

    public TickingPane(int x, int y, int length, int height, @NotNull Priority priority) {
        super(x, y, length, height, priority);
    }

    public TickingPane(Slot slot, int length, int height) {
        super(slot, length, height);
    }

    public TickingPane(int x, int y, int length, int height) {
        super(x, y, length, height);
    }


    public void setOnTick(TickingAction action) {
        this.onTick = action;
    }

    public void tick() {
        this.onTick.tick();
    }

}
package ez.iELib.gui;

import com.github.stefvanschie.inventoryframework.adventuresupport.TextHolder;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import ez.iELib.ticking.TickingAction;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TickingGui extends ChestGui {

    private long delay;
    private long period;
    private TickingAction tickingAction;

    public TickingGui(int rows, @NotNull String title, long delay, long period) {
        super(rows, title);
        this.delay = delay;
        this.period = period;
    }

    public TickingGui(int rows, @NotNull TextHolder title, long delay, long period) {
        super(rows, title);
        this.delay = delay;
        this.period = period;
    }

    public TickingGui(int rows, @NotNull String title, @NotNull Plugin plugin, long delay, long period) {
        super(rows, title, plugin);
        this.delay = delay;
        this.period = period;
    }

    public TickingGui(int rows, @NotNull TextHolder title, @NotNull Plugin plugin, long delay, long period) {
        super(rows, title, plugin);
        this.delay = delay;
        this.period = period;
    }


    public void open(@NotNull HumanEntity humanEntity) {
        super.show(humanEntity);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                getPanes().forEach(pane -> {
                    if (pane instanceof TickingPane) {
                        ((TickingPane) pane).tick();
                    }
                });
                if(tickingAction != null)
                    tickingAction.tick();
                update();
            }
        }.runTaskTimer(plugin, delay, period);
        setOnClose(event -> task.cancel());
    }

    public void setOnTick(TickingAction action) {
        this.tickingAction = action;
    }
}
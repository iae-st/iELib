package ez.iELib.utils.colorUtils;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class RGBColor {

    @Getter
    private final String hex;
    @Getter
    private final ChatColor color;
    @Getter
    private final String componentColor;

    public RGBColor(String hex) {
        this.hex = hex;
        this.color = ChatColor.of(hex);
        this.componentColor = "<color:" + hex + ">";
    }


}

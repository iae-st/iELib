package ez.iELib.utils.colorUtils;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class RGBColor {

    @Getter
    private final String hex;
    @Getter
    private final ChatColor color;

    public RGBColor(String hex) {
        this.hex = hex;
        this.color = ChatColor.of(hex);
    }


}

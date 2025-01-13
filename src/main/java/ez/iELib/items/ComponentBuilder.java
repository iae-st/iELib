package ez.iELib.items;

import ez.iELib.utils.colorUtils.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentBuilder {
    public static Component createComponent(String text) {
        Component sectionComponent = LegacyComponentSerializer.legacySection().deserialize(text);
        String legacyAmpersand = LegacyComponentSerializer.legacyAmpersand().serialize(sectionComponent);
        Component ampersandComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(legacyAmpersand);
        String miniMessageString = MiniMessage.miniMessage().serialize(ampersandComponent);
        return MiniMessage.miniMessage().deserialize("<!i>" + miniMessageString);
    }

}
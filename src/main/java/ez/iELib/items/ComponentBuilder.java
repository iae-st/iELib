package ez.iELib.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentBuilder {
    public static Component createComponent(String text) {
        String miniMessageString = MiniMessage.miniMessage().serialize(
                LegacyComponentSerializer.legacySection().deserialize(text));
        return MiniMessage.miniMessage().deserialize("<!i>" + miniMessageString);
    }


    public static Component createComponent(String text, String... replacements) {
        String miniMessageString = MiniMessage.miniMessage().serialize(
                LegacyComponentSerializer.legacySection().deserialize(text));
        for (String replacement : replacements) {
            miniMessageString = miniMessageString.replaceFirst("%s", replacement);
        }
        return MiniMessage.miniMessage().deserialize("<!i>" + miniMessageString);
    }
}
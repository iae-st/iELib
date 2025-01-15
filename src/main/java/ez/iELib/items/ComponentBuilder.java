package ez.iELib.items;

import ez.iELib.utils.colorUtils.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentBuilder {
    public static Component createComponent(String text) {
        String replaced = text.replaceAll("§", "&");
        MiniMessage mm = MiniMessage.builder()
                .tags(
                        TagResolver.builder()
                                .resolver(StandardTags.defaults())
                                .build()
                )
                .build();
        return mm.deserialize(replaced);
    }

}
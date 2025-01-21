package ez.iELib.items;

import ez.iELib.utils.colorUtils.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentBuilder {
    public static Component createComponent(String text) {
        String replaced = text.replaceAll("§", "&");
        Pattern pattern = Pattern.compile("&([0-9a-fk-or])");
        Matcher matcher = pattern.matcher(replaced);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String replacement = switch (matcher.group(1).toLowerCase()) {
                case "0" -> "<black>";
                case "1" -> "<dark_blue>";
                case "2" -> "<dark_green>";
                case "3" -> "<dark_aqua>";
                case "4" -> "<dark_red>";
                case "5" -> "<dark_purple>";
                case "6" -> "<gold>";
                case "7" -> "<gray>";
                case "8" -> "<dark_gray>";
                case "9" -> "<blue>";
                case "a" -> "<green>";
                case "b" -> "<aqua>";
                case "c" -> "<red>";
                case "d" -> "<light_purple>";
                case "e" -> "<yellow>";
                case "f" -> "<white>";
                case "k" -> "<obfuscated>";
                case "l" -> "<bold>";
                case "m" -> "<strikethrough>";
                case "n" -> "<underlined>";
                case "o" -> "<italic>";
                case "r" -> "<reset>";
                default -> "";
            };
            matcher.appendReplacement(buffer, replacement);
        }

        matcher.appendTail(buffer);
        MiniMessage mm = MiniMessage.builder()
                .tags(
                        TagResolver.builder()
                                .resolver(StandardTags.defaults())
                                .build()
                )
                .build();
        return mm.deserialize(buffer.toString()).decoration(TextDecoration.ITALIC, false);
    }





}
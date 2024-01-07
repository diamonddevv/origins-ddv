package dev.diamond.ddvorigins.power.badge;

import dev.diamond.ddvorigins.DDVOrigins;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.badge.Badge;
import io.github.apace100.origins.badge.BadgeFactory;
import io.github.apace100.origins.badge.TooltipBadge;
import net.minecraft.block.Block;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public record TagBadge(Identifier spriteId, Text text, Identifier tag, List<Identifier> except, TagType type) implements Badge {

    public enum TagType {
        Block, Item, EntityType;


        @Override
        public String toString() {
            return switch (this) {
                case Block -> "block";
                case Item -> "item";
                case EntityType -> "entity";
            };
        }
    }

    public TagBadge(SerializableData.Instance instance, TagType type) {
        this(instance.getId("sprite"), instance.get("text"), instance.getId("tag"), instance.get("except"), type);
    }

    @Override
    public boolean hasTooltip() {
        return true;
    }

    public static void addTags(List<TooltipComponent> tooltips, Identifier tag, List<Identifier> except, TextRenderer renderer, int widthLimit, TagType type) {

        Collection<String> lines = new ArrayList<>();

        switch (type) {
            case Block -> Registries.BLOCK.forEach(block -> {
                TagKey<Block> key = TagKey.of(RegistryKeys.BLOCK, tag);
                if (block.getDefaultState().isIn(key)) {
                    boolean canAdd = true;
                    for (Identifier id : except) {
                        TagKey<Block> eKey = TagKey.of(RegistryKeys.BLOCK, id);
                        if (block.getDefaultState().isIn(eKey)) {
                            canAdd = false;
                            break;
                        }
                    }

                    if (canAdd) lines.add(block.getTranslationKey());
                }
            });
            case Item -> Registries.ITEM.forEach(item -> {
                TagKey<Item> key = TagKey.of(RegistryKeys.ITEM, tag);
                if (item.getDefaultStack().isIn(key)) {
                    boolean canAdd = true;
                    for (Identifier id : except) {
                        TagKey<Item> eKey = TagKey.of(RegistryKeys.ITEM, id);
                        if (item.getDefaultStack().isIn(eKey)) {
                            canAdd = false;
                            break;
                        }
                    }

                    if (canAdd) lines.add(item.getTranslationKey());
                }
            });
            case EntityType -> Registries.ENTITY_TYPE.forEach(entityType -> {
                TagKey<EntityType<?>> key = TagKey.of(RegistryKeys.ENTITY_TYPE, tag);
                if (entityType.isIn(key)) {
                    boolean canAdd = true;
                    for (Identifier id : except) {
                        TagKey<EntityType<?>> eKey = TagKey.of(RegistryKeys.ENTITY_TYPE, id);
                        if (entityType.isIn(eKey)) {
                            canAdd = false;
                            break;
                        }
                    }

                    if (canAdd) lines.add(entityType.getTranslationKey());
                }
            });
        }

        for (String s : lines){
            Text text = Text.literal("- ").append(Text.translatable(s));

            if (renderer.getWidth(text) > widthLimit) {
                for (OrderedText orderedText : renderer.wrapLines(text, widthLimit)) {
                    tooltips.add(new OrderedTextTooltipComponent(orderedText));
                }
            } else {
                tooltips.add(new OrderedTextTooltipComponent(text.asOrderedText()));
            }
        }
    }

    @Override
    public List<TooltipComponent> getTooltipComponents(PowerType<?> powerType, int widthLimit, float time, TextRenderer textRenderer) {
        List<TooltipComponent> tooltips = new LinkedList<>();
        TooltipBadge.addLines(tooltips, text, textRenderer, widthLimit); // add header

        addTags(tooltips, tag, except, textRenderer, widthLimit, type);

        return tooltips;
    }

    @Override
    public SerializableData.Instance toData(SerializableData.Instance instance) {
        instance.set("sprite", spriteId);
        instance.set("text", text);
        instance.set("tag", tag);
        instance.set("except", except);
        return instance;
    }

    @Override
    public BadgeFactory getBadgeFactory() {
        return switch (type) {
            case Block -> buildFactory(TagType.Block);
            case Item -> buildFactory(TagType.Item);
            case EntityType -> buildFactory(TagType.EntityType);
        };
    }


    public static BadgeFactory buildFactory(TagType type) {
        return new BadgeFactory(DDVOrigins.id(type.toString() + "_tag"),
                new SerializableData()
                        .add("sprite", SerializableDataTypes.IDENTIFIER)
                        .add("text", SerializableDataTypes.TEXT)
                        .add("tag", SerializableDataTypes.IDENTIFIER)
                        .add("except", SerializableDataTypes.IDENTIFIERS, List.of()),
                (factory) -> new TagBadge(factory, type));
    }
}

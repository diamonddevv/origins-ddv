package dev.diamond.ddvorigins.effect;

import dev.diamond.ddvorigins.DDVOrigins;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class AbysmalIntoxicationEffect extends StatusEffect {
    public static final Identifier HEARTS = DDVOrigins.id("textures/gui/effect_hearts/abysmal.png");
    public static final Identifier HUNGER = DDVOrigins.id("textures/gui/effect_shanks/abysmal.png");

    private static final double FACTOR = -1 + 0.85;

    public AbysmalIntoxicationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x3A2742);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, MathHelper.randomUuid().toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }


    public static int calculateLengthFood(Item item) {
        if (item.isFood()) {
            double saturation = Objects.requireNonNull(item.getFoodComponent()).getSaturationModifier() * 2 * item.getFoodComponent().getSaturationModifier();

            return (int) Math.floor(50 * Math.pow(saturation , .5)); // time = 50 * saturation ^ (1/2) -> FLOORED
        } else return 0;
    }
}

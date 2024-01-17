package dev.diamond.ddvorigins.effect;

import dev.diamond.ddvorigins.DDVOrigins;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class VoidStungEffect extends StatusEffect {
    public static final Identifier HEARTS = DDVOrigins.id("textures/gui/effect_hearts/void_stung.png");

    public VoidStungEffect() {
        super(StatusEffectCategory.HARMFUL, 0x3f1345);
    }
}

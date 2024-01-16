package dev.diamond.ddvorigins.item;

import dev.diamond.ddvorigins.effect.AbysmalIntoxicationEffect;
import dev.diamond.ddvorigins.power.type.VoidFruitEatingPower;
import dev.diamond.ddvorigins.registry.InitEffects;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class VoidFruitItem extends Item {
    public VoidFruitItem() {
        super(new FabricItemSettings()
                .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())
                .maxCount(64));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        if (isFood()) {        // on eaten

            if (!PowerHolderComponent.hasPower(user, VoidFruitEatingPower.class)) {
                int length = AbysmalIntoxicationEffect.calculateLengthFood(this) * 20;
                user.addStatusEffect(new StatusEffectInstance(InitEffects.ABYSMAL_INTOXICATION, length, 0));
            }

            return stack;

        }

        return stack;
    }
}

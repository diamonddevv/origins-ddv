package dev.diamond.ddvorigins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.diamond.ddvorigins.power.type.VoidwalkPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @WrapOperation(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setPosition(DDD)V"))
    private static void ddvorigins$addFakeFloorForVoidwalk(Entity instance, double x, double y, double z, Operation<Void> original) {
        if (PowerHolderComponent.hasPower(instance, VoidwalkPower.class)) {
            List<VoidwalkPower> powers = PowerHolderComponent.getPowers(instance, VoidwalkPower.class);
            int floor = Integer.MAX_VALUE;
            for (VoidwalkPower power : powers) {
                if (power.getFloor(instance.getWorld()) < floor) {
                    floor = power.getFloor(instance.getWorld());
                }
            }

            if (y <= floor - 1) {
                y = instance.getY();
            }

        }
        original.call(instance, x, y, z);
    }
}

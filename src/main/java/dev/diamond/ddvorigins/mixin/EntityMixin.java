package dev.diamond.ddvorigins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.diamond.ddvorigins.DDVOrigins;
import dev.diamond.ddvorigins.power.type.VoidwalkPower;
import dev.diamond.ddvorigins.util.DDVOMath;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static dev.diamond.ddvorigins.util.Util.test;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @WrapOperation(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setPosition(DDD)V", ordinal = 1))
    private static void ddvorigins$addFakeFloorForVoidwalk(Entity instance, double x, double y, double z, Operation<Void> original) {
        if (PowerHolderComponent.hasPower(instance, VoidwalkPower.class)) {
            List<VoidwalkPower> powers = PowerHolderComponent.getPowers(instance, VoidwalkPower.class);


            int floor = Integer.MAX_VALUE; // no way the boring algorithms from my CS class comes in useful
            for (VoidwalkPower power : powers) {
                if (power.getFloor(instance.getWorld()) < floor) {
                    floor = power.getFloor(instance.getWorld());
                }
            }

            if (y <= floor) {

                if (!DDVOMath.approxEquals((float)y, floor, .5f)) y = floor;

                if (instance instanceof LivingEntity le) {
                    if (((LivingEntityAccessor)le).accessJumping()) {
                        le.getWorld().getProfiler().push("jump"); // i dont know if i need to do this but its probably a good idea to push the profiler
                        ((LivingEntityInvoker)le).invokeJump();
                    }
                }

                instance.setOnGround(true);
            }
        }

        original.call(instance, x, y, z);
    }

}

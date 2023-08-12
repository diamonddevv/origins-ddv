package net.diamonddev.ddvorigins.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.diamonddev.ddvorigins.power.type.AerialAffinityPower;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @WrapOperation(
            method = "getBlockBreakingSpeed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isOnGround()Z")
    )
    private boolean ddvorigins$applyAerialAffinity(PlayerEntity entity, Operation<Boolean> op) {
        if (!op.call(entity)) {
            if (PowerHolderComponent.hasPower(entity, AerialAffinityPower.class)) {
                return true;
            }
        }

        return op.call(entity);
    }
}

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
    private boolean ddvorigins$applyAerialAffinity(PlayerEntity player, Operation<Boolean> op) {
        if (PowerHolderComponent.hasPower(player, AerialAffinityPower.class)) {
            return false;
        } else return op.call(player);
    }
}

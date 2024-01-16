package dev.diamond.ddvorigins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @WrapOperation(
            method = "respawnPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;findRespawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;FZZ)Ljava/util/Optional;")
    )
    private Optional<Vec3d> ddvorigins$forceRespawnWhenSpawnIsInTheVoidThisWillHaveNoReprocussionsWhatsoever(
            ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, Operation<Optional<Vec3d>> original) {

        if (pos.getY() < world.getBottomY()) {
            return Optional.of(pos.toCenterPos());
        }
        return original.call(world, pos, angle, forced, alive);
    }
}

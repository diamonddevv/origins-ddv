package dev.diamond.ddvorigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import dev.diamond.ddvorigins.power.type.VoidspawnPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyReturnValue(at = @At("RETURN"), method = "getSpawnPointPosition")
    private BlockPos ddvorigins$setVoidSpawnIfNeeded(BlockPos original) {
        if (PowerHolderComponent.hasPower(this, VoidspawnPower.class)) {
            if (original == null) {
                return new BlockPos(getWorld().getSpawnPos().getX(), getWorld().getBottomY() - 10, getWorld().getSpawnPos().getZ());
            }
        } return original;
    }
}

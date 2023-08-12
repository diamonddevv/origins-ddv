package net.diamonddev.ddvorigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.diamonddev.ddvorigins.power.type.PreventWaterAmbientSoundsPower;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.AmbientSoundPlayer;
import net.minecraft.client.util.ClientPlayerTickable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AmbientSoundPlayer.class)
public abstract class AmbientSoundPlayerMixin implements ClientPlayerTickable {
    @Shadow @Final private ClientPlayerEntity player;

    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSubmergedInWater()Z")
    )
    private boolean ddvorigins$preventAmbientWaterSoundLoopAdditions(boolean original) {
        return original && !PowerHolderComponent.hasPower(this.player, PreventWaterAmbientSoundsPower.class);
    }
}

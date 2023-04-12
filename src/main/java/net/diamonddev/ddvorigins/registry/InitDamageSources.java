package net.diamonddev.ddvorigins.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.damage.DamageSource;

public class InitDamageSources implements RegistryInitializer {

    public static DamageSource RELOCATED_INTO;

    @Override
    public void register() {
        RELOCATED_INTO = new DamageSource("vai_relocated_into").setBypassesArmor().setUsesMagic();
    }
}

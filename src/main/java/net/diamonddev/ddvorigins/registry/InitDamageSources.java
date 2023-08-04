package net.diamonddev.ddvorigins.registry;

import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class InitDamageSources implements RegistryInitializer {


    public static RegistryKey<DamageType>
            VAI_TIME, VAI_RELOCATE;


    @Override
    public void register() {
        VAI_TIME = create(DDVOrigins.id("vai_time"));
        VAI_RELOCATE = create(DDVOrigins.id("vai_relocated_into"));
    }

    private static RegistryKey<DamageType> create(Identifier id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id);
    }

    public static DamageSource get(Entity entity, RegistryKey<DamageType> type, @Nullable Entity sourceOrProjectile, @Nullable Entity attacker) {
        if (attacker != null) return entity.getDamageSources().create(type, sourceOrProjectile, attacker);
        if (sourceOrProjectile != null) return entity.getDamageSources().create(type, sourceOrProjectile);
        return entity.getDamageSources().create(type);
    }
}

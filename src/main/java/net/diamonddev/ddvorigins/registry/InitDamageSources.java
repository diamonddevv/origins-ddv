package net.diamonddev.ddvorigins.registry;

import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class InitDamageSources {


    public static class RelocationDamageSource extends EntityDamageSource {
        private RelocationDamageSource(Entity source) {
            super("vai_relocated_into", source);
            this.setBypassesArmor().setUsesMagic();
        }

        public static RelocationDamageSource create(Entity source) {
            return new RelocationDamageSource(source);
        }
    }

    public static class TimeDamageSource extends EntityDamageSource {
        private TimeDamageSource(Entity source) {
            super("vai_time", source);
            this.setBypassesArmor().setUsesMagic();
        }

        public static TimeDamageSource create(Entity source) {
            return new TimeDamageSource(source);
        }
    }
}

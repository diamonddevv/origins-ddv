package net.diamonddev.ddvorigins.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.text.Text;

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
        private static final String NAME = "vai_time";
        private TimeDamageSource(Entity source) {
            super(NAME, source);
            this.setUsesMagic();
        }

        @Override
        public Text getDeathMessage(LivingEntity entity) {
            String s = "death.attack." + this.name;
            boolean bl = false;
            if (this.source != null) return Text.translatable(s + ".entity", entity.getDisplayName(), source.getDisplayName());
            else return Text.translatable(s, entity.getDisplayName());
        }

        public static TimeDamageSource create(Entity source) {
            return new TimeDamageSource(source);
        }
    }
}

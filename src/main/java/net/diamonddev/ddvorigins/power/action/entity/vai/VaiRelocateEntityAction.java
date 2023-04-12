package net.diamonddev.ddvorigins.power.action.entity.vai;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.registry.InitDamageSources;
import net.diamonddev.ddvorigins.util.FXUtil;
import net.diamonddev.libgenetics.common.api.v1.util.TriConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class VaiRelocateEntityAction { // alot of the code for this was taken from the raycast code so yeah

    private static void action(SerializableData.Instance data, Entity entity) {
        Vec3d originPoint = entity.getPos();
        VaiRelocationInstance relocationInstance = new VaiRelocationInstance(data, entity, originPoint, data.getDouble("distance"));
        relocationInstance.before();
        relocationInstance.execute();
    }
    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(DDVOrigins.id("vai_atomicrelocate"),
                new SerializableData()
                        .add("distance", SerializableDataTypes.DOUBLE)
                        .add("amplify_distance", SerializableDataTypes.DOUBLE)
                        .add("max_amplifies", SerializableDataTypes.INT),
                VaiRelocateEntityAction::action
        );
    }


    public static class VaiRelocationInstance {
        private final SerializableData.Instance data;
        private final Entity entity;

        private final Vec3d target;
        private final Vec3d originPoint;
        private final Vec3d rotVec;

        private final int maxAmplifies;
        private boolean continuing;

        private int amplifications = 0;

        public VaiRelocationInstance(SerializableData.Instance data, Entity entity, Vec3d originPoint, double distance) {
            this.data = data;
            this.entity = entity;

            this.maxAmplifies = data.getInt("max_amplifies");

            this.originPoint = originPoint;
            this.rotVec = entity.getRotationVec(1);
            this.target = originPoint.add(rotVec.multiply(distance));

            this.continuing = true;
        }

        public void execute() {
            onStep(((blockPos, blockState, step) -> {
                FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, entity.getX(), entity.getY(), entity.getZ(), 0f, 0f, 0f, 0f, 1), entity.world);

                if (shouldCancel(blockState)) {
                    onCancelled(blockPos);
                    return;
                }

                if (shouldAmplify(blockState)) {
                    onAmplify(blockPos);
                    return;
                }

                if (!shouldIgnoreBlock(blockState)) {
                    hitsBlock(blockPos);
                    return;
                }

                List<Entity> entities = entity.world.getOtherEntities(null, new Box(blockPos, blockPos), VaiRelocationInstance::shouldIgnoreEntity);
                if (!entities.isEmpty()) {
                    hitsEntity(entities.get(0));
                    return;
                }

                if (step >= originPoint.distanceTo(target)) {
                    hitsNothing();
                }
            }));

        }

        private void onStep(TriConsumer<BlockPos, BlockState, Double> performEachStep) {
            Vec3d direction = target.subtract(originPoint).normalize();
            double length = originPoint.distanceTo(target);
            for(double current = 0; current < length; current++) {
                if (continuing) {
                    Vec3d pos = originPoint.add(direction.multiply(current));
                    BlockPos bp = new BlockPos(pos);
                    performEachStep.accept(bp, entity.world.getBlockState(bp), current);
                }
            }
        }

        private void hitsEntity(Entity target) {
            entity.teleport(target.getX(), target.getY(), target.getZ());
            target.damage(InitDamageSources.RELOCATED_INTO, 8f);
            after();
        }
        private void hitsBlock(BlockPos target) {
            entity.teleport(target.getX(), target.getY(), target.getZ());
            after();
        }
        private void hitsNothing() {
            entity.teleport(target.getX(), target.getY(), target.getZ());
            after();
        }
        private void onCancelled(BlockPos cancelPos) {
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 0.1f, 0.1f, 0.1f, 0f, 50), entity.world);
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.CRIT, true, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 0.1f, 0.1f, 0.1f, 0f, 50), entity.world);
            FXUtil.playSounds(new FXUtil.SoundsData(Registries.SOUND_EVENT.get(new Identifier("minecraft:entity.ender_eye.death")), SoundCategory.MASTER, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 1f, 0.1f), entity.world);
            FXUtil.playSounds(new FXUtil.SoundsData(Registries.SOUND_EVENT.get(new Identifier("minecraft:entity.ender_eye.death")), SoundCategory.MASTER, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 1f, 2f), entity.world);

            continuing = false;
        }
        private void onAmplify(BlockPos amplificationPos) {
            if (amplifications <= maxAmplifies) {
                FXUtil.spawnParticles(
                        List.of(
                                new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, entity.getX(), entity.getY(), entity.getZ(), 0.1f, 0.1f, 0.1f, 0.3f, 150),
                                new FXUtil.ParticlesData<>(ParticleTypes.END_ROD, true, entity.getX(), entity.getY(), entity.getZ(), 0.1f, 0.1f, 0.1f, 0.1f, 50)
                        ), entity.world);

                FXUtil.playSounds(
                        List.of(
                                new FXUtil.SoundsData(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.MASTER, entity.getX(), entity.getY(), entity.getZ(), 1f, 1.4f),
                                new FXUtil.SoundsData(SoundEvents.BLOCK_ENDER_CHEST_CLOSE,SoundCategory.MASTER, entity.getX(), entity.getY(), entity.getZ(), 1f, 0.4f)
                        ), entity.world);

                continuing = false;
                amplifications++;

                var next = new VaiRelocationInstance(data, entity, new Vec3d(amplificationPos.getX(), amplificationPos.getY(), amplificationPos.getZ()), data.getDouble("amplify_distance"));
                next.execute();
            }
        }

        public static boolean shouldAmplify(BlockState state) {
            return state.isIn(TagKey.of(RegistryKeys.BLOCK, DDVOrigins.id("amplifying_blocks")));
        }
        public static boolean shouldCancel(BlockState state) {
            return state.isIn(TagKey.of(RegistryKeys.BLOCK, DDVOrigins.id("cancelling_blocks")));
        }

        private static boolean shouldIgnoreEntity(Entity entity) {
            return !(entity instanceof LivingEntity);
        }
        private static boolean shouldIgnoreBlock(BlockState state) {
            return state.isIn(TagKey.of(RegistryKeys.BLOCK, DDVOrigins.id("ignored_blocks")));
        }

        public void before() {
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, entity.getX(), entity.getY(), entity.getZ(), 0f, 0.5f, 0f, 0.8f, 100), entity.world);
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.END_ROD, true, entity.getX(), entity.getY(), entity.getZ(), 0f, 0.5f, 0f, 0.1f, 100), entity.world);
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, entity.getX(), entity.getY(), entity.getZ(), 0.1f, 0.5f, 0.1f, 0.3f, 100), entity.world);
        }
        public void after() {
            FXUtil.playSounds(new FXUtil.SoundsData(Registries.SOUND_EVENT.get(new Identifier("minecraft:entity.enderman.teleport")), SoundCategory.MASTER, entity.getX(), entity.getY(), entity.getZ(), 1f, 0.8f), entity.world);
        }
    }
}

package net.diamonddev.ddvorigins.power.action.entity.vai;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.diamonddev.ddvorigins.DDVOrigins;
import net.diamonddev.ddvorigins.registry.InitDamageSources;
import net.diamonddev.ddvorigins.util.DDVOriginsConfig;
import net.diamonddev.ddvorigins.util.FXUtil;
import net.diamonddev.ddvorigins.util.TriFunction;
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
import java.util.function.BiConsumer;

public class VaiRelocateEntityAction { // alot of the code for this was taken from the raycast code so yeah

    private static void action(SerializableData.Instance data, Entity entity) {
        Vec3d originPoint = entity.getEyePos();
        VaiRelocationInstance relocationInstance = new VaiRelocationInstance(data, entity, originPoint, data.getDouble("distance"), data.getInt("max_amplifies"));
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
        private final double density;
        private boolean targetMet;
        private boolean continuing;

        private int amplifications = 0;

        public VaiRelocationInstance(SerializableData.Instance data, Entity entity, Vec3d originPoint, double distance, int maxAmplifies) {
            this.data = data;
            this.entity = entity;

            this.maxAmplifies = maxAmplifies;
            this.density = DDVOriginsConfig.SERVER.originCfg.vaiRelocationLogicDensity;

            this.originPoint = originPoint;
            this.rotVec = entity.getRotationVec(1);
            this.target = originPoint.add(rotVec.multiply(distance));

            this.continuing = true;
            this.targetMet = false;
        }

        public void execute() {
            onStep(((vec, blockState, step) -> {
                BlockPos pos = new BlockPos(vec);
                FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, pos.toCenterPos().getX(), pos.toCenterPos().getY(), pos.toCenterPos().getZ(), 0f, 0f, 0f, 0f, 5), entity.world);

                if (shouldCancel(blockState)) {
                    onCancelled(vec);
                    return true;
                }

                if (shouldAmplify(blockState)) {
                    onAmplify(vec);
                    return true;
                }

                double d = DDVOriginsConfig.SERVER.originCfg.vaiRelocateIntoEntityLeeway;
                List<Entity> entities = entity.world.getOtherEntities(null, new Box(vec.add(-d,-d,-d), vec.add(d,d,d)), this::shouldHitEntity);
                if (!entities.isEmpty()) {
                    hitsEntity(entities.get(0));
                    return true;
                }

                if (!shouldIgnoreBlock(blockState)) {
                    hitsBlock(vec);
                    return true;
                }

                return false;
            }),
                    ((vec, blockState) -> {
                        if (targetMet) hitsNothing();
                    })
            );

        }

        private void onStep(TriFunction<Vec3d, BlockState, Double, Boolean> performEachStep, BiConsumer<Vec3d, BlockState> performAtEnd) {
            Vec3d direction = target.subtract(originPoint).normalize();
            double length = originPoint.distanceTo(target);
            for(double current = 0; current <= length; current += density) {
                if (continuing) {
                    Vec3d pos = originPoint.add(direction.multiply(current));
                    continuing = !performEachStep.accept(pos, entity.world.getBlockState(new BlockPos(pos)), current);
                }
            }
            if (continuing) targetMet = true;
            performAtEnd.accept(target, entity.world.getBlockState(new BlockPos(target)));
        }

        private static void teleport(Entity entityToTeleport, boolean upOne, Vec3d coords) {
            if (!upOne) entityToTeleport.teleport(coords.x, coords.y, coords.z);
            else entityToTeleport.teleport(coords.x, coords.y + 1, coords.z);
        }

        private void hitsEntity(Entity target) {
            teleport(entity, false, target.getPos());
            target.damage(InitDamageSources.RelocationDamageSource.create(entity), 8f);
            after();
        }
        private void hitsBlock(Vec3d target) {
            teleport(entity, true, target);
            after();
        }
        private void hitsNothing() {
            teleport(entity, false, target);
            after();
        }

        private void onCancelled(Vec3d cancelPos) {
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.ENCHANTED_HIT, true, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 0.1f, 0.1f, 0.1f, 0f, 50), entity.world);
            FXUtil.spawnParticles(new FXUtil.ParticlesData<>(ParticleTypes.CRIT, true, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 0.1f, 0.1f, 0.1f, 0f, 50), entity.world);
            FXUtil.playSounds(new FXUtil.SoundsData(Registries.SOUND_EVENT.get(new Identifier("minecraft:entity.ender_eye.death")), SoundCategory.MASTER, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 1f, 0.1f), entity.world);
            FXUtil.playSounds(new FXUtil.SoundsData(Registries.SOUND_EVENT.get(new Identifier("minecraft:entity.ender_eye.death")), SoundCategory.MASTER, cancelPos.getX(), cancelPos.getY(), cancelPos.getZ(), 1f, 2f), entity.world);
        }
        private void onAmplify(Vec3d amplificationPos) {
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

                amplifications++;

                var next = new VaiRelocationInstance(data, entity,
                        amplificationPos.add(rotVec.multiply(1.5)),
                        data.getDouble("amplify_distance"), maxAmplifies - 1);
                next.execute();
            }
        }

        public static boolean shouldAmplify(BlockState state) {
            return state.isIn(TagKey.of(RegistryKeys.BLOCK, DDVOrigins.id("amplifying_blocks")));
        }
        public static boolean shouldCancel(BlockState state) {
            return state.isIn(TagKey.of(RegistryKeys.BLOCK, DDVOrigins.id("cancelling_blocks")));
        }

        private boolean shouldHitEntity(Entity entity) {
            return entity instanceof LivingEntity && entity != this.entity;
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
            FXUtil.playSounds(new FXUtil.SoundsData(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.MASTER, entity.getX(), entity.getY(), entity.getZ(), 1f, 0.8f), entity.world);
        }
    }
}

package org.jurassicraft.server.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.entity.DinosaurEntity;

import java.util.LinkedList;
import java.util.List;

public class SelectTargetEntityAI<T extends EntityLivingBase> extends EntityAIBase {
    private DinosaurEntity entity;
    private Class<T> targetClass;
    private EntityLivingBase targetEntity;

    public SelectTargetEntityAI(DinosaurEntity entity, Class<T> targetClass) {
        this.entity = entity;
        this.targetClass = targetClass;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.entity.resetAttackCooldown();
    }

    @Override
    public void startExecuting() {
        this.entity.setAttackTarget(this.targetEntity);

        Herd herd = this.entity.herd;

        if (herd != null && this.targetEntity != null) {
            List<EntityLivingBase> enemies = new LinkedList<>();

            if (this.targetEntity instanceof DinosaurEntity && ((DinosaurEntity) this.targetEntity).herd != null) {
                enemies.addAll(((DinosaurEntity) this.targetEntity).herd.members);
            } else {
                enemies.add(this.targetEntity);
            }

            for (EntityLivingBase enemy : enemies) {
                if (!herd.enemies.contains(enemy)) {
                    herd.enemies.add(enemy);
                }
            }
        }
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.getRNG().nextInt(10) != 0) {
            return false;
        }

        if (!this.entity.getMetabolism().isHungry() && JurassiCraft.CONFIG.huntWhenHungry) {
            return false;
        }

        if (!(this.entity.herd != null && this.entity.herd.fleeing) && this.entity.getAgePercentage() > 50 && this.entity.getOwner() == null && !this.entity.isSleeping() && this.entity.getAttackCooldown() <= 0) {
            List<T> entities = this.entity.worldObj.getEntitiesWithinAABB(this.targetClass, this.entity.getEntityBoundingBox().expand(16, 16, 16));

            if (entities.size() > 0) {
                this.targetEntity = null;
                double bestScore = Double.MAX_VALUE;

                for (T entity : entities) {
                    if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
                        if (this.entity.getEntitySenses().canSee(entity) && !entity.isInLava()) {
                            double score = entity.getHealth() <= 0.0F ? (this.entity.getDistanceSqToEntity(entity) / entity.getHealth()) : 0;

                            if (entity.isInWater()) {
                                score *= 3;
                            }

                            if (score < bestScore) {
                                bestScore = score;

                                if (entity.getRidingEntity() instanceof EntityLivingBase) {
                                    this.targetEntity = (EntityLivingBase) entity.getRidingEntity();
                                } else {
                                    this.targetEntity = entity;
                                }
                            }
                        }
                    }
                }

                return this.targetEntity != null;
            }
        }

        return false;
    }
}

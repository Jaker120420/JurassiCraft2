package org.jurassicraft.server.entity.dinosaur;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.jurassicraft.client.model.animation.DinosaurAnimation;
import org.jurassicraft.client.sound.SoundHandler;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.animations.PeckGroundAnimationAI;

public class GallimimusEntity extends DinosaurEntity {
    public GallimimusEntity(World world) {
        super(world);
        this.animationTasks.addTask(3, new PeckGroundAnimationAI(this));
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (DinosaurAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.GALLIMIMUS_LIVING;
            case CALLING:
                return SoundHandler.GALLIMIMUS_LIVING;
            case DYING:
                return SoundHandler.GALLIMIMUS_DEATH;
            case INJURED:
                return SoundHandler.GALLIMIMUS_HURT;
        }

        return null;
    }
}

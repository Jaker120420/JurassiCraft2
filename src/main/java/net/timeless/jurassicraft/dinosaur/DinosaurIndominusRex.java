package net.timeless.jurassicraft.dinosaur;

import net.ilexiconn.llibrary.client.model.entity.animation.IModelAnimator;
import net.ilexiconn.llibrary.client.model.tabula.ModelJson;
import net.minecraft.client.model.ModelBase;
import net.timeless.jurassicraft.client.model.animation.AnimationIndominusRex;
import net.timeless.jurassicraft.entity.EntityIndominusRex;
import net.timeless.jurassicraft.entity.base.EntityDinosaur;
import net.timeless.jurassicraft.period.EnumTimePeriod;

public class DinosaurIndominusRex extends Dinosaur
{
    private IModelAnimator animator;
    private String[] maleTextures;
    private String[] femaleTextures;
    private String[] maleOverlayTextures;
    private String[] femaleOverlayTextures;
    private ModelJson model;

    public DinosaurIndominusRex()
    {
        this.animator = new AnimationIndominusRex();
        this.maleTextures = new String[] { getDinosaurTexture("") };
        this.femaleTextures = new String[] { getDinosaurTexture("") };

        this.maleOverlayTextures = new String[] { getDinosaurTexture("detail") };
        this.femaleOverlayTextures = new String[] { getDinosaurTexture("detail") };

        try
        {
            this.model = getDefaultTabulaModel();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getName()
    {
        return "Indominus Rex";
    }

    @Override
    public Class<? extends EntityDinosaur> getDinosaurClass()
    {
        return EntityIndominusRex.class;
    }

    @Override
    public EnumTimePeriod getPeriod()
    {
        return EnumTimePeriod.CRETACEOUS;
    }

    @Override
    public int getEggPrimaryColor()
    {
        return 0xBEBABB;
    }

    @Override
    public int getEggSecondaryColor()
    {
        return 0x95949A;
    }

    @Override
    public double getBabyHealth()
    {
        return 16;
    }

    @Override
    public double getAdultHealth()
    {
        return 55;
    }

    @Override
    public double getBabySpeed()
    {
        return 0.82;
    }

    @Override
    public double getAdultSpeed()
    {
        return 0.60;
    }

    public double getAttackSpeed()
    {
        return 1.20;
    }

    @Override
    public double getBabyStrength()
    {
        return 6;
    }

    @Override
    public double getAdultStrength()
    {
        return 36;
    }

    @Override
    public double getBabyKnockback()
    {
        return 0.3;
    }

    @Override
    public double getAdultKnockback()
    {
        return 0.6;
    }

    @Override
    public double getMaximumAge()
    {
        return 20F;
    }

    @Override
    public float getBabyScaleAdjustment()
    {
        return 0.5F;
    }

    @Override
    public float getAdultScaleAdjustment()
    {
        return 3.6F;
    }

    @Override
    public float getShadowSize()
    {
        return 0.65F;
    }

    @Override
    public ModelBase getModel()
    {
        return model;
    }

    @Override
    public String[] getMaleTextures()
    {
        return maleTextures;
    }

    @Override
    public String[] getFemaleTextures()
    {
        return femaleTextures;
    }

    @Override
    public String[] getMaleOverlayTextures()
    {
        return maleOverlayTextures;
    }

    @Override
    public String[] getFemaleOverlayTextures()
    {
        return femaleOverlayTextures;
    }

    @Override
    public IModelAnimator getModelAnimator()
    {
        return animator;
    }
}

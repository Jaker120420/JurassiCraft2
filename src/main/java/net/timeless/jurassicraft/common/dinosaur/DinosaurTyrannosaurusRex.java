package net.timeless.jurassicraft.common.dinosaur;

import net.timeless.jurassicraft.common.entity.EntityTyrannosaurusRex;
import net.timeless.jurassicraft.common.entity.base.EntityDinosaur;
import net.timeless.jurassicraft.common.period.EnumTimePeriod;

public class DinosaurTyrannosaurusRex extends Dinosaur
{
    private String[] maleTextures;
    private String[] femaleTextures;
    private String[] maleOverlayTextures;
    private String[] femaleOverlayTextures;

    public DinosaurTyrannosaurusRex()
    {
        this.maleTextures = new String[] { getDinosaurTexture("male") };
        this.femaleTextures = new String[] { getDinosaurTexture("female") };

        this.maleOverlayTextures = new String[] { getDinosaurTexture("male_detail") };
        this.femaleOverlayTextures = new String[] { getDinosaurTexture("female_detail") };
    }

    @Override
    public String getName()
    {
        return "Tyrannosaurus Rex";
    }

    @Override
    public Class<? extends EntityDinosaur> getDinosaurClass()
    {
        return EntityTyrannosaurusRex.class;
    }

    @Override
    public EnumTimePeriod getPeriod()
    {
        return EnumTimePeriod.CRETACEOUS;
    }

    @Override
    public int getEggPrimaryColor()
    {
        return 0x6B6628;
    }

    @Override
    public int getEggSecondaryColor()
    {
        return 0x39363B;
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
    public int getMaximumAge()
    {
        return fromDays(60);
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
    public float getBabyEyeHeight()
    {
        return 0.6F;
    }

    @Override
    public float getAdultEyeHeight()
    {
        return 3.8F;
    }

    @Override
    public float getBabySizeX()
    {
        return 0.8F;
    }

    @Override
    public float getBabySizeY()
    {
        return 0.8F;
    }

    @Override
    public float getAdultSizeX()
    {
        return 4.5F;
    }

    @Override
    public float getAdultSizeY()
    {
        return 5F;
    }
}
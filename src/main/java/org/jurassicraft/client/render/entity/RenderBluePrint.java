package org.jurassicraft.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.render.WorldRendererUtils;
import org.jurassicraft.common.entity.item.EntityBluePrint;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBluePrint extends Render
{
    private static final ResourceLocation texture = new ResourceLocation(JurassiCraft.MODID, "textures/blueprints/blueprints.png");

    public RenderBluePrint()
    {
        super(Minecraft.getMinecraft().getRenderManager());
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(EntityBluePrint entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();
        this.bindEntityTexture(entity);
        float f2 = 0.0625F;
        GlStateManager.scale(f2, f2, f2);

        int id = entity.getDinosaur();

        this.drawBluePrint(entity, 32, 16, ((id) % 8) * 32, (int) Math.floor((id) / 8) * 16); // 8x16 art
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }

    protected ResourceLocation getBluePrintTexture(EntityBluePrint bluePrint)
    {
        return texture;
    }

    private void drawBluePrint(EntityBluePrint bluePrint, int width, int height, int u, int v)
    {
        float f = (float) (-width) / 2.0F;
        float f1 = (float) (-height) / 2.0F;
        float f2 = 0F;
        float f3 = 0.75F;
        float f4 = 0.8125F;
        float f5 = 0.0F;
        float f6 = 0.0625F;
        float f9 = 0.001953125F;
        float f10 = 0.001953125F;
        float f13 = 0.0F;
        float f14 = 0.0625F;

        GL11.glTranslatef(0, 0, 0.45F);

        for (int i1 = 0; i1 < width / 16; ++i1)
        {
            for (int j1 = 0; j1 < height / 16; ++j1)
            {
                float f15 = f + (float) ((i1 + 1) * 16);
                float f16 = f + (float) (i1 * 16);
                float f17 = f1 + (float) ((j1 + 1) * 16);
                float f18 = f1 + (float) (j1 * 16);
                this.func_77008_a(bluePrint, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
                float f19 = (float) (u + width - i1 * 16) / 256.0F;
                float f20 = (float) (u + width - (i1 + 1) * 16) / 256.0F;
                float f21 = (float) (v + height - j1 * 16) / 256.0F;
                float f22 = (float) (v + height - (j1 + 1) * 16) / 256.0F;
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                WorldRendererUtils rendererUtils = new WorldRendererUtils(worldrenderer);
                rendererUtils.startDrawingQuads();
                rendererUtils.setNormal(0.0F, 0.0F, -1.0F);
                rendererUtils.addVertexWithUV((double) f15, (double) f18, (double) (-f2), (double) f20, (double) f21);
                rendererUtils.addVertexWithUV((double) f16, (double) f18, (double) (-f2), (double) f19, (double) f21);
                rendererUtils.addVertexWithUV((double) f16, (double) f17, (double) (-f2), (double) f19, (double) f22);
                rendererUtils.addVertexWithUV((double) f15, (double) f17, (double) (-f2), (double) f20, (double) f22);
                rendererUtils.setNormal(0.0F, 0.0F, 1.0F);
                rendererUtils.addVertexWithUV((double) f15, (double) f17, (double) f2, (double) f3, (double) f5);
                rendererUtils.addVertexWithUV((double) f16, (double) f17, (double) f2, (double) f4, (double) f5);
                rendererUtils.addVertexWithUV((double) f16, (double) f18, (double) f2, (double) f4, (double) f6);
                rendererUtils.addVertexWithUV((double) f15, (double) f18, (double) f2, (double) f3, (double) f6);
                rendererUtils.setNormal(0.0F, 1.0F, 0.0F);
                rendererUtils.addVertexWithUV((double) f15, (double) f17, (double) (-f2), (double) 0, (double) f9);
                rendererUtils.addVertexWithUV((double) f16, (double) f17, (double) (-f2), (double) 0, (double) f9);
                rendererUtils.addVertexWithUV((double) f16, (double) f17, (double) f2, (double) 0, (double) f10);
                rendererUtils.addVertexWithUV((double) f15, (double) f17, (double) f2, (double) 0, (double) f10);
                rendererUtils.setNormal(0.0F, -1.0F, 0.0F);
                rendererUtils.addVertexWithUV((double) f15, (double) f18, (double) f2, (double) 0, (double) f9);
                rendererUtils.addVertexWithUV((double) f16, (double) f18, (double) f2, (double) 0, (double) f9);
                rendererUtils.addVertexWithUV((double) f16, (double) f18, (double) (-f2), (double) 0, (double) f10);
                rendererUtils.addVertexWithUV((double) f15, (double) f18, (double) (-f2), (double) 0, (double) f10);
                rendererUtils.setNormal(-1.0F, 0.0F, 0.0F);
                rendererUtils.addVertexWithUV((double) f15, (double) f17, (double) f2, (double) 0, (double) f13);
                rendererUtils.addVertexWithUV((double) f15, (double) f18, (double) f2, (double) 0, (double) f14);
                rendererUtils.addVertexWithUV((double) f15, (double) f18, (double) (-f2), (double) 0, (double) f14);
                rendererUtils.addVertexWithUV((double) f15, (double) f17, (double) (-f2), (double) 0, (double) f13);
                rendererUtils.setNormal(1.0F, 0.0F, 0.0F);
                rendererUtils.addVertexWithUV((double) f16, (double) f17, (double) (-f2), (double) 0, (double) f13);
                rendererUtils.addVertexWithUV((double) f16, (double) f18, (double) (-f2), (double) 0, (double) f14);
                rendererUtils.addVertexWithUV((double) f16, (double) f18, (double) f2, (double) 0, (double) f14);
                rendererUtils.addVertexWithUV((double) f16, (double) f17, (double) f2, (double) 0, (double) f13);
                tessellator.draw();
            }
        }
    }

    private void func_77008_a(EntityBluePrint blueprint, float p_77008_2_, float p_77008_3_)
    {
        int i = MathHelper.floor_double(blueprint.posX);
        int j = MathHelper.floor_double(blueprint.posY + (double)(p_77008_3_ / 16.0F));
        int k = MathHelper.floor_double(blueprint.posZ);
        EnumFacing enumfacing = blueprint.facingDirection;

        if (enumfacing == EnumFacing.NORTH)
        {
            i = MathHelper.floor_double(blueprint.posX + (double)(p_77008_2_ / 16.0F));
        }

        if (enumfacing == EnumFacing.WEST)
        {
            k = MathHelper.floor_double(blueprint.posZ - (double)(p_77008_2_ / 16.0F));
        }

        if (enumfacing == EnumFacing.SOUTH)
        {
            i = MathHelper.floor_double(blueprint.posX - (double)(p_77008_2_ / 16.0F));
        }

        if (enumfacing == EnumFacing.EAST)
        {
            k = MathHelper.floor_double(blueprint.posZ + (double)(p_77008_2_ / 16.0F));
        }

        int l = this.renderManager.worldObj.getCombinedLight(new BlockPos(i, j, k), 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i1, (float)j1);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getBluePrintTexture((org.jurassicraft.common.entity.item.EntityBluePrint) entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1, double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((EntityBluePrint) entity, x, y, z, p_76986_8_, partialTicks);
    }
}

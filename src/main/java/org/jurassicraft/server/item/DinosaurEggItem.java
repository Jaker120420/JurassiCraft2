package org.jurassicraft.server.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.tab.TabHandler;
import org.jurassicraft.server.util.LangHelper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class DinosaurEggItem extends DNAContainerItem {
    public DinosaurEggItem() {
        super();

        this.setCreativeTab(TabHandler.DNA);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String dinoName = this.getDinosaur(stack).getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");

        return new LangHelper("item.dino_egg.name").withProperty("dino", "entity.jurassicraft." + dinoName + ".name").build();
    }

    public Dinosaur getDinosaur(ItemStack stack) {
        return EntityHandler.getDinosaurById(stack.getMetadata());
    }

    @Override
    public int getContainerId(ItemStack stack) {
        return EntityHandler.getDinosaurId(this.getDinosaur(stack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subtypes) {
        List<Dinosaur> dinosaurs = new LinkedList<>(EntityHandler.getDinosaurs().values());

        Collections.sort(dinosaurs);

        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur.shouldRegister()) {
                subtypes.add(new ItemStack(item, 1, EntityHandler.getDinosaurId(dinosaur)));
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
//        pos = pos.offset(side);
//
//        if (side == EnumFacing.EAST || side == EnumFacing.WEST)
//        {
//            hitX = 1.0F - hitX;
//        }
//        else if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
//        {
//            hitZ = 1.0F - hitZ;
//        }
//
//        if (player.canPlayerEdit(pos, side, stack) && !world.isRemote)
//        {
//            DinosaurEggEntity egg = new DinosaurEggEntity(world, getDinosaur(stack), getDNAQuality(player, stack), getGeneticCode(player, stack).toString());
//            egg.setPosition(pos.getX() + hitX, pos.getY(), pos.getZ() + hitZ);
//            egg.rotationYaw = player.rotationYaw;
//            world.spawnEntityInWorld(egg);
//
//            if (!player.capabilities.isCreativeMode)
//            {
//                stack.stackSize--;
//            }
//
//            return EnumActionResult.SUCCESS;
//        }

        return EnumActionResult.PASS;
    }
}

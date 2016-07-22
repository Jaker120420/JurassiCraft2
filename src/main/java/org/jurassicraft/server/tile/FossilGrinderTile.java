package org.jurassicraft.server.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.server.api.GrindableItem;
import org.jurassicraft.server.container.FossilGrinderContainer;

import java.util.Random;

public class FossilGrinderTile extends MachineBaseTile {
    private static final int[] INPUTS = new int[] { 0, 1, 2, 3, 4, 5 };
    private static final int[] OUTPUTS = new int[] { 6, 7, 8, 9, 10, 11 };

    private ItemStack[] slots = new ItemStack[12];

    @Override
    protected int getProcess(int slot) {
        return 0;
    }

    @Override
    protected boolean canProcess(int process) {
        for (int inputIndex = 0; inputIndex < 6; inputIndex++) {
            ItemStack input = this.slots[inputIndex];

            GrindableItem grindableItem = GrindableItem.getGrindableItem(input);

            if (grindableItem != null && grindableItem.isGrindable(input)) {
                for (int outputIndex = 6; outputIndex < 12; outputIndex++) {
                    if (this.slots[outputIndex] == null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    protected void processItem(int process) {
        Random rand = new Random();

        ItemStack input = null;
        int index = 0;

        for (int inputIndex = 0; inputIndex < 6; inputIndex++) {
            input = this.slots[inputIndex];

            if (input != null) {
                index = inputIndex;
                break;
            }
        }

        if (input != null) {
            GrindableItem grindableItem = GrindableItem.getGrindableItem(input);

            ItemStack output = grindableItem.getGroundItem(input, rand);

            int emptySlot = this.getOutputSlot(output);

            if (emptySlot != -1) {
                this.mergeStack(emptySlot, output);
                this.decreaseStackSize(index);
            }
        }
    }

    @Override
    protected int getMainOutput(int process) {
        return 1;
    }

    @Override
    protected int getStackProcessTime(ItemStack stack) {
        return 200;
    }

    @Override
    protected int getProcessCount() {
        return 1;
    }

    @Override
    protected int[] getInputs() {
        return INPUTS;
    }

    @Override
    protected int[] getInputs(int process) {
        return this.getInputs();
    }

    @Override
    protected int[] getOutputs() {
        return OUTPUTS;
    }

    @Override
    protected ItemStack[] getSlots() {
        return this.slots;
    }

    @Override
    protected void setSlots(ItemStack[] slots) {
        this.slots = slots;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new FossilGrinderContainer(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return JurassiCraft.MODID + ":fossil_grinder";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.fossil_grinder";
    }
}

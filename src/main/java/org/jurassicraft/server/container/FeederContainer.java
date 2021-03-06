package org.jurassicraft.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import org.jurassicraft.server.block.entity.FeederBlockEntity;
import org.jurassicraft.server.container.slot.CustomSlot;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.food.FoodHelper;

public class FeederContainer extends MachineContainer {
    private FeederBlockEntity tile;

    public FeederContainer(InventoryPlayer inventory, FeederBlockEntity tile) {
        super(tile);

        this.tile = tile;

        int id = 0;

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(inventory, id, 8 + x * 18, 142));
            id++;
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(inventory, id, 8 + x * 18, 84 + y * 18));
                id++;
            }
        }

        id = 0;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.addSlotToContainer(new CustomSlot(tile, id, 26 + x * 18, 18 + y * 18, stack -> FoodHelper.isEdible(Diet.CARNIVORE, stack.getItem())));
                id++;
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.addSlotToContainer(new CustomSlot(tile, id, 98 + x * 18, 18 + y * 18, stack -> FoodHelper.isEdible(Diet.HERBIVORE, stack.getItem())));
                id++;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUseableByPlayer(player);
    }
}

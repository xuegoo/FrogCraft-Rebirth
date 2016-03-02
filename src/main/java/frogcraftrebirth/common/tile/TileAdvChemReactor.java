package frogcraftrebirth.common.tile;

import java.util.Arrays;
import java.util.Map;

import frogcraftrebirth.api.FrogAPI;
import frogcraftrebirth.api.recipes.AdvChemRecRecipe;
import frogcraftrebirth.common.lib.tile.TileFrogMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class TileAdvChemReactor extends TileFrogMachine {
	public int process, processMax;
	boolean working, changed = false, inputsLocked = false, outputLocked = false;
	
	public TileAdvChemReactor() {
		super(13, "TileEntityAdvancedChemicalReactor", 2, 100000);
		//0 for module, 1-5 for input, 6-10 for output, 11 for cell input and 12 for cell output
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.working = tag.getBoolean("working");
		this.process = tag.getInteger("process");
		this.processMax = tag.getInteger("processMax");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setBoolean("working", this.working);
		tag.setInteger("process", this.process);
		tag.setInteger("processMax", this.processMax);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote) return;
		
		AdvChemRecRecipe recipe = (AdvChemRecRecipe)FrogAPI.managerACR.<ItemStack[]>getRecipe(Arrays.copyOfRange(inv, 1, 5));
		if (recipe == null)
			return;
		
		//Map<String, Integer> recipeInput = recipe.getInputs();
		
		if (!working) {
			working = canWork(recipe);
		} 
		
		this.processMax = recipe.getReactionTime();
		
		if (working && charge >= recipe.getEnergyRate()) {
			this.charge -= recipe.getEnergyRate();
			++process;
		}
		
		if (process == processMax) {
			Map<String, Integer> recipeOutput = recipe.getOutputs();
			
			//Overhaul required
			
			this.changed = true;
			this.working = false;
		}
		
		if (changed) {
			this.markDirty();
			changed = false;
		}
	}
	
	//TODO: COMPLETE THIS METHOD
	public boolean canWork(AdvChemRecRecipe recipe) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch (side) {
			case 0: //Bottom
				return new int[] {6,7,8,9,10,12};
			case 1: //Top
				return new int[] {1,2,3,4,5,11};
			default: //Disallow auto-insert module
				return null;
		}
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if (side != 1)
			return false;
		return Arrays.asList(1,2,3,4,5,11).contains(slot);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if (side != 0)
			return false;
		return Arrays.asList(6,7,8,9,10,12).contains(slot);
	}

}
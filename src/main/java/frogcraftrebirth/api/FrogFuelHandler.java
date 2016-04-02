package frogcraftrebirth.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
/**
 * This FuelHanlder implementation will handle both vanilla fuel registration
 * as well as combustion furnace fuel and byproducts registration.
 * @see frogcraftrebirth.common.tile.TileCombustionFurnace
 * @author 3TUSK
 */
public final class FrogFuelHandler implements IFuelHandler{
	
	FrogFuelHandler() {}
	
	@Override
	public int getBurnTime(ItemStack fuel) {
		for (Entry<ItemStack, Integer> entry : fuelMap.entrySet()) {
			if (fuel.isItemEqual(entry.getKey()))
				return entry.getValue().intValue();
		}
		return 0;
	}
	
	public FluidStack getFluidByproduct(@Nonnull ItemStack aStack) {
		for (Entry<ItemStack, FluidStack> entry : fuel2FluidMap.entrySet()) {
			if (aStack.isItemEqual(entry.getKey()))
				return entry.getValue();
		}
		return null;
	}
	
	public ItemStack getItemByproduct(@Nonnull ItemStack aStack) {
		for (Entry<ItemStack, ItemStack> entry : fuel2ByproductMap.entrySet()) {
			if (aStack.isItemEqual(entry.getKey()))
				return entry.getValue();
		}
		return null;
	}
	
	public void regFuel(@Nonnull Item fuel, int timeInTicks) {
		regFuel(new ItemStack(fuel, 1), timeInTicks);
	}
	
	public void regFuel(@Nonnull ItemStack fuel, int timeInTicks){
		fuelMap.put(fuel, timeInTicks < 0 ? 0 : timeInTicks);
	}
	
	public void regFuelByproduct(@Nonnull ItemStack fuel, @Nonnull Fluid byproduct) {
		regFuelByproduct(fuel, new FluidStack(byproduct, FluidContainerRegistry.BUCKET_VOLUME));
	}
	
	public void regFuelByproduct(@Nonnull ItemStack fuel, @Nonnull FluidStack byproduct) {
		fuel2FluidMap.put(fuel, byproduct);
	}
	
	public void regFuelByproduct(@Nonnull ItemStack fuel, @Nonnull ItemStack byproduct) {
		fuel2ByproductMap.put(fuel, byproduct);
	}

	private Map<ItemStack, Integer> fuelMap = new HashMap<ItemStack, Integer>();
	private Map<ItemStack, FluidStack> fuel2FluidMap = new HashMap<ItemStack, FluidStack>();
	private Map<ItemStack, ItemStack> fuel2ByproductMap = new HashMap<ItemStack, ItemStack>();
}

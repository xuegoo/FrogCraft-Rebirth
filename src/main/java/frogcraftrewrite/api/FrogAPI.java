package frogcraftrewrite.api;

import java.lang.reflect.Field;

import frogcraftrewrite.api.recipes.IRecipeManager;
import frogcraftrewrite.common.lib.FrogBlocks;
import frogcraftrewrite.common.lib.FrogItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FrogAPI {
	/**
	 * FrogCraft (2013-) is a Minecraft Mod authored by Rikka.<br>
	 * Credits to Rikka for original code and M3gaFrank (original
	 * author of ExtraCell) for porting to 1.6.4.<br> FrogCraftRebirth 
	 * is a Minecraft Mod, porting 1.6.2 FrogCraft to 1.7.10, authored 
	 * by 3TUSK.<br> Code are based on original code, may adjusted in 
	 * order to improve player performance.
	 * <p>
	 * This mod is my response to asie's Cult of Kitteh since 11:15 PM,
	 * Aug 31st, EST, 2015. As response, the resurrection of FrogCraft is 
	 * aiming to reduce the implements of graphic user interface (known 
	 * for GUI) in order to let players focus on designing a real, useful, 
	 * effective chemical plant inside of my limitation. GUI will be 
	 * applied on several special-designed block, e.g. mobile power station, 
	 * ic2-ish industrial device, etc.
	 * */
	private static final int MEANING_OF_LIFE_UNIVERSE_EVERYTHING = 42;
	
	//General stuff.
	public static final String MODID = "FrogCraftRebirth";
	public static final String NAME = "FrogCraft: Rebirth";
	public static final String DEPENDING = 
			"required-after:IC2;"
			+ "after:gregtech_api;"
			+ "after:thaumcraft;"
			+ "after:minetweaker3;"
			+ "after:techreborn;";
	
	//Not be used, and more likely not to be used in future
	@Deprecated
	public static CreativeTabs tabFrogCraft;
	
	//Recipe manager, and I can't stand the loooooooong name!
	@SuppressWarnings("rawtypes")
	public static IRecipeManager managerACR, managerCFG, managerCT, managerTC;
	
	/**
	 * @return THE FINAL ANSWER: 42
	 */
	public static String getFinalAnswer() {
		return Integer.toString(MEANING_OF_LIFE_UNIVERSE_EVERYTHING);
	}
	
	//Plan: This method need document
	public static ItemStack findFrogStuff(String name, int damage) {
		Field stuff;
		
		try {
			stuff = FrogItems.class.getField(name);
			return new ItemStack((Item)stuff.get(Item.class), 1, damage);
		} catch (Exception e) {
			
		}
		
		try {
			stuff = FrogBlocks.class.getField(name);
			return new ItemStack((Block)stuff.get(Block.class), 1, damage);
		} catch (Exception e) {
			
		}
		
		return null;
	}
}
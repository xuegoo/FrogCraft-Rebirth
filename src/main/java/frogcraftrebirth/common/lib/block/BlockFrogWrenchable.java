/*
 * Copyright (c) 2015 - 2017 3TUSK, et al.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package frogcraftrebirth.common.lib.block;

import java.util.Collections;
import java.util.List;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockFrogWrenchable extends BlockFrog implements IWrenchable {
	
	public static final PropertyDirection FACING_ALL = BlockDirectional.FACING;
	public static final PropertyDirection FACING_HORIZONTAL = BlockHorizontal.FACING;
	
	private final boolean allowVerticalRotation;

	protected BlockFrogWrenchable(Material material, String registryName, boolean allowVerticalRotation, int... metaForDisplay) {
		super(material, registryName, metaForDisplay);
		this.allowVerticalRotation = allowVerticalRotation;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(allowVerticalRotation ? FACING_ALL : FACING_HORIZONTAL, placer.getHorizontalFacing().getOpposite()));
	}

	@Override
	public EnumFacing getFacing(World world, BlockPos pos) {
		return world.getBlockState(pos).getValue(allowVerticalRotation ? FACING_ALL : FACING_HORIZONTAL);
	}

	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
		if (!allowVerticalRotation) {
			switch (newDirection) {
				case UP:
				case DOWN: return false;
				default: {
					world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING_HORIZONTAL, newDirection), 2);
					return true;
				}
			}
		} else {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING_ALL, newDirection), 2);
			return true;
		}
	}

	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
		return true;
	}

	@Override
	public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te, EntityPlayer player, int fortune) {
		return Collections.singletonList(new ItemStack(state.getBlock(), 1, state.getBlock().damageDropped(state)));
	}

}

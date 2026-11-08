/*
 * This file is part of the TweakerMore project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * TweakerMore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TweakerMore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TweakerMore.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.fallenbreath.tweakermore.impl.features.autoContainerProcess.processors;

import fi.dy.masa.itemscroller.util.InventoryUtils;
import fi.dy.masa.malilib.util.InfoUtils;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigBooleanHotkeyed;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class FurnaceOutputEmptier implements IContainerProcessor
{
	private static final int OUTPUT_SLOT = 2;

	@Override
	public TweakerMoreConfigBooleanHotkeyed getConfig()
	{
		return TweakerMoreConfigs.AUTO_EMPTY_FURNACE;
	}

	@Override
	public ProcessResult process(LocalPlayer player, AbstractContainerScreen<?> containerScreen, List<Slot> allSlots, List<Slot> playerInvSlots, List<Slot> containerInvSlots)
	{
		if (!(containerScreen.getMenu() instanceof AbstractFurnaceMenu))
		{
			return ProcessResult.skipped();
		}

		Slot output = containerScreen.getMenu().getSlot(OUTPUT_SLOT);
		if (output.hasItem())
		{
			InventoryUtils.shiftClickSlot(containerScreen, output.index);
			if (output.hasItem())
			{
				InfoUtils.printActionbarMessage("tweakermore.impl.autoEmptyFurnace.output_remaining");
			}
		}
		return new ProcessResult(true, TweakerMoreConfigs.AUTO_EMPTY_FURNACE_CLOSE_GUI.getBooleanValue());
	}
}

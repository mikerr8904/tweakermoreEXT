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

package me.fallenbreath.tweakermore.impl.features.autoClickFurnace;

import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.util.GameUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class AutoClickFurnace
{
	private static final LookTargetTracker TARGET = new LookTargetTracker();
	private static ClientLevel lastLevel;

	public static void onClientTick(Minecraft mc)
	{
		if (!TweakerMoreConfigs.AUTO_CLICK_FURNACE.getBooleanValue() || mc.level == null || mc.player == null || mc.gameMode == null)
		{
			TARGET.reset();
			lastLevel = null;
			return;
		}
		if (lastLevel != mc.level)
		{
			TARGET.reset();
			lastLevel = mc.level;
		}
		if (GameUtils.getCurrentMinecraftScreen(mc) != null || mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK)
		{
			return;
		}

		BlockHitResult hit = (BlockHitResult)mc.hitResult;
		BlockPos pos = hit.getBlockPos();
		TARGET.observe(pos);
		if (!TARGET.hasClicked() && mc.level.getBlockState(pos).getBlock() instanceof AbstractFurnaceBlock &&
				!mc.player.isSpectator() && !mc.player.isShiftKeyDown() && !mc.player.isUsingItem())
		{
			TARGET.markClicked();
			mc.gameMode.useItemOn(mc.player,
					//#if MC < 11900
					mc.level,
					//#endif
					InteractionHand.MAIN_HAND, hit);
		}
	}
}

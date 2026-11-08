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

package me.fallenbreath.tweakermore.tests;

import junit.framework.TestCase;
import me.fallenbreath.tweakermore.impl.features.autoClickFurnace.LookTargetTracker;
import net.minecraft.core.BlockPos;

public class LookTargetTrackerTest extends TestCase
{
	public void testSameBlockOnlyClicksOnce()
	{
		LookTargetTracker tracker = new LookTargetTracker();
		tracker.observe(new BlockPos(1, 2, 3));
		assertFalse(tracker.hasClicked());
		tracker.markClicked();
		tracker.observe(new BlockPos(1, 2, 3));
		assertTrue(tracker.hasClicked());
	}

	public void testAnotherBlockAllowsClickOnReturn()
	{
		LookTargetTracker tracker = new LookTargetTracker();
		BlockPos furnace = new BlockPos(1, 2, 3);
		tracker.observe(furnace);
		tracker.markClicked();
		tracker.observe(new BlockPos(4, 5, 6));
		assertFalse(tracker.hasClicked());
		tracker.observe(furnace);
		assertFalse(tracker.hasClicked());
	}

	public void testResetAllowsClickAgain()
	{
		LookTargetTracker tracker = new LookTargetTracker();
		BlockPos furnace = new BlockPos(1, 2, 3);
		tracker.observe(furnace);
		tracker.markClicked();
		tracker.reset();
		tracker.observe(furnace);
		assertFalse(tracker.hasClicked());
	}
}

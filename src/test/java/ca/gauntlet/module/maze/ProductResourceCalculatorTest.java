/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2026, eitjank <https://github.com/eitjank>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ca.gauntlet.module.maze;

import ca.gauntlet.TheGauntletConfig;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductResourceCalculatorTest
{

	private final ProductResourceCalculator calculator = new ProductResourceCalculator();

	private void assertResources(int expectedShards, boolean expectedOrb, boolean expectedBowstring, boolean expectedSpike,
								 int expectedGrym, int expectedFrames, int expectedOre, int expectedTirinum,
								 int expectedBark, int expectedPaddlefish)
	{
		assertEquals("shards", expectedShards, calculator.calculateShards());
		assertEquals("orb", expectedOrb, calculator.isOrbNeeded());
		assertEquals("bowstring", expectedBowstring, calculator.isBowstringNeeded());
		assertEquals("spike", expectedSpike, calculator.isSpikeNeeded());
		assertEquals("grym", expectedGrym, calculator.calculateGrym());
		assertEquals("frames", expectedFrames, calculator.calculateFrames());
		assertEquals("ore", expectedOre, calculator.calculateOre());
		assertEquals("tirinum", expectedTirinum, calculator.calculateTirinum());
		assertEquals("bark", expectedBark, calculator.calculateBark());
		assertEquals("paddlefish", expectedPaddlefish, calculator.calculatePaddlefish());
	}

	@Test
	public void nothingSelectedGivesZero()
	{
		// Arrange
		// Nothing selected is the default state, so we don't need to set anything.

		// Act & Assert
		assertResources(0, false, false, false, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void tier1ArmourOnly()
	{
		// Arrange
		calculator.setHelmTier(TheGauntletConfig.Tier.T1);
		calculator.setBodyTier(TheGauntletConfig.Tier.T1);
		calculator.setLegsTier(TheGauntletConfig.Tier.T1);

		// Act & Assert
		assertResources(150, false, false, false, 0,
			0, 3, 3, 3, 0);
	}

	@Test
	public void tier2ArmourOnly()
	{
		// Arrange
		calculator.setHelmTier(TheGauntletConfig.Tier.T2);
		calculator.setBodyTier(TheGauntletConfig.Tier.T2);
		calculator.setLegsTier(TheGauntletConfig.Tier.T2);

		// Act & Assert
		assertResources(350, false, false, false, 0,
			0, 7, 7, 7, 0);
	}

	@Test
	public void tier3ArmourOnly()
	{
		// Arrange
		calculator.setHelmTier(TheGauntletConfig.Tier.T3);
		calculator.setBodyTier(TheGauntletConfig.Tier.T3);
		calculator.setLegsTier(TheGauntletConfig.Tier.T3);

		// Act & Assert
		assertResources(650, false, false, false, 0,
			0, 13, 13, 13, 0);
	}

	@Test
	public void tier1StaffOnly()
	{
		// Arrange
		calculator.setStaffTier(TheGauntletConfig.Tier.T1);

		// Act & Assert
		assertResources(0, false, false, false, 0,
			1, 0, 0, 0, 0);
	}

	@Test
	public void tier2HalberdOnly()
	{
		// Arrange
		calculator.setHalberdTier(TheGauntletConfig.Tier.T2);

		// Act & Assert
		assertResources(50, false, false, false, 0,
			1, 0, 0, 0, 0);
	}

	@Test
	public void tier3HalberdOnly()
	{
		// Arrange
		calculator.setHalberdTier(TheGauntletConfig.Tier.T3);

		// Act & Assert
		assertResources(50, false, false, true, 0,
			1, 0, 0, 0, 0);
	}

	@Test
	public void tier2StaffAndBowNeedNoDemibossItems()
	{
		// Arrange
		calculator.setStaffTier(TheGauntletConfig.Tier.T2);
		calculator.setBowTier(TheGauntletConfig.Tier.T2);

		// Act & Assert
		assertResources(100, false, false, false, 0,
			2, 0, 0, 0, 0);
	}

	@Test
	public void tier3ArmourWithStaffAndBow()
	{
		// Arrange
		calculator.setHelmTier(TheGauntletConfig.Tier.T3);
		calculator.setBodyTier(TheGauntletConfig.Tier.T3);
		calculator.setLegsTier(TheGauntletConfig.Tier.T3);
		calculator.setStaffTier(TheGauntletConfig.Tier.T3);
		calculator.setBowTier(TheGauntletConfig.Tier.T3);

		// Act & Assert
		assertResources(750, true, true, false, 0,
			2, 13, 13, 13, 0);
	}

	@Test
	public void mixedGearAndConsumables()
	{
		// Arrange
		calculator.setHelmTier(TheGauntletConfig.Tier.T2);
		calculator.setBodyTier(TheGauntletConfig.Tier.T1);
		calculator.setLegsTier(TheGauntletConfig.Tier.T3);
		calculator.setStaffTier(TheGauntletConfig.Tier.T2);
		calculator.setBowTier(TheGauntletConfig.Tier.T3);
		calculator.setPotionCount(2);
		calculator.setTeleportCrystalCount(1);
		calculator.setPaddlefishCount(1);
		calculator.setCrystalPaddlefishCount(2);

		// Act & Assert
		// shards: armour 100 + 50 + 200, weapons 50 + 50, potions 2 * 20,
		//         teleport 50, crystal paddlefish 2 * 10 = 560
		// ore/tirinum/bark: 2 + 1 + 4 = 7
		// paddlefish: 1 + 2 = 3
		assertResources(560, false, true, false, 2,
			2, 7, 7, 7, 3);
	}

	@Test
	public void threePotionsOnly()
	{
		// Arrange
		calculator.setPotionCount(3);

		// Act & Assert
		assertResources(60, false, false, false, 3,
			0, 0, 0, 0, 0);
	}

	@Test
	public void oneTeleportCrystalOnly()
	{
		// Arrange
		calculator.setTeleportCrystalCount(1);

		// Act & Assert
		assertResources(50, false, false, false, 0,
			0, 0, 0, 0, 0);
	}

	@Test
	public void paddlefishAndCrystalPaddlefish()
	{
		// Arrange
		calculator.setPaddlefishCount(4);
		calculator.setCrystalPaddlefishCount(2);

		// Act & Assert
		assertResources(20, false, false, false, 0,
			0, 0, 0, 0, 6);
	}

	@Test
	public void crystalPaddlefishOnly()
	{
		// Arrange
		calculator.setCrystalPaddlefishCount(5);

		// Act & Assert
		assertResources(50, false, false, false, 0,
			0, 0, 0, 0, 5);
	}
}

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

import ca.gauntlet.TheGauntletConfig.Tier;
import lombok.Setter;

@Setter
public class ProductResourceCalculator
{
	private static final int ATTUNED_WEAPON_SHARDS = 50;
	private static final int POTION_SHARDS = 20;
	private static final int TELEPORT_CRYSTAL_SHARDS = 50;
	private static final int CRYSTAL_PADDLEFISH_SHARDS = 10;

	private Tier helmTier = Tier.NONE;
	private Tier bodyTier = Tier.NONE;
	private Tier legsTier = Tier.NONE;
	private Tier staffTier = Tier.NONE;
	private Tier bowTier = Tier.NONE;
	private Tier halberdTier = Tier.NONE;
	private int potionCount = 0;
	private int teleportCrystalCount = 0;
	private int paddlefishCount = 0;
	private int crystalPaddlefishCount = 0;

	public int calculateShards()
	{
		return weaponShards() + armourShards()
			+ potionCount * POTION_SHARDS
			+ teleportCrystalCount * TELEPORT_CRYSTAL_SHARDS
			+ crystalPaddlefishCount * CRYSTAL_PADDLEFISH_SHARDS;
	}

	private int weaponShards()
	{
		int weaponShards = 0;
		if (staffTier.compareTo(Tier.T2) >= 0)
		{
			weaponShards += ATTUNED_WEAPON_SHARDS;
		}
		if (bowTier.compareTo(Tier.T2) >= 0)
		{
			weaponShards += ATTUNED_WEAPON_SHARDS;
		}
		if (halberdTier.compareTo(Tier.T2) >= 0)
		{
			weaponShards += ATTUNED_WEAPON_SHARDS;
		}
		return weaponShards;
	}

	public boolean isOrbNeeded()
	{
		return staffTier == Tier.T3;
	}

	public boolean isBowstringNeeded()
	{
		return bowTier == Tier.T3;
	}

	public boolean isSpikeNeeded()
	{
		return halberdTier == Tier.T3;
	}

	public int calculateGrym()
	{
		return potionCount;
	}

	public int calculateFrames()
	{
		int frames = 0;
		if (staffTier != Tier.NONE)
		{
			frames++;
		}
		if (bowTier != Tier.NONE)
		{
			frames++;
		}
		if (halberdTier != Tier.NONE)
		{
			frames++;
		}
		return frames;
	}

	public int calculateOre()
	{
		return armourMaterials();
	}

	public int calculateTirinum()
	{
		return armourMaterials();
	}

	public int calculateBark()
	{
		return armourMaterials();
	}

	public int calculatePaddlefish()
	{
		return paddlefishCount + crystalPaddlefishCount;
	}

	private int armourMaterials()
	{
		return helmOrLegsMaterials(helmTier) + bodyMaterials(bodyTier) + helmOrLegsMaterials(legsTier);
	}

	private int bodyMaterials(Tier tier)
	{
		switch (tier)
		{
			case NONE:
				return 0;
			case T1:
				return 1;
			case T2:
				return 3;
			case T3:
				return 5;
			default:
				throw new IllegalArgumentException("Unknown tier: " + tier);
		}
	}

	private int helmOrLegsMaterials(Tier tier)
	{
		switch (tier)
		{
			case NONE:
				return 0;
			case T1:
				return 1;
			case T2:
				return 2;
			case T3:
				return 4;
			default:
				throw new IllegalArgumentException("Unknown tier: " + tier);
		}
	}

	private int armourShards()
	{
		return helmOrLegsShards(helmTier) + bodyShards(bodyTier) + helmOrLegsShards(legsTier);
	}

	private int helmOrLegsShards(Tier tier)
	{
		switch (tier)
		{
			case NONE:
				return 0;
			case T1:
				return 50;
			case T2:
				return 100;
			case T3:
				return 200;
			default:
				throw new IllegalArgumentException("Unknown tier: " + tier);
		}
	}

	private int bodyShards(Tier tier)
	{
		switch (tier)
		{
			case NONE:
				return 0;
			case T1:
				return 50;
			case T2:
				return 150;
			case T3:
				return 250;
			default:
				throw new IllegalArgumentException("Unknown tier: " + tier);
		}
	}
}

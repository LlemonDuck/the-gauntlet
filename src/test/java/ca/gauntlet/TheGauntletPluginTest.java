package ca.gauntlet;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TheGauntletPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TheGauntletPlugin.class);
		RuneLite.main(args);
	}
}

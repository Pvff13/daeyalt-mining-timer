package com.betterdaeyalttimers;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BetterDaeyaltTimersTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BetterDaeyaltTimersPlugin.class);
		RuneLite.main(args);
	}
}
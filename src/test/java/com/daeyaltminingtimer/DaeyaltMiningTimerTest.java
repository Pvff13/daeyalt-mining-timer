package com.daeyaltminingtimer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DaeyaltMiningTimerTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DaeyaltMiningTimerPlugin.class);
		RuneLite.main(args);
	}
}
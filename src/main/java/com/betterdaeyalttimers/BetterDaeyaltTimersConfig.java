package com.betterdaeyalttimers;

import java.awt.Color;

import net.runelite.api.SoundEffectID;
import net.runelite.client.config.*;

@ConfigGroup(BetterDaeyaltTimersConfig.group)
public interface BetterDaeyaltTimersConfig extends Config
{
	String group = "better-daeyalt-timers";

	enum TimerStyle
	{
		PIE,
		COUNTDOWN,
		PIE_AND_COUNTDOWN;

		@Override
		public String toString()
		{
			switch (this)
			{
				case PIE:
					return "Pie";
				case COUNTDOWN:
					return "Countdown";
				default:
					return "Pie + countdown";
			}
		}
	}

	enum TimerUnit
	{
		SECONDS,
		TICKS;

		@Override
		public String toString()
		{
			return this == SECONDS ? "Seconds" : "Ticks";
		}
	}

	@ConfigSection(
			name = "Notifications",
			description = "Configure notifications",
			position = 0
	)
	String notificationsSection = "notificationsSection";

	@ConfigItem(
			keyName = "notifications",
			name = "Client notification",
			description = "Trigger a client notification when the daeyalt essence moves",
			section = notificationsSection,
			position = 0
	)
	default Notification notification()
	{
		return Notification.ON;
	}

	@ConfigItem(
			keyName = "ingameSound",
			name = "In-game sound",
			description = "Play an in-game sound when the daeyalt essence moves",
			section = notificationsSection,
			position = 1
	)
	default boolean ingameSound()
	{
		return false;
	}

	@ConfigItem(
			keyName = "soundId",
			name = "Sound ID",
			description = "The in-game sound to play.\n3925 is the Grand Exchange offer chime; other easily-heard options are 2266 (UI beep), 3929 (plop) and 1930 (teleport woosh).",
			section = notificationsSection,
			position = 2
	)
	default int soundId()
	{
		return SoundEffectID.GE_ADD_OFFER_DINGALING;
	}

	@ConfigItem(
			keyName = "soundFollowGameVolume",
			name = "Follow game volume",
			description = "Use the in-game sound effect volume. If muted in-game, it will use the \"Volume\" setting.",
			section = notificationsSection,
			position = 3
	)
	default boolean soundFollowGameVolume()
	{
		return true;
	}

	@ConfigItem(
			keyName = "soundVolume",
			name = "Volume",
			description = "Volume to play the sound effect at when not using game volume.",
			section = notificationsSection,
			position = 4
	)
	@Range(
			min = 1,
			max = 127
	)
	default int soundVolume()
	{
		return 127;
	}

	@ConfigSection(
			name = "Overlays",
			description = "Configure overlays",
			position = 1
	)
	String overlaysSection = "overlaysSection";

	@ConfigItem(
			keyName = "showDaeyaltEssenceIndicator",
			name = "Show indicator",
			description = "Display a mining icon above the daeyalt essence that is ready to be mined",
			section = overlaysSection,
			position = 0
	)
	default boolean showDaeyaltEssenceIndicator()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showDaeyaltEssenceClickbox",
			name = "Show click box",
			description = "Display a click box on the daeyalt essence that is ready to be mined",
			section = overlaysSection,
			position = 1
	)
	default boolean showDaeyaltEssenceClickbox()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showDaeyaltEssenceClickboxAvailable",
			name = "Click box colour",
			description = "Colour of the click box (ignored while mining-state colours are on)",
			section = overlaysSection,
			position = 2
	)
	default Color showDaeyaltEssenceClickboxAvailable()
	{
		return Color.GREEN;
	}

	@ConfigSection(
			name = "Timer",
			description = "Configure the despawn timer",
			position = 2
	)
	String timerSection = "timerSection";

	@ConfigItem(
			keyName = "showDaeyaltEssenceTimer",
			name = "Show timer",
			description = "Display a timer for when the daeyalt essence is about to move",
			section = timerSection,
			position = 0
	)
	default boolean showDaeyaltEssenceTimer()
	{
		return true;
	}

	@ConfigItem(
			keyName = "timerStyle",
			name = "Timer style",
			description = "Pie: shrinking circle. Countdown: a number counting down. Pie + countdown: the number drawn on the circle.",
			section = timerSection,
			position = 1
	)
	default TimerStyle timerStyle()
	{
		return TimerStyle.PIE_AND_COUNTDOWN;
	}

	@ConfigItem(
			keyName = "timerUnit",
			name = "Countdown unit",
			description = "Count down in seconds or game ticks",
			section = timerSection,
			position = 2
	)
	default TimerUnit timerUnit()
	{
		return TimerUnit.SECONDS;
	}

	@ConfigItem(
			keyName = "timerSize",
			name = "Timer size",
			description = "Diameter of the timer circle in pixels",
			section = timerSection,
			position = 3
	)
	@Range(
			min = 10,
			max = 150
	)
	default int timerSize()
	{
		return 25;
	}

	@ConfigItem(
			keyName = "countdownTextSize",
			name = "Countdown text size",
			description = "Font size of the countdown number",
			section = timerSection,
			position = 4
	)
	@Range(
			min = 8,
			max = 72
	)
	default int countdownTextSize()
	{
		return 16;
	}

	@ConfigItem(
			keyName = "timerColour",
			name = "Timer colour",
			description = "Colour of the timer before the essence can move (used while mining-state colours are off)",
			section = timerSection,
			position = 5
	)
	default Color timerColour()
	{
		return new Color(0, 255, 0);
	}

	@ConfigItem(
			keyName = "warningColour",
			name = "Can move colour",
			description = "Colour of the timer once the essence can move at any moment",
			section = timerSection,
			position = 6
	)
	default Color warningColour()
	{
		return new Color(233, 213, 2);
	}

	@ConfigItem(
			keyName = "countdownTextColour",
			name = "Countdown text colour",
			description = "Colour of the countdown number when drawn on the circle",
			section = timerSection,
			position = 7
	)
	default Color countdownTextColour()
	{
		return Color.WHITE;
	}

	@ConfigSection(
			name = "Mining-state colours",
			description = "Change colours depending on whether you are mining the essence",
			position = 3
	)
	String miningSection = "miningSection";

	@ConfigItem(
			keyName = "miningStateColours",
			name = "Enable",
			description = "Colour the timer and click box by whether you are currently mining the essence",
			section = miningSection,
			position = 0
	)
	default boolean miningStateColours()
	{
		return false;
	}

	@ConfigItem(
			keyName = "miningColour",
			name = "Mining colour",
			description = "Colour while you are mining the essence",
			section = miningSection,
			position = 1
	)
	default Color miningColour()
	{
		return new Color(0, 255, 0);
	}

	@ConfigItem(
			keyName = "notMiningColour",
			name = "Not mining colour",
			description = "Colour while you are not mining the essence",
			section = miningSection,
			position = 2
	)
	default Color notMiningColour()
	{
		return new Color(255, 0, 0);
	}

	@ConfigItem(
			keyName = "miningStateOverridesWarning",
			name = "Override can move colour",
			description = "Keep using the mining/not mining colour after the essence can move, instead of the can move colour",
			section = miningSection,
			position = 3
	)
	default boolean miningStateOverridesWarning()
	{
		return false;
	}
}

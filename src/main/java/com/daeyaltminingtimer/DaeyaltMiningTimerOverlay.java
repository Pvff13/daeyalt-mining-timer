package com.daeyaltminingtimer;

import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.Skill;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;
import net.runelite.client.util.ColorUtil;

import java.awt.*;

public class DaeyaltMiningTimerOverlay extends Overlay {
    private static final int Z_OFFSET = 200;

    private static final int MIN_DESPAWN_TICK = 92;
    private static final int MAX_DESPAWN_TICK = 110;

    private final Client client;
    private final DaeyaltMiningTimerPlugin plugin;
    private final DaeyaltMiningTimerConfig config;
    private final SkillIconManager skillIconManager;

    private Font countdownFont;
    private int countdownFontSize = -1;

    @Inject
    private DaeyaltMiningTimerOverlay(Client client, DaeyaltMiningTimerPlugin plugin, DaeyaltMiningTimerConfig config, SkillIconManager skillIconManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.skillIconManager = skillIconManager;

        setLayer(OverlayLayer.ABOVE_SCENE);
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.outsideDaeyaltEssenceMine())
            return null;

        var activeDaeyaltEssence = plugin.getActiveDaeyaltEssence();
        if (activeDaeyaltEssence == null)
            return null;

        var miningStateColours = config.miningStateColours();

        if (config.showDaeyaltEssenceClickbox())
        {
            var clickbox = activeDaeyaltEssence.getClickbox();
            var mousePosition = client.getMouseCanvasPosition();
            if (miningStateColours)
            {
                var border = plugin.getMiningStateColour();
                OverlayUtil.renderHoverableArea(graphics, clickbox, mousePosition, ColorUtil.colorWithAlpha(border, 50), border, border.darker());
            }
            else
            {
                OverlayUtil.renderHoverableArea(graphics, clickbox, mousePosition, plugin.getClickboxFillColorMinable(), plugin.getClickboxBorderColorMinable(), plugin.getClickboxBorderHoverColorMinable());
            }
        }

        if (config.showDaeyaltEssenceIndicator())
        {
            var gameObjectLocation = activeDaeyaltEssence.getLocalLocation();
            OverlayUtil.renderImageLocation(client, graphics, gameObjectLocation, skillIconManager.getSkillImage(Skill.MINING, false), Z_OFFSET);
        }

        if (config.showDaeyaltEssenceTimer())
        {
            var position = activeDaeyaltEssence.getCanvasLocation(0);
            if (position == null)
                return null;

            var spawnedTicks = client.getTickCount() - plugin.getActiveDaeyaltEssenceSpawnTick();
            var canMove = spawnedTicks >= MIN_DESPAWN_TICK;

            Color colour;
            if (miningStateColours && (!canMove || config.miningStateOverridesWarning()))
                colour = plugin.getMiningStateColour();
            else
                colour = canMove ? config.warningColour() : config.timerColour();

            var style = config.timerStyle();

            if (style != DaeyaltMiningTimerConfig.TimerStyle.COUNTDOWN)
            {
                var pie = new ProgressPieComponent();
                pie.setPosition(position);
                pie.setDiameter(config.timerSize());
                pie.setProgress(Math.max(0d, 1.0d - spawnedTicks / (double)MAX_DESPAWN_TICK));
                pie.setBorderColor(colour.darker());
                pie.setFill(colour);
                pie.render(graphics);
            }

            if (style != DaeyaltMiningTimerConfig.TimerStyle.PIE)
            {
                // Count down to the earliest possible move, then to the latest once it can move.
                var remainingTicks = Math.max(0, (canMove ? MAX_DESPAWN_TICK : MIN_DESPAWN_TICK) - spawnedTicks);
                var text = formatCountdown(remainingTicks);
                var textColour = style == DaeyaltMiningTimerConfig.TimerStyle.COUNTDOWN ? colour : config.countdownTextColour();
                renderCentredText(graphics, text, position, textColour);
            }
        }

        return null;
    }

    private String formatCountdown(int ticks)
    {
        if (config.timerUnit() == DaeyaltMiningTimerConfig.TimerUnit.TICKS)
            return Integer.toString(ticks);

        return String.format("%.1f", ticks * Constants.GAME_TICK_LENGTH / 1000.0);
    }

    private void renderCentredText(Graphics2D graphics, String text, net.runelite.api.Point centre, Color colour)
    {
        var size = config.countdownTextSize();
        if (countdownFont == null || countdownFontSize != size)
        {
            countdownFont = FontManager.getRunescapeBoldFont().deriveFont((float) size);
            countdownFontSize = size;
        }

        graphics.setFont(countdownFont);
        var metrics = graphics.getFontMetrics();
        var x = centre.getX() - metrics.stringWidth(text) / 2;
        var y = centre.getY() + (metrics.getAscent() - metrics.getDescent()) / 2;

        OverlayUtil.renderTextLocation(graphics, new net.runelite.api.Point(x, y), text, colour);
    }
}

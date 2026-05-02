package com.evo.points.calculator.days;

import com.evo.points.calculator.DayRewardConfig;
import com.evo.points.calculator.RewardTier;
import com.evo.points.calculator.utils.ScreenshotUtils;
import com.evo.points.calculator.utils.ValidationUtils;
import com.evo.points.model.Reward;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day1AlbumCards {
    private static final Logger LOGGER = Logger.getLogger(Day1AlbumCards.class.getName());

    // Константы
    public static final int EVO_CARD_POINTS = 50;
    public static final int MIN_POINTS = 300;
    private static final int MAX_POINTS = 68000;

    public static final String SCREENSHOT_BASE_PATH = "img/day_1/";

    // Конфиг наград
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(MIN_POINTS, ScreenshotUtils.getRewardScreenshot(MIN_POINTS)),
                    new RewardTier(1200, ScreenshotUtils.getRewardScreenshot(1200)),
                    new RewardTier(3000, ScreenshotUtils.getRewardScreenshot(3000)),
                    new RewardTier(6000, ScreenshotUtils.getRewardScreenshot(6000)),
                    new RewardTier(15000, ScreenshotUtils.getRewardScreenshot(15000)),
                    new RewardTier(30000, ScreenshotUtils.getRewardScreenshot(30000)),
                    new RewardTier(40000, ScreenshotUtils.getRewardScreenshot(40000)),
                    new RewardTier(MAX_POINTS, ScreenshotUtils.getRewardScreenshot(MAX_POINTS))
            ),
            MAX_POINTS,
            ScreenshotUtils.TOP_REWARD,
            true   // сохраняем текущее поведение: топ всегда показывается
    );

    /**
     * День 1: Энергия
     *
     * @param cards количество карт
     * @return общее количество очков
     */
    public static int calculatePoints(int cards) {
        ValidationUtils.validateNonNegative(cards, "cards");
        int result = cards * EVO_CARD_POINTS;
        LOGGER.log(Level.FINE, "Day1 calculated: cards={0}, points={1}", new Object[]{cards, result});
        return result;
    }

    // Проверка, есть ли награда (минимальный порог)
    public static boolean hasReward(int points) {
        boolean result = points >= MIN_POINTS;
        LOGGER.log(Level.FINE, "Day 1 hasReward: point={0}, result={1}",
                new Object[]{points, result});
        return result;
    }

    // Получение списка наград
    public static List<Reward> getRewards(int points) {
        List<Reward> rewards = new ArrayList<>();
        for (RewardTier tier : REWARD_CONFIG.getNormalTiers()) {
            if (points >= tier.getThreshold()) {
                rewards.add(new Reward(REWARD_CONFIG.getBasePath() +
                        tier.getScreenshotName()));
            }
        }
        boolean shouldShowTop = REWARD_CONFIG.isAlwaysShowTop()
                || (REWARD_CONFIG.getTopThreshold() != null && points >=
                    REWARD_CONFIG.getTopThreshold());
        if (shouldShowTop && REWARD_CONFIG.getTopScreenshotName() != null) {
            rewards.add(new Reward(REWARD_CONFIG.getBasePath() +
                    REWARD_CONFIG.getTopScreenshotName(),
                    Reward.RewardType.TOP));
        }
        LOGGER.log(Level.FINE, "Day1 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});
        return rewards;
    }
}
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

public class Day4Blueprint {

    private static final Logger LOGGER = Logger.getLogger(Day4Blueprint.class.getName());

    // ===== Константы для Дня 4 (Чертежи) =====
    public static final int COMMON_POINTS = 30;
    public static final int ADVANCED_POINTS = 810;
    public static final int DONATE_POINTS = 3;
    public static final int MIN_POINTS = 300;

    private static final int MAX_POINTS = 88000;

    // Пути к скриншотам наград для Дня 4
    public static final String SCREENSHOT_BASE_PATH = "img/day_4/";

    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(MIN_POINTS, ScreenshotUtils.getRewardScreenshot(MIN_POINTS)),
                    new RewardTier(1200, ScreenshotUtils.getRewardScreenshot(1200)),
                    new RewardTier(2400, ScreenshotUtils.getRewardScreenshot(2400)),
                    new RewardTier(6000, ScreenshotUtils.getRewardScreenshot(6000)),
                    new RewardTier(15000, ScreenshotUtils.getRewardScreenshot(15000)),
                    new RewardTier(30000, ScreenshotUtils.getRewardScreenshot(30000)),
                    new RewardTier(56000, ScreenshotUtils.getRewardScreenshot(56000)),
                    new RewardTier(MAX_POINTS, ScreenshotUtils.getRewardScreenshot(MAX_POINTS))
            ),
            MAX_POINTS,
            ScreenshotUtils.TOP_REWARD,
            true
    );

    public static int calculatePoints(int commonModules, int advancedModules, int donate) {
        ValidationUtils.validateNonNegative(commonModules, "commonModules");
        ValidationUtils.validateNonNegative(advancedModules, "advancedModules");
        ValidationUtils.validateNonNegative(donate, "donate");
        int result = commonModules * COMMON_POINTS + advancedModules * ADVANCED_POINTS
                + donate * DONATE_POINTS;
        LOGGER.log(Level.FINE, "Day4 calculated: points={0}", result);
        return result;
    }

    public static boolean hasReward(int points) {
        boolean result = points >= MIN_POINTS;
        LOGGER.log(Level.FINE, "Day3 hasReward: points={0}, result={1}",
                new Object[]{points, result});
        return result;
    }

    public static List<Reward> getRewards(int points) {
        return buildRewardsByConfig(points, REWARD_CONFIG);
    }

    private static List<Reward> buildRewardsByConfig(int points, DayRewardConfig config) {
        List<Reward> rewards = new ArrayList<>();
        for (RewardTier tier : config.getNormalTiers()) {
            if (points >= tier.getThreshold()) {
                rewards.add(new Reward(config.getBasePath() + tier.getScreenshotName()));
            }
        }
        boolean shouldShowTop = config.isAlwaysShowTop()
                || (config.getTopThreshold() != null && points >= config.getTopThreshold());
        if (shouldShowTop && config.getTopScreenshotName() != null) {
            rewards.add(new Reward(config.getBasePath() + config.getTopScreenshotName(),
                    Reward.RewardType.TOP));
        }
        LOGGER.log(Level.FINE, "Day4 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});
        return rewards;
    }
}
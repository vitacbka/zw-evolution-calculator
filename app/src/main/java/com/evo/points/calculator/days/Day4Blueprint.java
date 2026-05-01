package com.evo.points.calculator.days;

import com.evo.points.calculator.DayRewardConfig;
import com.evo.points.calculator.RewardTier;
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

    // Пути к скриншотам наград для Дня 4
    public static final String SCREENSHOT_BASE_PATH = "img/day_4/";
    public static final String SCREENSHOT_300 = "300_reward.png";
    public static final String SCREENSHOT_1200 = "1200_reward.png";
    public static final String SCREENSHOT_2400 = "2400_reward.png";
    public static final String SCREENSHOT_6000 = "6000_reward.png";
    public static final String SCREENSHOT_15000 = "15000_reward.png";
    public static final String SCREENSHOT_30000 = "30000_reward.png";
    public static final String SCREENSHOT_56000 = "56000_reward.png";
    public static final String SCREENSHOT_88000 = "88000_reward.png";
    public static final String SCREENSHOT_TOP = "top_reward.png";

    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, SCREENSHOT_300),
                    new RewardTier(1200, SCREENSHOT_1200),
                    new RewardTier(2400, SCREENSHOT_2400),
                    new RewardTier(6000, SCREENSHOT_6000),
                    new RewardTier(15000, SCREENSHOT_15000),
                    new RewardTier(30000, SCREENSHOT_30000),
                    new RewardTier(56000, SCREENSHOT_56000),
                    new RewardTier(88000, SCREENSHOT_88000)
            ),
            88000,
            SCREENSHOT_TOP,
            true
    );

    public static int calculatePoints(int commonModules, int advancedModules, int donate) {
        validateNonNegative(commonModules, "commonModules");
        validateNonNegative(advancedModules, "advancedModules");
        validateNonNegative(donate, "donate");
        int result = commonModules * COMMON_POINTS + advancedModules * ADVANCED_POINTS
                + donate * DONATE_POINTS;
        LOGGER.log(Level.FINE, "Day4 calculated: points={0}", result);
        return result;
    }

    public static boolean hasReward(int points) {
        return points >= MIN_POINTS;
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

    private static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " cannot be negative: " + value);
        }
    }
}
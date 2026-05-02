package com.evo.points.calculator.days;

import static com.evo.points.calculator.utils.ValidationUtils.*;

import com.evo.points.calculator.DayRewardConfig;
import com.evo.points.calculator.RewardTier;
import com.evo.points.calculator.utils.ScreenshotUtils;
import com.evo.points.model.Reward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day3Camp {

    private static final Logger LOGGER = Logger.getLogger(Day3Camp.class.getName());

    // ===== Константы для Дня 3 (Лагерь) =====
    public static final int STEEL_DIVISOR = 200;
    public static final int ENERGY_DIVISOR = 200;
    public static final int BOOST_POINTS = 1;
    public static final int BATTLE_CORE_POINTS = 500;
    public static final int DEV_CORE_POINTS = 500;
    public static final int DONATE_POINTS = 3;
    public static final int MIN_POINTS = 300;
    private static final int MAX_POINTS = 74000;

    // Пути к скриншотам наград для Дня 3
    public static final String SCREENSHOT_BASE_PATH = "img/day_3/";

    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(MIN_POINTS, ScreenshotUtils.getRewardScreenshot(MIN_POINTS)),
                    new RewardTier(1200, ScreenshotUtils.getRewardScreenshot(1200)),
                    new RewardTier(2400, ScreenshotUtils.getRewardScreenshot(2400)),
                    new RewardTier(6000, ScreenshotUtils.getRewardScreenshot(6000)),
                    new RewardTier(17000, ScreenshotUtils.getRewardScreenshot(17000)),
                    new RewardTier(30000, ScreenshotUtils.getRewardScreenshot(30000)),
                    new RewardTier(45000, ScreenshotUtils.getRewardScreenshot(45000)),
                    new RewardTier(MAX_POINTS, ScreenshotUtils.getRewardScreenshot(MAX_POINTS))
                    ),
            MAX_POINTS,
            ScreenshotUtils.TOP_REWARD,
            true);

    /**
     * День 3: Лагерь
     *
     * @param steel      количество стали
     * @param energy     количество энергии
     * @param boost      количество ускорений
     * @param battleCore количество техноядер (бой)
     * @param devCore    количество техноядер (развитие)
     * @param donate     количество пополнений
     * @return общее количество очков
     */
    public static int calculatePoints(int steel, int energy, int boost, int battleCore, int devCore, int donate) {
        validateNonNegative(steel, "steel");
        validateNonNegative(energy, "energy");
        validateNonNegative(boost, "boost");
        validateNonNegative(battleCore, "battleCore");
        validateNonNegative(devCore, "devCore");
        validateNonNegative(donate, "donate");
        int result = (steel / STEEL_DIVISOR) + (energy / ENERGY_DIVISOR) +
                (boost * BOOST_POINTS) + (battleCore * BATTLE_CORE_POINTS) +
                (devCore * DEV_CORE_POINTS) + (donate * DONATE_POINTS);
        LOGGER.log(Level.FINE, "Day3 calculated: points={0}", result);
        return result;
    }

    public static boolean hasReward(int points) {
        boolean result = points >= MIN_POINTS;
        LOGGER.log(Level.FINE, "Day 3 hasReward, points={0}, result={1}",
                new Object[]{points,result});
        return result;
    }


    public static List<Reward> getRewards(int points) {  // переименовать метод
        List<Reward> rewards = new ArrayList<>();
        for (RewardTier tier : REWARD_CONFIG.getNormalTiers()) {
            if (points >= tier.getThreshold()) {
                rewards.add(new Reward(REWARD_CONFIG.getBasePath() + tier.getScreenshotName()));
            }
        }
        boolean shouldShowTop = REWARD_CONFIG.isAlwaysShowTop()
                || (REWARD_CONFIG.getTopThreshold() != null && points >= REWARD_CONFIG.getTopThreshold());
        if (shouldShowTop && REWARD_CONFIG.getTopScreenshotName() != null) {
            rewards.add(new Reward(REWARD_CONFIG.getBasePath() + REWARD_CONFIG.getTopScreenshotName(),
                    Reward.RewardType.TOP));
        }

        LOGGER.log(Level.FINE, "Day3 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});

        return rewards;
    }
}

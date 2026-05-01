package com.evo.points.calculator.days;

import com.evo.points.calculator.DayRewardConfig;
import com.evo.points.calculator.RewardTier;
import com.evo.points.model.Reward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day5Neurolink {
    private static final Logger LOGGER = Logger.getLogger(Day5Neurolink.class.getName());

    // ===== Константы для расчёта очков =====
    public static final int SYNAPTIC_CHIP_POINTS = 5;
    public static final int NEURO_CODER_POINTS = 10;
    public static final int CORTICAL_IMPLANT_POINTS = 2000;
    public static final int DONATE_POINTS = 3;
    public static final int MIN_POINTS = 300;

    // ===== Пути к скриншотам наград для Дня 5 =====
    public static final String SCREENSHOT_BASE_PATH = "img/day_5/";
    public static final String SCREENSHOT_300 = "300_reward.png";
    public static final String SCREENSHOT_1200 = "10000_reward.png";
    public static final String SCREENSHOT_2400 = "2400_reward.png";
    public static final String SCREENSHOT_6000 = "6000_reward.png";
    public static final String SCREENSHOT_10000 = "10000_reward.png";
    public static final String SCREENSHOT_25000 = "25000_reward.png";
    public static final String SCREENSHOT_48000 = "48000_reward.png";
    public static final String SCREENSHOT_72000 = "72000_reward.png";
    public static final String SCREENSHOT_TOP = "top_reward.png";

    // ===== Конфиг наград =====
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, SCREENSHOT_300),
                    new RewardTier(1200, SCREENSHOT_1200),
                    new RewardTier(2400, SCREENSHOT_2400),
                    new RewardTier(6000, SCREENSHOT_6000),
                    new RewardTier(10000, SCREENSHOT_10000),
                    new RewardTier(25000, SCREENSHOT_25000),
                    new RewardTier(48000, SCREENSHOT_48000),
                    new RewardTier(72000, SCREENSHOT_72000)
            ),
            72000,
            SCREENSHOT_TOP,
            true
    );

    /**
     * День 5: Невролинк
     *
     * @param synapticChips   количество чипов синаптического усиления
     * @param neuroCoder      количество нейрокодировщиков
     * @param corticalImplant количество кортикальных имплантов
     * @param donate          количество пополнений
     * @return общее количество очков
     */
    public static int calculatePoints(int synapticChips, int neuroCoder, int corticalImplant, int donate) {
        validateNonNegative(synapticChips, "synapticChips");
        validateNonNegative(neuroCoder, "neuroCoder");
        validateNonNegative(corticalImplant, "corticalImplant");
        validateNonNegative(donate, "donate");

        int result = synapticChips * SYNAPTIC_CHIP_POINTS
                + neuroCoder * NEURO_CODER_POINTS
                + corticalImplant * CORTICAL_IMPLANT_POINTS
                + donate * DONATE_POINTS;

        LOGGER.log(Level.FINE, "Day5 calculated: synapticChips={0}, neuroCoder={1}, " +
                        "corticalImplant={2}, donate={3}, points={4}",
                new Object[]{synapticChips, neuroCoder, corticalImplant, donate, result});
        return result;
    }

    /**
     * Проверяет, достигнут ли минимальный порог для получения награды.
     *
     * @param points количество очков
     * @return true, если есть хотя бы одна награда
     */
    public static boolean hasReward(int points) {
        return points >= MIN_POINTS;
    }

    /**
     * Возвращает список наград в зависимости от количества очков.
     *
     * @param points количество очков
     * @return список наград
     */
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

        LOGGER.log(Level.FINE, "Day5 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});

        return rewards;
    }

    private static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " cannot be negative: " + value);
        }
    }
}
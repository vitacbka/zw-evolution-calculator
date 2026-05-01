package com.evo.points.calculator.days;

import com.evo.points.calculator.DayRewardConfig;
import com.evo.points.calculator.RewardTier;
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

    public static final String SCREENSHOT_BASE_PATH = "img/day_1/";
    public static final String SCREENSHOT_300 = "300_reward.png";
    public static final String SCREENSHOT_1200 = "1200_reward.png";
    public static final String SCREENSHOT_3000 = "3000_reward.png";
    public static final String SCREENSHOT_6000 = "6000_reward.png";
    public static final String SCREENSHOT_15000 = "15000_reward.png";
    public static final String SCREENSHOT_30000 = "30000_reward.png";
    public static final String SCREENSHOT_40000 = "40000_reward.png";
    public static final String SCREENSHOT_68000 = "68000_reward.png";
    public static final String SCREENSHOT_TOP = "top_reward.png";

    // Конфиг наград
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, SCREENSHOT_300),
                    new RewardTier(1200, SCREENSHOT_1200),
                    new RewardTier(3000, SCREENSHOT_3000),
                    new RewardTier(6000, SCREENSHOT_6000),
                    new RewardTier(15000, SCREENSHOT_15000),
                    new RewardTier(30000, SCREENSHOT_30000),
                    new RewardTier(40000, SCREENSHOT_40000),
                    new RewardTier(68000, SCREENSHOT_68000)
            ),
            68000,
            SCREENSHOT_TOP,
            true   // сохраняем текущее поведение: топ всегда показывается
    );

    /**
     * День 1: Энергия
     *
     * @param cards количество карт
     * @return общее количество очков
     */
    public static int calculatePoints(int cards) {
        if (cards < 0) {
            throw new IllegalArgumentException("cards cannot be negative: " + cards);
        }
        int result = cards * EVO_CARD_POINTS;
        LOGGER.log(Level.FINE, "Day1 calculated: cards={0}, points={1}", new Object[]{cards, result});
        return result;
    }

    // Проверка, есть ли награда (минимальный порог)
    public static boolean hasReward(int points) {
        return points >= MIN_POINTS;
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
package com.evo.points.calculator.days;

import com.evo.points.calculator.DayRewardConfig;
import com.evo.points.calculator.RewardTier;
import com.evo.points.model.Reward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day7Topup {
    private static final Logger LOGGER = Logger.getLogger(Day7Topup.class.getName());
    public static final int DONATE_POINTS = 6;

    // ===== Пути к скриншотам наград для Дня 7 =====
    public static final String SCREENSHOT_BASE_PATH = "img/day_7/";
    public static final String SCREENSHOT_TOP = "top_revard.png";

    // ===== Конфиг наград =====
    // День 7: normalTiers пустой, показывается только TOP
    // как только в игре появятся пороги обычных наград, добавьте их в normalTiers:
    // new RewardTier(10000, "10000_revard.png"), new RewardTier(25000, "25000_revard.png"), ...
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Collections.emptyList(),  // нет обычных наград
            null,                      // нет порога для TOP (показывается всегда)
            SCREENSHOT_TOP,
            true                       // всегда показывать TOP
    );

    /**
     * День 7: Пополнение
     *
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculatePoints(int donate) {
        validateNonNegative(donate, "donate");
        int result = donate * DONATE_POINTS;
        LOGGER.log(Level.FINE, "Day7 calculated: donate={0}, points={1}", new Object[]{donate, result});
        return result;
    }

    /**
     * День 7: Всегда возвращает true, так как TOP награда показывается всегда.
     * Если появятся обычные награды, этот метод нужно будет изменить.
     *
     * @param points количество очков
     * @return true (всегда есть TOP награда)
     */
    public static boolean hasReward(int points) {
        // TOP награда показывается всегда независимо от очков
        return true;
    }

    /**
     * Возвращает список наград в зависимости от количества очков.
     * Для дня 7 всегда возвращает только TOP награду.
     *
     * @param points количество очков
     * @return список наград (всегда содержит TOP)
     */
    public static List<Reward> getRewards(int points) {
        return buildRewardsByConfig(points, REWARD_CONFIG);
    }

    // ===== Вспомогательные методы =====

    private static List<Reward> buildRewardsByConfig(int points, DayRewardConfig config) {
        List<Reward> rewards = new ArrayList<>();

        // Обычные награды (пусто для дня 7)
        for (RewardTier tier : config.getNormalTiers()) {
            if (points >= tier.getThreshold()) {
                rewards.add(new Reward(config.getBasePath() + tier.getScreenshotName()));
            }
        }

        // TOP награда (всегда показывается для дня 7)
        boolean shouldShowTop = config.isAlwaysShowTop()
                || (config.getTopThreshold() != null && points >= config.getTopThreshold());

        if (shouldShowTop && config.getTopScreenshotName() != null) {
            rewards.add(new Reward(config.getBasePath() + config.getTopScreenshotName(),
                    Reward.RewardType.TOP));
        }

        LOGGER.log(Level.FINE, "Day7 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});

        return rewards;
    }

    private static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " cannot be negative: " + value);
        }
    }
}
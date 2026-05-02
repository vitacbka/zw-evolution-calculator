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

public class Day7Topup {
    private static final Logger LOGGER = Logger.getLogger(Day7Topup.class.getName());

    // ===== Константы для расчёта очков =====
    public static final int DONATE_POINTS = 6;
    public static final int MIN_POINTS = 600;
    private static final int MAX_POINTS = 148800;

    // ===== Пути к скриншотам наград для Дня 7 =====
    public static final String SCREENSHOT_BASE_PATH = "img/day_7/";

    // ===== Конфиг наград =====
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(MIN_POINTS, ScreenshotUtils.getRewardScreenshot(MIN_POINTS)),
                    new RewardTier(2400, ScreenshotUtils.getRewardScreenshot(2400)),
                    new RewardTier(4800, ScreenshotUtils.getRewardScreenshot(4800)),
                    new RewardTier(9800, ScreenshotUtils.getRewardScreenshot(9800)),
                    new RewardTier(58000, ScreenshotUtils.getRewardScreenshot(58000)),
                    new RewardTier(88800, ScreenshotUtils.getRewardScreenshot(88800)),
                    new RewardTier(118800, ScreenshotUtils.getRewardScreenshot(118800)),
                    new RewardTier(MAX_POINTS, ScreenshotUtils.getRewardScreenshot(MAX_POINTS))
            ),
            MAX_POINTS,
            ScreenshotUtils.TOP_REWARD,
            true
    );

    /**
     * День 7: Пополнение
     *
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculatePoints(int donate) {
        ValidationUtils.validateNonNegative(donate, "donate");
        int result = donate * DONATE_POINTS;
        LOGGER.log(Level.FINE, "Day7 calculated: donate={0}, points={1}", new Object[]{donate, result});
        return result;
    }

    /**
     * Проверяет, достигнут ли минимальный порог для получения награды.
     *
     * @param points количество очков
     * @return true, если есть хотя бы одна награда
     */
    public static boolean hasReward(int points) {
        boolean result = points >= MIN_POINTS;
        LOGGER.log(Level.FINE, "Day7 hasReward: points={0}, result={1}", new Object[]{points, result});
        return result;
    }

    /**
     * Возвращает список наград в зависимости от количества очков.
     *
     * @param points количество очков
     * @return список наград
     */
    public static List<Reward> getRewards(int points) {
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

        LOGGER.log(Level.FINE, "Day7 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});

        return rewards;
    }
}
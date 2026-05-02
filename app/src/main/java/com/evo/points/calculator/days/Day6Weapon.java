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

public class Day6Weapon {
    private static final Logger LOGGER = Logger.getLogger(Day6Weapon.class.getName());

    // ===== Константы для расчёта очков =====
    public static final int WEAPON_TICKET_POINTS = 120;
    public static final int GREEN_BOX_POINTS = 10;
    public static final int BLUE_BOX_POINTS = 30;
    public static final int VIOLET_BOX_POINTS = 250;
    public static final int YELLOW_BOX_POINTS = 2500;
    public static final int DONATE_POINTS = 3;
    public static final int MIN_POINTS = 7500;
    private static final int MAX_POINTS = 115000;

    // ===== Вероятности получения сундуков =====
    public static final double BLUE_FROM_GREEN_CHANCE = 0.05; // 5%
    public static final double VIOLET_FROM_BLUE_CHANCE = 0.04; // 4%

    // ===== Пути к скриншотам наград для Дня 6 =====
    public static final String SCREENSHOT_BASE_PATH = "img/day_6/";

    // ===== Конфиг наград =====
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(MIN_POINTS, ScreenshotUtils.getRewardScreenshot(MIN_POINTS)),
                    new RewardTier(1200, ScreenshotUtils.getRewardScreenshot(1200)),
                    new RewardTier(2400, ScreenshotUtils.getRewardScreenshot(2400)),
                    new RewardTier(6000, ScreenshotUtils.getRewardScreenshot(6000)),
                    new RewardTier(15000, ScreenshotUtils.getRewardScreenshot(15000)),
                    new RewardTier(25000, ScreenshotUtils.getRewardScreenshot(25000)),
                    new RewardTier(45000, ScreenshotUtils.getRewardScreenshot(45000)),
                    new RewardTier(75000, ScreenshotUtils.getRewardScreenshot(75000)),
                    new RewardTier(MAX_POINTS, ScreenshotUtils.getRewardScreenshot(MAX_POINTS))),
            MAX_POINTS,
            ScreenshotUtils.TOP_REWARD,
            true);

    /**
     * День 6: Оружие/Акс.
     *
     * @param weaponTickets количество билетов розыгрыша оружия
     * @param greenBoxes    количество зелёных ящиков
     * @param blueBoxes     количество синих ящиков
     * @param violetBoxes   количество фиолетовых ящиков
     * @param yellowBoxes   количество жёлтых ящиков
     * @param donate        количество пополнений
     * @return общее количество очков
     */
    public static int calculatePoints(int weaponTickets, int greenBoxes, int blueBoxes,
            int violetBoxes, int yellowBoxes, int donate) {
        ValidationUtils.validateNonNegative(weaponTickets, "weaponTickets");
        ValidationUtils.validateNonNegative(greenBoxes, "greenBoxes");
        ValidationUtils.validateNonNegative(blueBoxes, "blueBoxes");
        ValidationUtils.validateNonNegative(violetBoxes, "violetBoxes");
        ValidationUtils.validateNonNegative(yellowBoxes, "yellowBoxes");
        ValidationUtils.validateNonNegative(donate, "donate");

        int result = weaponTickets * WEAPON_TICKET_POINTS
                + greenBoxes * GREEN_BOX_POINTS
                + blueBoxes * BLUE_BOX_POINTS
                + violetBoxes * VIOLET_BOX_POINTS
                + yellowBoxes * YELLOW_BOX_POINTS
                + donate * DONATE_POINTS;

        LOGGER.log(Level.FINE, "Day6 calculated: weaponTickets={0}, greenBoxes={1}, blueBoxes={2}, " +
                "violetBoxes={3}, yellowBoxes={4}, donate={5}, points={6}",
                new Object[] { weaponTickets, greenBoxes, blueBoxes, violetBoxes, yellowBoxes, donate, result });
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
        LOGGER.log(Level.FINE, "Day6 hasReward: points={0}, result={1}",
                new Object[]{points,result});
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

    // ===== Расчёт вероятностей для Дня 6 =====

    /**
     * Расчёт ожидаемого количества синих сундуков из зелёных (5% шанс)
     */
    public static int getExpectedBlueFromGreen(int greenBoxes) {
        ValidationUtils.validateNonNegative(greenBoxes, "greenBoxes");
        return (int) Math.round(greenBoxes * BLUE_FROM_GREEN_CHANCE);
    }

    /**
     * Расчёт ожидаемого количества фиолетовых сундуков из синих (4% шанс)
     */
    public static int getExpectedVioletFromBlue(int blueBoxes) {
        ValidationUtils.validateNonNegative(blueBoxes, "blueBoxes");
        return (int) Math.round(blueBoxes * VIOLET_FROM_BLUE_CHANCE);
    }

    /**
     * Расчёт диапазона (мин/макс) с учётом дисперсии
     */
    public static int getMinExpected(int expected) {
        return (int) Math.floor(expected * 0.5);
    }

    public static int getMaxExpected(int expected) {
        return (int) Math.ceil(expected * 1.5);
    }

    /**
     * Расчёт потенциальных дополнительных очков от сундуков
     */
    public static int getPotentialPointsFromBoxes(int greenBoxes, int blueBoxes) {
        int blueFromGreen = getExpectedBlueFromGreen(greenBoxes);
        int violetFromBlue = getExpectedVioletFromBlue(blueBoxes);
        return blueFromGreen * BLUE_BOX_POINTS + violetFromBlue * VIOLET_BOX_POINTS;
    }

    // ===== Вспомогательные методы =====

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

        LOGGER.log(Level.FINE, "Day6 rewards built for points={0}, count={1}, top={2}",
                new Object[] { points, rewards.size(), shouldShowTop });

        return rewards;
    }
}

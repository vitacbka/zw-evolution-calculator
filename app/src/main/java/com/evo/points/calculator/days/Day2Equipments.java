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

public class Day2Equipments {
    private static final Logger LOGGER = Logger.getLogger(Day2Equipments.class.getName());

    // ===== Константы для Дня 2 (Экипировка) =====
    public static final int TICKET_POINTS = 300;
    public static final int DONATE_POINTS = 3;
    public static final int MIN_POINTS = 300;
    private static final int MAX_POINTS = 68000;

    // Пути к скриншотам наград для Дня 2
    public static final String DAY2_SCREENSHOT_BASE_PATH = "img/day_2/";

    /**
 * Конфигурация наград для Дня 2 (Экипировка).
 * <p>
 * Содержит базовый путь к изображениям наград, список порогов с соответствующими скриншотами,
 * порог для топ-награды, имя файла скриншота топ-награды и флаг, указывающий,
 * нужно ли всегда отображать топ-награду независимо от количества очков.
 * </p>
 *
 * @param basePath          базовый путь к директории с изображениями наград
 * @param normalTiers       список обычных уровней наград с порогами и именами файлов скриншотов
 * @param topThreshold      порог очков для получения топ-награды (может быть null)
 * @param topScreenshotName имя файла скриншота топ-награды (может быть null)
 * @param alwaysShowTop     true по дефолту, должно отображаться даже если награды = 0
 */
    private static final DayRewardConfig REWARD_CONFIG = new DayRewardConfig(
            DAY2_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(MIN_POINTS, ScreenshotUtils.getRewardScreenshot(MIN_POINTS)),
                    new RewardTier(1200, ScreenshotUtils.getRewardScreenshot(1200)),
                    new RewardTier(2400, ScreenshotUtils.getRewardScreenshot(2400)),
                    new RewardTier(6800, ScreenshotUtils.getRewardScreenshot(6800)),
                    new RewardTier(15000, ScreenshotUtils.getRewardScreenshot(15000)),
                    new RewardTier(30000, ScreenshotUtils.getRewardScreenshot(30000)),
                    new RewardTier(48000, ScreenshotUtils.getRewardScreenshot(48000)),
                    new RewardTier(MAX_POINTS, ScreenshotUtils.getRewardScreenshot(MAX_POINTS))
            ),
            MAX_POINTS,
            ScreenshotUtils.TOP_REWARD,
            true
            );

    /**
     * День 2: Экипировка
     *
     * @param tickets количество билетов на экипировку
     * @param donate  количество пополнений
     * @return общее количество очков
     */
    public static int calculatePoints(int tickets, int donate) {
        ValidationUtils.validateNonNegative(tickets, "tickets");
        ValidationUtils.validateNonNegative(donate, "donate");
        int result = tickets * TICKET_POINTS + donate * DONATE_POINTS;
        LOGGER.log(Level.FINE, "Day2 calculated: tickets={0}, donate={1}, points={2}",
                new Object[]{tickets, donate, result});
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
        LOGGER.log(Level.FINE, "Day2 hasReward={0}, result={1}",
                new Object[]{points, result});
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
        LOGGER.log(Level.FINE, "Day2 rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});
        return rewards;
    }
}
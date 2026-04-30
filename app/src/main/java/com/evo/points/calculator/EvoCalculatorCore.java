package com.evo.points.calculator;

import com.evo.points.model.Reward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ядро калькулятора очков эволюции.
 * Содержит всю бизнес-логику расчёта очков и наград.
 */
public class EvoCalculatorCore {
    private static final Logger LOGGER = Logger.getLogger(EvoCalculatorCore.class.getName());

    // ===== Константы для Дня 1 (Карты эволюции) =====
    public static final int DAY1_EVO_CARD_POINTS = 50;
    public static final int DAY1_MIN_POINTS = 300;

    // Пути к скриншотам наград для Дня 1
    public static final String DAY1_SCREENSHOT_BASE_PATH = "img/day_1/";
    public static final String DAY1_SCREENSHOT_300 = "300_reward.png";
    public static final String DAY1_SCREENSHOT_1200 = "1200_reward.png";
    public static final String DAY1_SCREENSHOT_3000 = "3000_reward.png";
    public static final String DAY1_SCREENSHOT_6000 = "6000_reward.png";
    public static final String DAY1_SCREENSHOT_15000 = "15000_reward.png";
    public static final String DAY1_SCREENSHOT_30000 = "30000_reward.png";
    public static final String DAY1_SCREENSHOT_40000 = "40000_reward.png";
    public static final String DAY1_SCREENSHOT_68000 = "68000_reward.png";
    public static final String DAY1_SCREENSHOT_TOP = "top_reward.png";

    // ===== Константы для Дня 2 (Экипировка) =====
    public static final int DAY2_TICKET_POINTS = 300;
    public static final int DAY2_DONATE_POINTS = 3;
    public static final int DAY2_MIN_POINTS = 300;

    // Пути к скриншотам наград для Дня 2
    public static final String DAY2_SCREENSHOT_BASE_PATH = "img/day_2/";
    public static final String DAY2_SCREENSHOT_300 = "300_reward.png";
    public static final String DAY2_SCREENSHOT_1200 = "1200_reward.png";
    public static final String DAY2_SCREENSHOT_2400 = "2400_reward.png";
    public static final String DAY2_SCREENSHOT_6800 = "6800_reward.png";
    public static final String DAY2_SCREENSHOT_15000 = "15000_reward.png";
    public static final String DAY2_SCREENSHOT_30000 = "30000_reward.png";
    public static final String DAY2_SCREENSHOT_48000 = "48000_reward.png";
    public static final String DAY2_SCREENSHOT_68000 = "68000_reward.png";
    public static final String DAY2_SCREENSHOT_TOP = "top_reward.png";

    // ===== Константы для Дня 3 (Лагерь) =====
    public static final int DAY3_STEEL_DIVISOR = 200;
    public static final int DAY3_ENERGY_DIVISOR = 200;
    public static final int DAY3_BOOST_DIVISOR = 2;
    public static final int DAY3_BATTLE_CORE_POINTS = 500;
    public static final int DAY3_DEV_CORE_POINTS = 500;
    public static final int DAY3_DONATE_POINTS = 3;
    public static final int DAY3_MIN_POINTS = 300;

    // Пути к скриншотам наград для Дня 3
    public static final String DAY3_SCREENSHOT_BASE_PATH = "img/day_3/";
    public static final String DAY3_SCREENSHOT_300 = "300_reward.png";
    public static final String DAY3_SCREENSHOT_1200 = "1200_reward.png";
    public static final String DAY3_SCREENSHOT_2400 = "2400_reward.png";
    public static final String DAY3_SCREENSHOT_6000 = "6000_reward.png";
    public static final String DAY3_SCREENSHOT_17000 = "17000_reward.png";
    public static final String DAY3_SCREENSHOT_30000 = "30000_reward.png";
    public static final String DAY3_SCREENSHOT_45000 = "45000_reward.png";
    public static final String DAY3_SCREENSHOT_74000 = "74000_reward.png";
    public static final String DAY3_SCREENSHOT_TOP = "top_reward.png";

    // ===== Константы для Дня 4 (Чертежи) =====
    public static final int DAY4_COMMON_POINTS = 30;
    public static final int DAY4_ADVANCED_POINTS = 810;
    public static final int DAY4_DONATE_POINTS = 3;
    public static final int DAY4_MIN_POINTS = 300;

    // Пути к скриншотам наград для Дня 4
    public static final String DAY4_SCREENSHOT_BASE_PATH = "img/day_4/";
    public static final String DAY4_SCREENSHOT_300 = "300_reward.png";
    public static final String DAY4_SCREENSHOT_1200 = "1200_reward.png";
    public static final String DAY4_SCREENSHOT_2400 = "2400_reward.png";
    public static final String DAY4_SCREENSHOT_6000 = "6000_reward.png";
    public static final String DAY4_SCREENSHOT_15000 = "15000_reward.png";
    public static final String DAY4_SCREENSHOT_30000 = "30000_reward.png";
    public static final String DAY4_SCREENSHOT_56000 = "56000_reward.png";
    public static final String DAY4_SCREENSHOT_88000 = "88000_reward.png";
    public static final String DAY4_SCREENSHOT_TOP = "top_reward.png";

    // ===== Константы для Дня 5 (Невролинк) =====
    public static final int DAY5_SYNAPTIC_CHIP_POINTS = 5;
    public static final int DAY5_NEURO_CODER_POINTS = 10;
    public static final int DAY5_CORTICAL_IMPLANT_POINTS = 2000;
    public static final int DAY5_DONATE_POINTS = 3;
    public static final int DAY5_MIN_POINTS = 5000;
    public static final int DAY5_MAX_POINTS = 72000;

    // Пути к скриншотам наград для Дня 5
    public static final String DAY5_SCREENSHOT_BASE_PATH = "img/day_5/";
    public static final String DAY5_SCREENSHOT_5000 = "5000_revard.png";
    public static final String DAY5_SCREENSHOT_10000 = "10000_revard.png";
    public static final String DAY5_SCREENSHOT_25000 = "25000_revard.png";
    public static final String DAY5_SCREENSHOT_48000 = "48000_revard.png";
    public static final String DAY5_SCREENSHOT_72000 = "72000_revard.png";
    public static final String DAY5_SCREENSHOT_TOP = "top_revard.png";

    // ===== Константы для Дня 6 (Оружие/Акс.) =====
    public static final int DAY6_WEAPON_TICKET_POINTS = 120;
    public static final int DAY6_GREEN_BOX_POINTS = 10;
    public static final int DAY6_BLUE_BOX_POINTS = 30;
    public static final int DAY6_VIOLET_BOX_POINTS = 250;
    public static final int DAY6_YELLOW_BOX_POINTS = 2500;
    public static final int DAY6_DONATE_POINTS = 3;
    public static final int DAY6_MIN_POINTS = 7500;
    public static final int DAY6_MAX_POINTS = 115000;

    // Пути к скриншотам наград для Дня 6
    public static final String DAY6_SCREENSHOT_BASE_PATH = "img/day_6/";
    public static final String DAY6_SCREENSHOT_7500 = "7500_revard.png";
    public static final String DAY6_SCREENSHOT_20000 = "20000_revard.png";
    public static final String DAY6_SCREENSHOT_45000 = "45000_revard.png";
    public static final String DAY6_SCREENSHOT_75000 = "75000_revard.png";
    public static final String DAY6_SCREENSHOT_115000 = "115000_revard.png";
    public static final String DAY6_SCREENSHOT_TOP = "top_revard.png";

    // ===== Константы для Дня 7 (Пополнение) =====
    public static final int DAY7_DONATE_POINTS = 6;

    // Пути к скриншотам наград для Дня 7
    public static final String DAY7_SCREENSHOT_BASE_PATH = "img/day_7/";
    public static final String DAY7_SCREENSHOT_TOP = "top_revard.png";

    // Вероятности получения сундуков
    public static final double DAY6_BLUE_FROM_GREEN_CHANCE = 0.05; // 5%
    public static final double DAY6_VIOLET_FROM_BLUE_CHANCE = 0.04; // 4%

    private static final class RewardTier {
        private final int threshold;
        private final String screenshotName;

        private RewardTier(int threshold, String screenshotName) {
            this.threshold = threshold;
            this.screenshotName = screenshotName;
        }
    }

    private static final class DayRewardConfig {
        private final String basePath;
        private final List<RewardTier> normalTiers;
        private final Integer topThreshold;
        private final String topScreenshotName;
        private final boolean alwaysShowTop;

        private DayRewardConfig(String basePath,
                                List<RewardTier> normalTiers,
                                Integer topThreshold,
                                String topScreenshotName,
                                boolean alwaysShowTop) {
            this.basePath = basePath;
            this.normalTiers = normalTiers;
            this.topThreshold = topThreshold;
            this.topScreenshotName = topScreenshotName;
            this.alwaysShowTop = alwaysShowTop;
        }
    }

    private static final DayRewardConfig DAY1_REWARD_CONFIG = new DayRewardConfig(
            DAY1_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, DAY1_SCREENSHOT_300),
                    new RewardTier(1200, DAY1_SCREENSHOT_1200),
                    new RewardTier(3000, DAY1_SCREENSHOT_3000),
                    new RewardTier(6000, DAY1_SCREENSHOT_6000),
                    new RewardTier(15000, DAY1_SCREENSHOT_15000),
                    new RewardTier(30000, DAY1_SCREENSHOT_30000),
                    new RewardTier(40000, DAY1_SCREENSHOT_40000),
                    new RewardTier(68000, DAY1_SCREENSHOT_68000)
            ),
            68000,
            DAY1_SCREENSHOT_TOP,
            true
    );

    private static final DayRewardConfig DAY2_REWARD_CONFIG = new DayRewardConfig(
            DAY2_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, DAY2_SCREENSHOT_300),
                    new RewardTier(1200, DAY2_SCREENSHOT_1200),
                    new RewardTier(2400, DAY2_SCREENSHOT_2400),
                    new RewardTier(6800, DAY2_SCREENSHOT_6800),
                    new RewardTier(15000, DAY2_SCREENSHOT_15000),
                    new RewardTier(30000, DAY2_SCREENSHOT_30000),
                    new RewardTier(48000, DAY2_SCREENSHOT_48000),
                    new RewardTier(68000, DAY2_SCREENSHOT_68000)
            ),
            68000,
            DAY2_SCREENSHOT_TOP,
            true
    );

    private static final DayRewardConfig DAY3_REWARD_CONFIG = new DayRewardConfig(
            DAY3_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, DAY3_SCREENSHOT_300),
                    new RewardTier(1200, DAY3_SCREENSHOT_1200),
                    new RewardTier(2400, DAY3_SCREENSHOT_2400),
                    new RewardTier(6000, DAY3_SCREENSHOT_6000),
                    new RewardTier(17000, DAY3_SCREENSHOT_17000),
                    new RewardTier(30000, DAY3_SCREENSHOT_30000),
                    new RewardTier(45000, DAY3_SCREENSHOT_45000),
                    new RewardTier(74000, DAY3_SCREENSHOT_74000)
            ),
            74000,
            DAY3_SCREENSHOT_TOP,
            true
    );

    private static final DayRewardConfig DAY4_REWARD_CONFIG = new DayRewardConfig(
            DAY4_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(300, DAY4_SCREENSHOT_300),
                    new RewardTier(1200, DAY4_SCREENSHOT_1200),
                    new RewardTier(2400, DAY4_SCREENSHOT_2400),
                    new RewardTier(6000, DAY4_SCREENSHOT_6000),
                    new RewardTier(15000, DAY4_SCREENSHOT_15000),
                    new RewardTier(30000, DAY4_SCREENSHOT_30000),
                    new RewardTier(56000, DAY4_SCREENSHOT_56000),
                    new RewardTier(88000, DAY4_SCREENSHOT_88000)
            ),
            DAY4_MIN_POINTS,
            DAY4_SCREENSHOT_TOP,
            true
    );

    private static final DayRewardConfig DAY5_REWARD_CONFIG = new DayRewardConfig(
            DAY5_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(5000, DAY5_SCREENSHOT_5000),
                    new RewardTier(10000, DAY5_SCREENSHOT_10000),
                    new RewardTier(25000, DAY5_SCREENSHOT_25000),
                    new RewardTier(48000, DAY5_SCREENSHOT_48000),
                    new RewardTier(72000, DAY5_SCREENSHOT_72000)
            ),
            72000,
            DAY5_SCREENSHOT_TOP,
            true
    );

    private static final DayRewardConfig DAY6_REWARD_CONFIG = new DayRewardConfig(
            DAY6_SCREENSHOT_BASE_PATH,
            Arrays.asList(
                    new RewardTier(7500, DAY6_SCREENSHOT_7500),
                    new RewardTier(20000, DAY6_SCREENSHOT_20000),
                    new RewardTier(45000, DAY6_SCREENSHOT_45000),
                    new RewardTier(75000, DAY6_SCREENSHOT_75000),
                    new RewardTier(115000, DAY6_SCREENSHOT_115000)
            ),
            115000,
            DAY6_SCREENSHOT_TOP,
            true
    );

    // День 7 уже подготовлен к расширению:
    // - сейчас normalTiers пустой и показывается только TOP;
    // - как только в игре появятся пороги обычных наград, добавьте их в normalTiers:
    //   new RewardTier(10000, "10000_revard.png"), new RewardTier(25000, "25000_revard.png"), ...
    // - если top должен открываться только от порога, задайте topThreshold и установите alwaysShowTop=false.
    private static final DayRewardConfig DAY7_REWARD_CONFIG = new DayRewardConfig(
            DAY7_SCREENSHOT_BASE_PATH,
            Collections.emptyList(),
            null,
            DAY7_SCREENSHOT_TOP,
            true
    );

    // ===== Расчёт очков для всех дней =====

    /**
     * День 1: Карты эволюции
     *
     * @param cards количество карт эволюции
     * @return общее количество очков
     */
    public static int calculateDay1(int cards) {
        validateNonNegative(cards, "cards");
        int result = cards * DAY1_EVO_CARD_POINTS;
        logCalculation("Day1", result, cards);
        return result;
    }

    /**
     * День 2: Экипировка
     *
     * @param tickets количество билетов на экипировку
     * @param donate  количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay2(int tickets, int donate) {
        validateNonNegative(tickets, "tickets");
        validateNonNegative(donate, "donate");
        int result = tickets * DAY2_TICKET_POINTS + donate * DAY2_DONATE_POINTS;
        logCalculation("Day2", result, tickets, donate);
        return result;
    }

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
    public static int calculateDay3(int steel, int energy, int boost, int battleCore, int devCore, int donate) {
        validateNonNegative(steel, "steel");
        validateNonNegative(energy, "energy");
        validateNonNegative(boost, "boost");
        validateNonNegative(battleCore, "battleCore");
        validateNonNegative(devCore, "devCore");
        validateNonNegative(donate, "donate");

        int result = (steel / DAY3_STEEL_DIVISOR) +
                (energy / DAY3_ENERGY_DIVISOR) +
                (boost / DAY3_BOOST_DIVISOR) +
                (battleCore * DAY3_BATTLE_CORE_POINTS) +
                (devCore * DAY3_DEV_CORE_POINTS) +
                (donate * DAY3_DONATE_POINTS);

        logCalculation("Day3", result, steel, energy, boost, battleCore, devCore, donate);
        return result;
    }

    /**
     * День 4: Чертежи
     *
     * @param commonModules   количество обычных модулей
     * @param advancedModules количество продвинутых модулей
     * @param donate          количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay4(int commonModules, int advancedModules, int donate) {
        validateNonNegative(commonModules, "commonModules");
        validateNonNegative(advancedModules, "advancedModules");
        validateNonNegative(donate, "donate");
        int result = commonModules * DAY4_COMMON_POINTS + advancedModules * DAY4_ADVANCED_POINTS + donate * DAY4_DONATE_POINTS;
        logCalculation("Day4", result, commonModules, advancedModules, donate);
        return result;
    }

    /**
     * День 5: Невролинк
     *
     * @param synapticChips   количество чипов синаптического усиления
     * @param neuroCoder      количество нейрокодировщиков
     * @param corticalImplant количество кортикальных имплантов
     * @param donate          количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay5(int synapticChips, int neuroCoder, int corticalImplant, int donate) {
        validateNonNegative(synapticChips, "synapticChips");
        validateNonNegative(neuroCoder, "neuroCoder");
        validateNonNegative(corticalImplant, "corticalImplant");
        validateNonNegative(donate, "donate");
        int result = synapticChips * DAY5_SYNAPTIC_CHIP_POINTS + neuroCoder * DAY5_NEURO_CODER_POINTS +
                corticalImplant * DAY5_CORTICAL_IMPLANT_POINTS + donate * DAY5_DONATE_POINTS;
        logCalculation("Day5", result, synapticChips, neuroCoder, corticalImplant, donate);
        return result;
    }

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
    public static int calculateDay6(int weaponTickets, int greenBoxes, int blueBoxes,
                                    int violetBoxes, int yellowBoxes, int donate) {
        validateNonNegative(weaponTickets, "weaponTickets");
        validateNonNegative(greenBoxes, "greenBoxes");
        validateNonNegative(blueBoxes, "blueBoxes");
        validateNonNegative(violetBoxes, "violetBoxes");
        validateNonNegative(yellowBoxes, "yellowBoxes");
        validateNonNegative(donate, "donate");
        int result = weaponTickets * DAY6_WEAPON_TICKET_POINTS + greenBoxes * DAY6_GREEN_BOX_POINTS +
                blueBoxes * DAY6_BLUE_BOX_POINTS + violetBoxes * DAY6_VIOLET_BOX_POINTS +
                yellowBoxes * DAY6_YELLOW_BOX_POINTS + donate * DAY6_DONATE_POINTS;
        logCalculation("Day6", result, weaponTickets, greenBoxes, blueBoxes, violetBoxes, yellowBoxes, donate);
        return result;
    }

    /**
     * День 7: Пополнение
     *
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay7(int donate) {
        validateNonNegative(donate, "donate");
        int result = donate * DAY7_DONATE_POINTS;
        logCalculation("Day7", result, donate);
        return result;
    }

    // ===== Расчёт вероятностей для Дня 6 =====

    /**
     * Расчёт ожидаемого количества синих сундуков из зелёных (5% шанс)
     */
    public static int getExpectedBlueFromGreen(int greenBoxes) {
        validateNonNegative(greenBoxes, "greenBoxes");
        return (int) Math.round(greenBoxes * DAY6_BLUE_FROM_GREEN_CHANCE);
    }

    /**
     * Расчёт ожидаемого количества фиолетовых сундуков из синих (4% шанс)
     */
    public static int getExpectedVioletFromBlue(int blueBoxes) {
        validateNonNegative(blueBoxes, "blueBoxes");
        return (int) Math.round(blueBoxes * DAY6_VIOLET_FROM_BLUE_CHANCE);
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
        return blueFromGreen * DAY6_BLUE_BOX_POINTS + violetFromBlue * DAY6_VIOLET_BOX_POINTS;
    }

    // ===== Проверка наград =====

    public static boolean hasDay1Reward(int points) {
        return points >= DAY1_MIN_POINTS;
    }

    public static boolean hasDay2Reward(int points) {
        return points >= DAY2_MIN_POINTS;
    }

    public static boolean hasDay3Reward(int points) {
        return points >= DAY3_MIN_POINTS;
    }

    public static boolean hasDay4Reward(int points) {
        return points >= DAY4_MIN_POINTS;
    }

    public static boolean hasDay5Reward(int points) {
        return points >= DAY5_MIN_POINTS;
    }

    public static boolean hasDay6Reward(int points) {
        return points >= DAY6_MIN_POINTS;
    }

    // ===== Методы для получения списка наград =====

    /**
     * День 1: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 3000 → 9000 → 20000 → 36000 → 40000 → 68000 (top)
     * top_revard показывается ТОЛЬКО при 69000+
     */
    public static List<Reward> getDay1Rewards(int points) {
        return buildRewardsByConfig(points, DAY1_REWARD_CONFIG);
    }

    /**
     * День 2: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 300 → 1200 → 2400 → 6800 → 15000 → 30000 → 48000 → 68000 → top
     * top_revard показывается ТОЛЬКО при 68000+
     */
    public static List<Reward> getDay2Rewards(int points) {
        return buildRewardsByConfig(points, DAY2_REWARD_CONFIG);
    }

    /**
     * День 3: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 300 → 1200 → 2400 → 6000 → 17000 → 30000 → 45000 → 74000 → top
     * top_revard показывается ТОЛЬКО при 74000+
     */
    public static List<Reward> getDay3Rewards(int points) {
        return buildRewardsByConfig(points, DAY3_REWARD_CONFIG);
    }

    /**
     * День 4: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 300 → 1200 → 2400 → 6000 → 15000 → 30000 → 56000 → 88000 → top
     * top_revard показывается ТОЛЬКО при 88000+
     */
    public static List<Reward> getDay4Rewards(int points) {
        return buildRewardsByConfig(points, DAY4_REWARD_CONFIG);
    }

    /**
     * День 5: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 5000 → 10000 → 25000 → 48000 → 72000 → top
     * top_revard показывается ТОЛЬКО при 72000+
     */
    public static List<Reward> getDay5Rewards(int points) {
        return buildRewardsByConfig(points, DAY5_REWARD_CONFIG);
    }

    /**
     * День 6: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 7500 → 20000 → 45000 → 75000 → 115000 → top
     * top_revard показывается ТОЛЬКО при 115000+
     */
    public static List<Reward> getDay6Rewards(int points) {
        return buildRewardsByConfig(points, DAY6_REWARD_CONFIG);
    }

    /**
     * День 7: Возвращает только топ награду (всегда показывается)
     */
    public static List<Reward> getDay7Rewards(int points) {
        return buildRewardsByConfig(points, DAY7_REWARD_CONFIG);
    }

    // ===== Вспомогательные методы =====

    private static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " не может быть отрицательным: " + value);
        }
    }

    private static List<Reward> buildRewardsByConfig(int points, DayRewardConfig config) {
        List<Reward> rewards = new ArrayList<>();

        // Пример логики: при points=30000 и tier-ах [6000, 15000, 30000, 48000]
        // будут добавлены первые 3 изображения (награды до текущего порога включительно).
        for (RewardTier tier : config.normalTiers) {
            if (points >= tier.threshold) {
                rewards.add(new Reward(config.basePath + tier.screenshotName));
            }
        }

        boolean shouldShowTop = config.alwaysShowTop
                || (config.topThreshold != null && points >= config.topThreshold);

        if (shouldShowTop && config.topScreenshotName != null) {
            rewards.add(new Reward(config.basePath + config.topScreenshotName,
                    Reward.RewardType.TOP)
            );
        }

        LOGGER.log(Level.FINE, "Rewards built for points={0}, count={1}, top={2}",
                new Object[]{points, rewards.size(), shouldShowTop});

        return rewards;
    }

    private static void logCalculation(String day, int result, int... values) {
        if (!LOGGER.isLoggable(Level.FINE)) {
            return;
        }
        LOGGER.log(Level.FINE, "{0} calculated: inputs={1}, points={2}",
                new Object[]{day, Arrays.toString(values), result});
    }
}

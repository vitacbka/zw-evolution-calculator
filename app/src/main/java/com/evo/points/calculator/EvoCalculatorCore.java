package com.evo.points.calculator;

import com.evo.points.model.Reward;

import java.util.ArrayList;
import java.util.List;

/**
 * Ядро калькулятора очков эволюции.
 * Содержит всю бизнес-логику расчёта очков и наград.
 */
public class EvoCalculatorCore {

    // ===== ID иконок для наград (placeholder) =====
    public static final int ICON_TESSERACT = 1;
    public static final int ICON_VOUCHER_GEAR = 2;
    public static final int ICON_CHIP = 3;
    public static final int ICON_VOUCHER_WEAPON = 4;
    public static final int ICON_DIAMOND = 5;
    public static final int ICON_CHEST_S = 6;
    public static final int ICON_CHEST_RESOURCE = 7;
    public static final int ICON_EPIC_GEAR = 8;
    public static final int ICON_EXCHANGE_GEAR = 9;
    public static final int ICON_TONIC = 10;
    public static final int ICON_ORNAMENT_LEGENDARY = 11;
    public static final int ICON_ORNAMENT_EPIC = 12;
    public static final int ICON_TECH_CORE_BATTLE = 13;
    public static final int ICON_TECH_CORE_DEV = 14;
    public static final int ICON_MODULE_VI = 15;
    public static final int ICON_MODULE_V = 16;
    public static final int ICON_MODULE_EXCHANGE = 17;
    public static final int ICON_ADVANCED_MODULE = 18;
    public static final int ICON_NEURO_MATERIAL = 19;
    public static final int ICON_NEURO_CHEST = 20;
    public static final int ICON_WEAPON_CHEST = 21;
    public static final int ICON_CRYSTAL_LEGENDARY = 22;
    public static final int ICON_IMPLANT = 23;
    public static final int ICON_ANTI_MATTER = 24;
    public static final int ICON_PRECISION_COMPONENT = 25;
    public static final int ICON_SUPPLY_CHEST_LEGENDARY = 26;
    public static final int ICON_SUPPLY_CHEST_EPIC = 27;
    public static final int ICON_SUPPLY_CHEST_ELITE = 28;
    public static final int ICON_SUPPLY_CHEST_ADVANCED = 29;

    // ===== Константы для Дня 1 (Энергия) =====
    public static final int DAY1_ENERGY_POINTS = 30;
    public static final int DAY1_DONATE_POINTS = 3;
    public static final int DAY1_MIN_POINTS = 3000;
    public static final int DAY1_MAX_POINTS = 69000;

    // Пути к скриншотам наград для Дня 1
    public static final String DAY1_SCREENSHOT_BASE_PATH = "img/Day 1/";
    public static final String DAY1_SCREENSHOT_3000 = "3000_revard.png";
    public static final String DAY1_SCREENSHOT_9000 = "9000_revard.png";
    public static final String DAY1_SCREENSHOT_20000 = "20000_revard.png";
    public static final String DAY1_SCREENSHOT_36000 = "36000_revard.png";
    public static final String DAY1_SCREENSHOT_40000 = "40000_revard.png";
    public static final String DAY1_SCREENSHOT_TOP = "top_revard.png";

    // ===== Константы для Дня 2 (Экипировка) =====
    public static final int DAY2_TICKET_POINTS = 300;
    public static final int DAY2_DONATE_POINTS = 3;
    public static final int DAY2_MIN_POINTS = 6000;
    public static final int DAY2_MAX_POINTS = 68000;

    // Пути к скриншотам наград для Дня 2
    public static final String DAY2_SCREENSHOT_BASE_PATH = "img/Day 2/";
    public static final String DAY2_SCREENSHOT_6000 = "6000_revard.png";
    public static final String DAY2_SCREENSHOT_15000 = "15000_revard.png";
    public static final String DAY2_SCREENSHOT_30000 = "30000_revard.png";
    public static final String DAY2_SCREENSHOT_48000 = "48000_revard.png";
    public static final String DAY2_SCREENSHOT_68000 = "68000_revard.png";
    public static final String DAY2_SCREENSHOT_TOP = "2)day_top_tier_revard.png";

    // ===== Константы для Дня 3 (Лагерь) =====
    public static final int DAY3_STEEL_DIVISOR = 200;
    public static final int DAY3_ENERGY_DIVISOR = 200;
    public static final int DAY3_BOOST_POINTS = 1;
    public static final int DAY3_BATTLE_CORE_POINTS = 500;
    public static final int DAY3_DEV_CORE_POINTS = 500;
    public static final int DAY3_DONATE_POINTS = 3;
    public static final int DAY3_MIN_POINTS = 6000;
    public static final int DAY3_MAX_POINTS = 74000;

    // Пути к скриншотам наград для Дня 3
    public static final String DAY3_SCREENSHOT_BASE_PATH = "img/Day 3/";
    public static final String DAY3_SCREENSHOT_6000 = "6000_revard.png";
    public static final String DAY3_SCREENSHOT_17000 = "17000_revard.png";
    public static final String DAY3_SCREENSHOT_30000 = "30000_revard.png";
    public static final String DAY3_SCREENSHOT_45000 = "45000_revard.png";
    public static final String DAY3_SCREENSHOT_74000 = "74000_revard.png";
    public static final String DAY3_SCREENSHOT_TOP = "top_revard.png";

    // ===== Константы для Дня 4 (Чертежи) =====
    public static final int DAY4_COMMON_POINTS = 30;
    public static final int DAY4_ADVANCED_POINTS = 810;
    public static final int DAY4_DONATE_POINTS = 3;
    public static final int DAY4_MIN_POINTS = 6000;
    public static final int DAY4_MAX_POINTS = 88000;

    // Пути к скриншотам наград для Дня 4
    public static final String DAY4_SCREENSHOT_BASE_PATH = "img/Day 4/";
    public static final String DAY4_SCREENSHOT_6000 = "6000_revard.png";
    public static final String DAY4_SCREENSHOT_15000 = "15000_revard.png";
    public static final String DAY4_SCREENSHOT_30000 = "30000_revard.png";
    public static final String DAY4_SCREENSHOT_56000 = "56000_revard.png";
    public static final String DAY4_SCREENSHOT_88000 = "88000_revard.png";
    public static final String DAY4_SCREENSHOT_TOP = "top_revard.png";

    // ===== Константы для Дня 5 (Невролинк) =====
    public static final int DAY5_SYNAPTIC_CHIP_POINTS = 5;
    public static final int DAY5_NEURO_CODER_POINTS = 10;
    public static final int DAY5_CORTICAL_IMPLANT_POINTS = 2000;
    public static final int DAY5_DONATE_POINTS = 3;
    public static final int DAY5_MIN_POINTS = 5000;
    public static final int DAY5_MAX_POINTS = 72000;

    // Пути к скриншотам наград для Дня 5
    public static final String DAY5_SCREENSHOT_BASE_PATH = "img/Day 5/";
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
    public static final String DAY6_SCREENSHOT_BASE_PATH = "img/Day 6/";
    public static final String DAY6_SCREENSHOT_7500 = "7500_revard.png";
    public static final String DAY6_SCREENSHOT_20000 = "20000_revard.png";
    public static final String DAY6_SCREENSHOT_45000 = "45000_revard.png";
    public static final String DAY6_SCREENSHOT_75000 = "75000_revard.png";
    public static final String DAY6_SCREENSHOT_115000 = "115000_revard.png";
    public static final String DAY6_SCREENSHOT_TOP = "top_revard.png";

    // ===== Константы для Дня 7 (Пополнение) =====
    public static final int DAY7_DONATE_POINTS = 6;

    // Пути к скриншотам наград для Дня 7
    public static final String DAY7_SCREENSHOT_BASE_PATH = "img/Day 7/";
    public static final String DAY7_SCREENSHOT_TOP = "top_revard.png";

    // Вероятности получения сундуков
    public static final double DAY6_BLUE_FROM_GREEN_CHANCE = 0.05; // 5%
    public static final double DAY6_VIOLET_FROM_BLUE_CHANCE = 0.04; // 4%

    // ===== Расчёт очков для всех дней =====

    /**
     * День 1: Энергия
     * @param energy количество энергии
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay1(int energy, int donate) {
        validateNonNegative(energy, "energy");
        validateNonNegative(donate, "donate");
        return energy * DAY1_ENERGY_POINTS + donate * DAY1_DONATE_POINTS;
    }

    /**
     * День 2: Экипировка
     * @param tickets количество билетов на экипировку
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay2(int tickets, int donate) {
        validateNonNegative(tickets, "tickets");
        validateNonNegative(donate, "donate");
        return tickets * DAY2_TICKET_POINTS + donate * DAY2_DONATE_POINTS;
    }

    /**
     * День 3: Лагерь
     * @param steel количество стали
     * @param energy количество энергии
     * @param boost количество ускорений
     * @param battleCore количество техноядер (бой)
     * @param devCore количество техноядер (развитие)
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay3(int steel, int energy, int boost, int battleCore, int devCore, int donate) {
        validateNonNegative(steel, "steel");
        validateNonNegative(energy, "energy");
        validateNonNegative(boost, "boost");
        validateNonNegative(battleCore, "battleCore");
        validateNonNegative(devCore, "devCore");
        validateNonNegative(donate, "donate");
        return (steel / DAY3_STEEL_DIVISOR) + (energy / DAY3_ENERGY_DIVISOR) + 
               (boost * DAY3_BOOST_POINTS) + (battleCore * DAY3_BATTLE_CORE_POINTS) + 
               (devCore * DAY3_DEV_CORE_POINTS) + (donate * DAY3_DONATE_POINTS);
    }

    /**
     * День 4: Чертежи
     * @param commonModules количество обычных модулей
     * @param advancedModules количество продвинутых модулей
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay4(int commonModules, int advancedModules, int donate) {
        validateNonNegative(commonModules, "commonModules");
        validateNonNegative(advancedModules, "advancedModules");
        validateNonNegative(donate, "donate");
        return commonModules * DAY4_COMMON_POINTS + advancedModules * DAY4_ADVANCED_POINTS + donate * DAY4_DONATE_POINTS;
    }

    /**
     * День 5: Невролинк
     * @param synapticChips количество чипов синаптического усиления
     * @param neuroCoder количество нейрокодировщиков
     * @param corticalImplant количество кортикальных имплантов
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay5(int synapticChips, int neuroCoder, int corticalImplant, int donate) {
        validateNonNegative(synapticChips, "synapticChips");
        validateNonNegative(neuroCoder, "neuroCoder");
        validateNonNegative(corticalImplant, "corticalImplant");
        validateNonNegative(donate, "donate");
        return synapticChips * DAY5_SYNAPTIC_CHIP_POINTS + neuroCoder * DAY5_NEURO_CODER_POINTS + 
               corticalImplant * DAY5_CORTICAL_IMPLANT_POINTS + donate * DAY5_DONATE_POINTS;
    }

    /**
     * День 6: Оружие/Акс.
     * @param weaponTickets количество билетов розыгрыша оружия
     * @param greenBoxes количество зелёных ящиков
     * @param blueBoxes количество синих ящиков
     * @param violetBoxes количество фиолетовых ящиков
     * @param yellowBoxes количество жёлтых ящиков
     * @param donate количество пополнений
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
        return weaponTickets * DAY6_WEAPON_TICKET_POINTS + greenBoxes * DAY6_GREEN_BOX_POINTS + 
               blueBoxes * DAY6_BLUE_BOX_POINTS + violetBoxes * DAY6_VIOLET_BOX_POINTS + 
               yellowBoxes * DAY6_YELLOW_BOX_POINTS + donate * DAY6_DONATE_POINTS;
    }

    /**
     * День 7: Пополнение
     * @param donate количество пополнений
     * @return общее количество очков
     */
    public static int calculateDay7(int donate) {
        validateNonNegative(donate, "donate");
        return donate * DAY7_DONATE_POINTS;
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
     * Логика порогов: 3000 → 9000 → 20000 → 36000 → 40000 → 69000 (top)
     * top_revard показывается ТОЛЬКО при 69000+
     */
    public static List<Reward> getDay1Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();

        if (points >= 69000) {
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_3000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_9000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_20000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_36000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_40000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_TOP, Reward.RewardType.TOP));
        } else if (points >= 40000) {
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_3000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_9000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_20000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_36000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_40000));
        } else if (points >= 36000) {
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_3000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_9000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_20000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_36000));
        } else if (points >= 20000) {
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_3000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_9000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_20000));
        } else if (points >= 9000) {
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_3000));
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_9000));
        } else if (points >= 3000) {
            rewards.add(new Reward(DAY1_SCREENSHOT_BASE_PATH + DAY1_SCREENSHOT_3000));
        }

        return rewards;
    }

    /**
     * День 2: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 6000 → 15000 → 30000 → 48000 → 68000 → top
     * top_revard показывается ТОЛЬКО при 68000+
     */
    public static List<Reward> getDay2Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();

        if (points >= 68000) {
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_6000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_15000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_30000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_48000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_68000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_TOP, Reward.RewardType.TOP));
        } else if (points >= 48000) {
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_6000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_15000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_30000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_48000));
        } else if (points >= 30000) {
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_6000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_15000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_30000));
        } else if (points >= 15000) {
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_6000));
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_15000));
        } else if (points >= 6000) {
            rewards.add(new Reward(DAY2_SCREENSHOT_BASE_PATH + DAY2_SCREENSHOT_6000));
        }

        return rewards;
    }

    /**
     * День 3: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 6000 → 17000 → 30000 → 45000 → 74000 → top
     * top_revard показывается ТОЛЬКО при 74000+
     */
    public static List<Reward> getDay3Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();

        if (points >= 74000) {
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_6000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_17000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_30000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_45000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_74000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_TOP, Reward.RewardType.TOP));
        } else if (points >= 45000) {
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_6000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_17000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_30000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_45000));
        } else if (points >= 30000) {
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_6000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_17000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_30000));
        } else if (points >= 17000) {
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_6000));
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_17000));
        } else if (points >= 6000) {
            rewards.add(new Reward(DAY3_SCREENSHOT_BASE_PATH + DAY3_SCREENSHOT_6000));
        }

        return rewards;
    }

    /**
     * День 4: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 6000 → 15000 → 30000 → 56000 → 88000 → top
     * top_revard показывается ТОЛЬКО при 88000+
     */
    public static List<Reward> getDay4Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();

        if (points >= 88000) {
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_6000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_15000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_30000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_56000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_88000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_TOP, Reward.RewardType.TOP));
        } else if (points >= 56000) {
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_6000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_15000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_30000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_56000));
        } else if (points >= 30000) {
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_6000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_15000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_30000));
        } else if (points >= 15000) {
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_6000));
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_15000));
        } else if (points >= 6000) {
            rewards.add(new Reward(DAY4_SCREENSHOT_BASE_PATH + DAY4_SCREENSHOT_6000));
        }

        return rewards;
    }

    /**
     * День 5: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 5000 → 10000 → 25000 → 48000 → 72000 → top
     * top_revard показывается ТОЛЬКО при 72000+
     */
    public static List<Reward> getDay5Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();

        if (points >= 72000) {
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_5000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_10000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_25000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_48000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_72000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_TOP, Reward.RewardType.TOP));
        } else if (points >= 48000) {
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_5000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_10000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_25000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_48000));
        } else if (points >= 25000) {
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_5000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_10000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_25000));
        } else if (points >= 10000) {
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_5000));
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_10000));
        } else if (points >= 5000) {
            rewards.add(new Reward(DAY5_SCREENSHOT_BASE_PATH + DAY5_SCREENSHOT_5000));
        }

        return rewards;
    }

    /**
     * День 6: Возвращает список скриншотов наград в зависимости от очков
     * Логика порогов: 7500 → 20000 → 45000 → 75000 → 115000 → top
     * top_revard показывается ТОЛЬКО при 115000+
     */
    public static List<Reward> getDay6Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();

        if (points >= 115000) {
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_7500));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_20000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_45000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_75000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_115000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_TOP, Reward.RewardType.TOP));
        } else if (points >= 75000) {
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_7500));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_20000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_45000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_75000));
        } else if (points >= 45000) {
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_7500));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_20000));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_45000));
        } else if (points >= 20000) {
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_7500));
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_20000));
        } else if (points >= 7500) {
            rewards.add(new Reward(DAY6_SCREENSHOT_BASE_PATH + DAY6_SCREENSHOT_7500));
        }

        return rewards;
    }

    /**
     * День 7: Возвращает только топ награду (всегда показывается)
     */
    public static List<Reward> getDay7Rewards(int points) {
        List<Reward> rewards = new ArrayList<>();
        rewards.add(new Reward(DAY7_SCREENSHOT_BASE_PATH + DAY7_SCREENSHOT_TOP, Reward.RewardType.TOP));
        return rewards;
    }

    // ===== Вспомогательные методы =====

    private static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " не может быть отрицательным: " + value);
        }
    }
}

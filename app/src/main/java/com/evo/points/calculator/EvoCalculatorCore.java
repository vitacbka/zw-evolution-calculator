package com.evo.points.calculator;

/**
 * Ядро калькулятора очков эволюции.
 * Содержит всю бизнес-логику расчёта очков и наград.
 */
public class EvoCalculatorCore {

    // ===== Константы для Дня 1 (Энергия) =====
    public static final int DAY1_ENERGY_POINTS = 30;
    public static final int DAY1_DONATE_POINTS = 3;
    public static final int DAY1_MIN_POINTS = 3000;
    public static final int DAY1_MAX_POINTS = 69000;

    // ===== Константы для Дня 2 (Экипировка) =====
    public static final int DAY2_TICKET_POINTS = 300;
    public static final int DAY2_DONATE_POINTS = 3;
    public static final int DAY2_MIN_POINTS = 6000;
    public static final int DAY2_MAX_POINTS = 68000;

    // ===== Константы для Дня 3 (Лагерь) =====
    public static final int DAY3_STEEL_DIVISOR = 200;
    public static final int DAY3_ENERGY_DIVISOR = 200;
    public static final int DAY3_BOOST_POINTS = 1;
    public static final int DAY3_BATTLE_CORE_POINTS = 500;
    public static final int DAY3_DEV_CORE_POINTS = 500;
    public static final int DAY3_DONATE_POINTS = 3;
    public static final int DAY3_MIN_POINTS = 6000;
    public static final int DAY3_MAX_POINTS = 74000;

    // ===== Константы для Дня 4 (Чертежи) =====
    public static final int DAY4_COMMON_POINTS = 30;
    public static final int DAY4_ADVANCED_POINTS = 810;
    public static final int DAY4_DONATE_POINTS = 3;
    public static final int DAY4_MIN_POINTS = 6000;
    public static final int DAY4_MAX_POINTS = 88000;

    // ===== Константы для Дня 5 (Невролинк) =====
    public static final int DAY5_SYNAPTIC_CHIP_POINTS = 5;
    public static final int DAY5_NEURO_CODER_POINTS = 10;
    public static final int DAY5_CORTICAL_IMPLANT_POINTS = 2000;
    public static final int DAY5_DONATE_POINTS = 3;
    public static final int DAY5_MIN_POINTS = 5000;
    public static final int DAY5_MAX_POINTS = 72000;

    // ===== Константы для Дня 6 (Оружие/Акс.) =====
    public static final int DAY6_WEAPON_TICKET_POINTS = 120;
    public static final int DAY6_GREEN_BOX_POINTS = 10;
    public static final int DAY6_BLUE_BOX_POINTS = 30;
    public static final int DAY6_VIOLET_BOX_POINTS = 250;
    public static final int DAY6_YELLOW_BOX_POINTS = 2500;
    public static final int DAY6_DONATE_POINTS = 3;
    public static final int DAY6_MIN_POINTS = 7500;
    public static final int DAY6_MAX_POINTS = 115000;
    
    // Вероятности получения сундуков
    public static final double DAY6_BLUE_FROM_GREEN_CHANCE = 0.05; // 5%
    public static final double DAY6_VIOLET_FROM_BLUE_CHANCE = 0.04; // 4%

    // ===== Константы для Дня 7 (Пополнение) =====
    public static final int DAY7_DONATE_POINTS = 6;

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

    // ===== Вспомогательные методы =====

    private static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " не может быть отрицательным: " + value);
        }
    }
}

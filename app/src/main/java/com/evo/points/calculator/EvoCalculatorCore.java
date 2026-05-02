package com.evo.points.calculator;

import java.util.List;
import java.util.logging.Logger;

import com.evo.points.calculator.days.Day1AlbumCards;
import com.evo.points.calculator.days.Day2Equipments;
import com.evo.points.calculator.days.Day3Camp;
import com.evo.points.calculator.days.Day4Blueprint;
import com.evo.points.calculator.days.Day5Neurolink;
import com.evo.points.calculator.days.Day6Weapon;
import com.evo.points.calculator.days.Day7Topup;
import com.evo.points.model.Reward;

/**
 * Ядро калькулятора очков эволюции.
 * Содержит всю бизнес-логику расчёта очков и наград.
 * Делегирует вызовы в специализированные классы для каждого дня.
 */
public class EvoCalculatorCore {
    private static final Logger LOGGER = Logger.getLogger(EvoCalculatorCore.class.getName());

    // ==================== ДЕНЬ 1: Альбом/Карты ====================
    public static int calculateDay1(int cards) {
        return Day1AlbumCards.calculatePoints(cards);
    }

    public static boolean hasDay1Reward(int points) {
        return Day1AlbumCards.hasReward(points);
    }

    public static List<Reward> getDay1Rewards(int points) {
        return Day1AlbumCards.getRewards(points);
    }

    // ==================== ДЕНЬ 2: Экипировка ====================
    public static int calculateDay2(int tickets, int donate) {
        return Day2Equipments.calculatePoints(tickets, donate);
    }

    public static boolean hasDay2Reward(int points) {
        return Day2Equipments.hasReward(points);
    }

    public static List<Reward> getDay2Rewards(int points) {
        return Day2Equipments.getRewards(points);
    }

    // ==================== ДЕНЬ 3: Лагерь ====================
    public static int calculateDay3(int steel, int energy, int boost,
                                    int battleCore, int devCore, int donate) {
        return Day3Camp.calculatePoints(steel, energy, boost, battleCore, devCore, donate);
    }

    public static boolean hasDay3Reward(int points) {
        return Day3Camp.hasReward(points);
    }

    public static List<Reward> getDay3Rewards(int points) {
        return Day3Camp.getRewards(points);
    }

    // ==================== ДЕНЬ 4: Чертежи ====================
    public static int calculateDay4(int commonModules, int advancedModules, int donate) {
        return Day4Blueprint.calculatePoints(commonModules, advancedModules, donate);
    }

    public static boolean hasDay4Reward(int points) {
        return Day4Blueprint.hasReward(points);
    }

    public static List<Reward> getDay4Rewards(int points) {
        return Day4Blueprint.getRewards(points);
    }

    // ==================== ДЕНЬ 5: Невролинк ====================
    public static int calculateDay5(int synapticChips, int neuroCoder, int corticalImplant, int donate) {
        return Day5Neurolink.calculatePoints(synapticChips, neuroCoder, corticalImplant, donate);
    }

    public static boolean hasDay5Reward(int points) {
        return Day5Neurolink.hasReward(points);
    }

    public static List<Reward> getDay5Rewards(int points) {
        return Day5Neurolink.getRewards(points);
    }

    // ==================== ДЕНЬ 6: Оружие/Акс. ====================
    public static int calculateDay6(int weaponTickets, int greenBoxes, int blueBoxes,
                                    int violetBoxes, int yellowBoxes, int donate) {
        return Day6Weapon.calculatePoints(weaponTickets, greenBoxes, blueBoxes, violetBoxes, yellowBoxes, donate);
    }

    public static boolean hasDay6Reward(int points) {
        return Day6Weapon.hasReward(points);
    }

    public static List<Reward> getDay6Rewards(int points) {
        return Day6Weapon.getRewards(points);
    }

    /**
     * Расчёт ожидаемого количества синих сундуков из зелёных (5% шанс)
     */
    public static int getExpectedBlueFromGreen(int greenBoxes) {
        return Day6Weapon.getExpectedBlueFromGreen(greenBoxes);
    }

    /**
     * Расчёт ожидаемого количества фиолетовых сундуков из синих (4% шанс)
     */
    public static int getExpectedVioletFromBlue(int blueBoxes) {
        return Day6Weapon.getExpectedVioletFromBlue(blueBoxes);
    }

    /**
     * Расчёт диапазона (мин/макс) с учётом дисперсии
     */
    public static int getMinExpected(int expected) {
        return Day6Weapon.getMinExpected(expected);
    }

    public static int getMaxExpected(int expected) {
        return Day6Weapon.getMaxExpected(expected);
    }

    /**
     * Расчёт потенциальных дополнительных очков от сундуков
     */
    public static int getPotentialPointsFromBoxes(int greenBoxes, int blueBoxes) {
        return Day6Weapon.getPotentialPointsFromBoxes(greenBoxes, blueBoxes);
    }

    // ==================== ДЕНЬ 7: Пополнение ====================
    public static int calculateDay7(int donate) {
        return Day7Topup.calculatePoints(donate);
    }

    public static boolean hasDay7Reward(int points) {
        return Day7Topup.hasReward(points);
    }

    public static List<Reward> getDay7Rewards(int points) {
        return Day7Topup.getRewards(points);
    }
}
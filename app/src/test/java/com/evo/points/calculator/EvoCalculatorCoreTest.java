package com.evo.points.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for EvoCalculatorCore.
 * Tests all calculation methods with valid and invalid inputs.
 */
public class EvoCalculatorCoreTest {

    // ============================================================================
    // DAY 1 TESTS (Энергия)
    // ============================================================================

    @Test
    public void testDay1_basicCalculation() {
        // 100 энергии * 30 + 5 донатов * 3 = 3000 + 15 = 3015
        int result = EvoCalculatorCore.calculateDay1(100, 5);
        assertEquals(3015, result);
    }

    @Test
    public void testDay1_zeroValues() {
        int result = EvoCalculatorCore.calculateDay1(0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay1_onlyEnergy() {
        // 500 энергии * 30 = 15000
        int result = EvoCalculatorCore.calculateDay1(500, 0);
        assertEquals(15000, result);
    }

    @Test
    public void testDay1_onlyDonate() {
        // 10 донатов * 3 = 30
        int result = EvoCalculatorCore.calculateDay1(0, 10);
        assertEquals(30, result);
    }

    @Test
    public void testDay1_largeValues() {
        // 10000 энергии * 30 + 100 донатов * 3 = 300000 + 300 = 300300
        int result = EvoCalculatorCore.calculateDay1(10000, 100);
        assertEquals(300300, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay1_negativeEnergy() {
        EvoCalculatorCore.calculateDay1(-100, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay1_negativeDonate() {
        EvoCalculatorCore.calculateDay1(100, -5);
    }

    @Test
    public void testDay1_rewardThresholds() {
        assertTrue(EvoCalculatorCore.hasDay1Reward(3000));
        assertTrue(EvoCalculatorCore.hasDay1Reward(69000));
        assertFalse(EvoCalculatorCore.hasDay1Reward(2999));
    }

    // ============================================================================
    // DAY 2 TESTS (Экипировка)
    // ============================================================================

    @Test
    public void testDay2_basicCalculation() {
        // 50 билетов * 300 + 10 донатов * 3 = 15000 + 30 = 15030
        int result = EvoCalculatorCore.calculateDay2(50, 10);
        assertEquals(15030, result);
    }

    @Test
    public void testDay2_zeroValues() {
        int result = EvoCalculatorCore.calculateDay2(0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay2_onlyTickets() {
        // 200 билетов * 300 = 60000
        int result = EvoCalculatorCore.calculateDay2(200, 0);
        assertEquals(60000, result);
    }

    @Test
    public void testDay2_onlyDonate() {
        // 50 донатов * 3 = 150
        int result = EvoCalculatorCore.calculateDay2(0, 50);
        assertEquals(150, result);
    }

    @Test
    public void testDay2_largeValues() {
        // 1000 билетов * 300 + 500 донатов * 3 = 300000 + 1500 = 301500
        int result = EvoCalculatorCore.calculateDay2(1000, 500);
        assertEquals(301500, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay2_negativeTickets() {
        EvoCalculatorCore.calculateDay2(-50, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay2_negativeDonate() {
        EvoCalculatorCore.calculateDay2(50, -10);
    }

    @Test
    public void testDay2_rewardThresholds() {
        assertTrue(EvoCalculatorCore.hasDay2Reward(6000));
        assertTrue(EvoCalculatorCore.hasDay2Reward(68000));
        assertFalse(EvoCalculatorCore.hasDay2Reward(5999));
    }

    // ============================================================================
    // DAY 3 TESTS (Лагерь)
    // ============================================================================

    @Test
    public void testDay3_basicCalculation() {
        // steel/200 + energy/200 + boost + battleCore*500 + devCore*500 + donate*3
        // 4000/200 + 2000/200 + 100 + 10*500 + 5*500 + 3*3
        // = 20 + 10 + 100 + 5000 + 2500 + 9 = 7639
        int result = EvoCalculatorCore.calculateDay3(4000, 2000, 100, 10, 5, 3);
        assertEquals(7639, result);
    }

    @Test
    public void testDay3_zeroValues() {
        int result = EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay3_boostCalculation() {
        // 1 ускорение = 1 очко (исправлено!)
        int result = EvoCalculatorCore.calculateDay3(0, 0, 100, 0, 0, 0);
        assertEquals(100, result);
    }

    @Test
    public void testDay3_steelEnergyDivision() {
        // 200 стали = 1 очко, 200 энергии = 1 очко
        int result = EvoCalculatorCore.calculateDay3(200, 200, 0, 0, 0, 0);
        assertEquals(2, result);
    }

    @Test
    public void testDay3_steelEnergyDivisionRemainder() {
        // 199 стали = 0 очков (целочисленное деление)
        int result = EvoCalculatorCore.calculateDay3(199, 199, 0, 0, 0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay3_techCoreCalculation() {
        // 1 техноядро (бой) = 500, 1 техноядро (развитие) = 500
        int result = EvoCalculatorCore.calculateDay3(0, 0, 0, 1, 1, 0);
        assertEquals(1000, result);
    }

    @Test
    public void testDay3_donateCalculation() {
        // 10 донатов * 3 = 30 очков (исправлено!)
        int result = EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, 10);
        assertEquals(30, result);
    }

    @Test
    public void testDay3_largeValues() {
        // 100000/200 + 100000/200 + 1000 + 100*500 + 100*500 + 100*3
        // = 500 + 500 + 1000 + 50000 + 50000 + 300 = 102300
        int result = EvoCalculatorCore.calculateDay3(100000, 100000, 1000, 100, 100, 100);
        assertEquals(102300, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay3_negativeSteel() {
        EvoCalculatorCore.calculateDay3(-100, 0, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay3_negativeEnergy() {
        EvoCalculatorCore.calculateDay3(0, -100, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay3_negativeBoost() {
        EvoCalculatorCore.calculateDay3(0, 0, -100, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay3_negativeBattleCore() {
        EvoCalculatorCore.calculateDay3(0, 0, 0, -10, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay3_negativeDevCore() {
        EvoCalculatorCore.calculateDay3(0, 0, 0, 0, -10, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay3_negativeDonate() {
        EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, -10);
    }

    @Test
    public void testDay3_rewardThresholds() {
        assertTrue(EvoCalculatorCore.hasDay3Reward(6000));
        assertTrue(EvoCalculatorCore.hasDay3Reward(74000));
        assertFalse(EvoCalculatorCore.hasDay3Reward(5999));
    }

    // ============================================================================
    // DAY 4 TESTS (Чертежи)
    // ============================================================================

    @Test
    public void testDay4_basicCalculation() {
        // 100 обычных * 30 + 10 продвинутых * 810 + 5 донатов * 3
        // = 3000 + 8100 + 15 = 11115
        int result = EvoCalculatorCore.calculateDay4(100, 10, 5);
        assertEquals(11115, result);
    }

    @Test
    public void testDay4_zeroValues() {
        int result = EvoCalculatorCore.calculateDay4(0, 0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay4_onlyCommonModules() {
        // 500 обычных * 30 = 15000
        int result = EvoCalculatorCore.calculateDay4(500, 0, 0);
        assertEquals(15000, result);
    }

    @Test
    public void testDay4_onlyAdvancedModules() {
        // 50 продвинутых * 810 = 40500
        int result = EvoCalculatorCore.calculateDay4(0, 50, 0);
        assertEquals(40500, result);
    }

    @Test
    public void testDay4_onlyDonate() {
        // 100 донатов * 3 = 300
        int result = EvoCalculatorCore.calculateDay4(0, 0, 100);
        assertEquals(300, result);
    }

    @Test
    public void testDay4_largeValues() {
        // 10000 * 30 + 1000 * 810 + 100 * 3 = 300000 + 810000 + 300 = 1110300
        int result = EvoCalculatorCore.calculateDay4(10000, 1000, 100);
        assertEquals(1110300, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay4_negativeCommonModules() {
        EvoCalculatorCore.calculateDay4(-100, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay4_negativeAdvancedModules() {
        EvoCalculatorCore.calculateDay4(0, -10, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay4_negativeDonate() {
        EvoCalculatorCore.calculateDay4(0, 0, -5);
    }

    @Test
    public void testDay4_rewardThresholds() {
        assertTrue(EvoCalculatorCore.hasDay4Reward(6000));
        assertTrue(EvoCalculatorCore.hasDay4Reward(88000));
        assertFalse(EvoCalculatorCore.hasDay4Reward(5999));
    }

    // ============================================================================
    // DAY 5 TESTS (Невролинк)
    // ============================================================================

    @Test
    public void testDay5_basicCalculation() {
        // 100 чипов * 5 + 50 кодировщиков * 10 + 5 имплантов * 2000 + 10 донатов * 3
        // = 500 + 500 + 10000 + 30 = 11030
        int result = EvoCalculatorCore.calculateDay5(100, 50, 5, 10);
        assertEquals(11030, result);
    }

    @Test
    public void testDay5_zeroValues() {
        int result = EvoCalculatorCore.calculateDay5(0, 0, 0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay5_onlySynapticChips() {
        // 1000 чипов * 5 = 5000
        int result = EvoCalculatorCore.calculateDay5(1000, 0, 0, 0);
        assertEquals(5000, result);
    }

    @Test
    public void testDay5_onlyNeuroCoder() {
        // 500 кодировщиков * 10 = 5000
        int result = EvoCalculatorCore.calculateDay5(0, 500, 0, 0);
        assertEquals(5000, result);
    }

    @Test
    public void testDay5_onlyCorticalImplant() {
        // 10 имплантов * 2000 = 20000
        int result = EvoCalculatorCore.calculateDay5(0, 0, 10, 0);
        assertEquals(20000, result);
    }

    @Test
    public void testDay5_onlyDonate() {
        // 100 донатов * 3 = 300
        int result = EvoCalculatorCore.calculateDay5(0, 0, 0, 100);
        assertEquals(300, result);
    }

    @Test
    public void testDay5_largeValues() {
        // 10000 * 5 + 5000 * 10 + 100 * 2000 + 1000 * 3
        // = 50000 + 50000 + 200000 + 3000 = 303000
        int result = EvoCalculatorCore.calculateDay5(10000, 5000, 100, 1000);
        assertEquals(303000, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay5_negativeSynapticChips() {
        EvoCalculatorCore.calculateDay5(-100, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay5_negativeNeuroCoder() {
        EvoCalculatorCore.calculateDay5(0, -50, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay5_negativeCorticalImplant() {
        EvoCalculatorCore.calculateDay5(0, 0, -5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay5_negativeDonate() {
        EvoCalculatorCore.calculateDay5(0, 0, 0, -10);
    }

    @Test
    public void testDay5_rewardThresholds() {
        assertTrue(EvoCalculatorCore.hasDay5Reward(5000));
        assertTrue(EvoCalculatorCore.hasDay5Reward(72000));
        assertFalse(EvoCalculatorCore.hasDay5Reward(4999));
    }

    // ============================================================================
    // DAY 6 TESTS (Оружие/Акс.)
    // ============================================================================

    @Test
    public void testDay6_basicCalculation() {
        // 100 билетов * 120 + 1273 зелёных * 10 + 62 синих * 30 + 62 фиолетовых * 250 + 2 жёлтых * 2500 + 5 донатов * 3
        // = 12000 + 12730 + 1860 + 15500 + 5000 + 15 = 47105
        int result = EvoCalculatorCore.calculateDay6(100, 1273, 62, 62, 2, 5);
        assertEquals(47105, result);
    }

    @Test
    public void testDay6_zeroValues() {
        int result = EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDay6_onlyWeaponTickets() {
        // 500 билетов * 120 = 60000
        int result = EvoCalculatorCore.calculateDay6(500, 0, 0, 0, 0, 0);
        assertEquals(60000, result);
    }

    @Test
    public void testDay6_onlyGreenBoxes() {
        // 1000 зелёных * 10 = 10000
        int result = EvoCalculatorCore.calculateDay6(0, 1000, 0, 0, 0, 0);
        assertEquals(10000, result);
    }

    @Test
    public void testDay6_onlyBlueBoxes() {
        // 500 синих * 30 = 15000
        int result = EvoCalculatorCore.calculateDay6(0, 0, 500, 0, 0, 0);
        assertEquals(15000, result);
    }

    @Test
    public void testDay6_onlyVioletBoxes() {
        // 100 фиолетовых * 250 = 25000
        int result = EvoCalculatorCore.calculateDay6(0, 0, 0, 100, 0, 0);
        assertEquals(25000, result);
    }

    @Test
    public void testDay6_onlyYellowBoxes() {
        // 20 жёлтых * 2500 = 50000
        int result = EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 20, 0);
        assertEquals(50000, result);
    }

    @Test
    public void testDay6_donateCalculation() {
        // 100 донатов * 3 = 300 очков (исправлено с 1 на 3!)
        int result = EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, 100);
        assertEquals(300, result);
    }

    @Test
    public void testDay6_largeValues() {
        // 1000 * 120 + 10000 * 10 + 5000 * 30 + 1000 * 250 + 100 * 2500 + 500 * 3
        // = 120000 + 100000 + 150000 + 250000 + 250000 + 1500 = 871500
        int result = EvoCalculatorCore.calculateDay6(1000, 10000, 5000, 1000, 100, 500);
        assertEquals(871500, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeWeaponTickets() {
        EvoCalculatorCore.calculateDay6(-100, 0, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeGreenBoxes() {
        EvoCalculatorCore.calculateDay6(0, -1000, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeBlueBoxes() {
        EvoCalculatorCore.calculateDay6(0, 0, -500, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeVioletBoxes() {
        EvoCalculatorCore.calculateDay6(0, 0, 0, -100, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeYellowBoxes() {
        EvoCalculatorCore.calculateDay6(0, 0, 0, 0, -20, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeDonate() {
        EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, -100);
    }

    @Test
    public void testDay6_rewardThresholds() {
        assertTrue(EvoCalculatorCore.hasDay6Reward(7500));
        assertTrue(EvoCalculatorCore.hasDay6Reward(115000));
        assertFalse(EvoCalculatorCore.hasDay6Reward(7499));
    }

    // ============================================================================
    // DAY 6 PROBABILITY TESTS
    // ============================================================================

    @Test
    public void testDay6_blueFromGreen_5percent() {
        // 100 зелёных * 5% = 5 синих
        int result = EvoCalculatorCore.getExpectedBlueFromGreen(100);
        assertEquals(5, result);
    }

    @Test
    public void testDay6_blueFromGreen_1273Boxes() {
        // 1273 зелёных * 5% = 63.65 ≈ 64 синих
        int result = EvoCalculatorCore.getExpectedBlueFromGreen(1273);
        assertEquals(64, result);
    }

    @Test
    public void testDay6_blueFromGreen_zero() {
        int result = EvoCalculatorCore.getExpectedBlueFromGreen(0);
        assertEquals(0, result);
    }

    @Test
    public void testDay6_violetFromBlue_4percent() {
        // 100 синих * 4% = 4 фиолетовых
        int result = EvoCalculatorCore.getExpectedVioletFromBlue(100);
        assertEquals(4, result);
    }

    @Test
    public void testDay6_violetFromBlue_62Boxes() {
        // 62 синих * 4% = 2.48 ≈ 2 фиолетовых
        int result = EvoCalculatorCore.getExpectedVioletFromBlue(62);
        assertEquals(2, result);
    }

    @Test
    public void testDay6_violetFromBlue_zero() {
        int result = EvoCalculatorCore.getExpectedVioletFromBlue(0);
        assertEquals(0, result);
    }

    @Test
    public void testDay6_minExpected() {
        // 50% от 100 = 50
        int result = EvoCalculatorCore.getMinExpected(100);
        assertEquals(50, result);
    }

    @Test
    public void testDay6_maxExpected() {
        // 150% от 100 = 150
        int result = EvoCalculatorCore.getMaxExpected(100);
        assertEquals(150, result);
    }

    @Test
    public void testDay6_potentialPointsFromBoxes() {
        // 100 зелёных → 5 синих * 30 = 150
        // 100 синих → 4 фиолетовых * 250 = 1000
        // Итого: 1150
        int result = EvoCalculatorCore.getPotentialPointsFromBoxes(100, 100);
        assertEquals(1150, result);
    }

    @Test
    public void testDay6_potentialPointsFromBoxes_1273_62() {
        // 1273 зелёных → 64 синих * 30 = 1920
        // 62 синих → 2 фиолетовых * 250 = 500
        // Итого: 2420
        int result = EvoCalculatorCore.getPotentialPointsFromBoxes(1273, 62);
        assertEquals(2420, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeGreenBoxesProbability() {
        EvoCalculatorCore.getExpectedBlueFromGreen(-100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeBlueBoxesProbability() {
        EvoCalculatorCore.getExpectedVioletFromBlue(-100);
    }

    // ============================================================================
    // DAY 7 TESTS (Пополнение)
    // ============================================================================

    @Test
    public void testDay7_basicCalculation() {
        // 50 донатов * 6 = 300
        int result = EvoCalculatorCore.calculateDay7(50);
        assertEquals(300, result);
    }

    @Test
    public void testDay7_zeroValues() {
        int result = EvoCalculatorCore.calculateDay7(0);
        assertEquals(0, result);
    }

    @Test
    public void testDay7_largeValues() {
        // 1000 донатов * 6 = 6000
        int result = EvoCalculatorCore.calculateDay7(1000);
        assertEquals(6000, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay7_negativeDonate() {
        EvoCalculatorCore.calculateDay7(-50);
    }

    // ============================================================================
    // INTEGRATION TESTS
    // ============================================================================

    @Test
    public void testAllDays_withMaxRewards() {
        // Проверяем, что все дни могут достичь максимальных порогов наград
        int day1Points = EvoCalculatorCore.calculateDay1(10000, 1000); // 303000
        assertTrue(day1Points >= EvoCalculatorCore.DAY1_MAX_POINTS);

        int day2Points = EvoCalculatorCore.calculateDay2(1000, 500); // 301500
        assertTrue(day2Points >= EvoCalculatorCore.DAY2_MAX_POINTS);

        int day3Points = EvoCalculatorCore.calculateDay3(100000, 100000, 1000, 100, 100, 100); // 102300
        assertTrue(day3Points >= EvoCalculatorCore.DAY3_MAX_POINTS);

        int day4Points = EvoCalculatorCore.calculateDay4(10000, 1000, 100); // 1110300
        assertTrue(day4Points >= EvoCalculatorCore.DAY4_MAX_POINTS);

        int day5Points = EvoCalculatorCore.calculateDay5(10000, 5000, 100, 1000); // 303000
        assertTrue(day5Points >= EvoCalculatorCore.DAY5_MAX_POINTS);

        int day6Points = EvoCalculatorCore.calculateDay6(1000, 10000, 5000, 1000, 100, 500); // 871500
        assertTrue(day6Points >= EvoCalculatorCore.DAY6_MAX_POINTS);

        int day7Points = EvoCalculatorCore.calculateDay7(1000); // 6000
        assertTrue(day7Points > 0);
    }

    @Test
    public void testAllDays_withZeroInput() {
        // Проверяем, что все дни возвращают 0 при нулевых входах
        assertEquals(0, EvoCalculatorCore.calculateDay1(0, 0));
        assertEquals(0, EvoCalculatorCore.calculateDay2(0, 0));
        assertEquals(0, EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, 0));
        assertEquals(0, EvoCalculatorCore.calculateDay4(0, 0, 0));
        assertEquals(0, EvoCalculatorCore.calculateDay5(0, 0, 0, 0));
        assertEquals(0, EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, 0));
        assertEquals(0, EvoCalculatorCore.calculateDay7(0));
    }

    @Test
    public void testConstants_consistency() {
        // Проверяем, что константы донатов согласованы
        assertEquals(3, EvoCalculatorCore.DAY1_DONATE_POINTS);
        assertEquals(3, EvoCalculatorCore.DAY2_DONATE_POINTS);
        assertEquals(3, EvoCalculatorCore.DAY3_DONATE_POINTS);
        assertEquals(3, EvoCalculatorCore.DAY4_DONATE_POINTS);
        assertEquals(3, EvoCalculatorCore.DAY5_DONATE_POINTS);
        assertEquals(3, EvoCalculatorCore.DAY6_DONATE_POINTS);
        assertEquals(6, EvoCalculatorCore.DAY7_DONATE_POINTS); // День 7 - особый
    }
}

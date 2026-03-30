package com.evo.points.calculator;

import com.evo.points.model.Reward;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit тесты для EvoCalculatorCore.
 * Проверяют расчёт очков и логику наград для всех 7 дней.
 */
public class EvoCalculatorCoreTest {

    // ===== Тесты для Дня 1 (Энергия) =====

    @Test
    public void testCalculateDay1() {
        // 100 энергии + 0 пополнений = 3000 очков
        assertEquals(3000, EvoCalculatorCore.calculateDay1(100, 0));
        
        // 500 энергии + 10 пополнений = 15030 очков
        assertEquals(15030, EvoCalculatorCore.calculateDay1(500, 10));
        
        // 2300 энергии + 0 пополнений = 69000 очков (максимум)
        assertEquals(69000, EvoCalculatorCore.calculateDay1(2300, 0));
    }

    @Test
    public void testGetDay1Rewards_3000points() {
        // Минимальный порог - только 1 награда
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(3000);
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0).getScreenshotPath().contains("3000_revard.png"));
        assertFalse(rewards.get(0).isTopReward());
    }

    @Test
    public void testGetDay1Rewards_9000points() {
        // 2 награды
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(9000);
        assertEquals(2, rewards.size());
        assertTrue(rewards.get(0).getScreenshotPath().contains("3000_revard.png"));
        assertTrue(rewards.get(1).getScreenshotPath().contains("9000_revard.png"));
    }

    @Test
    public void testGetDay1Rewards_20000points() {
        // 3 награды
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(20000);
        assertEquals(3, rewards.size());
    }

    @Test
    public void testGetDay1Rewards_36000points() {
        // 4 награды
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(36000);
        assertEquals(4, rewards.size());
    }

    @Test
    public void testGetDay1Rewards_40000points() {
        // 5 наград
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(40000);
        assertEquals(5, rewards.size());
    }

    @Test
    public void testGetDay1Rewards_69000points() {
        // Максимум - 5 наград + ТОП
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(69000);
        assertEquals(6, rewards.size());
        assertTrue(rewards.get(5).isTopReward());
        assertTrue(rewards.get(5).getScreenshotPath().contains("top_revard.png"));
    }

    @Test
    public void testGetDay1Rewards_noRewards() {
        // Меньше минимального порога - нет наград
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(2999);
        assertEquals(0, rewards.size());
    }

    // ===== Тесты для Дня 2 (Экипировка) =====

    @Test
    public void testCalculateDay2() {
        assertEquals(6000, EvoCalculatorCore.calculateDay2(20, 0));
        assertEquals(90150, EvoCalculatorCore.calculateDay2(300, 50));
        // 226 билетов * 300 + 74 пополнения * 3 = 67800 + 222 = 68022
        assertEquals(68022, EvoCalculatorCore.calculateDay2(226, 74));
    }

    @Test
    public void testGetDay2Rewards_6000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(6000);
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0).getScreenshotPath().contains("6000_revard.png"));
    }

    @Test
    public void testGetDay2Rewards_15000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(15000);
        assertEquals(2, rewards.size());
    }

    @Test
    public void testGetDay2Rewards_30000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(30000);
        assertEquals(3, rewards.size());
    }

    @Test
    public void testGetDay2Rewards_48000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(48000);
        assertEquals(4, rewards.size());
    }

    @Test
    public void testGetDay2Rewards_68000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(68000);
        assertEquals(6, rewards.size()); // 5 наград + ТОП
        assertTrue(rewards.get(5).isTopReward());
    }

    // ===== Тесты для Дня 3 (Лагерь) =====

    @Test
    public void testCalculateDay3() {
        // 6000 стали/200 + 6000 энергии/200 + 0 ускорений + 0 ядер + 0 пополнений = 30 + 30 = 60 очков
        assertEquals(60, EvoCalculatorCore.calculateDay3(6000, 6000, 0, 0, 0, 0));
        
        // Полный расчёт: 2M/200 + 2M/200 + 10000 + 100*500 + 100*500 = 10000 + 10000 + 10000 + 50000 + 50000 = 130000
        assertEquals(130000, EvoCalculatorCore.calculateDay3(
            2000000, 2000000, 10000, 100, 100, 0
        ));
    }

    @Test
    public void testGetDay3Rewards_6000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(6000);
        assertEquals(1, rewards.size());
    }

    @Test
    public void testGetDay3Rewards_17000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(17000);
        assertEquals(2, rewards.size());
    }

    @Test
    public void testGetDay3Rewards_30000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(30000);
        assertEquals(3, rewards.size());
    }

    @Test
    public void testGetDay3Rewards_45000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(45000);
        assertEquals(4, rewards.size());
    }

    @Test
    public void testGetDay3Rewards_74000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(74000);
        assertEquals(6, rewards.size()); // 5 наград + ТОП
        assertTrue(rewards.get(5).isTopReward());
    }

    // ===== Тесты для Дня 4 (Чертежи) =====

    @Test
    public void testCalculateDay4() {
        assertEquals(6000, EvoCalculatorCore.calculateDay4(200, 0, 0));
        // 108 продвинутых * 810 + 16 пополнений * 3 = 87480 + 48 = 87528
        assertEquals(87528, EvoCalculatorCore.calculateDay4(0, 108, 16));
    }

    @Test
    public void testGetDay4Rewards_6000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(6000);
        assertEquals(1, rewards.size());
    }

    @Test
    public void testGetDay4Rewards_15000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(15000);
        assertEquals(2, rewards.size());
    }

    @Test
    public void testGetDay4Rewards_30000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(30000);
        assertEquals(3, rewards.size());
    }

    @Test
    public void testGetDay4Rewards_56000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(56000);
        assertEquals(4, rewards.size());
    }

    @Test
    public void testGetDay4Rewards_88000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(88000);
        assertEquals(6, rewards.size()); // 5 наград + ТОП
        assertTrue(rewards.get(5).isTopReward());
    }

    // ===== Тесты для Дня 5 (Невролинк) =====

    @Test
    public void testCalculateDay5() {
        assertEquals(5000, EvoCalculatorCore.calculateDay5(1000, 0, 0, 0));
        assertEquals(72000, EvoCalculatorCore.calculateDay5(0, 0, 36, 0));
    }

    @Test
    public void testGetDay5Rewards_5000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(5000);
        assertEquals(1, rewards.size());
    }

    @Test
    public void testGetDay5Rewards_10000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(10000);
        assertEquals(2, rewards.size());
    }

    @Test
    public void testGetDay5Rewards_25000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(25000);
        assertEquals(3, rewards.size());
    }

    @Test
    public void testGetDay5Rewards_48000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(48000);
        assertEquals(4, rewards.size());
    }

    @Test
    public void testGetDay5Rewards_72000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(72000);
        assertEquals(6, rewards.size()); // 5 наград + ТОП
        assertTrue(rewards.get(5).isTopReward());
    }

    // ===== Тесты для Дня 6 (Оружие/Акс.) =====

    @Test
    public void testCalculateDay6() {
        assertEquals(7500, EvoCalculatorCore.calculateDay6(0, 750, 0, 0, 0, 0));
        assertEquals(115000, EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 46, 0));
    }

    @Test
    public void testGetDay6Rewards_7500points() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(7500);
        assertEquals(1, rewards.size());
    }

    @Test
    public void testGetDay6Rewards_20000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(20000);
        assertEquals(2, rewards.size());
    }

    @Test
    public void testGetDay6Rewards_45000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(45000);
        assertEquals(3, rewards.size());
    }

    @Test
    public void testGetDay6Rewards_75000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(75000);
        assertEquals(4, rewards.size());
    }

    @Test
    public void testGetDay6Rewards_115000points() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(115000);
        assertEquals(6, rewards.size()); // 5 наград + ТОП
        assertTrue(rewards.get(5).isTopReward());
    }

    @Test
    public void testGetDay6Probabilities() {
        // 5% шанс из зелёных в синие
        assertEquals(5, EvoCalculatorCore.getExpectedBlueFromGreen(100));
        assertEquals(10, EvoCalculatorCore.getExpectedBlueFromGreen(200));
        
        // 4% шанс из синих в фиолетовые
        assertEquals(4, EvoCalculatorCore.getExpectedVioletFromBlue(100));
        assertEquals(8, EvoCalculatorCore.getExpectedVioletFromBlue(200));
        
        // Потенциальные очки
        assertEquals(150, EvoCalculatorCore.getPotentialPointsFromBoxes(100, 0));
        assertEquals(1000, EvoCalculatorCore.getPotentialPointsFromBoxes(0, 100));
    }

    // ===== Тесты для Дня 7 (Пополнение) =====

    @Test
    public void testCalculateDay7() {
        assertEquals(600, EvoCalculatorCore.calculateDay7(100));
        assertEquals(6000, EvoCalculatorCore.calculateDay7(1000));
    }

    @Test
    public void testGetDay7Rewards() {
        // День 7 всегда показывает только топ награду
        List<Reward> rewards = EvoCalculatorCore.getDay7Rewards(0);
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0).isTopReward());
        assertTrue(rewards.get(0).getScreenshotPath().contains("top_revard.png"));
        
        rewards = EvoCalculatorCore.getDay7Rewards(1000000);
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0).isTopReward());
    }

    // ===== Тесты валидации =====

    @Test(expected = IllegalArgumentException.class)
    public void testDay1_negativeEnergy() {
        EvoCalculatorCore.calculateDay1(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay1_negativeDonate() {
        EvoCalculatorCore.calculateDay1(100, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay2_negativeTickets() {
        EvoCalculatorCore.calculateDay2(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDay6_negativeBoxes() {
        EvoCalculatorCore.calculateDay6(-1, 0, 0, 0, 0, 0);
    }

    // ===== Тесты порядка наград =====

    @Test
    public void testDay1_rewardsOrder() {
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(69000);
        
        // Проверка порядка возрастания
        assertTrue(rewards.get(0).getScreenshotPath().contains("3000"));
        assertTrue(rewards.get(1).getScreenshotPath().contains("9000"));
        assertTrue(rewards.get(2).getScreenshotPath().contains("20000"));
        assertTrue(rewards.get(3).getScreenshotPath().contains("36000"));
        assertTrue(rewards.get(4).getScreenshotPath().contains("40000"));
        assertTrue(rewards.get(5).getScreenshotPath().contains("top"));
    }

    @Test
    public void testDay2_rewardsOrder() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(68000);
        
        assertTrue(rewards.get(0).getScreenshotPath().contains("6000"));
        assertTrue(rewards.get(1).getScreenshotPath().contains("15000"));
        assertTrue(rewards.get(2).getScreenshotPath().contains("30000"));
        assertTrue(rewards.get(3).getScreenshotPath().contains("48000"));
        assertTrue(rewards.get(4).getScreenshotPath().contains("68000"));
    }

    @Test
    public void testDay6_rewardsOrder() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(115000);
        
        assertTrue(rewards.get(0).getScreenshotPath().contains("7500"));
        assertTrue(rewards.get(1).getScreenshotPath().contains("20000"));
        assertTrue(rewards.get(2).getScreenshotPath().contains("45000"));
        assertTrue(rewards.get(3).getScreenshotPath().contains("75000"));
        assertTrue(rewards.get(4).getScreenshotPath().contains("115000"));
    }

    // ===== Тесты граничных значений =====

    @Test
    public void testDay1_boundaryValues() {
        // Ровно на пороге
        assertEquals(1, EvoCalculatorCore.getDay1Rewards(3000).size());
        assertEquals(2, EvoCalculatorCore.getDay1Rewards(9000).size());
        assertEquals(3, EvoCalculatorCore.getDay1Rewards(20000).size());
        assertEquals(4, EvoCalculatorCore.getDay1Rewards(36000).size());
        assertEquals(5, EvoCalculatorCore.getDay1Rewards(40000).size());
        assertEquals(6, EvoCalculatorCore.getDay1Rewards(69000).size());
        
        // На 1 очко меньше порога
        assertEquals(0, EvoCalculatorCore.getDay1Rewards(2999).size());
        assertEquals(1, EvoCalculatorCore.getDay1Rewards(8999).size());
        assertEquals(2, EvoCalculatorCore.getDay1Rewards(19999).size());
        assertEquals(3, EvoCalculatorCore.getDay1Rewards(35999).size());
        assertEquals(4, EvoCalculatorCore.getDay1Rewards(39999).size());
        assertEquals(5, EvoCalculatorCore.getDay1Rewards(68999).size());
    }

    @Test
    public void testDay2_boundaryValues() {
        assertEquals(1, EvoCalculatorCore.getDay2Rewards(6000).size());
        assertEquals(2, EvoCalculatorCore.getDay2Rewards(15000).size());
        assertEquals(3, EvoCalculatorCore.getDay2Rewards(30000).size());
        assertEquals(4, EvoCalculatorCore.getDay2Rewards(48000).size());
        assertEquals(6, EvoCalculatorCore.getDay2Rewards(68000).size()); // 5 + ТОП

        assertEquals(0, EvoCalculatorCore.getDay2Rewards(5999).size());
        assertEquals(1, EvoCalculatorCore.getDay2Rewards(14999).size());
        assertEquals(2, EvoCalculatorCore.getDay2Rewards(29999).size());
        assertEquals(3, EvoCalculatorCore.getDay2Rewards(47999).size());
        assertEquals(5, EvoCalculatorCore.getDay2Rewards(67999).size()); // без ТОПа
    }

    @Test
    public void testDay5_boundaryValues() {
        assertEquals(1, EvoCalculatorCore.getDay5Rewards(5000).size());
        assertEquals(2, EvoCalculatorCore.getDay5Rewards(10000).size());
        assertEquals(3, EvoCalculatorCore.getDay5Rewards(25000).size());
        assertEquals(4, EvoCalculatorCore.getDay5Rewards(48000).size());
        assertEquals(6, EvoCalculatorCore.getDay5Rewards(72000).size()); // 5 + ТОП
        
        assertEquals(0, EvoCalculatorCore.getDay5Rewards(4999).size());
        assertEquals(1, EvoCalculatorCore.getDay5Rewards(9999).size());
        assertEquals(2, EvoCalculatorCore.getDay5Rewards(24999).size());
        assertEquals(3, EvoCalculatorCore.getDay5Rewards(47999).size());
        assertEquals(4, EvoCalculatorCore.getDay5Rewards(71999).size()); // 4 без ТОПа
    }
}

package com.evo.points.calculator;

import com.evo.points.calculator.days.Day3Camp;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    // ===== Позитивные тесты для calculatePoints =====

    @Test
    @DisplayName("Расчёт очков с нулевыми значениями")
    void testCalculatePointsWithZeros() {
        int points = Day3Camp.calculatePoints(0, 0, 0, 0, 0, 0);
        assertEquals(0, points);
    }

    @ParameterizedTest
    @CsvSource({
            "200, 0, 1",
            "400, 0, 2",
            "600, 0, 3",
            "0, 200, 1",
            "0, 400, 2",
            "200, 200, 2"
    })
    @DisplayName("Расчёт очков со сталью и энергией")
    void testCalculatePointsWithSteelAndEnergy(int steel, int energy, int expected) {
        int points = Day3Camp.calculatePoints(steel, energy, 0, 0, 0, 0);
        assertEquals(expected, points);
    }

    @Test
    @DisplayName("Комплексный расчёт очков со всеми параметрами")
    void testCalculatePointsComplex() {
        // Сталь: 500/200=2
        // Энергия: 300/200=1
        // Ускорения: 3 = 3
        // Боевые ядра: 2 = 1000
        // Ядра развития: 1 = 500
        // Пополнения: 4 = 12
        // Итого: 2 + 1 + 3 + 1000 + 500 + 12 = 1518
        int points = Day3Camp.calculatePoints(500, 300, 3, 2, 1, 4);
        assertEquals(1518, points);
    }

    // ===== Негативные тесты для calculatePoints =====

    @Test
    @DisplayName("Выброс исключения при отрицательной стали")
    void testCalculatePointsWithNegativeSteel() {
        assertThrows(IllegalArgumentException.class,
                () -> Day3Camp.calculatePoints(-1, 0, 0, 0, 0, 0));
    }

    @Test
    @DisplayName("Выброс исключения при отрицательной энергии")
    void testCalculatePointsWithNegativeEnergy() {
        assertThrows(IllegalArgumentException.class,
                () -> Day3Camp.calculatePoints(0, -1, 0, 0, 0, 0));
    }

    @Test
    @DisplayName("Выброс исключения при отрицательных ускорениях")
    void testCalculatePointsWithNegativeBoost() {
        assertThrows(IllegalArgumentException.class,
                () -> Day3Camp.calculatePoints(0, 0, -1, 0, 0, 0));
    }

    // ===== Тесты для hasReward =====

    @ParameterizedTest
    @CsvSource({
            "0, false",
            "5999, false",
            "6000, true",
            "74000, true"
    })
    @DisplayName("Проверка наличия награды (минимальный порог 6000)")
    void testHasReward(int points, boolean expected) {
        assertEquals(expected, Day3Camp.hasReward(points));
    }

    // ===== Тесты для getRewards (8 обычных наград + TOP = всегда 9?) =====
    // ВНИМАНИЕ: TOP показывается всегда (alwaysShowTop = true)

    @Test
    @DisplayName("0 очков - только TOP награда (так как alwaysShowTop = true)")
    void testGetRewardsWithZeroPoints() {
        List<Reward> rewards = Day3Camp.getRewards(0);
        // TOP показывается всегда, даже при 0 очков
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0).isTopReward());
    }

    @Test
    @DisplayName("299 очков - только TOP награда")
    void testGetRewardsWith299Points() {
        List<Reward> rewards = Day3Camp.getRewards(299);
        assertEquals(1, rewards.size());
        assertTrue(rewards.get(0).isTopReward());
    }

    @Test
    @DisplayName("300 очков - награда 300 + TOP")
    void testGetRewardsWith300Points() {
        List<Reward> rewards = Day3Camp.getRewards(300);
        assertEquals(2, rewards.size());

        // Первая награда - обычная (300)
        assertEquals(Reward.RewardType.NORMAL, rewards.get(0).getType());
        assertEquals("img/day_3/300_reward.png", rewards.get(0).getScreenshotPath());

        // Вторая награда - TOP
        assertTrue(rewards.get(1).isTopReward());
        assertEquals("img/day_3/top_revard.png", rewards.get(1).getScreenshotPath());
    }

    @Test
    @DisplayName("1200 очков - награды 300, 1200 + TOP")
    void testGetRewardsWith1200Points() {
        List<Reward> rewards = Day3Camp.getRewards(1200);
        assertEquals(3, rewards.size());

        assertEquals("img/day_3/300_reward.png", rewards.get(0).getScreenshotPath());
        assertEquals("img/day_3/1200_reward.png", rewards.get(1).getScreenshotPath());
        assertTrue(rewards.get(2).isTopReward());
    }

    @Test
    @DisplayName("2400 очков - награды 300, 1200, 2400 + TOP")
    void testGetRewardsWith2400Points() {
        List<Reward> rewards = Day3Camp.getRewards(2400);
        assertEquals(4, rewards.size());

        assertEquals("img/day_3/300_reward.png", rewards.get(0).getScreenshotPath());
        assertEquals("img/day_3/1200_reward.png", rewards.get(1).getScreenshotPath());
        assertEquals("img/day_3/2400_reward.png", rewards.get(2).getScreenshotPath());
        assertTrue(rewards.get(3).isTopReward());
    }

    @Test
    @DisplayName("6000 очков - награды 300, 1200, 2400, 6000 + TOP")
    void testGetRewardsWith6000Points() {
        List<Reward> rewards = Day3Camp.getRewards(6000);
        assertEquals(5, rewards.size());
        assertEquals("img/day_3/6000_reward.png", rewards.get(3).getScreenshotPath());
        assertTrue(rewards.get(4).isTopReward());
    }

    @Test
    @DisplayName("17000 очков - награды до 17000 + TOP")
    void testGetRewardsWith17000Points() {
        List<Reward> rewards = Day3Camp.getRewards(17000);
        assertEquals(6, rewards.size()); // 300,1200,2400,6000,17000 + TOP
        assertEquals("img/day_3/17000_revard.png", rewards.get(4).getScreenshotPath());
        assertTrue(rewards.get(5).isTopReward());
    }

    @Test
    @DisplayName("30000 очков - награды до 30000 + TOP")
    void testGetRewardsWith30000Points() {
        List<Reward> rewards = Day3Camp.getRewards(30000);
        assertEquals(7, rewards.size()); // 300,1200,2400,6000,17000,30000 + TOP
        assertEquals("img/day_3/30000_reward.png", rewards.get(5).getScreenshotPath());
        assertTrue(rewards.get(6).isTopReward());
    }

    @Test
    @DisplayName("45000 очков - награды до 45000 + TOP")
    void testGetRewardsWith45000Points() {
        List<Reward> rewards = Day3Camp.getRewards(45000);
        assertEquals(8, rewards.size()); // 7 обычных до 45000 + TOP
        assertEquals("img/day_3/45000_reward.png", rewards.get(6).getScreenshotPath());
        assertTrue(rewards.get(7).isTopReward());
    }

    @Test
    @DisplayName("74000 очков - все 8 обычных наград + TOP")
    void testGetRewardsWith74000Points() {
        List<Reward> rewards = Day3Camp.getRewards(74000);

        // 8 обычных наград (300,1200,2400,6000,17000,30000,45000,74000) + TOP = 9
        assertEquals(9, rewards.size());

        // Проверяем последнюю обычную награду
        assertEquals("img/day_3/74000_reward.png", rewards.get(7).getScreenshotPath());

        // Проверяем TOP награду (последняя)
        Reward topReward = rewards.get(8);
        assertEquals("img/day_3/top_revard.png", topReward.getScreenshotPath());
        assertEquals(Reward.RewardType.TOP, topReward.getType());
        assertTrue(topReward.isTopReward());

        // Проверяем, что все остальные награды - NORMAL
        for (int i = 0; i < 8; i++) {
            assertEquals(Reward.RewardType.NORMAL, rewards.get(i).getType());
            assertFalse(rewards.get(i).isTopReward());
        }
    }

    @Test
    @DisplayName("100000 очков - все 8 обычных наград + TOP")
    void testGetRewardsWith100000Points() {
        List<Reward> rewards = Day3Camp.getRewards(100000);
        assertEquals(9, rewards.size());

        // Проверяем наличие TOP награды
        boolean hasTop = rewards.stream().anyMatch(Reward::isTopReward);
        assertTrue(hasTop, "TOP награда должна присутствовать");
    }

    @Test
    @DisplayName("Между порогами 1200 и 2400 - награды 300, 1200 + TOP")
    void testGetRewardsBetween1200And2400() {
        List<Reward> rewards = Day3Camp.getRewards(2000);
        assertEquals(3, rewards.size()); // 300, 1200 + TOP
        assertEquals("img/day_3/300_reward.png", rewards.get(0).getScreenshotPath());
        assertEquals("img/day_3/1200_reward.png", rewards.get(1).getScreenshotPath());
        assertTrue(rewards.get(2).isTopReward());
    }

    @Test
    @DisplayName("Между порогами 2400 и 6000 - награды 300, 1200, 2400 + TOP")
    void testGetRewardsBetween2400And6000() {
        List<Reward> rewards = Day3Camp.getRewards(5000);
        assertEquals(4, rewards.size()); // 300, 1200, 2400 + TOP
    }

    @Test
    @DisplayName("TOP награда показывается всегда (даже при 0 очков)")
    void testTopRewardAlwaysShown() {
        List<Reward> rewardsAtZero = Day3Camp.getRewards(0);
        assertTrue(rewardsAtZero.stream().anyMatch(Reward::isTopReward));

        List<Reward> rewardsAtLow = Day3Camp.getRewards(100);
        assertTrue(rewardsAtLow.stream().anyMatch(Reward::isTopReward));

        List<Reward> rewardsAtHigh = Day3Camp.getRewards(100000);
        assertTrue(rewardsAtHigh.stream().anyMatch(Reward::isTopReward));
    }

    @Test
    @DisplayName("Проверка корректности всех путей к изображениям")
    void testAllScreenshotPathsAreCorrect() {
        List<Reward> rewards = Day3Camp.getRewards(100000);

        for (Reward reward : rewards) {
            String path = reward.getScreenshotPath();
            assertNotNull(path);
            assertTrue(path.startsWith("img/day_3/"),
                    "Путь должен начинаться с 'img/day_3/', получен: " + path);
            assertTrue(path.endsWith(".png"),
                    "Файл должен иметь расширение .png, получен: " + path);
        }
    }

    @Test
    @DisplayName("Точные значения порогов - награды включают текущий порог")
    void testRewardsIncludeCurrentThreshold() {
        // Проверяем, что награда за порог включается при точном равенстве
        assertEquals(2, Day3Camp.getRewards(300).size());   // 300 + TOP
        assertEquals(3, Day3Camp.getRewards(1200).size());  // 300,1200 + TOP
        assertEquals(4, Day3Camp.getRewards(2400).size());  // 300,1200,2400 + TOP
        assertEquals(5, Day3Camp.getRewards(6000).size());  // +6000
        assertEquals(6, Day3Camp.getRewards(17000).size()); // +17000
        assertEquals(7, Day3Camp.getRewards(30000).size()); // +30000
        assertEquals(8, Day3Camp.getRewards(45000).size()); // +45000
        assertEquals(9, Day3Camp.getRewards(74000).size()); // +74000 + TOP
    }

    @Test
    @DisplayName("Проверка, что все награды имеют screenshotPath")
    void testAllRewardsHaveScreenshot() {
        List<Reward> rewards = Day3Camp.getRewards(100000);
        for (Reward reward : rewards) {
            assertTrue(reward.hasScreenshot(), "Каждая награда должна иметь screenshot");
        }
    }
}
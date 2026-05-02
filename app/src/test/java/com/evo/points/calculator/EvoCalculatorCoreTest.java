package com.evo.points.calculator;

import com.evo.points.calculator.days.Day1AlbumCards;
import com.evo.points.calculator.days.Day2Equipments;
import com.evo.points.calculator.days.Day3Camp;
import com.evo.points.calculator.days.Day4Blueprint;
import com.evo.points.calculator.days.Day5Neurolink;
import com.evo.points.calculator.days.Day6Weapon;
import com.evo.points.calculator.days.Day7Topup;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для фасада {@link EvoCalculatorCore}.
 * <p>
 * Проверяют корректность делегирования вызовов к специализированным классам
 * для каждого дня, а также проброс валидации и обработку граничных случаев.
 * </p>
 */
public class EvoCalculatorCoreTest {

    // ==================== ДЕНЬ 1: Альбом/Карты ====================

    @Test
    void calculateDay1_delegatesToDay1AlbumCards() {
        assertAll("Делегирование расчёта Дня 1",
                () -> assertEquals(
                        Day1AlbumCards.calculatePoints(0),
                        EvoCalculatorCore.calculateDay1(0)),
                () -> assertEquals(
                        Day1AlbumCards.calculatePoints(10),
                        EvoCalculatorCore.calculateDay1(10)),
                () -> assertEquals(
                        Day1AlbumCards.calculatePoints(1360),
                        EvoCalculatorCore.calculateDay1(1360))
        );
    }

    @Test
    void calculateDay1_propagatesValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> EvoCalculatorCore.calculateDay1(-1),
                "Отрицательное значение должно выбрасывать исключение");
    }

    @Test
    void getDay1Rewards_delegatesCorrectly() {
        List<Reward> coreRewards = EvoCalculatorCore.getDay1Rewards(15000);
        List<Reward> directRewards = Day1AlbumCards.getRewards(15000);

        assertEquals(directRewards.size(), coreRewards.size(), "Количество наград должно совпадать");
        for (int i = 0; i < directRewards.size(); i++) {
            assertEquals(
                    directRewards.get(i).getScreenshotPath(),
                    coreRewards.get(i).getScreenshotPath(),
                    "Путь к скриншоту награды #" + i + " должен совпадать");
        }
    }

    @Test
    void hasDay1Reward_delegatesCorrectly() {
        assertAll("Проверка hasDay1Reward",
                () -> assertEquals(
                        Day1AlbumCards.hasReward(299),
                        EvoCalculatorCore.hasDay1Reward(299)),
                () -> assertEquals(
                        Day1AlbumCards.hasReward(300),
                        EvoCalculatorCore.hasDay1Reward(300))
        );
    }

    // ==================== ДЕНЬ 2: Экипировка ====================

    @Test
    void calculateDay2_delegatesToDay2Equipments() {
        assertAll("Делегирование расчёта Дня 2",
                () -> assertEquals(
                        Day2Equipments.calculatePoints(0, 0),
                        EvoCalculatorCore.calculateDay2(0, 0)),
                () -> assertEquals(
                        Day2Equipments.calculatePoints(1, 1),
                        EvoCalculatorCore.calculateDay2(1, 1)),
                () -> assertEquals(
                        Day2Equipments.calculatePoints(100, 50),
                        EvoCalculatorCore.calculateDay2(100, 50))
        );
    }

    @Test
    void calculateDay2_propagatesValidation() {
        assertAll("Валидация входных параметров Дня 2",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay2(-1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay2(0, -1))
        );
    }

    @Test
    void getDay2Rewards_includesTopRewardAlways() {
        // Топ-награда должна быть даже при 0 очков (alwaysShowTop = true)
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(0);
        assertFalse(rewards.isEmpty(), "Список наград не должен быть пустым");
        assertTrue(rewards.stream().anyMatch(Reward::isTopReward),
                "Топ-награда должна присутствовать всегда");
    }

    @Test
    void getDay2Rewards_atThresholdBoundaries() {
        assertAll("Граничные значения порогов Дня 2",
                () -> assertEquals(
                        Day2Equipments.getRewards(299).size(),
                        EvoCalculatorCore.getDay2Rewards(299).size()),
                () -> assertEquals(
                        Day2Equipments.getRewards(300).size(),
                        EvoCalculatorCore.getDay2Rewards(300).size()),
                () -> assertEquals(
                        Day2Equipments.getRewards(68000).size(),
                        EvoCalculatorCore.getDay2Rewards(68000).size())
        );
    }

    // ==================== ДЕНЬ 3: Лагерь ====================

    @Test
    void calculateDay3_delegatesToDay3Camp() {
        assertAll("Делегирование расчёта Дня 3",
                () -> assertEquals(
                        Day3Camp.calculatePoints(0, 0, 0, 0, 0, 0),
                        EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, 0)),
                () -> assertEquals(
                        Day3Camp.calculatePoints(200, 200, 1, 1, 1, 1),
                        EvoCalculatorCore.calculateDay3(200, 200, 1, 1, 1, 1)),
                () -> assertEquals(
                        Day3Camp.calculatePoints(10000, 5000, 10, 5, 5, 10),
                        EvoCalculatorCore.calculateDay3(10000, 5000, 10, 5, 5, 10))
        );
    }

    @Test
    void calculateDay3_propagatesValidation() {
        assertAll("Валидация входных параметров Дня 3",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay3(-1, 0, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, -1))
        );
    }

    @Test
    void getDay3Rewards_consistentWithDirectCall() {
        int testPoints = 30000;
        List<Reward> coreRewards = EvoCalculatorCore.getDay3Rewards(testPoints);
        List<Reward> directRewards = Day3Camp.getRewards(testPoints);

        assertEquals(directRewards.size(), coreRewards.size());
        assertTrue(coreRewards.stream().anyMatch(Reward::isTopReward),
                "Топ-награда должна присутствовать");
    }

    // ==================== ДЕНЬ 4: Чертежи ====================

    @Test
    void calculateDay4_delegatesToDay4Blueprint() {
        assertAll("Делегирование расчёта Дня 4",
                () -> assertEquals(
                        Day4Blueprint.calculatePoints(0, 0, 0),
                        EvoCalculatorCore.calculateDay4(0, 0, 0)),
                () -> assertEquals(
                        Day4Blueprint.calculatePoints(10, 1, 0),
                        EvoCalculatorCore.calculateDay4(10, 1, 0)),
                () -> assertEquals(
                        Day4Blueprint.calculatePoints(100, 50, 25),
                        EvoCalculatorCore.calculateDay4(100, 50, 25))
        );
    }

    @Test
    void calculateDay4_propagatesValidation() {
        assertAll("Валидация входных параметров Дня 4",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay4(-1, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay4(0, 0, -1))
        );
    }

    @Test
    void getDay4Rewards_thresholdBoundary_88000() {
        List<Reward> atThreshold = EvoCalculatorCore.getDay4Rewards(88000);
        List<Reward> belowThreshold = EvoCalculatorCore.getDay4Rewards(87999);

        assertEquals(9, atThreshold.size(), "При 88000 должны быть все 8 обычных + топ");
        assertEquals(8, belowThreshold.size(), "При 87999 топ-награда всё равно есть, но последняя обычная — нет");
        assertTrue(atThreshold.stream().anyMatch(Reward::isTopReward));
        assertTrue(belowThreshold.stream().anyMatch(Reward::isTopReward));
    }

    // ==================== ДЕНЬ 5: Невролинк ====================

    @Test
    void calculateDay5_delegatesToDay5Neurolink() {
        assertAll("Делегирование расчёта Дня 5",
                () -> assertEquals(
                        Day5Neurolink.calculatePoints(0, 0, 0, 0),
                        EvoCalculatorCore.calculateDay5(0, 0, 0, 0)),
                () -> assertEquals(
                        Day5Neurolink.calculatePoints(10, 10, 1, 1),
                        EvoCalculatorCore.calculateDay5(10, 10, 1, 1)),
                () -> assertEquals(
                        Day5Neurolink.calculatePoints(100, 50, 10, 5),
                        EvoCalculatorCore.calculateDay5(100, 50, 10, 5))
        );
    }

    @Test
    void calculateDay5_propagatesValidation() {
        assertAll("Валидация входных параметров Дня 5",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay5(-1, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay5(0, 0, 0, -1))
        );
    }

    @Test
    void getDay5Rewards_largeValue_allTiersUnlocked() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(Integer.MAX_VALUE);
        assertEquals(9, rewards.size(), "8 обычных + 1 топ-награда");
        assertTrue(rewards.get(rewards.size() - 1).isTopReward());
    }

    // ==================== ДЕНЬ 6: Оружие/Акс. ====================

    @Test
    void calculateDay6_delegatesToDay6Weapon() {
        assertAll("Делегирование расчёта Дня 6",
                () -> assertEquals(
                        Day6Weapon.calculatePoints(0, 0, 0, 0, 0, 0),
                        EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, 0)),
                () -> assertEquals(
                        Day6Weapon.calculatePoints(10, 10, 10, 1, 1, 1),
                        EvoCalculatorCore.calculateDay6(10, 10, 10, 1, 1, 1)),
                () -> assertEquals(
                        Day6Weapon.calculatePoints(500, 100, 50, 20, 10, 100),
                        EvoCalculatorCore.calculateDay6(500, 100, 50, 20, 10, 100))
        );
    }

    @Test
    void calculateDay6_propagatesValidation() {
        assertAll("Валидация входных параметров Дня 6",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay6(-1, 0, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay6(0, 0, 0, 0, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, -1))
        );
    }

    @Test
    void getDay6Rewards_probabilitiesMethods_delegated() {
        assertAll("Делегирование методов вероятностей Дня 6",
                () -> assertEquals(
                        Day6Weapon.getExpectedBlueFromGreen(100),
                        EvoCalculatorCore.getExpectedBlueFromGreen(100)),
                () -> assertEquals(
                        Day6Weapon.getExpectedVioletFromBlue(50),
                        EvoCalculatorCore.getExpectedVioletFromBlue(50)),
                () -> assertEquals(
                        Day6Weapon.getMinExpected(100),
                        EvoCalculatorCore.getMinExpected(100)),
                () -> assertEquals(
                        Day6Weapon.getMaxExpected(100),
                        EvoCalculatorCore.getMaxExpected(100)),
                () -> assertEquals(
                        Day6Weapon.getPotentialPointsFromBoxes(100, 50),
                        EvoCalculatorCore.getPotentialPointsFromBoxes(100, 50))
        );
    }

    @Test
    void getDay6Rewards_allTiersUnlocked_atMaxThreshold() {
        // 115000 — порог последней обычной награды + топ
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(115000);
        assertEquals(10, rewards.size(), "9 обычных + 1 топ-награда");

        // Проверка, что последняя награда — топ
        Reward lastReward = rewards.get(rewards.size() - 1);
        assertTrue(lastReward.isTopReward());
        assertTrue(lastReward.getScreenshotPath().contains("top_reward.png"));
    }

    @Test
    void getDay6Rewards_belowFirstTier_onlyTopShown() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(0);
        assertEquals(1, rewards.size(), "Только топ-награда при 0 очков");
        assertTrue(rewards.get(0).isTopReward());
    }

    // ==================== ДЕНЬ 7: Пополнение ====================

    @Test
    void calculateDay7_delegatesToDay7Topup() {
        assertAll("Делегирование расчёта Дня 7",
                () -> assertEquals(
                        Day7Topup.calculatePoints(0),
                        EvoCalculatorCore.calculateDay7(0)),
                () -> assertEquals(
                        Day7Topup.calculatePoints(100),
                        EvoCalculatorCore.calculateDay7(100)),
                () -> assertEquals(
                        Day7Topup.calculatePoints(10000),
                        EvoCalculatorCore.calculateDay7(10000))
        );
    }

    @Test
    void calculateDay7_propagatesValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> EvoCalculatorCore.calculateDay7(-1),
                "Отрицательное значение доната должно выбрасывать исключение");
    }

    @Test
    void getDay7Rewards_firstTier_600points() {
        List<Reward> rewards = EvoCalculatorCore.getDay7Rewards(600);
        assertAll("Награды при первом пороге Дня 7",
                () -> assertEquals(2, rewards.size(), "1 обычная + 1 топ"),
                () -> assertFalse(rewards.get(0).isTopReward()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("600_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay7Rewards_maxThreshold_148800() {
        List<Reward> rewards = EvoCalculatorCore.getDay7Rewards(148800);
        assertEquals(9, rewards.size(), "8 обычных + 1 топ-награда");
        assertTrue(rewards.stream().anyMatch(Reward::isTopReward));
    }

    // ==================== Интеграционные тесты ====================


    @Test
    void allDays_topRewardAlwaysPresent() {
        // Топ-награда должна присутствовать во всех днях даже при 0 очков
        assertAll("Топ-награда всегда отображается",
                () -> assertTrue(
                        EvoCalculatorCore.getDay1Rewards(0).stream().anyMatch(Reward::isTopReward)),
                () -> assertTrue(
                        EvoCalculatorCore.getDay2Rewards(0).stream().anyMatch(Reward::isTopReward)),
                () -> assertTrue(
                        EvoCalculatorCore.getDay3Rewards(0).stream().anyMatch(Reward::isTopReward)),
                () -> assertTrue(
                        EvoCalculatorCore.getDay4Rewards(0).stream().anyMatch(Reward::isTopReward)),
                () -> assertTrue(
                        EvoCalculatorCore.getDay5Rewards(0).stream().anyMatch(Reward::isTopReward)),
                () -> assertTrue(
                        EvoCalculatorCore.getDay6Rewards(0).stream().anyMatch(Reward::isTopReward)),
                () -> assertTrue(
                        EvoCalculatorCore.getDay7Rewards(0).stream().anyMatch(Reward::isTopReward))
        );
    }

    @Test
    void calculateMethods_returnNonNegativeValues() {
        // Все методы расчёта должны возвращать неотрицательные значения при валидном вводе
        assertAll("Расчёт очков возвращает неотрицательные значения",
                () -> assertTrue(EvoCalculatorCore.calculateDay1(0) >= 0),
                () -> assertTrue(EvoCalculatorCore.calculateDay2(0, 0) >= 0),
                () -> assertTrue(EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, 0) >= 0),
                () -> assertTrue(EvoCalculatorCore.calculateDay4(0, 0, 0) >= 0),
                () -> assertTrue(EvoCalculatorCore.calculateDay5(0, 0, 0, 0) >= 0),
                () -> assertTrue(EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, 0) >= 0),
                () -> assertTrue(EvoCalculatorCore.calculateDay7(0) >= 0)
        );
    }

    @Test
    void getRewards_methods_returnImmutableSafeLists() {
        // Проверка, что возвращаемые списки можно безопасно использовать
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(300);
        assertNotNull(rewards, "Список наград не должен быть null");
        assertFalse(rewards.contains(null), "Список не должен содержать null-элементов");

        // Проверка, что элементы списка имеют валидные пути
        for (Reward reward : rewards) {
            assertNotNull(reward.getScreenshotPath(), "Путь к скриншоту не должен быть null");
            assertFalse(reward.getScreenshotPath().isEmpty(), "Путь к скриншоту не должен быть пустым");
        }
    }

    // ==================== Тесты на очень большие значения ====================

    @Test
    void allDays_handleLargePointValues() {
        int hugePoints = Integer.MAX_VALUE / 2; // Достаточно большое, но без риска переполнения

        assertAll("Обработка очень больших значений очков",
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay1Rewards(hugePoints)),
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay2Rewards(hugePoints)),
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay3Rewards(hugePoints)),
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay4Rewards(hugePoints)),
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay5Rewards(hugePoints)),
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay6Rewards(hugePoints)),
                () -> assertDoesNotThrow(() -> EvoCalculatorCore.getDay7Rewards(hugePoints))
        );
    }

    @Test
    void allDays_largeValues_unlockAllTiers() {
        int pointsAboveAllThresholds = 200000; // Выше любого порога в системе

        assertAll("Все пороги открыты при большом значении",
                () -> assertEquals(9, EvoCalculatorCore.getDay1Rewards(pointsAboveAllThresholds).size()),
                () -> assertEquals(9, EvoCalculatorCore.getDay2Rewards(pointsAboveAllThresholds).size()),
                () -> assertEquals(9, EvoCalculatorCore.getDay3Rewards(pointsAboveAllThresholds).size()),
                () -> assertEquals(9, EvoCalculatorCore.getDay4Rewards(pointsAboveAllThresholds).size()),
                () -> assertEquals(9, EvoCalculatorCore.getDay5Rewards(pointsAboveAllThresholds).size()),
                () -> assertEquals(10, EvoCalculatorCore.getDay6Rewards(pointsAboveAllThresholds).size()), // 9 обычных + топ
                () -> assertEquals(9, EvoCalculatorCore.getDay7Rewards(pointsAboveAllThresholds).size())
        );
    }
}
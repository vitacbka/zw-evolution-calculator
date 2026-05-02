package com.evo.points.calculator;

import com.evo.points.calculator.days.Day6Weapon;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day6WeaponTest {

    @Test
    void calculatePoints_validInputs() {
        assertAll("Расчёт очков Дня 6 (Оружие)",
                () -> assertEquals(0, Day6Weapon.calculatePoints(0, 0, 0, 0, 0, 0)),
                () -> assertEquals(Day6Weapon.WEAPON_TICKET_POINTS, Day6Weapon.calculatePoints(1, 0, 0, 0, 0, 0)),
                () -> assertEquals(Day6Weapon.GREEN_BOX_POINTS, Day6Weapon.calculatePoints(0, 1, 0, 0, 0, 0)),
                () -> assertEquals(Day6Weapon.BLUE_BOX_POINTS, Day6Weapon.calculatePoints(0, 0, 1, 0, 0, 0)),
                () -> assertEquals(Day6Weapon.VIOLET_BOX_POINTS, Day6Weapon.calculatePoints(0, 0, 0, 1, 0, 0)),
                () -> assertEquals(Day6Weapon.YELLOW_BOX_POINTS, Day6Weapon.calculatePoints(0, 0, 0, 0, 1, 0)),
                () -> assertEquals(Day6Weapon.DONATE_POINTS, Day6Weapon.calculatePoints(0, 0, 0, 0, 0, 1)),
                // 10*120 + 10*10 + 10*30 + 1*250 + 1*2500 + 1*3 = 1200+100+300+250+2500+3 = 4353
                () -> assertEquals(4353, Day6Weapon.calculatePoints(10, 10, 10, 1, 1, 1))
        );
    }

    @Test
    void calculatePoints_negativeInput() {
        assertAll("Отрицательные входные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day6Weapon.calculatePoints(-1, 0, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day6Weapon.calculatePoints(0, 0, 0, 0, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day6Weapon.calculatePoints(0, 0, 0, 0, 0, -1))
        );
    }

    @Test
    void hasReward_boundary() {
        assertFalse(Day6Weapon.hasReward(Day6Weapon.MIN_POINTS - 1));
        assertTrue(Day6Weapon.hasReward(Day6Weapon.MIN_POINTS));
    }

    @Test
    void getRewards_firstTier() {
        List<Reward> rewards = Day6Weapon.getRewards(300);
        assertAll("Награды при первом пороге",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getRewards_maxThreshold() {
        List<Reward> rewards = Day6Weapon.getRewards(115000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(10, rewards.size()), // 9 обычных + 1 топ
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains("115000_reward.png")),
                () -> assertTrue(rewards.get(9).isTopReward())
        );
    }

    @Test
    void getRewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, Day6Weapon.getRewards(300).size()),
                () -> assertEquals(1, Day6Weapon.getRewards(299).size()),
                () -> assertEquals(3, Day6Weapon.getRewards(1200).size()),
                () -> assertEquals(10, Day6Weapon.getRewards(115000).size()),
                () -> assertEquals(9, Day6Weapon.getRewards(114999).size())
        );
    }

    @Test
    void getRewards_veryLargeValue_allTiersUnlocked() {
        List<Reward> rewards = Day6Weapon.getRewards(Integer.MAX_VALUE);
        assertAll("Награды при очень большом значении",
                () -> assertEquals(10, rewards.size()),
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains("115000_reward.png")),
                () -> assertTrue(rewards.get(9).isTopReward()),
                () -> assertTrue(rewards.get(9).getScreenshotPath().contains("top_reward.png"))
        );
    }

    @Test
    void getRewards_realisticLargeValue() {
        // 500 билетов + 100 зелёных + 50 синих + 20 фиолетовых + 10 жёлтых + 100 донатов
        // = 500*120 + 100*10 + 50*30 + 20*250 + 10*2500 + 100*3
        // = 60000 + 1000 + 1500 + 5000 + 25000 + 300 = 92800
        // 92800 >= 75000, но < 115000 → 8 обычных наград + 1 топ = 9 всего
        int largePoints = Day6Weapon.calculatePoints(500, 100, 50, 20, 10, 100);
        List<Reward> rewards = Day6Weapon.getRewards(largePoints);
        assertAll("Награды при реалистично большом значении (92800 очков)",
                () -> assertEquals(92800, largePoints),
                () -> assertTrue(largePoints >= 75000, "Открыты награды до 75000"),
                () -> assertTrue(largePoints < 115000, "Последний порог 115000 ещё не достигнут"),
                () -> assertEquals(9, rewards.size(), "8 обычных наград + 1 топ-награда (всегда)"),
                () -> assertTrue(rewards.stream().anyMatch(Reward::isTopReward), "Топ-награда должна присутствовать"),
                () -> assertFalse(rewards.stream()
                                .filter(r -> !r.isTopReward())
                                .anyMatch(r -> r.getScreenshotPath().contains("115000_reward.png")),
                        "Награда 115000 не должна быть открыта")
        );
    }

    @Test
    void probabilityCalculations_basic() {
        assertAll("Расчёт вероятностей получения сундуков",
                () -> assertEquals(0, Day6Weapon.getExpectedBlueFromGreen(0)),
                () -> assertEquals(1, Day6Weapon.getExpectedBlueFromGreen(20)), // 20*0.05=1
                () -> assertEquals(0, Day6Weapon.getExpectedVioletFromBlue(0)),
                () -> assertEquals(1, Day6Weapon.getExpectedVioletFromBlue(25)) // 25*0.04=1
        );
    }

    @Test
    void probabilityCalculations_range() {
        assertAll("Расчёт диапазона ожидаемых значений",
                () -> assertTrue(Day6Weapon.getMinExpected(100) <= 50),
                () -> assertTrue(Day6Weapon.getMaxExpected(100) >= 150)
        );
    }

    @Test
    void probabilityCalculations_potentialPoints() {
        int potential = Day6Weapon.getPotentialPointsFromBoxes(100, 50);
        // 100*0.05=5 синих * 30 + 50*0.04=2 фиолетовых * 250 = 150 + 500 = 650
        assertEquals(650, potential);
    }
}
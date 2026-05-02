package com.evo.points.calculator;

import com.evo.points.calculator.days.Day2Equipments;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day2EquipmentsTest {

    @Test
    void calculatePoints_validInputs() {
        assertAll("Расчёт очков Дня 2 (Экипировка)",
                () -> assertEquals(0, Day2Equipments.calculatePoints(0, 0)),
                () -> assertEquals(Day2Equipments.TICKET_POINTS, Day2Equipments.calculatePoints(1, 0)),
                () -> assertEquals(Day2Equipments.DONATE_POINTS, Day2Equipments.calculatePoints(0, 1)),
                () -> assertEquals(303, Day2Equipments.calculatePoints(1, 1)),
                () -> assertEquals(3000, Day2Equipments.calculatePoints(10, 0)),
                () -> assertEquals(30, Day2Equipments.calculatePoints(0, 10))
        );
    }

    @Test
    void calculatePoints_negativeInput() {
        assertAll("Отрицательные входные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day2Equipments.calculatePoints(-1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day2Equipments.calculatePoints(0, -1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day2Equipments.calculatePoints(-5, -10))
        );
    }

    @Test
    void hasReward_belowMinPoints() {
        assertFalse(Day2Equipments.hasReward(Day2Equipments.MIN_POINTS - 1),
                "Награда не должна быть доступна ниже минимального порога");
    }

    @Test
    void hasReward_atMinPoints() {
        assertTrue(Day2Equipments.hasReward(Day2Equipments.MIN_POINTS),
                "Награда должна быть доступна при минимальном пороге");
    }

    @Test
    void getRewards_belowFirstTier_alwaysShowTop() {
        List<Reward> rewards = Day2Equipments.getRewards(Day2Equipments.MIN_POINTS - 1);
        assertAll("Награды ниже первого порога (топ всегда показывается)",
                () -> assertEquals(1, rewards.size(), "Должна быть только топ-награда"),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getRewards_firstTier() {
        List<Reward> rewards = Day2Equipments.getRewards(300);
        assertAll("Награды при первом пороге (300 очков)",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains(Day2Equipments.DAY2_SCREENSHOT_300)),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getRewards_midTier() {
        List<Reward> rewards = Day2Equipments.getRewards(15000);
        assertAll("Награды при 15000 очков",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains(Day2Equipments.DAY2_SCREENSHOT_15000)),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }

    @Test
    void getRewards_maxThreshold() {
        List<Reward> rewards = Day2Equipments.getRewards(68000);
        assertAll("Награды при максимальном пороге (68000)",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains(Day2Equipments.DAY2_SCREENSHOT_68000)),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getRewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, Day2Equipments.getRewards(300).size()),
                () -> assertEquals(1, Day2Equipments.getRewards(299).size()),
                () -> assertEquals(3, Day2Equipments.getRewards(1200).size()),
                () -> assertEquals(2, Day2Equipments.getRewards(1199).size()),
                () -> assertEquals(9, Day2Equipments.getRewards(68000).size()),
                () -> assertEquals(8, Day2Equipments.getRewards(67999).size())
        );
    }

    @Test
    void getRewards_veryLargeValue_allTiersUnlocked() {
        int hugePoints = Integer.MAX_VALUE;
        List<Reward> rewards = Day2Equipments.getRewards(hugePoints);
        assertAll("Награды при очень большом значении очков",
                () -> assertEquals(9, rewards.size(), "Все 8 обычных + 1 топ-награда"),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains(Day2Equipments.DAY2_SCREENSHOT_68000)),
                () -> assertTrue(rewards.get(8).isTopReward()),
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains(Day2Equipments.DAY2_SCREENSHOT_TOP))
        );
    }

    @Test
    void getRewards_overflowSafety_largeButRealistic() {
        // Реалистичное большое значение: 1000 билетов + 1000 донатов
        int largePoints = Day2Equipments.calculatePoints(1000, 1000);
        List<Reward> rewards = Day2Equipments.getRewards(largePoints);
        assertAll("Награды при реалистично большом значении",
                () -> assertTrue(largePoints > 68000),
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.stream().anyMatch(r -> r.isTopReward()))
        );
    }
}
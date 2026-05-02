package com.evo.points.calculator;

import com.evo.points.calculator.days.Day3Camp;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day3CampTest {

    @Test
    void calculatePoints_validInputs() {
        assertAll("Расчёт очков Дня 3 (Лагерь)",
                () -> assertEquals(0, Day3Camp.calculatePoints(0, 0, 0, 0, 0, 0)),
                () -> assertEquals(1, Day3Camp.calculatePoints(200, 0, 0, 0, 0, 0)), // steel/200 = 1
                () -> assertEquals(1, Day3Camp.calculatePoints(0, 200, 0, 0, 0, 0)), // energy/200 = 1
                () -> assertEquals(1, Day3Camp.calculatePoints(0, 0, 1, 0, 0, 0)), // boost = 1
                () -> assertEquals(500, Day3Camp.calculatePoints(0, 0, 0, 1, 0, 0)), // battleCore = 500
                () -> assertEquals(500, Day3Camp.calculatePoints(0, 0, 0, 0, 1, 0)), // devCore = 500
                () -> assertEquals(3, Day3Camp.calculatePoints(0, 0, 0, 0, 0, 1)), // donate = 3
                // 200/200 + 200/200 + 1 + 500 + 500 + 3 = 1 + 1 + 1 + 500 + 500 + 3 = 1006
                () -> assertEquals(1006, Day3Camp.calculatePoints(200, 200, 1, 1, 1, 1))
        );
    }

    @Test
    void calculatePoints_negativeInput() {
        assertAll("Отрицательные входные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day3Camp.calculatePoints(-1, 0, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day3Camp.calculatePoints(0, 0, 0, 0, 0, -1))
        );
    }

    @Test
    void hasReward_boundary() {
        assertFalse(Day3Camp.hasReward(Day3Camp.MIN_POINTS - 1));
        assertTrue(Day3Camp.hasReward(Day3Camp.MIN_POINTS));
    }

    @Test
    void getRewards_firstTier() {
        List<Reward> rewards = Day3Camp.getRewards(300);
        assertAll("Награды при первом пороге",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getRewards_maxThreshold() {
        List<Reward> rewards = Day3Camp.getRewards(74000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("74000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getRewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, Day3Camp.getRewards(300).size()),
                () -> assertEquals(1, Day3Camp.getRewards(299).size()),
                () -> assertEquals(3, Day3Camp.getRewards(1200).size()),
                () -> assertEquals(9, Day3Camp.getRewards(74000).size()),
                () -> assertEquals(8, Day3Camp.getRewards(73999).size())
        );
    }

    @Test
    void getRewards_veryLargeValue_allTiersUnlocked() {
        List<Reward> rewards = Day3Camp.getRewards(Integer.MAX_VALUE);
        assertAll("Награды при очень большом значении",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("74000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward()),
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains("top_reward.png"))
        );
    }

    @Test
    void getRewards_realisticLargeValue() {
        // 100000 стали + 100000 энергии + 100 бустов + 100 ядер боя + 100 ядер развития + 100 донатов
        int largePoints = Day3Camp.calculatePoints(100000, 100000, 100, 100, 100, 100);
        List<Reward> rewards = Day3Camp.getRewards(largePoints);
        assertAll("Награды при реалистично большом значении",
                () -> assertTrue(largePoints > 74000),
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.stream().anyMatch(Reward::isTopReward))
        );
    }
}
package com.evo.points.calculator;

import com.evo.points.calculator.days.Day4Blueprint;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day4BlueprintTest {

    @Test
    void calculatePoints_validInputs() {
        assertAll("Расчёт очков Дня 4 (Чертежи)",
                () -> assertEquals(0, Day4Blueprint.calculatePoints(0, 0, 0)),
                () -> assertEquals(Day4Blueprint.COMMON_POINTS, Day4Blueprint.calculatePoints(1, 0, 0)),
                () -> assertEquals(Day4Blueprint.ADVANCED_POINTS, Day4Blueprint.calculatePoints(0, 1, 0)),
                () -> assertEquals(Day4Blueprint.DONATE_POINTS, Day4Blueprint.calculatePoints(0, 0, 1)),
                // 10*30 + 1*810 = 300 + 810 = 1110
                () -> assertEquals(1110, Day4Blueprint.calculatePoints(10, 1, 0)),
                // 1*30 + 1*3 = 33
                () -> assertEquals(33, Day4Blueprint.calculatePoints(1, 0, 1))
        );
    }

    @Test
    void calculatePoints_negativeInput() {
        assertAll("Отрицательные входные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day4Blueprint.calculatePoints(-1, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day4Blueprint.calculatePoints(0, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day4Blueprint.calculatePoints(0, 0, -1))
        );
    }

    @Test
    void hasReward_boundary() {
        assertFalse(Day4Blueprint.hasReward(Day4Blueprint.MIN_POINTS - 1));
        assertTrue(Day4Blueprint.hasReward(Day4Blueprint.MIN_POINTS));
    }

    @Test
    void getRewards_firstTier() {
        List<Reward> rewards = Day4Blueprint.getRewards(300);
        assertAll("Награды при первом пороге",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getRewards_maxThreshold() {
        List<Reward> rewards = Day4Blueprint.getRewards(88000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("88000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getRewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, Day4Blueprint.getRewards(300).size()),
                () -> assertEquals(1, Day4Blueprint.getRewards(299).size()),
                () -> assertEquals(3, Day4Blueprint.getRewards(1200).size()),
                () -> assertEquals(9, Day4Blueprint.getRewards(88000).size()),
                () -> assertEquals(8, Day4Blueprint.getRewards(87999).size())
        );
    }

    @Test
    void getRewards_veryLargeValue_allTiersUnlocked() {
        List<Reward> rewards = Day4Blueprint.getRewards(Integer.MAX_VALUE);
        assertAll("Награды при очень большом значении",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("88000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward()),
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains("top_reward.png"))
        );
    }

    @Test
    void getRewards_realisticLargeValue() {
        // 1000 обычных + 100 продвинутых + 100 донатов
        int largePoints = Day4Blueprint.calculatePoints(1000, 100, 100);
        List<Reward> rewards = Day4Blueprint.getRewards(largePoints);
        assertAll("Награды при реалистично большом значении",
                () -> assertTrue(largePoints > 88000),
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.stream().anyMatch(Reward::isTopReward))
        );
    }
}
package com.evo.points.calculator;

import com.evo.points.calculator.days.Day5Neurolink;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day5NeurolinkTest {

    @Test
    void calculatePoints_validInputs() {
        assertAll("Расчёт очков Дня 5 (Невролинк)",
                () -> assertEquals(0, Day5Neurolink.calculatePoints(0, 0, 0, 0)),
                () -> assertEquals(Day5Neurolink.SYNAPTIC_CHIP_POINTS, Day5Neurolink.calculatePoints(1, 0, 0, 0)),
                () -> assertEquals(Day5Neurolink.NEURO_CODER_POINTS, Day5Neurolink.calculatePoints(0, 1, 0, 0)),
                () -> assertEquals(Day5Neurolink.CORTICAL_IMPLANT_POINTS, Day5Neurolink.calculatePoints(0, 0, 1, 0)),
                () -> assertEquals(Day5Neurolink.DONATE_POINTS, Day5Neurolink.calculatePoints(0, 0, 0, 1)),
                // 10*5 + 10*10 + 1*2000 + 1*3 = 50 + 100 + 2000 + 3 = 2153
                () -> assertEquals(2153, Day5Neurolink.calculatePoints(10, 10, 1, 1))
        );
    }

    @Test
    void calculatePoints_negativeInput() {
        assertAll("Отрицательные входные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day5Neurolink.calculatePoints(-1, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day5Neurolink.calculatePoints(0, 0, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> Day5Neurolink.calculatePoints(0, 0, 0, -1))
        );
    }

    @Test
    void hasReward_boundary() {
        assertFalse(Day5Neurolink.hasReward(Day5Neurolink.MIN_POINTS - 1));
        assertTrue(Day5Neurolink.hasReward(Day5Neurolink.MIN_POINTS));
    }

    @Test
    void getRewards_firstTier() {
        List<Reward> rewards = Day5Neurolink.getRewards(300);
        assertAll("Награды при первом пороге",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getRewards_maxThreshold() {
        List<Reward> rewards = Day5Neurolink.getRewards(72000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("72000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getRewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, Day5Neurolink.getRewards(300).size()),
                () -> assertEquals(1, Day5Neurolink.getRewards(299).size()),
                () -> assertEquals(3, Day5Neurolink.getRewards(1200).size()),
                () -> assertEquals(9, Day5Neurolink.getRewards(72000).size()),
                () -> assertEquals(8, Day5Neurolink.getRewards(71999).size())
        );
    }

    @Test
    void getRewards_veryLargeValue_allTiersUnlocked() {
        List<Reward> rewards = Day5Neurolink.getRewards(Integer.MAX_VALUE);
        assertAll("Награды при очень большом значении",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("72000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward()),
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains("top_reward.png"))
        );
    }

    @Test
    void getRewards_realisticLargeValue() {
        // 1000 чипов + 500 кодировщиков + 50 имплантов + 100 донатов
        int largePoints = Day5Neurolink.calculatePoints(1000, 500, 50, 100);
        List<Reward> rewards = Day5Neurolink.getRewards(largePoints);
        assertAll("Награды при реалистично большом значении",
                () -> assertTrue(largePoints > 72000),
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.stream().anyMatch(Reward::isTopReward))
        );
    }
}
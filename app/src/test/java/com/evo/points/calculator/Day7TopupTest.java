package com.evo.points.calculator;

import com.evo.points.calculator.days.Day7Topup;
import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day7TopupTest {

    @Test
    void calculatePoints_validInputs() {
        assertAll("Расчёт очков Дня 7 (Пополнение)",
                () -> assertEquals(0, Day7Topup.calculatePoints(0)),
                () -> assertEquals(Day7Topup.DONATE_POINTS, Day7Topup.calculatePoints(1)),
                () -> assertEquals(60, Day7Topup.calculatePoints(10)),
                () -> assertEquals(600, Day7Topup.calculatePoints(100)),
                () -> assertEquals(6000, Day7Topup.calculatePoints(1000))
        );
    }

    @Test
    void calculatePoints_negativeInput() {
        assertThrows(IllegalArgumentException.class,
                () -> Day7Topup.calculatePoints(-1));
    }

    @Test
    void hasReward_boundary() {
        assertFalse(Day7Topup.hasReward(Day7Topup.MIN_POINTS - 1));
        assertTrue(Day7Topup.hasReward(Day7Topup.MIN_POINTS));
    }

    @Test
    void getRewards_firstTier() {
        List<Reward> rewards = Day7Topup.getRewards(600);
        assertAll("Награды при первом пороге",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("600_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getRewards_maxThreshold() {
        List<Reward> rewards = Day7Topup.getRewards(148800);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("148800_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getRewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, Day7Topup.getRewards(600).size()),
                () -> assertEquals(1, Day7Topup.getRewards(599).size()),
                () -> assertEquals(3, Day7Topup.getRewards(2400).size()),
                () -> assertEquals(9, Day7Topup.getRewards(148800).size()),
                () -> assertEquals(8, Day7Topup.getRewards(148799).size())
        );
    }

    @Test
    void getRewards_veryLargeValue_allTiersUnlocked() {
        List<Reward> rewards = Day7Topup.getRewards(Integer.MAX_VALUE);
        assertAll("Награды при очень большом значении",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("148800_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward()),
                () -> assertTrue(rewards.get(8).getScreenshotPath().contains("top_reward.png"))
        );
    }

    @Test
    void getRewards_realisticLargeValue() {
        // 30000 пополнений = 180000 очков
        int largePoints = Day7Topup.calculatePoints(30000);
        List<Reward> rewards = Day7Topup.getRewards(largePoints);
        assertAll("Награды при реалистично большом значении",
                () -> assertTrue(largePoints > 148800),
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.stream().anyMatch(Reward::isTopReward))
        );
    }

    @Test
    void getRewards_overflowSafety() {
        // Проверка, что при значении близком к MAX_VALUE нет переполнения
        List<Reward> rewards = Day7Topup.getRewards(Integer.MAX_VALUE - 1000);
        assertNotNull(rewards);
        assertEquals(9, rewards.size());
        assertTrue(rewards.get(rewards.size() - 1).isTopReward());
    }
}
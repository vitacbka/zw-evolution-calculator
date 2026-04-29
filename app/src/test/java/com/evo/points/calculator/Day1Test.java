package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day1Test {

    @Test
    void calculateDay1_validInputs() {
        assertAll("Расчёт очков Дня 1",
                () -> assertEquals(0, EvoCalculatorCore.calculateDay1(0)),
                () -> assertEquals(50, EvoCalculatorCore.calculateDay1(1)),
                () -> assertEquals(500, EvoCalculatorCore.calculateDay1(10)),
                () -> assertEquals(68000, EvoCalculatorCore.calculateDay1(1360))
        );
    }

    @Test
    void calculateDay1_negativeInput() {
        assertThrows(IllegalArgumentException.class,
                () -> EvoCalculatorCore.calculateDay1(-1));
    }

    @Test
    void getDay1Rewards_belowFirstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(299);
        assertAll("Награды ниже первого порога",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getDay1Rewards_firstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(300);
        assertAll("Награды при 300 очках",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_reward.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay1Rewards_midTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(15000);
        assertAll("Награды при 15000 очках",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains("15000_reward.png")),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }

    @Test
    void getDay1Rewards_maxThreshold() {
        List<Reward> rewards = EvoCalculatorCore.getDay1Rewards(68000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("68000_reward.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getDay1Rewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, EvoCalculatorCore.getDay1Rewards(300).size()),
                () -> assertEquals(1, EvoCalculatorCore.getDay1Rewards(299).size()),
                () -> assertEquals(3, EvoCalculatorCore.getDay1Rewards(1200).size()),
                () -> assertEquals(2, EvoCalculatorCore.getDay1Rewards(1199).size())
        );
    }
}
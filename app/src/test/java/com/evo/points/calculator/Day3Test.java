package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day3Test {

    @Test
    void calculateDay3_individualResources() {
        assertAll("Расчёт по отдельным ресурсам",
                () -> assertEquals(0, EvoCalculatorCore.calculateDay3(199, 0, 0, 0, 0, 0)),
                () -> assertEquals(1, EvoCalculatorCore.calculateDay3(200, 0, 0, 0, 0, 0)),
                () -> assertEquals(1, EvoCalculatorCore.calculateDay3(0, 200, 0, 0, 0, 0)),
                () -> assertEquals(1, EvoCalculatorCore.calculateDay3(0, 0, 2, 0, 0, 0)),
                () -> assertEquals(500, EvoCalculatorCore.calculateDay3(0, 0, 0, 1, 0, 0)),
                () -> assertEquals(500, EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 1, 0)),
                () -> assertEquals(3, EvoCalculatorCore.calculateDay3(0, 0, 0, 0, 0, 1))
        );
    }

    @Test
    void calculateDay3_combined() {
        assertAll("Комбинированный расчёт",
                () -> assertEquals(1012, EvoCalculatorCore.calculateDay3(400, 400, 4, 1, 1, 2)),
                () -> assertEquals(4235, EvoCalculatorCore.calculateDay3(10000, 5000, 20, 5, 3, 50))
        );
    }

    @Test
    void calculateDay3_negativeInputs() {
        assertAll("Отрицательные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay3(-1, 0, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay3(0, -1, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay3(0, 0, -1, 0, 0, 0))
        );
    }

    @Test
    void getDay3Rewards_belowFirstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(299);
        assertAll("Награды ниже первого порога",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getDay3Rewards_firstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(300);
        assertAll("Награды при 300 очках",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_revard.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay3Rewards_midTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(17000);
        assertAll("Награды при 17000 очках",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains("17000_revard.png")),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }

    @Test
    void getDay3Rewards_maxThreshold() {
        List<Reward> rewards = EvoCalculatorCore.getDay3Rewards(74000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("74000_revard.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }

    @Test
    void getDay3Rewards_thresholdBoundaries() {
        assertAll("Граничные значения порогов",
                () -> assertEquals(2, EvoCalculatorCore.getDay3Rewards(300).size()),
                () -> assertEquals(1, EvoCalculatorCore.getDay3Rewards(299).size()),
                () -> assertEquals(5, EvoCalculatorCore.getDay3Rewards(6000).size()),
                () -> assertEquals(4, EvoCalculatorCore.getDay3Rewards(5999).size())
        );
    }
}
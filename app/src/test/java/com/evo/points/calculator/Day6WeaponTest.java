package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day6Test {

    @Test
    void calculateDay6_individualResources() {
        assertAll("Расчёт по отдельным ресурсам",
                () -> assertEquals(120, EvoCalculatorCore.calculateDay6(1, 0, 0, 0, 0, 0)),
                () -> assertEquals(10, EvoCalculatorCore.calculateDay6(0, 1, 0, 0, 0, 0)),
                () -> assertEquals(30, EvoCalculatorCore.calculateDay6(0, 0, 1, 0, 0, 0)),
                () -> assertEquals(250, EvoCalculatorCore.calculateDay6(0, 0, 0, 1, 0, 0)),
                () -> assertEquals(2500, EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 1, 0)),
                () -> assertEquals(3, EvoCalculatorCore.calculateDay6(0, 0, 0, 0, 0, 1))
        );
    }

    @Test
    void calculateDay6_combined() {
        assertAll("Комбинированный расчёт",
                () -> assertEquals(2913, EvoCalculatorCore.calculateDay6(1, 1, 1, 1, 1, 1)),
                () -> assertEquals(7500, EvoCalculatorCore.calculateDay6(62, 0, 0, 0, 0, 20))
        );
    }

    @Test
    void calculateDay6_negativeInputs() {
        assertAll("Отрицательные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay6(-1, 0, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay6(0, -1, 0, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay6(0, 0, -1, 0, 0, 0))
        );
    }

    @Test
    void getDay6Rewards_belowFirstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(7499);
        assertAll("Награды ниже первого порога",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getDay6Rewards_firstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(7500);
        assertAll("Награды при 7500 очках",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("7500_revard.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay6Rewards_midTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(45000);
        assertAll("Награды при 45000 очках",
                () -> assertEquals(4, rewards.size()),
                () -> assertTrue(rewards.get(2).getScreenshotPath().contains("45000_revard.png")),
                () -> assertTrue(rewards.get(3).isTopReward())
        );
    }

    @Test
    void getDay6Rewards_maxThreshold() {
        List<Reward> rewards = EvoCalculatorCore.getDay6Rewards(115000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains("115000_revard.png")),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }
}
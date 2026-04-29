package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day4Test {

    @Test
    void calculateDay4_individualResources() {
        assertAll("Расчёт по отдельным ресурсам",
                () -> assertEquals(30, EvoCalculatorCore.calculateDay4(1, 0, 0)),
                () -> assertEquals(810, EvoCalculatorCore.calculateDay4(0, 1, 0)),
                () -> assertEquals(3, EvoCalculatorCore.calculateDay4(0, 0, 1))
        );
    }

    @Test
    void calculateDay4_combined() {
        assertAll("Комбинированный расчёт",
                () -> assertEquals(843, EvoCalculatorCore.calculateDay4(1, 1, 1)),
                () -> assertEquals(3000, EvoCalculatorCore.calculateDay4(100, 0, 0)),
                () -> assertEquals(8100, EvoCalculatorCore.calculateDay4(0, 10, 0))
        );
    }

    @Test
    void calculateDay4_negativeInputs() {
        assertAll("Отрицательные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay4(-1, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay4(0, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay4(0, 0, -1))
        );
    }

    @Test
    void getDay4Rewards_belowFirstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(5999);
        assertAll("Награды ниже первого порога",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getDay4Rewards_firstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(6000);
        assertAll("Награды при 6000 очках",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("6000_revard.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay4Rewards_midTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(30000);
        assertAll("Награды при 30000 очках",
                () -> assertEquals(4, rewards.size()),
                () -> assertTrue(rewards.get(2).getScreenshotPath().contains("30000_revard.png")),
                () -> assertTrue(rewards.get(3).isTopReward())
        );
    }

    @Test
    void getDay4Rewards_maxThreshold() {
        List<Reward> rewards = EvoCalculatorCore.getDay4Rewards(88000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains("88000_revard.png")),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }
}
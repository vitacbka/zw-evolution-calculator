package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day5Test {

    @Test
    void calculateDay5_individualResources() {
        assertAll("Расчёт по отдельным ресурсам",
                () -> assertEquals(5, EvoCalculatorCore.calculateDay5(1, 0, 0, 0)),
                () -> assertEquals(10, EvoCalculatorCore.calculateDay5(0, 1, 0, 0)),
                () -> assertEquals(2000, EvoCalculatorCore.calculateDay5(0, 0, 1, 0)),
                () -> assertEquals(3, EvoCalculatorCore.calculateDay5(0, 0, 0, 1))
        );
    }

    @Test
    void calculateDay5_combined() {
        assertAll("Комбинированный расчёт",
                () -> assertEquals(2018, EvoCalculatorCore.calculateDay5(1, 1, 1, 1)),
                () -> assertEquals(5000, EvoCalculatorCore.calculateDay5(1000, 0, 0, 0)),
                () -> assertEquals(72000, EvoCalculatorCore.calculateDay5(0, 0, 36, 0))
        );
    }

    @Test
    void calculateDay5_negativeInputs() {
        assertAll("Отрицательные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay5(-1, 0, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay5(0, -1, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay5(0, 0, -1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay5(0, 0, 0, -1))
        );
    }

    @Test
    void getDay5Rewards_belowFirstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(4999);
        assertAll("Награды ниже первого порога",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getDay5Rewards_firstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(5000);
        assertAll("Награды при 5000 очках",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("5000_revard.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay5Rewards_midTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(25000);
        assertAll("Награды при 25000 очках",
                () -> assertEquals(4, rewards.size()),
                () -> assertTrue(rewards.get(2).getScreenshotPath().contains("25000_revard.png")),
                () -> assertTrue(rewards.get(3).isTopReward())
        );
    }

    @Test
    void getDay5Rewards_maxThreshold() {
        List<Reward> rewards = EvoCalculatorCore.getDay5Rewards(72000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains("72000_revard.png")),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }
}
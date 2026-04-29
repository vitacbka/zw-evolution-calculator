package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day2Test {

    @Test
    void calculateDay2_onlyTickets() {
        assertAll("Расчёт только по билетам",
                () -> assertEquals(0, EvoCalculatorCore.calculateDay2(0, 0)),
                () -> assertEquals(300, EvoCalculatorCore.calculateDay2(1, 0)),
                () -> assertEquals(6000, EvoCalculatorCore.calculateDay2(20, 0))
        );
    }

    @Test
    void calculateDay2_onlyDonate() {
        assertAll("Расчёт только по донату",
                () -> assertEquals(0, EvoCalculatorCore.calculateDay2(0, 0)),
                () -> assertEquals(3, EvoCalculatorCore.calculateDay2(0, 1)),
                () -> assertEquals(30, EvoCalculatorCore.calculateDay2(0, 10))
        );
    }

    @Test
    void calculateDay2_combined() {
        assertAll("Комбинированный расчёт",
                () -> assertEquals(6030, EvoCalculatorCore.calculateDay2(20, 10)),
                () -> assertEquals(30150, EvoCalculatorCore.calculateDay2(100, 50))
        );
    }

    @Test
    void calculateDay2_negativeInputs() {
        assertAll("Отрицательные значения",
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay2(-1, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> EvoCalculatorCore.calculateDay2(0, -1))
        );
    }

    @Test
    void getDay2Rewards_belowFirstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(299);
        assertAll("Награды ниже первого порога",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }

    @Test
    void getDay2Rewards_firstTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(300);
        assertAll("Награды при 300 очках",
                () -> assertEquals(2, rewards.size()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("300_revard.png")),
                () -> assertTrue(rewards.get(1).isTopReward())
        );
    }

    @Test
    void getDay2Rewards_midTier() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(15000);
        assertAll("Награды при 15000 очках",
                () -> assertEquals(6, rewards.size()),
                () -> assertTrue(rewards.get(4).getScreenshotPath().contains("15000_revard.png")),
                () -> assertTrue(rewards.get(5).isTopReward())
        );
    }

    @Test
    void getDay2Rewards_maxThreshold() {
        List<Reward> rewards = EvoCalculatorCore.getDay2Rewards(68000);
        assertAll("Награды при максимальном пороге",
                () -> assertEquals(9, rewards.size()),
                () -> assertTrue(rewards.get(7).getScreenshotPath().contains("68000_revard.png")),
                () -> assertTrue(rewards.get(8).isTopReward())
        );
    }
}
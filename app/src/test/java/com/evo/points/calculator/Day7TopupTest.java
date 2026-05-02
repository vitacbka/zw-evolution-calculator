package com.evo.points.calculator;

import com.evo.points.model.Reward;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Day7Test {

    @Test
    void calculateDay7_validInputs() {
        assertAll("Расчёт очков Дня 7",
                () -> assertEquals(0, EvoCalculatorCore.calculateDay7(0)),
                () -> assertEquals(6, EvoCalculatorCore.calculateDay7(1)),
                () -> assertEquals(60, EvoCalculatorCore.calculateDay7(10)),
                () -> assertEquals(600, EvoCalculatorCore.calculateDay7(100))
        );
    }

    @Test
    void calculateDay7_negativeInput() {
        assertThrows(IllegalArgumentException.class,
                () -> EvoCalculatorCore.calculateDay7(-1));
    }

    @Test
    void getDay7Rewards_zeroPoints() {
        List<Reward> rewards = EvoCalculatorCore.getDay7Rewards(0);
        assertAll("Награды при 0 очков",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward()),
                () -> assertTrue(rewards.get(0).getScreenshotPath().contains("top_revard.png"))
        );
    }

    @Test
    void getDay7Rewards_highPoints() {
        List<Reward> rewards = EvoCalculatorCore.getDay7Rewards(100000);
        assertAll("Награды при большом количестве очков",
                () -> assertEquals(1, rewards.size()),
                () -> assertTrue(rewards.get(0).isTopReward())
        );
    }
}
package com.evo.points.calculator.utils;

import android.annotation.SuppressLint;

public class ScreenshotUtils {
    private static final String REWARD_PATTERN = "%d_reward.png";
    public static final String TOP_REWARD = "top_reward.png";

    private ScreenshotUtils() {}

    @SuppressLint("DefaultLocale")
    public static String getRewardScreenshot(int amount) {
        return String.format(REWARD_PATTERN, amount);
    }
}

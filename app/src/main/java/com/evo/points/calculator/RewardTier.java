package com.evo.points.calculator;

public class RewardTier {
    private final int threshold;
    private final String screenshotName;

    public RewardTier(int threshold, String screenshotName) {
        this.threshold = threshold;
        this.screenshotName = screenshotName;
    }

    public int getThreshold() { return threshold; }
    public String getScreenshotName() { return screenshotName; }
}
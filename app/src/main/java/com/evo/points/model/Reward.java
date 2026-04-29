package com.evo.points.model;

/**
 * Модель награды события эволюции.
 */
public class Reward {
    
    public enum RewardType {
        NORMAL,  // Обычная награда по очкам
        TOP      // Топ награда (отдельная вкладка)
    }
    
    private final String screenshotPath;
    private final RewardType type;

    public Reward(String screenshotPath) {
        this.screenshotPath = screenshotPath;
        this.type = RewardType.NORMAL;
    }

    public Reward(String screenshotPath, RewardType type) {
        this.screenshotPath = screenshotPath;
        this.type = type;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public boolean hasScreenshot() {
        return screenshotPath != null;
    }

    public RewardType getType() {
        return type;
    }

    public boolean isTopReward() {
        return type == RewardType.TOP;
    }
}

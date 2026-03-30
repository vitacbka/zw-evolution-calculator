package com.evo.points.model;

/**
 * Модель награды события эволюции.
 */
public class Reward {
    
    public enum RewardType {
        NORMAL,  // Обычная награда по очкам
        TOP      // Топ награда (отдельная вкладка)
    }
    
    private final int iconResId;
    private final String name;
    private final int quantity;
    private final String screenshotPath;
    private final RewardType type;

    public Reward(int iconResId, String name, int quantity) {
        this.iconResId = iconResId;
        this.name = name;
        this.quantity = quantity;
        this.screenshotPath = null;
        this.type = RewardType.NORMAL;
    }

    public Reward(String screenshotPath) {
        this.iconResId = 0;
        this.name = null;
        this.quantity = 0;
        this.screenshotPath = screenshotPath;
        this.type = RewardType.NORMAL;
    }

    public Reward(String screenshotPath, RewardType type) {
        this.iconResId = 0;
        this.name = null;
        this.quantity = 0;
        this.screenshotPath = screenshotPath;
        this.type = type;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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

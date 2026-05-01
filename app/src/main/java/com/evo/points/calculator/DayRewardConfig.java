package com.evo.points.calculator;

import java.util.List;

public class DayRewardConfig {
    private final String basePath;
    private final List<RewardTier> normalTiers;
    private final Integer topThreshold;
    private final String topScreenshotName;
    private final boolean alwaysShowTop;

    public DayRewardConfig(String basePath,
                           List<RewardTier> normalTiers,
                           Integer topThreshold,
                           String topScreenshotName,
                           boolean alwaysShowTop) {
        this.basePath = basePath;
        this.normalTiers = normalTiers;
        this.topThreshold = topThreshold;
        this.topScreenshotName = topScreenshotName;
        this.alwaysShowTop = alwaysShowTop;
    }

    public String getBasePath() { return basePath; }
    public List<RewardTier> getNormalTiers() { return normalTiers; }
    public Integer getTopThreshold() { return topThreshold; }
    public String getTopScreenshotName() { return topScreenshotName; }
    public boolean isAlwaysShowTop() { return alwaysShowTop; }
}
export interface RewardTier {
  points: number;
  screenshot: string;
}

export interface DayRewardConfig {
  basePath: string;
  normalTiers: RewardTier[];
  topThreshold: number;
  topScreenshotName: string;
  alwaysShowTop: boolean;
}

export interface Reward {
  points: number;
  imagePath: string;
  isTopReward: boolean;
}

export interface DayConfig {
  id: number;
  name: string;
  inputHints: string[];
  calculatePoints: (values: number[]) => number;
  getRewards: (points: number) => Reward[];
  showDay6Probabilities?: boolean;
}

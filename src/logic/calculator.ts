import type { Reward, DayRewardConfig, DayConfig } from '../types';

// Используем базовый путь из окружения Vite
const BASE_URL = import.meta.env.BASE_URL || '/';

const buildRewardsByConfig = (points: number, config: DayRewardConfig): Reward[] => {
  const rewards: Reward[] = [];

  for (const tier of config.normalTiers) {
    if (points >= tier.points) {
      rewards.push({
        points: tier.points,
        imagePath: `${BASE_URL}${config.basePath}${tier.screenshot}`,
        isTopReward: false,
      });
    }
  }

  if (config.alwaysShowTop || points >= config.topThreshold) {
    rewards.push({
      points: config.topThreshold,
      imagePath: `${BASE_URL}${config.basePath}${config.topScreenshotName}`,
      isTopReward: true,
    });
  }

  return rewards;
};

// Исправлено: в файлах формат {points}_reward.png, а не reward_{points}.png
const getRewardScreenshot = (points: number) => `${points}_reward.png`;
const TOP_REWARD = 'top_reward.png';

export const DAYS: DayConfig[] = [
  {
    id: 1,
    name: 'День 1 — Карты эволюции',
    inputHints: ['Количество карт'],
    calculatePoints: ([cards]) => (cards || 0) * 50,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_1/',
      normalTiers: [300, 1200, 3000, 6000, 15000, 30000, 40000, 68000].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 68000,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
  },
  {
    id: 2,
    name: 'День 2 — Экипировка',
    inputHints: ['Билеты на экипировку', 'Количество пополнений (донат)'],
    calculatePoints: ([tickets, donate]) => (tickets || 0) * 300 + (donate || 0) * 3,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_2/',
      normalTiers: [300, 1200, 2400, 6800, 15000, 30000, 48000, 68000].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 68000,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
  },
  {
    id: 3,
    name: 'День 3 — Лагерь',
    inputHints: ['Сталь', 'Энергия', 'Ускорения', 'Техноядра (бой)', 'Техноядра (развитие)', 'Количество пополнений (донат)'],
    calculatePoints: ([steel, energy, boost, battleCore, devCore, donate]) => 
      Math.floor((steel || 0) / 200) + Math.floor((energy || 0) / 200) + (boost || 0) * 1 + (battleCore || 0) * 500 + (devCore || 0) * 500 + (donate || 0) * 3,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_3/',
      normalTiers: [300, 1200, 2400, 6000, 17000, 30000, 45000, 74000].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 74000,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
  },
  {
    id: 4,
    name: 'День 4 — Чертежи',
    inputHints: ['Обычные модули', 'Улучшенные модули', 'Количество пополнений (донат)'],
    calculatePoints: ([common, advanced, donate]) => (common || 0) * 30 + (advanced || 0) * 810 + (donate || 0) * 3,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_4/',
      normalTiers: [300, 1200, 2400, 6000, 15000, 30000, 56000, 88000].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 88000,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
  },
  {
    id: 5,
    name: 'День 5 — Невролинк',
    inputHints: ['Чипы синаптического усиления', 'Нейрокодировщики', 'Кортикальные импланты', 'Количество пополнений (донат)'],
    calculatePoints: ([chips, coder, implant, donate]) => (chips || 0) * 5 + (coder || 0) * 10 + (implant || 0) * 2000 + (donate || 0) * 3,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_5/',
      normalTiers: [300, 1200, 2400, 6000, 10000, 25000, 48000, 72000].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 72000,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
  },
  {
    id: 6,
    name: 'День 6 — Оружие/Акс.',
    inputHints: ['Билеты розыгрыша', 'Зелёные ящики', 'Синие ящики', 'Фиолетовые ящики', 'Жёлтые ящики', 'Количество пополнений (донат)'],
    calculatePoints: ([tickets, green, blue, violet, yellow, donate]) => 
      (tickets || 0) * 120 + (green || 0) * 10 + (blue || 0) * 30 + (violet || 0) * 250 + (yellow || 0) * 2500 + (donate || 0) * 3,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_6/',
      normalTiers: [300, 1200, 2400, 6000, 15000, 25000, 45000, 75000, 115000].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 115000,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
    showDay6Probabilities: true,
  },
  {
    id: 7,
    name: 'День 7 — Пополнение',
    inputHints: ['Количество пополнений (донат)'],
    calculatePoints: ([donate]) => (donate || 0) * 6,
    getRewards: (points) => buildRewardsByConfig(points, {
      basePath: 'img/day_7/',
      normalTiers: [600, 2400, 4800, 9800, 58000, 88800, 118800, 148800].map(p => ({ points: p, screenshot: getRewardScreenshot(p) })),
      topThreshold: 148800,
      topScreenshotName: TOP_REWARD,
      alwaysShowTop: true,
    }),
  },
];

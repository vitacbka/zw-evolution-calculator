package com.evo.points.util;

import com.evo.points.R;

/**
 * Утилита для маппинга ID наград на drawable-ресурсы.
 */
public class RewardIconMapper {

    public static int getDrawableResId(int iconId) {
        switch (iconId) {
            case 1: return R.drawable.ic_reward_tesseract;
            case 2: return R.drawable.ic_reward_voucher_gear;
            case 3: return R.drawable.ic_reward_chip;
            case 4: return R.drawable.ic_reward_voucher_weapon;
            case 5: return R.drawable.ic_reward_diamond;
            case 6: return R.drawable.ic_reward_chest_s;
            case 7: return R.drawable.ic_reward_chest_resource;
            case 8: return R.drawable.ic_reward_epic_gear;
            case 9: return R.drawable.ic_reward_exchange_gear;
            case 10: return R.drawable.ic_reward_tonic;
            case 11: return R.drawable.ic_reward_ornament_legendary;
            case 12: return R.drawable.ic_reward_ornament_epic;
            case 13: return R.drawable.ic_reward_tech_core_battle;
            case 14: return R.drawable.ic_reward_tech_core_dev;
            case 15: return R.drawable.ic_reward_module_vi;
            case 16: return R.drawable.ic_reward_module_v;
            case 17: return R.drawable.ic_reward_module_exchange;
            case 18: return R.drawable.ic_reward_advanced_module;
            case 19: return R.drawable.ic_reward_neuro_material;
            case 20: return R.drawable.ic_reward_neuro_chest;
            case 21: return R.drawable.ic_reward_weapon_chest;
            case 22: return R.drawable.ic_reward_crystal_legendary;
            case 23: return R.drawable.ic_reward_implant;
            case 24: return R.drawable.ic_reward_anti_matter;
            case 25: return R.drawable.ic_reward_precision_component;
            case 26: return R.drawable.ic_reward_supply_chest_legendary;
            case 27: return R.drawable.ic_reward_supply_chest_epic;
            case 28: return R.drawable.ic_reward_supply_chest_elite;
            case 29: return R.drawable.ic_reward_supply_chest_advanced;
            default: return R.drawable.ic_reward_chip;
        }
    }
}

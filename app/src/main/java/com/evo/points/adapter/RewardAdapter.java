package com.evo.points.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evo.points.model.Reward;
import com.evo.points.util.AssetBitmapLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер для отображения списка наград со скриншотами.
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
    private static final String TAG = "EvoCalc";
    /** Максимум большей стороны bitmap для элемента списка (снижает память и уменьшает шанс null от декодера). */
    private static final int MAX_REWARD_IMAGE_SIDE_PX = 900;

    private List<Reward> rewards = new ArrayList<>();
    private Context context;

    public RewardAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards != null ? new ArrayList<>(rewards) : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.evo.points.R.layout.reward_item, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Reward reward = rewards.get(position);
        holder.bind(reward, context);
    }

    @Override
    public long getItemId(int position) {
        return rewards.get(position).getScreenshotPath().hashCode();
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    static class RewardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView rewardImageView;

        RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardImageView = itemView.findViewById(com.evo.points.R.id.rewardImage);
        }

        void bind(Reward reward, Context context) {
            rewardImageView.setImageBitmap(null);
            rewardImageView.setImageDrawable(null); // сброс

            if (reward.hasScreenshot()) {
                String path = reward.getScreenshotPath();
                Log.d(TAG, "🖼️ Loading reward image: " + path); // 🔍 DEBUG

                try {
                    Bitmap bitmap = AssetBitmapLoader.decodeSampled(context, path, MAX_REWARD_IMAGE_SIDE_PX);
                    if (bitmap == null) {
                        Log.e(TAG, "❌ Декодер вернул null для: " + path);
                    } else {
                        rewardImageView.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "❌ Ошибка загрузки: " + path, e);
                }

                // 🔍 Проверка: если после всего этого изображение всё ещё пустое
                if (rewardImageView.getDrawable() == null) {
                    Log.w(TAG, "⚠️ ImageView остался пустым после bind(): " + path);
                }
            }
        }
    }
}

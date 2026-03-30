package com.evo.points.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evo.points.model.Reward;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер для отображения списка наград со скриншотами.
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    private List<Reward> rewards = new ArrayList<>();
    private Context context;

    public RewardAdapter(Context context) {
        this.context = context;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards != null ? rewards : new ArrayList<>();
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
            if (reward.hasScreenshot()) {
                String path = reward.getScreenshotPath();
                
                // Загружаем изображение из assets
                try {
                    InputStream inputStream = context.getAssets().open(path);
                    Bitmap bitmap = decodeSampledBitmapFromStream(inputStream, 300, 60);
                    rewardImageView.setImageBitmap(bitmap);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Фолбэк - заглушка
                    rewardImageView.setImageResource(com.evo.points.R.drawable.ic_reward_chip);
                }
            }
        }

        /**
         * Декодирует Bitmap с уменьшением размера для оптимизации памяти
         */
        private Bitmap decodeSampledBitmapFromStream(InputStream inputStream, int reqWidth, int reqHeight) {
            // Сначала получаем размеры изображения
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            
            // Создаём копию InputStream для первого прохода
            try {
                inputStream.mark(Integer.MAX_VALUE);
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Вычисляем inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Декодируем с уменьшением
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(inputStream, null, options);
        }

        /**
         * Вычисляет коэффициент уменьшения изображения
         */
        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }
    }
}

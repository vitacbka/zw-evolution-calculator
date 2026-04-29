package com.evo.points.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Загрузка изображений из {@code assets/} с уменьшением (inSampleSize), чтобы не ловить OOM
 * на широких PNG и чтобы {@link BitmapFactory#decodeStream(InputStream)} не возвращал {@code null}
 * из‑за особенностей потока.
 *
 * <p>Использование: передайте путь так же, как в {@link android.content.res.AssetManager#open(String)},
 * например {@code "img/Day 1/top_revard.png"}.
 */
public final class AssetBitmapLoader {

    private static final int BUFFER_SIZE = 8192;

    private AssetBitmapLoader() {
    }

    /**
     * Декодирует картинку из assets, ограничивая большую сторону примерно {@code maxSidePx} пикселями.
     *
     * @param context    контекст приложения
     * @param assetPath  путь относительно {@code assets/}
     * @param maxSidePx  целевой максимум для большей стороны (ширина или высота)
     * @return bitmap или {@code null}, если декодер не смог разобрать файл
     */
    public static Bitmap decodeSampled(Context context, String assetPath, int maxSidePx) throws IOException {
        byte[] data = readAssetFully(context, assetPath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = computeSampleSize(options, maxSidePx);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    private static byte[] readAssetFully(Context context, String assetPath) throws IOException {
        try (InputStream in = context.getAssets().open(assetPath);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            return out.toByteArray();
        }
    }

    private static int computeSampleSize(BitmapFactory.Options options, int maxSidePx) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= 0 || width <= 0 || maxSidePx <= 0) {
            return 1;
        }
        int longest = Math.max(height, width);
        int inSampleSize = 1;
        while (longest / inSampleSize > maxSidePx) {
            inSampleSize *= 2;
        }
        return Math.max(1, inSampleSize);
    }
}

package com.evo.points;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.evo.points.calculator.EvoCalculatorCore;
import com.evo.points.calculator.days.Day2Equipments;
import com.evo.points.calculator.days.Day3Camp;
import com.evo.points.model.Reward;
import com.evo.points.util.AssetBitmapLoader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный экран: выбор дня, ввод ресурсов, расчёт очков и показ наград из {@code assets/img}.
 *
 * <p>Сохранение ввода реализовано через {@link EvoDataViewModel} – данные переживают
 * смену дня, поворот экрана и переключение между фрагментами/активностями.
 */
public class MainActivity extends AppCompatActivity {
    private LinearLayout rewardsImagesContainer;
    private static final String TAG = "EvoCalc";
    private static final int DAY_COUNT = 7;
    private static final int MAX_TOP_REWARD_IMAGE_SIDE_PX = 2048;

    // ViewModel для хранения введённых данных
    private EvoDataViewModel viewModel;

    private Spinner daySpinner;
    private LinearLayout inputContainer;
    private MaterialButton calculateButton;
    private MaterialCardView resultCard;
    private TextView resultText;

    // Аккордеон 1: обычные награды
    private MaterialCardView rewardsAccordionCard;
    private LinearLayout rewardsAccordionHeader;
    private LinearLayout rewardsAccordionContent;
    private ImageView rewardsAccordionIcon;
    private TextView pointsText;
    private TextView day6ProbabilitiesText;

    // Аккордеон 2: топ награда
    private MaterialCardView topRewardAccordionCard;
    private LinearLayout topRewardAccordionHeader;
    private LinearLayout topRewardAccordionContent;
    private ImageView topRewardAccordionIcon;
    private ImageView topRewardImage;

    private MaterialButton donateYoomoneyButton;
    private MaterialButton donateSberButton;
    private MaterialButton clearAllButton;

    private int selectedDay = 0;
    private boolean isRewardsAccordionExpanded = false;
    private boolean isTopRewardAccordionExpanded = false;
    private final List<EditText> inputFields = new ArrayList<>();
    private List<DayUiConfig> dayConfigs;

    // Интерфейсы для конфигурации дней
    private interface DayPointsCalculator {
        int calculate(List<Integer> values);
    }

    private interface DayRewardsProvider {
        List<Reward> getRewards(int points);
    }

    private static class DayUiConfig {
        private final String resultLabel;
        private final String[] inputHints;
        private final DayPointsCalculator pointsCalculator;
        private final DayRewardsProvider rewardsProvider;
        private final boolean showDay6Probabilities;
        private final int greenBoxesIndex;
        private final int blueBoxesIndex;

        private DayUiConfig(String resultLabel,
                            String[] inputHints,
                            DayPointsCalculator pointsCalculator,
                            DayRewardsProvider rewardsProvider,
                            boolean showDay6Probabilities,
                            int greenBoxesIndex,
                            int blueBoxesIndex) {
            this.resultLabel = resultLabel;
            this.inputHints = inputHints;
            this.pointsCalculator = pointsCalculator;
            this.rewardsProvider = rewardsProvider;
            this.showDay6Probabilities = showDay6Probabilities;
            this.greenBoxesIndex = greenBoxesIndex;
            this.blueBoxesIndex = blueBoxesIndex;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация ViewModel (после setContentView)
        viewModel = new ViewModelProvider(this).get(EvoDataViewModel.class);

        // Привязка View
        daySpinner = findViewById(R.id.daySpinner);
        inputContainer = findViewById(R.id.inputContainer);
        calculateButton = findViewById(R.id.calculateButton);
        clearAllButton = findViewById(R.id.clearAllButton);
        resultCard = findViewById(R.id.resultCard);
        resultText = findViewById(R.id.resultText);

        // Аккордеон 1: обычные награды
        rewardsAccordionCard = findViewById(R.id.rewardsAccordionCard);
        rewardsAccordionHeader = findViewById(R.id.rewardsAccordionHeader);
        rewardsAccordionContent = findViewById(R.id.rewardsAccordionContent);
        rewardsAccordionIcon = findViewById(R.id.rewardsAccordionIcon);
        pointsText = findViewById(R.id.pointsText);
        day6ProbabilitiesText = findViewById(R.id.day6ProbabilitiesText);

        // 🔥 Новый контейнер для наград (вместо RecyclerView)
        rewardsImagesContainer = findViewById(R.id.rewardsImagesContainer);

        // Аккордеон 2: топ награда
        topRewardAccordionCard = findViewById(R.id.topRewardAccordionCard);
        topRewardAccordionHeader = findViewById(R.id.topRewardAccordionHeader);
        topRewardAccordionContent = findViewById(R.id.topRewardAccordionContent);
        topRewardAccordionIcon = findViewById(R.id.topRewardAccordionIcon);
        topRewardImage = findViewById(R.id.topRewardImage);

        donateYoomoneyButton = findViewById(R.id.donateYoomoneyButton);
        donateSberButton = findViewById(R.id.donateSberButton);

        // Создаём конфигурации дней
        dayConfigs = createDayConfigs();

        // Обработчики аккордеонов
        rewardsAccordionHeader.setOnClickListener(v -> toggleRewardsAccordion());
        topRewardAccordionHeader.setOnClickListener(v -> toggleTopRewardAccordion());

        // Настройка Spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_item, android.R.id.text1, getResources().getStringArray(R.array.days_array)) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.kanagawa_text, null));
                return view;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.kanagawa_text, null));
                textView.setBackgroundColor(getResources().getColor(R.color.kanagawa_surface, null));
                return view;
            }
        };
        daySpinner.setAdapter(adapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedDay) return;
                persistDraftForDayIndex(selectedDay);
                clearResultUi();
                selectedDay = position;
                Log.i(TAG, "Выбран день: " + (selectedDay + 1));
                createInputFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        calculateButton.setOnClickListener(v -> calculatePoints());
        clearAllButton.setOnClickListener(v -> showClearConfirmDialog());

        donateYoomoneyButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.yoomoney_link)));
            startActivity(intent);
        });
        donateSberButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.sber_link)));
            startActivity(intent);
        });

        // Создаём поля ввода для начального дня
        createInputFields();
    }

    // ==================== Работа с черновиками через ViewModel ====================

    /**
     * Сохраняет текущие значения полей для указанного дня в ViewModel.
     */
    private void persistDraftForDayIndex(int dayIndex) {
        if (dayIndex < 0 || dayIndex >= DAY_COUNT || inputFields.isEmpty()) return;
        List<String> snapshot = new ArrayList<>(inputFields.size());
        for (EditText et : inputFields) {
            snapshot.add(et.getText().toString());
        }
        viewModel.getDayInputs().put(dayIndex, snapshot);
        Log.d(TAG, "Сохранили день " + (dayIndex + 1) + ": " + snapshot);
    }

    /**
     * Восстанавливает значения полей для текущего дня из ViewModel.
     */
    private void applyDraftForCurrentDay() {
        List<String> draft = viewModel.getDayInputs().get(selectedDay);
        if (draft == null || draft.size() != inputFields.size()) {
            Log.d(TAG, "Нет сохранённых данных для дня " + (selectedDay + 1));
            return;
        }
        for (int i = 0; i < draft.size(); i++) {
            inputFields.get(i).setText(draft.get(i));
        }
        Log.d(TAG, "Восстановили день " + (selectedDay + 1) + ": " + draft);

        // Автоматический расчёт, если все поля заполнены
        boolean allFilled = true;
        for (EditText et : inputFields) {
            if (et.getText().toString().trim().isEmpty()) {
                allFilled = false;
                break;
            }
        }
        if (allFilled) {
            calculatePoints();
        }
    }

    /**
     * Полная очистка всех сохранённых данных.
     */
    private void clearAllInputDrafts() {
        viewModel.getDayInputs().clear();
        viewModel.getDayPoints().clear();
        Log.d(TAG, "Все черновики очищены");
    }

    // ==================== UI методы ====================

    private void showClearConfirmDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(R.string.clear_all_confirm_title)
                .setMessage(R.string.clear_all_confirm_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> clearAllData())
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void clearAllData() {
        clearAllInputDrafts();
        for (EditText et : inputFields) {
            et.setText("");
        }
        clearResultUi();
    }

    private void clearResultUi() {
        resultCard.setVisibility(View.GONE);
        resultText.setText("");
        pointsText.setText("Количество очков: 0");
        day6ProbabilitiesText.setVisibility(View.GONE);

        // 🔥 Очищаем контейнер с наградами вместо адаптера
        if (rewardsImagesContainer != null) {
            rewardsImagesContainer.removeAllViews();
        }
        topRewardImage.setImageDrawable(null);

        isRewardsAccordionExpanded = false;
        isTopRewardAccordionExpanded = false;
        rewardsAccordionContent.setVisibility(View.GONE);
        topRewardAccordionContent.setVisibility(View.GONE);
        rewardsAccordionIcon.setImageResource(R.drawable.ic_expand_more);
        topRewardAccordionIcon.setImageResource(R.drawable.ic_expand_more);
        rewardsAccordionCard.setVisibility(View.GONE);
        topRewardAccordionCard.setVisibility(View.GONE);
    }

    private void toggleRewardsAccordion() {
        isRewardsAccordionExpanded = !isRewardsAccordionExpanded;
        rewardsAccordionContent.setVisibility(isRewardsAccordionExpanded ? View.VISIBLE : View.GONE);
        rewardsAccordionIcon.setImageResource(isRewardsAccordionExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
    }

    private void toggleTopRewardAccordion() {
        isTopRewardAccordionExpanded = !isTopRewardAccordionExpanded;
        topRewardAccordionContent.setVisibility(isTopRewardAccordionExpanded ? View.VISIBLE : View.GONE);
        topRewardAccordionIcon.setImageResource(isTopRewardAccordionExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
    }

    private void createInputFields() {
        inputContainer.removeAllViews();
        inputFields.clear();

        // Сброс аккордеонов
        rewardsAccordionCard.setVisibility(View.GONE);
        topRewardAccordionCard.setVisibility(View.GONE);
        isRewardsAccordionExpanded = false;
        isTopRewardAccordionExpanded = false;
        rewardsAccordionContent.setVisibility(View.GONE);
        topRewardAccordionContent.setVisibility(View.GONE);
        rewardsAccordionIcon.setImageResource(R.drawable.ic_expand_more);
        topRewardAccordionIcon.setImageResource(R.drawable.ic_expand_more);

        DayUiConfig config = getCurrentDayConfig();
        for (String hint : config.inputHints) {
            EditText editText = new EditText(this);
            editText.setId(View.generateViewId());
            editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            editText.setHint(hint);
            editText.setTextColor(getResources().getColor(R.color.kanagawa_text, null));
            editText.setHintTextColor(getResources().getColor(R.color.kanagawa_text_dim, null));
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.kanagawa_accent, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 12);
            editText.setLayoutParams(params);

            inputContainer.addView(editText);
            inputFields.add(editText);
        }

        // Восстанавливаем сохранённые значения для этого дня
        applyDraftForCurrentDay();
    }

    private int getValue(EditText edit) {
        if (edit == null) return 0;
        String val = edit.getText().toString().trim();
        if (val.isEmpty()) return 0;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void calculatePoints() {
        DayUiConfig config = getCurrentDayConfig();
        List<Integer> values = getInputValues();
        Log.d(TAG, "Входные значения дня " + (selectedDay + 1) + ": " + values);

        int totalPoints = config.pointsCalculator.calculate(values);
        List<Reward> allRewards = config.rewardsProvider.getRewards(totalPoints);
        List<Reward> normalRewards = getNormalRewards(allRewards);
        Reward topReward = getTopReward(allRewards);

        // Сохраняем рассчитанные очки в ViewModel (опционально)
        viewModel.getDayPoints().put(selectedDay, totalPoints);

        Log.i(TAG, "Расчёт: day=" + (selectedDay + 1) + ", points=" + totalPoints +
                ", normalRewards=" + normalRewards.size() + ", hasTop=" + (topReward != null));

        resultText.setText(config.resultLabel + ": " + totalPoints + " очков");
        pointsText.setText("Количество очков: " + totalPoints);

        // Вероятности для дня 6
        if (config.showDay6Probabilities) {
            int greenBoxes = getValueByIndex(values, config.greenBoxesIndex);
            int blueBoxes = getValueByIndex(values, config.blueBoxesIndex);
            day6ProbabilitiesText.setText(getDay6ProbabilitiesText(greenBoxes, blueBoxes));
            day6ProbabilitiesText.setVisibility(View.VISIBLE);
        } else {
            day6ProbabilitiesText.setVisibility(View.GONE);
        }

        // Отображение аккордеона с обычными наградами
        if (normalRewards != null && !normalRewards.isEmpty()) {
            displayRewardsInContainer(normalRewards); // 🔥 новый метод
            rewardsAccordionCard.setVisibility(View.VISIBLE);
        } else {
            rewardsImagesContainer.removeAllViews();
            rewardsImagesContainer.setVisibility(View.GONE);
            rewardsAccordionCard.setVisibility(View.GONE);
        }

        // Топ награда
        if (topReward != null && topReward.hasScreenshot()) {
            String topPath = topReward.getScreenshotPath();
            try {
                android.graphics.Bitmap bitmap = AssetBitmapLoader.decodeSampled(
                        this, topPath, MAX_TOP_REWARD_IMAGE_SIDE_PX);
                if (bitmap != null) {
                    topRewardImage.setImageBitmap(bitmap);
                    topRewardAccordionCard.setVisibility(View.VISIBLE);
                } else {
                    topRewardAccordionCard.setVisibility(View.GONE);
                }
            } catch (IOException e) {
                Log.e(TAG, "Ошибка загрузки топ-награды: " + topPath, e);
                topRewardAccordionCard.setVisibility(View.GONE);
            }
        } else {
            topRewardAccordionCard.setVisibility(View.GONE);
        }

        resultCard.setVisibility(View.VISIBLE);
        // Сохраняем текущий ввод после расчёта
        persistDraftForDayIndex(selectedDay);
    }

    // ==================== Вспомогательные методы ====================

    private DayUiConfig getCurrentDayConfig() {
        if (selectedDay < 0 || selectedDay >= dayConfigs.size()) return dayConfigs.get(0);
        return dayConfigs.get(selectedDay);
    }

    private List<Integer> getInputValues() {
        List<Integer> values = new ArrayList<>(inputFields.size());
        for (EditText et : inputFields) values.add(getValue(et));
        return values;
    }

    private int getValueByIndex(List<Integer> values, int index) {
        return (index >= 0 && index < values.size()) ? values.get(index) : 0;
    }

    private List<DayUiConfig> createDayConfigs() {
        List<DayUiConfig> configs = new ArrayList<>();
        configs.add(new DayUiConfig("День 1 (Карты эволюции)",
                new String[]{"Карты эволюции"},
                values -> EvoCalculatorCore.calculateDay1(getValueByIndex(values, 0)),
                EvoCalculatorCore::getDay1Rewards, false, -1, -1));
        configs.add(new DayUiConfig("День 2 (Экипировка)",
                new String[]{"Билеты на экипировку", "Пополнения"},
                values -> Day2Equipments.calculatePoints(getValueByIndex(values, 0), getValueByIndex(values, 1)),
                Day2Equipments::getReward, false, -1, -1));
        configs.add(new DayUiConfig("День 3 (Лагерь)",
                new String[]{"Сталь", "Энергия", "Ускорения", "Техноядро (бой)", "Техноядро (развитие)", "Пополнения"},
                values -> Day3Camp.calculatePoints(getValueByIndex(values, 0), getValueByIndex(values, 1),
                        getValueByIndex(values, 2), getValueByIndex(values, 3), getValueByIndex(values, 4),
                        getValueByIndex(values, 5)),
                Day3Camp::getRewards, false, -1, -1));
        configs.add(new DayUiConfig("День 4 (Чертежи)",
                new String[]{"Обычные модули", "Продвинутые модули", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay4(getValueByIndex(values, 0), getValueByIndex(values, 1),
                        getValueByIndex(values, 2)),
                EvoCalculatorCore::getDay4Rewards, false, -1, -1));
        configs.add(new DayUiConfig("День 5 (Невролинк)",
                new String[]{"Чипы синаптического усиления", "Нейрокодировщик", "Кортикальный имплант", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay5(getValueByIndex(values, 0), getValueByIndex(values, 1),
                        getValueByIndex(values, 2), getValueByIndex(values, 3)),
                EvoCalculatorCore::getDay5Rewards, false, -1, -1));
        configs.add(new DayUiConfig("День 6 (Оружие/Акс.)",
                new String[]{"Билеты розыгрыша оружия", "Зелёные ящики", "Синие ящики", "Фиолетовые ящики", "Жёлтые ящики", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay6(getValueByIndex(values, 0), getValueByIndex(values, 1),
                        getValueByIndex(values, 2), getValueByIndex(values, 3), getValueByIndex(values, 4),
                        getValueByIndex(values, 5)),
                EvoCalculatorCore::getDay6Rewards, true, 1, 2));
        configs.add(new DayUiConfig("День 7 (Пополнение)",
                new String[]{"Пополнения"},
                values -> EvoCalculatorCore.calculateDay7(getValueByIndex(values, 0)),
                EvoCalculatorCore::getDay7Rewards, false, -1, -1));
        return configs;
    }

    private String getDay6ProbabilitiesText(int greenBoxes, int blueBoxes) {
        double expectedBlueFromGreen = greenBoxes * 0.05;
        double expectedVioletFromBlue = blueBoxes * 0.04;
        int minBlue = (int) Math.floor(expectedBlueFromGreen * 0.5);
        int maxBlue = (int) Math.ceil(expectedBlueFromGreen * 1.5);
        int minViolet = (int) Math.floor(expectedVioletFromBlue * 0.5);
        int maxViolet = (int) Math.ceil(expectedVioletFromBlue * 1.5);
        int blueCount = (int) Math.round(expectedBlueFromGreen);
        int violetCount = (int) Math.round(expectedVioletFromBlue);

        StringBuilder sb = new StringBuilder();
        sb.append("🎲 ДОПОЛНИТЕЛЬНО ВЫ МОЖЕТЕ ПОЛУЧИТЬ ПРИ ОТКРЫТИИ СУНДУКОВ:\n");
        sb.append("Приблизительный расчёт (ожидаемое количество, диапазон с учётом случайности):\n");
        sb.append("  🔵 Синих сундуков из зелёных (5%): ~").append(blueCount)
                .append(" (от ").append(minBlue).append(" до ").append(maxBlue).append(")\n");
        sb.append("  🟣 Фиолетовых сундуков из синих (4%): ~").append(violetCount)
                .append(" (от ").append(minViolet).append(" до ").append(maxViolet).append(")\n");
        int potentialPoints = blueCount * 30 + violetCount * 250;
        int minPotentialPoints = minBlue * 30 + minViolet * 250;
        int maxPotentialPoints = maxBlue * 30 + maxViolet * 250;
        sb.append("\n💎 Потенциально дополнительных очков: ~").append(potentialPoints)
                .append(" (от ").append(minPotentialPoints).append(" до ").append(maxPotentialPoints).append(")");
        return sb.toString();
    }

    private List<Reward> getNormalRewards(List<Reward> allRewards) {
        List<Reward> normal = new ArrayList<>();
        for (Reward r : allRewards) if (!r.isTopReward()) normal.add(r);
        return normal;
    }

    private Reward getTopReward(List<Reward> allRewards) {
        for (Reward r : allRewards) if (r.isTopReward()) return r;
        return null;
    }

    /**
     * Отображает список наград как ImageView в LinearLayout.
     * Размер контролируется через res/values/dimens.xml (в процентах).
     */
    private void displayRewardsInContainer(List<Reward> rewards) {
        rewardsImagesContainer.removeAllViews();

        if (rewards == null || rewards.isEmpty()) {
            rewardsImagesContainer.setVisibility(View.GONE);
            return;
        }

        rewardsImagesContainer.setVisibility(View.VISIBLE);

        //  Читаем настройки из dimens.xml
        // Делим на 100.0f, чтобы получить коэффициент: 50 → 0.5
        final float SCALE_FACTOR = getResources().getInteger(R.integer.reward_image_scale_percent) / 100.0f;

        final int BASE_SIZE = getResources().getInteger(R.integer.reward_decode_base_size);
        final int DECODE_TARGET_SIZE = (int) (BASE_SIZE * SCALE_FACTOR);
        final int MARGIN_BOTTOM = getResources().getDimensionPixelSize(R.dimen.reward_image_margin_bottom);

        for (Reward reward : rewards) {
            if (!reward.hasScreenshot()) continue;

            ImageView imageView = new ImageView(this);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            // Ширина: если масштаб < 1.0, то пропорционально экрану
            int layoutWidth = SCALE_FACTOR >= 1.0f
                    ? LinearLayout.LayoutParams.MATCH_PARENT
                    : (int) (getResources().getDisplayMetrics().widthPixels * SCALE_FACTOR);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    layoutWidth,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = android.view.Gravity.CENTER_HORIZONTAL;
            params.setMargins(0, 0, 0, MARGIN_BOTTOM);
            imageView.setLayoutParams(params);

            String path = reward.getScreenshotPath();

            try {
                android.graphics.Bitmap bitmap = AssetBitmapLoader.decodeSampled(
                        this, path, DECODE_TARGET_SIZE);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                Log.e(TAG, "❌ Error loading: " + path, e);
            }

            rewardsImagesContainer.addView(imageView);
        }
    }
}
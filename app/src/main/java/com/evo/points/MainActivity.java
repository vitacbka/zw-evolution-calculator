package com.evo.points;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evo.points.adapter.RewardAdapter;
import com.evo.points.calculator.EvoCalculatorCore;
import com.evo.points.model.Reward;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
    private RecyclerView rewardsRecyclerView;
    private RewardAdapter rewardAdapter;
    
    // Аккордеон 2: топ награда
    private MaterialCardView topRewardAccordionCard;
    private LinearLayout topRewardAccordionHeader;
    private LinearLayout topRewardAccordionContent;
    private ImageView topRewardAccordionIcon;
    private ImageView topRewardImage;
    
    private MaterialButton donateYoomoneyButton;
    private MaterialButton donateSberButton;

    private int selectedDay = 0;
    private boolean isRewardsAccordionExpanded = false;
    private boolean isTopRewardAccordionExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daySpinner = findViewById(R.id.daySpinner);
        inputContainer = findViewById(R.id.inputContainer);
        calculateButton = findViewById(R.id.calculateButton);
        resultCard = findViewById(R.id.resultCard);
        resultText = findViewById(R.id.resultText);
        
        // Аккордеон 1
        rewardsAccordionCard = findViewById(R.id.rewardsAccordionCard);
        rewardsAccordionHeader = findViewById(R.id.rewardsAccordionHeader);
        rewardsAccordionContent = findViewById(R.id.rewardsAccordionContent);
        rewardsAccordionIcon = findViewById(R.id.rewardsAccordionIcon);
        pointsText = findViewById(R.id.pointsText);
        day6ProbabilitiesText = findViewById(R.id.day6ProbabilitiesText);
        rewardsRecyclerView = findViewById(R.id.rewardsRecyclerView);
        
        // Аккордеон 2
        topRewardAccordionCard = findViewById(R.id.topRewardAccordionCard);
        topRewardAccordionHeader = findViewById(R.id.topRewardAccordionHeader);
        topRewardAccordionContent = findViewById(R.id.topRewardAccordionContent);
        topRewardAccordionIcon = findViewById(R.id.topRewardAccordionIcon);
        topRewardImage = findViewById(R.id.topRewardImage);
        
        donateYoomoneyButton = findViewById(R.id.donateYoomoneyButton);
        donateSberButton = findViewById(R.id.donateSberButton);

        // Настройка RecyclerView для наград
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rewardAdapter = new RewardAdapter(this);
        rewardsRecyclerView.setAdapter(rewardAdapter);

        // Обработчик клика на аккордеон 1
        rewardsAccordionHeader.setOnClickListener(v -> toggleRewardsAccordion());

        // Обработчик клика на аккордеон 2
        topRewardAccordionHeader.setOnClickListener(v -> toggleTopRewardAccordion());

        // Настройка Spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                R.layout.spinner_item, android.R.id.text1, getResources().getStringArray(R.array.days_array)) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.kanagawa_text, null));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
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
                selectedDay = position;
                createInputFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        calculateButton.setOnClickListener(v -> calculatePoints());

        // Кнопки доната
        donateYoomoneyButton.setOnClickListener(v -> {
            String url = getString(R.string.yoomoney_link);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        donateSberButton.setOnClickListener(v -> {
            String url = getString(R.string.sber_link);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        createInputFields();
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
        
        // Сброс аккордеонов
        rewardsAccordionCard.setVisibility(View.GONE);
        topRewardAccordionCard.setVisibility(View.GONE);
        isRewardsAccordionExpanded = false;
        isTopRewardAccordionExpanded = false;
        rewardsAccordionContent.setVisibility(View.GONE);
        topRewardAccordionContent.setVisibility(View.GONE);
        rewardsAccordionIcon.setImageResource(R.drawable.ic_expand_more);
        topRewardAccordionIcon.setImageResource(R.drawable.ic_expand_more);

        String[] inputs = getInputsForDay(selectedDay);
        String[] hints = getHintsForDay(selectedDay);

        for (int i = 0; i < inputs.length; i++) {
            EditText editText = new EditText(this);
            editText.setId(View.generateViewId());
            editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            editText.setHint(hints[i]);
            editText.setTextColor(getResources().getColor(R.color.kanagawa_text, null));
            editText.setHintTextColor(getResources().getColor(R.color.kanagawa_text_dim, null));
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.kanagawa_accent, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 12);
            editText.setLayoutParams(params);

            inputContainer.addView(editText);
        }
    }

    private String[] getInputsForDay(int day) {
        switch (day) {
            case 0: // День 1 - Энергия
                return new String[]{"energy", "donate"};
            case 1: // День 2 - Экипировка
                return new String[]{"tickets", "donate"};
            case 2: // День 3 - Лагерь
                return new String[]{"steel", "energy", "boost", "battleCore", "devCore", "donate"};
            case 3: // День 4 - Чертежи
                return new String[]{"commonModules", "advancedModules", "donate"};
            case 4: // День 5 - Невролинк
                return new String[]{"synapticChips", "neuroCoder", "corticalImplant", "donate"};
            case 5: // День 6 - Оружие/Акс.
                return new String[]{"weaponTickets", "greenBoxes", "blueBoxes", "violetBoxes", "yellowBoxes", "donate"};
            case 6: // День 7 - Пополнение
                return new String[]{"donate"};
            default:
                return new String[]{};
        }
    }

    private String[] getHintsForDay(int day) {
        switch (day) {
            case 0:
                return new String[]{"Количество энергии", "Пополнения"};
            case 1:
                return new String[]{"Билеты на экипировку", "Пополнения"};
            case 2:
                return new String[]{"Сталь", "Энергия", "Ускорения", "Техноядро (бой)", "Техноядро (развитие)", "Пополнения"};
            case 3:
                return new String[]{"Обычные модули", "Продвинутые модули", "Пополнения"};
            case 4:
                return new String[]{"Чипы синаптического усиления", "Нейрокодировщик", "Кортикальный имплант", "Пополнения"};
            case 5:
                return new String[]{"Билеты розыгрыша оружия", "Зелёные ящики", "Синие ящики", "Фиолетовые ящики", "Жёлтые ящики", "Пополнения"};
            case 6:
                return new String[]{"Пополнения"};
            default:
                return new String[]{};
        }
    }

    private int getValue(int id) {
        EditText edit = findViewById(id);
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
        int totalPoints = 0;
        StringBuilder result = new StringBuilder();
        List<Reward> normalRewards = new ArrayList<>();
        Reward topReward = null;
        int greenBoxes = 0;
        int blueBoxes = 0;

        switch (selectedDay) {
            case 0: // День 1
                totalPoints = calculateDay1();
                result.append("День 1 (Энергия): ").append(totalPoints).append(" очков");
                normalRewards = getNormalRewards(EvoCalculatorCore.getDay1Rewards(totalPoints));
                topReward = getTopReward(EvoCalculatorCore.getDay1Rewards(totalPoints));
                break;
            case 1: // День 2
                totalPoints = calculateDay2();
                result.append("День 2 (Экипировка): ").append(totalPoints).append(" очков");
                normalRewards = getNormalRewards(EvoCalculatorCore.getDay2Rewards(totalPoints));
                topReward = getTopReward(EvoCalculatorCore.getDay2Rewards(totalPoints));
                break;
            case 2: // День 3
                totalPoints = calculateDay3();
                result.append("День 3 (Лагерь): ").append(totalPoints).append(" очков");
                normalRewards = getNormalRewards(EvoCalculatorCore.getDay3Rewards(totalPoints));
                topReward = getTopReward(EvoCalculatorCore.getDay3Rewards(totalPoints));
                break;
            case 3: // День 4
                totalPoints = calculateDay4();
                result.append("День 4 (Чертежи): ").append(totalPoints).append(" очков");
                normalRewards = getNormalRewards(EvoCalculatorCore.getDay4Rewards(totalPoints));
                topReward = getTopReward(EvoCalculatorCore.getDay4Rewards(totalPoints));
                break;
            case 4: // День 5
                totalPoints = calculateDay5();
                result.append("День 5 (Невролинк): ").append(totalPoints).append(" очков");
                normalRewards = getNormalRewards(EvoCalculatorCore.getDay5Rewards(totalPoints));
                topReward = getTopReward(EvoCalculatorCore.getDay5Rewards(totalPoints));
                break;
            case 5: // День 6
                totalPoints = calculateDay6();
                result.append("День 6 (Оружие/Акс.): ").append(totalPoints).append(" очков");
                greenBoxes = getValue(inputContainer.getChildAt(1).getId());
                blueBoxes = getValue(inputContainer.getChildAt(2).getId());
                normalRewards = getNormalRewards(EvoCalculatorCore.getDay6Rewards(totalPoints));
                topReward = getTopReward(EvoCalculatorCore.getDay6Rewards(totalPoints));
                break;
            case 6: // День 7
                totalPoints = calculateDay7();
                result.append("День 7 (Пополнение): ").append(totalPoints).append(" очков");
                topReward = getTopReward(EvoCalculatorCore.getDay7Rewards(totalPoints));
                break;
        }

        resultText.setText(result.toString());

        // Обновляем количество очков
        pointsText.setText("Количество очков: " + totalPoints);

        // Показываем вероятности для Дня 6
        if (selectedDay == 5) { // День 6
            String probabilities = getDay6ProbabilitiesText(greenBoxes, blueBoxes);
            day6ProbabilitiesText.setText(probabilities);
            day6ProbabilitiesText.setVisibility(View.VISIBLE);
        } else {
            day6ProbabilitiesText.setVisibility(View.GONE);
        }

        // Отображение аккордеона с обычными наградами
        if (normalRewards != null && !normalRewards.isEmpty()) {
            rewardAdapter.setRewards(normalRewards);
            rewardsAccordionCard.setVisibility(View.VISIBLE);
        } else {
            rewardsAccordionCard.setVisibility(View.GONE);
        }

        // Отображение аккордеона с топ наградой (всегда показывается для дней 1-6, для дня 7 только топ)
        if (topReward != null && topReward.hasScreenshot()) {
            String topPath = topReward.getScreenshotPath();
            
            // Загружаем из assets
            try {
                android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(
                    getAssets().open(topPath)
                );
                topRewardImage.setImageBitmap(bitmap);
                topRewardAccordionCard.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
                topRewardAccordionCard.setVisibility(View.GONE);
            }
        }

        resultCard.setVisibility(View.VISIBLE);
    }

    /**
     * Текст с вероятностями для Дня 6
     */
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

    /**
     * Фильтрует обычные награды (не топ)
     */
    private List<Reward> getNormalRewards(List<Reward> allRewards) {
        List<Reward> normal = new ArrayList<>();
        for (Reward r : allRewards) {
            if (!r.isTopReward()) {
                normal.add(r);
            }
        }
        return normal;
    }

    /**
     * Находит топ награду
     */
    private Reward getTopReward(List<Reward> allRewards) {
        for (Reward r : allRewards) {
            if (r.isTopReward()) {
                return r;
            }
        }
        return null;
    }

    private int calculateDay1() {
        int energy = getValue(inputContainer.getChildAt(0).getId());
        int donate = getValue(inputContainer.getChildAt(1).getId());
        return energy * 30 + donate * 3;
    }

    private int calculateDay2() {
        int tickets = getValue(inputContainer.getChildAt(0).getId());
        int donate = getValue(inputContainer.getChildAt(1).getId());
        return tickets * 300 + donate * 3;
    }

    private int calculateDay3() {
        int steel = getValue(inputContainer.getChildAt(0).getId());
        int energy = getValue(inputContainer.getChildAt(1).getId());
        int boost = getValue(inputContainer.getChildAt(2).getId());
        int battleCore = getValue(inputContainer.getChildAt(3).getId());
        int devCore = getValue(inputContainer.getChildAt(4).getId());
        int donate = getValue(inputContainer.getChildAt(5).getId());
        return (steel / 200) + (energy / 200) + boost + (battleCore * 500) + (devCore * 500) + donate * 3;
    }

    private int calculateDay4() {
        int common = getValue(inputContainer.getChildAt(0).getId());
        int advanced = getValue(inputContainer.getChildAt(1).getId());
        int donate = getValue(inputContainer.getChildAt(2).getId());
        return common * 30 + advanced * 810 + donate * 3;
    }

    private int calculateDay5() {
        int synapticChips = getValue(inputContainer.getChildAt(0).getId());
        int neuroCoder = getValue(inputContainer.getChildAt(1).getId());
        int corticalImplant = getValue(inputContainer.getChildAt(2).getId());
        int donate = getValue(inputContainer.getChildAt(3).getId());
        return synapticChips * 5 + neuroCoder * 10 + corticalImplant * 2000 + donate * 3;
    }

    private int calculateDay6() {
        int weaponTickets = getValue(inputContainer.getChildAt(0).getId());
        int greenBoxes = getValue(inputContainer.getChildAt(1).getId());
        int blueBoxes = getValue(inputContainer.getChildAt(2).getId());
        int violetBoxes = getValue(inputContainer.getChildAt(3).getId());
        int yellowBoxes = getValue(inputContainer.getChildAt(4).getId());
        int donate = getValue(inputContainer.getChildAt(5).getId());
        return weaponTickets * 120 + greenBoxes * 10 + blueBoxes * 30 + violetBoxes * 250 + yellowBoxes * 2500 + donate * 3;
    }

    private int calculateDay7() {
        int donate = getValue(inputContainer.getChildAt(0).getId());
        return donate * 6;
    }
}

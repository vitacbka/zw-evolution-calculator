package com.evo.points;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private Spinner daySpinner;
    private LinearLayout inputContainer;
    private MaterialButton calculateButton;
    private MaterialCardView resultCard;
    private TextView resultText;
    private TextView rewardsText;
    private MaterialButton donateYoomoneyButton;
    private MaterialButton donateSberButton;

    private int selectedDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daySpinner = findViewById(R.id.daySpinner);
        inputContainer = findViewById(R.id.inputContainer);
        calculateButton = findViewById(R.id.calculateButton);
        resultCard = findViewById(R.id.resultCard);
        resultText = findViewById(R.id.resultText);
        rewardsText = findViewById(R.id.rewardsText);
        donateYoomoneyButton = findViewById(R.id.donateYoomoneyButton);
        donateSberButton = findViewById(R.id.donateSberButton);

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

    private void createInputFields() {
        inputContainer.removeAllViews();
        resultCard.setVisibility(View.GONE);

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
        StringBuilder rewards = new StringBuilder();

        switch (selectedDay) {
            case 0: // День 1
                totalPoints = calculateDay1();
                result.append("День 1 (Энергия): ").append(totalPoints).append(" очков");
                rewards.append(getDay1Rewards(totalPoints));
                break;
            case 1: // День 2
                totalPoints = calculateDay2();
                result.append("День 2 (Экипировка): ").append(totalPoints).append(" очков");
                rewards.append(getDay2Rewards(totalPoints));
                break;
            case 2: // День 3
                totalPoints = calculateDay3();
                result.append("День 3 (Лагерь): ").append(totalPoints).append(" очков");
                rewards.append(getDay3Rewards(totalPoints));
                break;
            case 3: // День 4
                totalPoints = calculateDay4();
                result.append("День 4 (Чертежи): ").append(totalPoints).append(" очков");
                rewards.append(getDay4Rewards(totalPoints));
                break;
            case 4: // День 5
                totalPoints = calculateDay5();
                result.append("День 5 (Невролинк): ").append(totalPoints).append(" очков");
                rewards.append(getDay5Rewards(totalPoints));
                break;
            case 5: // День 6
                totalPoints = calculateDay6();
                result.append("День 6 (Оружие/Акс.): ").append(totalPoints).append(" очков");
                rewards.append(getDay6Rewards(totalPoints));
                rewards.append("\n\n").append(getDay6BoxProbabilities(
                    getValue(inputContainer.getChildAt(1).getId()),
                    getValue(inputContainer.getChildAt(2).getId())
                ));
                break;
            case 6: // День 7
                totalPoints = calculateDay7();
                result.append("День 7 (Пополнение): ").append(totalPoints).append(" очков");
                rewards.append("Награды для дня 7 не предусмотрены");
                break;
        }

        resultText.setText(result.toString());
        
        if (rewards.length() > 0) {
            rewardsText.setText("🎁 НАГРАДЫ:\n" + rewards);
            rewardsText.setVisibility(View.VISIBLE);
        } else {
            rewardsText.setVisibility(View.GONE);
        }

        resultCard.setVisibility(View.VISIBLE);
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

    private String getDay1Rewards(int points) {
        StringBuilder sb = new StringBuilder();
        boolean hasReward = false;

        if (points >= 69000) {
            sb.append("✓ 1 тессеракт\n✓ 10 ваучеров для розыгрыша снаряжения\n✓ 30 чипов\n");
            hasReward = true;
        }
        if (points >= 36000) {
            sb.append("✓ 10 ваучеров для розыгрыша снаряжения\n✓ 20 чипов\n");
            hasReward = true;
        }
        if (points >= 20000) {
            sb.append("✓ 1 тессеракт\n✓ 15 чипов\n");
            hasReward = true;
        }
        if (points >= 9000) {
            sb.append("✓ 10 ваучеров оружейных материалов\n✓ 10 чипов\n");
            hasReward = true;
        }
        if (points >= 3000) {
            sb.append("✓ 100 алмазов\n✓ 5 чипов\n");
            hasReward = true;
        }

        if (!hasReward) {
            sb.append("Недостаточно очков (мин. 3000)");
        }
        return sb.toString();
    }

    private String getDay2Rewards(int points) {
        StringBuilder sb = new StringBuilder();
        boolean hasReward = false;

        if (points >= 68000) {
            sb.append("✓ 1 сундук снаряжения S\n✓ 2 ящика ресурсов лагеря\n");
            hasReward = true;
        }
        if (points >= 48000) {
            sb.append("✓ 1 эпическое снаряжение S\n✓ 1 ящик ресурсов лагеря\n");
            hasReward = true;
        }
        if (points >= 30000) {
            sb.append("✓ 1 обменник снаряжения\n✓ 1 ящик ресурсов лагеря\n");
            hasReward = true;
        }
        if (points >= 15000) {
            sb.append("✓ 1 сундук снаряжения на выбор (эпический)\n✓ 2 тоника силы\n");
            hasReward = true;
        }
        if (points >= 6000) {
            sb.append("✓ 100 алмазов\n✓ 1 тоник силы\n");
            hasReward = true;
        }

        if (!hasReward) {
            sb.append("Недостаточно очков (мин. 6000)");
        }
        return sb.toString();
    }

    private String getDay3Rewards(int points) {
        StringBuilder sb = new StringBuilder();
        boolean hasReward = false;

        if (points >= 74000) {
            sb.append("✓ 1 случайный сундук украшений (легендарный)\n✓ 1 техноядро (бой)\n✓ 100 ваучеров модуля\n");
            hasReward = true;
        }
        if (points >= 45000) {
            sb.append("✓ 1 случайный сундук украшений (эпический)\n✓ 1 техноядро (развитие)\n✓ 80 ваучеров модуля\n");
            hasReward = true;
        }
        if (points >= 30000) {
            sb.append("✓ 1 случайный сундук украшений (эпический)\n✓ 60 ваучеров модуля\n");
            hasReward = true;
        }
        if (points >= 17000) {
            sb.append("✓ 1 техноядро (бой)\n✓ 1 техноядро (развитие)\n✓ 2 ящика ресурсов лагеря\n");
            hasReward = true;
        }
        if (points >= 6000) {
            sb.append("✓ 100 алмазов\n✓ 1 тоник силы\n");
            hasReward = true;
        }

        if (!hasReward) {
            sb.append("Недостаточно очков (мин. 6000)");
        }
        return sb.toString();
    }

    private String getDay4Rewards(int points) {
        StringBuilder sb = new StringBuilder();
        boolean hasReward = false;

        if (points >= 88000) {
            sb.append("✓ 1 Модуль VI лвл\n✓ 1 обменник модулей VI лвл\n✓ 80 ящиков материалов нейросвязи\n");
            hasReward = true;
        }
        if (points >= 56000) {
            sb.append("✓ 1 обменник модулей VI лвл\n✓ 1 Модуль V лвл\n✓ 60 ящиков материалов нейросвязи\n");
            hasReward = true;
        }
        if (points >= 30000) {
            sb.append("✓ 1 Модуль V лвл\n✓ 40 ящиков материалов нейросвязи\n");
            hasReward = true;
        }
        if (points >= 15000) {
            sb.append("✓ 1 продвинутый модуль\n✓ 36 ваучеров модуля\n");
            hasReward = true;
        }
        if (points >= 6000) {
            sb.append("✓ 100 алмазов\n✓ 1 тоник силы\n✓ 10 ваучеров модуля\n");
            hasReward = true;
        }

        if (!hasReward) {
            sb.append("Недостаточно очков (мин. 6000)");
        }
        return sb.toString();
    }

    private String getDay5Rewards(int points) {
        StringBuilder sb = new StringBuilder();
        boolean hasReward = false;

        if (points >= 72000) {
            sb.append("✓ 1 Легендарный кристалл S\n✓ 1 Сундук нейросвязи на выбор\n✓ 10 Ящик припасов с аксами (изысканный)\n");
            hasReward = true;
        }
        if (points >= 48000) {
            sb.append("✓ 1 Сундук нейросвязи на выбор\n✓ 10 Ящик припасов с аксами (изысканный)\n");
            hasReward = true;
        }
        if (points >= 25000) {
            sb.append("✓ 1 кортикальный имплант\n✓ 5 Ящик припасов с аксами (изысканный)\n✓ 30 ящиков с материалами нейросвязи на выбор\n");
            hasReward = true;
        }
        if (points >= 10000) {
            sb.append("✓ 30 ящиков с материалами нейросвязи на выбор\n✓ 30 нейрокодировщиков\n✓ 30 чип синаптического усиления\n");
            hasReward = true;
        }
        if (points >= 5000) {
            sb.append("✓ 100 алмазов\n✓ 20 нейрокодировщиков\n✓ 20 чип синаптического усиления\n");
            hasReward = true;
        }

        if (!hasReward) {
            sb.append("Недостаточно очков (мин. 5000)");
        }
        return sb.toString();
    }

    private String getDay6Rewards(int points) {
        StringBuilder sb = new StringBuilder();
        boolean hasReward = false;

        if (points >= 115000) {
            sb.append("✓ 2 Ящик припасов (легендарный)\n✓ 1 тессеракт\n✓ 4 Блок антиматерии\n✓ 200 Точный компонент\n");
            hasReward = true;
        }
        if (points >= 75000) {
            sb.append("✓ 1 Ящик с аксами S\n✓ 2 Блок антиматерии\n✓ 160 Точный компонент\n");
            hasReward = true;
        }
        if (points >= 45000) {
            sb.append("✓ 2 Блок антиматерии\n✓ 10 Ящик припасов (изысканный)\n✓ 120 Точный компонент\n");
            hasReward = true;
        }
        if (points >= 20000) {
            sb.append("✓ 1 Блок антиматерии\n✓ 10 Ящик припасов (изысканный)\n✓ 100 Точный компонент\n");
            hasReward = true;
        }
        if (points >= 7500) {
            sb.append("✓ 1 Блок антиматерии\n✓ 10 Ящик припасов (продвинутый)\n✓ 100 Точный компонент\n");
            hasReward = true;
        }

        if (!hasReward) {
            sb.append("Недостаточно очков (мин. 7500)");
        }
        return sb.toString();
    }

    private String getDay6BoxProbabilities(int greenBoxes, int blueBoxes) {
        double expectedBlueFromGreen = greenBoxes * 0.05;
        double expectedVioletFromBlue = blueBoxes * 0.04;

        int minBlue = (int) Math.floor(expectedBlueFromGreen * 0.5);
        int maxBlue = (int) Math.ceil(expectedBlueFromGreen * 1.5);
        int minViolet = (int) Math.floor(expectedVioletFromBlue * 0.5);
        int maxViolet = (int) Math.ceil(expectedVioletFromBlue * 1.5);

        int blueCount = (int) Math.round(expectedBlueFromGreen);
        int violetCount = (int) Math.round(expectedVioletFromBlue);

        StringBuilder sb = new StringBuilder();
        sb.append("\n🎲 ДОПОЛНИТЕЛЬНО ВЫ МОЖЕТЕ ПОЛУЧИТЬ ПРИ ОТКРЫТИИ СУНДУКОВ:\n");
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
}

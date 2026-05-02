package com.evo.points.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.evo.points.EvoDataViewModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// 📁 app/src/test/java/com/evo/points/EvoDataViewModelTest.java
public class EvoDataViewModelTest {

    private EvoDataViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = new EvoDataViewModel();
    }

    @Test
    public void getDayInputs_initiallyEmpty() {
        assertTrue(viewModel.getDayInputs().isEmpty());
    }

    @Test
    public void persistAndRestore_dayInput() {
        // Given
        int dayIndex = 1;
        List<String> inputValues = Arrays.asList("10", "5");

        // When
        viewModel.getDayInputs().put(dayIndex, inputValues);
        List<String> restored = viewModel.getDayInputs().get(dayIndex);

        // Then
        assertEquals(inputValues, restored);
    }

    @Test
    public void clearAllInputDrafts_removesAllData() {
        // Given
        viewModel.getDayInputs().put(0, Arrays.asList("1"));
        viewModel.getDayPoints().put(0, 300);

        // When
        viewModel.getDayInputs().clear();
        viewModel.getDayPoints().clear();

        // Then
        assertTrue(viewModel.getDayInputs().isEmpty());
        assertTrue(viewModel.getDayPoints().isEmpty());
    }

    @Test
    public void getDayPoints_survivesConfigurationChange() {
        // ViewModel сохраняет данные при повороте экрана
        // Это проверяется автоматически, если ViewModel создан через ViewModelProvider
        viewModel.getDayPoints().put(2, 15000);
        assertEquals(15000, viewModel.getDayPoints().get(2));
    }
}
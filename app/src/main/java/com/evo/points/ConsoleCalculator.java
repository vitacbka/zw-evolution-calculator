package com.evo.points;

import com.evo.points.calculator.EvoCalculatorCore;
import com.evo.points.model.Reward;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Консольный калькулятор очков эволюции — зеркалит сценарий Android-приложения (день → ввод → очки → награды).
 *
 * <p><b>Точка входа JVM:</b> метод {@link #main(String[])} внизу файла (сразу после конфигурации дней).
 * Сигнатура обязана быть именно {@code public static void main(String[])} — с {@code private}
 * виртуальная машина не сможет запустить программу.
 *
 * <p><b>Запуск из корня репозитория:</b>
 * <pre>
 * ./run-console.sh
 *
 * # вручную (после компиляции debug Java):
 * ANDROID_USER_HOME="$PWD/.android" gradle :app:compileDebugJavaWithJavac
 * java -cp app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes com.evo.points.ConsoleCalculator
 * </pre>
 *
 * <p>Логи пишутся в файл {@code logs/evo_console.log} (уровень FINE в файле, INFO в консоли).
 *
 * <p>Аргументы: {@code --help} или {@code -h} — краткая справка и выход.
 */
public class ConsoleCalculator {
    private static final Logger LOGGER = Logger.getLogger(ConsoleCalculator.class.getName());
    private static final String ASSETS_BASE_DIR = "app/src/main/assets";

    private interface DayCalculator {
        int calculate(List<Integer> values);
    }

    private static class DayConsoleConfig {
        private final String label;
        private final String[] prompts;
        private final DayCalculator calculator;
        private final Function<Integer, List<Reward>> rewardsProvider;
        private final boolean hasDay6Probabilities;
        private final int greenIndex;
        private final int blueIndex;

        private DayConsoleConfig(String label,
                                 String[] prompts,
                                 DayCalculator calculator,
                                 Function<Integer, List<Reward>> rewardsProvider,
                                 boolean hasDay6Probabilities,
                                 int greenIndex,
                                 int blueIndex) {
            this.label = label;
            this.prompts = prompts;
            this.calculator = calculator;
            this.rewardsProvider = rewardsProvider;
            this.hasDay6Probabilities = hasDay6Probabilities;
            this.greenIndex = greenIndex;
            this.blueIndex = blueIndex;
        }
    }

    private static final List<DayConsoleConfig> DAY_CONFIGS = createDayConfigs();

    /* ======================================================================
     *  ТОЧКА ВХОДА КОНСОЛЬНОГО ПРИЛОЖЕНИЯ (JVM)
     *  Имя и модификаторы фиксированы спецификацией: public static void main(String[])
     * ====================================================================== */

    /**
     * Единственная точка входа для запуска из командной строки: {@code java ... com.evo.points.ConsoleCalculator}.
     * <p>Не заменяйте на {@code private static void main} — такой метод JVM не вызывает.
     *
     * @param args необязательно: {@code -h} / {@code --help} — справка и выход без интерактива
     */
    public static void main(String[] args) {
        runConsoleApplication(args);
    }

    /**
     * Вся интерактивная логика консоли; вынесена отдельно, чтобы {@link #main(String[])} был коротким и заметным.
     */
    private static void runConsoleApplication(String[] args) {
        if (args != null && args.length > 0) {
            String a = args[0];
            if ("-h".equals(a) || "--help".equals(a)) {
                printLaunchHelp();
                return;
            }
        }

        configureLogging();
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   🧟 ZOMBIE WAVES EVO CALCULATOR 🧟   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        while (true) {
            System.out.println("Выберите день эволюции (1-7, 0 - выход):");
            System.out.println("  1 — Карты эволюции");
            System.out.println("  2 — Экипировка");
            System.out.println("  3 — Лагерь");
            System.out.println("  4 — Чертежи");
            System.out.println("  5 — Невролинк");
            System.out.println("  6 — Оружие/Акс.");
            System.out.println("  7 — Пополнение");
            System.out.print("→ ");

            int day = readInt(scanner);
            if (day == 0) break;
            if (day < 1 || day > 7) {
                System.out.println("❌ Неверный день! Попробуйте снова.\n");
                continue;
            }
            DayConsoleConfig config = DAY_CONFIGS.get(day - 1);
            LOGGER.info("Выбран " + config.label);

            List<Integer> values = readInputsForDay(scanner, config);
            int points = config.calculator.calculate(values);
            System.out.println("\n📊 ВСЕГО ОЧКОВ: " + points);
            LOGGER.info("Результат: day=" + day + ", inputs=" + values + ", points=" + points);

            List<Reward> rewards = config.rewardsProvider.apply(points);
            if (rewards != null && !rewards.isEmpty()) {
                System.out.println("\n🎁 НАГРАДЫ:");
                System.out.println("   Разверните, чтобы посмотреть текущие награды:");
                System.out.println();

                // Показываем количество очков
                System.out.println("   📊 Количество очков: " + points);
                System.out.println();

                // Показываем вероятности для Дня 6
                if (config.hasDay6Probabilities) {
                    System.out.println("   🎲 ВЕРОЯТНОСТИ ДОП. СУНДУКОВ:");
                    int green = getByIndex(values, config.greenIndex);
                    int blue = getByIndex(values, config.blueIndex);
                    int expectedBlue = EvoCalculatorCore.getExpectedBlueFromGreen(green);
                    int expectedViolet = EvoCalculatorCore.getExpectedVioletFromBlue(blue);
                    int potentialPoints = EvoCalculatorCore.getPotentialPointsFromBoxes(green, blue);
                    System.out.println("   🔵 Ожидаемо синих из зелёных (5%): ~" + expectedBlue);
                    System.out.println("   🟣 Ожидаемо фиолетовых из синих (4%): ~" + expectedViolet);
                    System.out.println("   💎 Доп. очков: ~" + potentialPoints);
                    System.out.println();
                }

                for (Reward reward : rewards) {
                    if (reward.hasScreenshot()) {
                        String path = reward.getScreenshotPath();
                        if (reward.isTopReward()) {
                            System.out.println("   ┌─────────────────────────────────────────┐");
                            System.out.println("   │ 🏆 НАГРАДА ЗА ТОП (нажмите для просмотра)│");
                            System.out.println("   └─────────────────────────────────────────┘");
                            System.out.println("       📷 " + path);
                        } else {
                            System.out.println("   📷 " + path);
                        }
                        printImageInfo(path);
                    }
                }
            } else {
                System.out.println("\n⚠️  Недостаточно очков для наград");
            }

            System.out.println();
            }

            System.out.println("\nВыход из приложения.");
            scanner.close();
            }

            private static void printLaunchHelp() {
            System.out.println("Evo Points — консольный калькулятор");
            System.out.println();
            System.out.println("Запуск:");
            System.out.println("  ./run-console.sh");
            System.out.println("  java -cp app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes \\");
            System.out.println("       com.evo.points.ConsoleCalculator");
            System.out.println();
            System.out.println("Логи: logs/evo_console.log");
            }

            private static void configureLogging() {
            try {
            Logger rootLogger = Logger.getLogger("");
            for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            Formatter formatter = new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format("%1$tF %1$tT [%2$s] %3$s%n",
                            record.getMillis(),
                            record.getLevel().getName(),
                            record.getMessage());
                }
            };

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(formatter);
            rootLogger.addHandler(consoleHandler);

            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            FileHandler fileHandler = new FileHandler("logs/evo_console.log", true);
            fileHandler.setLevel(Level.FINE);
            fileHandler.setFormatter(formatter);
            rootLogger.addHandler(fileHandler);

            rootLogger.setLevel(Level.FINE);
            LOGGER.info("Логирование включено. Файл логов: logs/evo_console.log");
            } catch (IOException exception) {
            System.err.println("Не удалось настроить логирование: " + exception.getMessage());
            }
            }

            private static int readInt(Scanner scanner) {
            try {
            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            int value = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return value;
            } catch (Exception e) {
            return 0;
            }
            }

            private static List<Integer> readInputsForDay(Scanner scanner, DayConsoleConfig config) {
            List<Integer> values = new ArrayList<>();
            for (String prompt : config.prompts) {
            System.out.print(prompt + ": ");
            values.add(readInt(scanner));
            }
            return values;
            }

            private static int getByIndex(List<Integer> values, int index) {
            if (index < 0 || index >= values.size()) {
            return 0;
            }
            return values.get(index);
            }

            private static void printImageInfo(String relativePath) {
            File imageFile = new File(ASSETS_BASE_DIR, relativePath);
            if (!imageFile.exists()) {
            LOGGER.warning("Изображение не найдено: " + imageFile.getPath());
            System.out.println("      ⚠ Файл не найден: " + imageFile.getPath());
            return;
            }

            System.out.println("      Файл: " + imageFile.getPath());
            System.out.println("      Размер файла: " + imageFile.length() + " байт");
            LOGGER.fine("Image resolved: " + imageFile.getPath() + ", bytes=" + imageFile.length());
            }

            private static List<DayConsoleConfig> createDayConfigs() {
            List<DayConsoleConfig> configs = new ArrayList<>();

            configs.add(new DayConsoleConfig(
                "День 1 — Карты эволюции",
                new String[]{"Карты эволюции"},
                values -> EvoCalculatorCore.calculateDay1(getByIndex(values, 0)),
                EvoCalculatorCore::getDay1Rewards,
                false,
                -1,
                -1
            ));

            configs.add(new DayConsoleConfig(
                "День 2 — Экипировка",
                new String[]{"Билеты на экипировку", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay2(getByIndex(values, 0), getByIndex(values, 1)),
                EvoCalculatorCore::getDay2Rewards,
                false,
                -1,
                -1
            ));

            configs.add(new DayConsoleConfig(
                "День 3 — Лагерь",
                new String[]{"Сталь", "Энергия", "Ускорения", "Техноядро (бой)", "Техноядро (развитие)", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay3(
                        getByIndex(values, 0),
                        getByIndex(values, 1),
                        getByIndex(values, 2),
                        getByIndex(values, 3),
                        getByIndex(values, 4),
                        getByIndex(values, 5)
                ),
                EvoCalculatorCore::getDay3Rewards,
                false,
                -1,
                -1
            ));

            configs.add(new DayConsoleConfig(
                "День 4 — Чертежи",
                new String[]{"Обычные модули", "Продвинутые модули", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay4(
                        getByIndex(values, 0),
                        getByIndex(values, 1),
                        getByIndex(values, 2)
                ),
                EvoCalculatorCore::getDay4Rewards,
                false,
                -1,
                -1
            ));

            configs.add(new DayConsoleConfig(
                "День 5 — Невролинк",
                new String[]{"Чипы синаптического усиления", "Нейрокодировщик", "Кортикальный имплант", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay5(
                        getByIndex(values, 0),
                        getByIndex(values, 1),
                        getByIndex(values, 2),
                        getByIndex(values, 3)
                ),
                EvoCalculatorCore::getDay5Rewards,
                false,
                -1,
                -1
            ));

            configs.add(new DayConsoleConfig(
                "День 6 — Оружие/Акс.",
                new String[]{"Билеты розыгрыша оружия", "Зелёные ящики", "Синие ящики", "Фиолетовые ящики", "Жёлтые ящики", "Пополнения"},
                values -> EvoCalculatorCore.calculateDay6(
                        getByIndex(values, 0),
                        getByIndex(values, 1),
                        getByIndex(values, 2),
                        getByIndex(values, 3),
                        getByIndex(values, 4),
                        getByIndex(values, 5)
                ),
                EvoCalculatorCore::getDay6Rewards,
                true,
                1,
                2
            ));

            configs.add(new DayConsoleConfig(
                "День 7 — Пополнение",
                new String[]{"Пополнения"},
                values -> EvoCalculatorCore.calculateDay7(getByIndex(values, 0)),
                EvoCalculatorCore::getDay7Rewards,
                false,
                -1,
                -1
            ));

            return configs;
            }
}

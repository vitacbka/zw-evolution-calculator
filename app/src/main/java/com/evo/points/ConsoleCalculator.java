package com.evo.points;

import com.evo.points.calculator.EvoCalculatorCore;
import com.evo.points.model.Reward;

import java.util.List;
import java.util.Scanner;

/**
 * Консольный калькулятор очков эволюции.
 * Запуск: java -cp app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes com.evo.points.ConsoleCalculator
 */
public class ConsoleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   🧟 ZOMBIE WAVES EVO CALCULATOR 🧟   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        while (true) {
            System.out.println("Выберите день эволюции (1-7, 0 - выход):");
            System.out.println("  1 — Энергия");
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

            int points = calculateForDay(scanner, day);
            System.out.println("\n📊 ВСЕГО ОЧКОВ: " + points);

            List<Reward> rewards = getRewardsForDay(day, points);
            if (rewards != null && !rewards.isEmpty()) {
                System.out.println("\n🎁 НАГРАДЫ:");
                System.out.println("   Разверните, чтобы посмотреть текущие награды:");
                System.out.println();
                
                // Показываем количество очков
                System.out.println("   📊 Количество очков: " + points);
                System.out.println();
                
                // Показываем вероятности для Дня 6
                if (day == 6) {
                    System.out.println("   🎲 ВЕРОЯТНОСТИ ДОП. СУНДУКОВ:");
                    System.out.println("   Введите количество зелёных и синих ящиков для расчёта:");
                    System.out.print("   Зелёные ящики: ");
                    int green = readInt(scanner);
                    System.out.print("   Синие ящики: ");
                    int blue = readInt(scanner);
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

    private static int calculateForDay(Scanner scanner, int day) {
        switch (day) {
            case 1:
                System.out.print("Энергия: ");
                int energy = readInt(scanner);
                System.out.print("Пополнения: ");
                int donate1 = readInt(scanner);
                return EvoCalculatorCore.calculateDay1(energy, donate1);

            case 2:
                System.out.print("Билеты на экипировку: ");
                int tickets = readInt(scanner);
                System.out.print("Пополнения: ");
                int donate2 = readInt(scanner);
                return EvoCalculatorCore.calculateDay2(tickets, donate2);

            case 3:
                System.out.print("Сталь: ");
                int steel = readInt(scanner);
                System.out.print("Энергия: ");
                int energy3 = readInt(scanner);
                System.out.print("Ускорения: ");
                int boost = readInt(scanner);
                System.out.print("Техноядро (бой): ");
                int battleCore = readInt(scanner);
                System.out.print("Техноядро (развитие): ");
                int devCore = readInt(scanner);
                System.out.print("Пополнения: ");
                int donate3 = readInt(scanner);
                return EvoCalculatorCore.calculateDay3(steel, energy3, boost, battleCore, devCore, donate3);

            case 4:
                System.out.print("Обычные модули: ");
                int common = readInt(scanner);
                System.out.print("Продвинутые модули: ");
                int advanced = readInt(scanner);
                System.out.print("Пополнения: ");
                int donate4 = readInt(scanner);
                return EvoCalculatorCore.calculateDay4(common, advanced, donate4);

            case 5:
                System.out.print("Чипы синаптического усиления: ");
                int chips = readInt(scanner);
                System.out.print("Нейрокодировщик: ");
                int neuroCoder = readInt(scanner);
                System.out.print("Кортикальный имплант: ");
                int implant = readInt(scanner);
                System.out.print("Пополнения: ");
                int donate5 = readInt(scanner);
                return EvoCalculatorCore.calculateDay5(chips, neuroCoder, implant, donate5);

            case 6:
                System.out.print("Билеты розыгрыша оружия: ");
                int weaponTickets = readInt(scanner);
                System.out.print("Зелёные ящики: ");
                int greenBoxes = readInt(scanner);
                System.out.print("Синие ящики: ");
                int blueBoxes = readInt(scanner);
                System.out.print("Фиолетовые ящики: ");
                int violetBoxes = readInt(scanner);
                System.out.print("Жёлтые ящики: ");
                int yellowBoxes = readInt(scanner);
                System.out.print("Пополнения: ");
                int donate6 = readInt(scanner);
                return EvoCalculatorCore.calculateDay6(weaponTickets, greenBoxes, blueBoxes, violetBoxes, yellowBoxes, donate6);

            case 7:
                System.out.print("Пополнения: ");
                int donate7 = readInt(scanner);
                return EvoCalculatorCore.calculateDay7(donate7);

            default:
                return 0;
        }
    }

    private static List<Reward> getRewardsForDay(int day, int points) {
        switch (day) {
            case 1: return EvoCalculatorCore.getDay1Rewards(points);
            case 2: return EvoCalculatorCore.getDay2Rewards(points);
            case 3: return EvoCalculatorCore.getDay3Rewards(points);
            case 4: return EvoCalculatorCore.getDay4Rewards(points);
            case 5: return EvoCalculatorCore.getDay5Rewards(points);
            case 6: return EvoCalculatorCore.getDay6Rewards(points);
            case 7: return EvoCalculatorCore.getDay7Rewards(points);
            default: return null;
        }
    }
}

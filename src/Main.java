import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        printMenu();

        int months = 3;

        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        Checker checker = new Checker(monthlyReport, yearlyReport);

        while (true) {
            int userInput = scanner.nextInt();
            boolean isReadMonthlyReport = !monthlyReport.transacts.isEmpty();
            boolean isReadYearlyReport = !yearlyReport.pivots.isEmpty();
            if (userInput == 0) {
                break;
            } else if (userInput == 1) {
                monthlyReport.transacts.clear();
                for (int month = 1; month <= months; month++) {
                    monthlyReport.loadFile("0"+month,"resources/m.20210"+month+".csv");
                }
            } else if (userInput == 2) {
                yearlyReport.pivots.clear();
                yearlyReport.loadFile("resources/y.2021.csv");
            } else if (userInput == 3) {
                if (isReadMonthlyReport && isReadYearlyReport) {
                    checker.check();
                } else {
                    System.out.println("Ошибка! Загрузите годовой и месячный отчет.");
                }
            } else if (userInput == 4) {
                if (isReadMonthlyReport) {
                    for (int month = 1; month <= months; month++) {
                        System.out.println("Самая доходная позиция за 0"+month+" месяц: " + monthlyReport.getMaxExpense("0"+month, false));
                    }
                    for (int month = 1; month <= months; month++) {
                        System.out.println("Самая большая трата за 0"+month+" месяц: " + monthlyReport.getMaxExpense("0"+month, true));
                    }
                } else {
                    System.out.println("Ошибка! Загрузите месячный отчет.");
                }
            } else if (userInput == 5) {
                if (isReadYearlyReport) {
                    System.out.println("Прибыль по месяцам: " + yearlyReport.getProfit());
                    System.out.println("Траты за год: " + yearlyReport.getAvgExpense(true));
                    System.out.println("Доход за год: " + yearlyReport.getAvgExpense(false));
                } else {
                    System.out.println("Ошибка! Загрузите годовой отчет.");
                }
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
            printMenu(); // печатаем меню ещё раз перед завершением предыдущего действия
        }
        System.out.println("Программа завершена");
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти из приложения");
    }
}


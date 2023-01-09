public class Report {

    int months = 3; // количество месячных отчетов

    MonthlyReport monthlyReport = new MonthlyReport();
    YearlyReport yearlyReport = new YearlyReport();
    Checker checker = new Checker(monthlyReport, yearlyReport);

    public void readMonthlyReports() {
        monthlyReport.transacts.clear();
        for (int month = 1; month <= months; month++) {
            monthlyReport.loadFile("0"+month,"resources/m.20210"+month+".csv");
        }
    }

    public void readYearlyReport() {
        yearlyReport.pivots.clear();
        yearlyReport.loadFile("resources/y.2021.csv");
    }

    public void reconciliationReports() {
        if (!yearlyReport.pivots.isEmpty() && !monthlyReport.transacts.isEmpty()) {
            checker.check();
        } else {
            System.out.println("Ошибка! Загрузите годовой и месячный отчет.");
        }
    }

    public void monthlyReportsInfo() {
        if (!monthlyReport.transacts.isEmpty()) {
            for (int month = 1; month <= months; month++) {
                System.out.println("Самая доходная позиция за 0"+month+" месяц: "
                        + monthlyReport.getMaxExpense("0"+month, false));
            }
            for (int month = 1; month <= months; month++) {
                System.out.println("Самая большая трата за 0"+month+" месяц: "
                        + monthlyReport.getMaxExpense("0"+month, true));
            }
        } else {
            System.out.println("Ошибка! Загрузите месячный отчет.");
        }
    }

    public void yearlyReportInfo() {
        if (!yearlyReport.pivots.isEmpty()) {
            System.out.println("Прибыль по месяцам: " + yearlyReport.getProfit());
            System.out.println("Траты за год: " + yearlyReport.getAvgExpense(true));
            System.out.println("Доход за год: " + yearlyReport.getAvgExpense(false));
        } else {
            System.out.println("Ошибка! Загрузите годовой отчет.");
        }
    }
}

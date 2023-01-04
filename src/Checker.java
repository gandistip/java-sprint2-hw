import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Checker {
    public MonthlyReport monthlyReport;
    public YearlyReport yearlyReport;

    public Checker(MonthlyReport monthlyReport, YearlyReport yearlyReport) {
        this.monthlyReport = monthlyReport;
        this.yearlyReport = yearlyReport;
    }

    public void check() {

        boolean check = true;

        HashMap<String, HashMap<Boolean, Integer>> reportYear = new HashMap<>();
        for (Pivot pivot : yearlyReport.pivots) {
            if (!reportYear.containsKey(pivot.month)) {
                reportYear.put(pivot.month, new HashMap<>());
            }
            HashMap<Boolean, Integer> expenseAmount = reportYear.get(pivot.month);
            expenseAmount.put(pivot.isExpense, expenseAmount.getOrDefault(pivot.isExpense, 0) + pivot.amount);
        }

        HashMap<String, HashMap<Boolean, Integer>> reportMonth = new HashMap<>();
        for (Transact transact : monthlyReport.transacts) {
            if (!reportMonth.containsKey(transact.month)) {
                reportMonth.put(transact.month, new HashMap<>());
            }
            HashMap<Boolean, Integer> expenseAmount = reportMonth.get(transact.month);
            expenseAmount.put(transact.isExpense, (expenseAmount.getOrDefault(transact.isExpense, 0) + transact.count * transact.price));
        }

        for (String month : reportYear.keySet()) {
            HashMap<Boolean, Integer> expenseYear = reportYear.get(month);
            HashMap<Boolean, Integer> expenseMonth = reportMonth.get(month);
            if (expenseMonth == null) {
                System.out.println("Ошибка! Месяц " + month + " есть в годовом отчете, но отсутствует в месячном отчете.");
                return;
            }
            for (Boolean isExpense : expenseYear.keySet()) {
                int amountYear = expenseYear.get(isExpense);
                int amountMonth = expenseMonth.get(isExpense);
                if (amountYear != amountMonth) {
                    System.out.println("Ошибка! В месяце " + month + ", транзакция типа " + isExpense + " - различается в отчетах. " +
                            "В годовом: " + amountYear + ", в месячном: " + amountMonth + ".");
                    return;
                }
            }
        }
        if (check) {
            System.out.println("Успех! Информация по месяцу в годовом отчёте не противоречит информации в месячном отчёте.");
        }
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    public ArrayList<Pivot> pivots = new ArrayList<>();
    public void loadFile(String path) {
        String content = readFileContents(path);
        String[] lines = content.split("\r?\n"); // получаем список строк файла
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i]; // получаем строку вида: "01,1593150,false"
            String[] parts = line.split(",");
            String month = parts[0];
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            Pivot transactAmount = new Pivot(month, amount, isExpense);
            pivots.add(transactAmount);
        }
    }

    public HashMap<String, Integer> getProfit() {
        HashMap<String, Integer> profits = new HashMap<>();
        for (Pivot pivot : pivots) {
            if (pivot.isExpense) {
                profits.put(pivot.month, pivot.amount);
            }
        }
        for (Pivot pivot : pivots) {
            if (!pivot.isExpense) {
                profits.put(pivot.month, pivot.amount - profits.getOrDefault(pivot.month, 0));
            }
        }
        return profits;
    }

    public int getAvgExpense(boolean isTypeWaste) {
        int sumExpense = 0;
        int count = 0;
        for (Pivot pivot : pivots) {
            if (pivot.isExpense == isTypeWaste) {
                sumExpense = sumExpense + pivot.amount;
                count++;
            }
        }
        return sumExpense / count;
    }

    public String readFileContents(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return null;
        }
    }
}

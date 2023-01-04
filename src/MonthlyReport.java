import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    public ArrayList<Transact> transacts = new ArrayList<>();
    public void loadFile(String month, String path) {
        String content = readFileContents(path);
        String[] lines = content.split("\r?\n"); // получаем список строк файла
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i]; // получаем строку вида: "Коньки,TRUE,50,2000"
            String[] parts = line.split(","); // получаем массив вида: ["Коньки", "TRUE", "50", "2000"]
            String title = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]); // получаем и преобразуем элемент "TRUE" => true
            int count = Integer.parseInt(parts[2]); // получаем и преобразуем элемент "50" => 50
            int price = Integer.parseInt(parts[3]);

            Transact transact = new Transact(title, isExpense, count, price, month);
            transacts.add(transact);
        }
    }

    public HashMap<String, Integer> getMaxExpense(String month, boolean isTypeWaste) {
        HashMap<String, Integer> maxExpense = new HashMap<>();
        HashMap<String, Integer> freqs = new HashMap<>();
        for (Transact transact : transacts) {
            if (!month.equals(transact.month)) {
                continue;
            }
            if (transact.isExpense == isTypeWaste) {
                freqs.put(transact.title, (freqs.getOrDefault(transact.title, 0) + transact.count) * transact.price);
            }
        }
        String maxTitle = null;
        for (String title : freqs.keySet()) {
            if (maxTitle == null) {
                maxTitle = title;
                continue;
            }
            if (freqs.get(maxTitle) < freqs.get(title)) {
                maxTitle = title;
            }
        }
        maxExpense.put(maxTitle, freqs.get(maxTitle));
        return maxExpense;
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

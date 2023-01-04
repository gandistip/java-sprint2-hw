public class Transact {
    public String title;
    public boolean isExpense;
    public int count;
    public int price;
    public String month;

    public Transact(String title, boolean isExpense, int count, int price, String month) {
        this.title = title;
        this.isExpense = isExpense;
        this.count = count;
        this.price = price;
        this.month = month;
    }
}

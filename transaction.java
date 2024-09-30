import java.io.Serializable;

public class Transaction implements Serializable {
    private String description;
    private double amount;
    private Category category;
    private boolean isIncome;

    public Transaction(String description, double amount, Category category, boolean isIncome) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.isIncome = isIncome;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isIncome() {
        return isIncome;
    }

    @Override
    public String toString() {
        return (isIncome ? "Income: " : "Expense: ") + description + ", Amount: " + amount + ", Category: " + category;
    }
}

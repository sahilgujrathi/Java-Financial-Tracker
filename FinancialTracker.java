import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FinancialTracker {
    private List<Transaction> transactions;
    private double balance;
    private static final String FILE_NAME = "financial_data.dat";

    public FinancialTracker() {
        this.transactions = new ArrayList<>();
        loadTransactions();
        updateBalance();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactions();
        updateBalance();
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private void updateBalance() {
        balance = transactions.stream()
                .mapToDouble(t -> t.isIncome() ? t.getAmount() : -t.getAmount())
                .sum();
    }

    // Save transactions to file
    private void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load transactions from file
    @SuppressWarnings("unchecked")
    private void loadTransactions() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            transactions = (List<Transaction>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing records found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

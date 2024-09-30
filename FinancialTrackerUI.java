import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinancialTrackerUI extends JFrame {
    private FinancialTracker tracker;
    private JTextField descriptionField, amountField;
    private JComboBox<Category> categoryComboBox;
    private JCheckBox incomeCheckBox;
    private JLabel balanceLabel;
    private JTextArea transactionArea;

    public FinancialTrackerUI() {
        tracker = new FinancialTracker();
        setTitle("Financial Tracker");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top section: Add transaction
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(Category.values());
        inputPanel.add(categoryComboBox);

        incomeCheckBox = new JCheckBox("Income");
        inputPanel.add(incomeCheckBox);

        JButton addButton = new JButton("Add Transaction");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Middle section: Transaction list
        transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom section: Balance and summary
        balanceLabel = new JLabel("Balance: $0.00");
        add(balanceLabel, BorderLayout.SOUTH);

        // Add transaction action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });

        // Display initial data
        updateTransactionList();
        updateBalance();
    }

    private void addTransaction() {
        String description = descriptionField.getText();
        double amount;

        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            return;
        }

        Category category = (Category) categoryComboBox.getSelectedItem();
        boolean isIncome = incomeCheckBox.isSelected();

        Transaction transaction = new Transaction(description, amount, category, isIncome);
        tracker.addTransaction(transaction);

        // Update UI
        updateTransactionList();
        updateBalance();

        // Clear inputs
        descriptionField.setText("");
        amountField.setText("");
        incomeCheckBox.setSelected(false);
    }

    private void updateTransactionList() {
        StringBuilder sb = new StringBuilder();
        for (Transaction t : tracker.getTransactions()) {
            sb.append(t).append("\n");
        }
        transactionArea.setText(sb.toString());
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", tracker.getBalance()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinancialTrackerUI ui = new FinancialTrackerUI();
            ui.setVisible(true);
        });
    }
}

package client;

//Client class
import javax.swing.*;

import server.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends JFrame {

    private Bank bank;

    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextField toAccountField;
    private JTextField transferAmountField; // New field for transfer amount
    private JTextArea balanceArea;

    public Client(Bank bank) {
        this.bank = bank;

        setTitle("Bank Account Management System");
        setSize(650, 250); // Increased height to accommodate new field
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First line: Account number
        JLabel createAccountLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(10);
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                if (!accountNumber.isEmpty()) {
                    try {
                        bank.createAccount(accountNumber);
                        JOptionPane.showMessageDialog(null, "Account created successfully");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter an account number");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(createAccountLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(accountNumberField, gbc);
        gbc.gridx = 2;
        mainPanel.add(createAccountButton, gbc);

        // Second line: Amount
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JButton creditButton = new JButton("Credit");
        JButton debitButton = new JButton("Debit");
        creditButton.addActionListener(new TransactionActionListener("Credit"));
        debitButton.addActionListener(new TransactionActionListener("Debit"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(amountLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(amountField, gbc);
        gbc.gridx = 2;
        mainPanel.add(creditButton, gbc);
        gbc.gridx = 3;
        mainPanel.add(debitButton, gbc);

        // Third line: To Account
        JLabel transferLabel = new JLabel("To Account:");
        toAccountField = new JTextField(10);
        JLabel transferAmountLabel = new JLabel("Amount:");
        transferAmountField = new JTextField(10); // Added new field for transfer amount
        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromAccount = accountNumberField.getText();
                String toAccount = toAccountField.getText();
                String amountText = amountField.getText();
                String transferAmountText = transferAmountField.getText(); // Retrieve transfer amount
                if (!fromAccount.isEmpty() && !toAccount.isEmpty() && !amountText.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountText);
                        double transferAmount = Double.parseDouble(transferAmountText); // Parse transfer amount
                        bank.transfer(fromAccount, toAccount, transferAmount); // Use transfer amount
                        JOptionPane.showMessageDialog(null, "Transfer successful");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid amount format");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter account numbers and amount");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(transferLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(toAccountField, gbc);
        gbc.gridx = 2;
        mainPanel.add(transferAmountLabel, gbc);
        gbc.gridx = 3;
        mainPanel.add(transferAmountField, gbc);
        gbc.gridx = 4;
        mainPanel.add(transferButton, gbc);

        // Fourth line: Balance
        JLabel balanceLabel = new JLabel("Balance of Account:");
        balanceArea = new JTextArea(1, 20);
        balanceArea.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(balanceLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(balanceArea, gbc);

        // Fifth line: Check Balance
        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new TransactionActionListener("Check Balance"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        mainPanel.add(checkBalanceButton, gbc);

        add(mainPanel);
    }

    private class TransactionActionListener implements ActionListener {
        private String action;

        public TransactionActionListener(String action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String accountNumber = accountNumberField.getText();
            String amountText = amountField.getText();
            if (!accountNumber.isEmpty() && !amountText.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountText);
                    switch (action) {
                        case "Credit":
                            bank.credit(accountNumber, amount);
                            JOptionPane.showMessageDialog(null, "Credit successful");
                            break;
                        case "Debit":
                            bank.debit(accountNumber, amount);
                            JOptionPane.showMessageDialog(null, "Debit successful");
                            break;
                        case "Check Balance":
                            double balance = bank.getBalance(accountNumber);
                            balanceArea.setText(String.format("%.2f", balance));
                            break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter an account number and amount");
            }
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1009);
            Bank bank = (Bank) registry.lookup("Bank");

            SwingUtilities.invokeLater(() -> {
            	Client clientUI = new Client(bank);
                clientUI.setVisible(true);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the bank server");
            e.printStackTrace();
        }
    }
}

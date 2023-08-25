
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

public class ATM extends JFrame implements ActionListener {
    private JTextField tf;
    private JPasswordField pf;
    private JButton b1, b2, b3;
    private JLabel l1, l2, l3;
    private JPanel p1, p2, p3;
    private BankAccount bankAccount;

    public ATM() {
        setTitle("ATM");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bankAccount = new BankAccount(1000.0); // Initial balance is $1000
        
        tf = new JTextField(10);
        pf = new JPasswordField(10);
        b1 = new JButton("Deposit");
        b2 = new JButton("Withdraw");
        b3 = new JButton("Check Balance");
        l1 = new JLabel("Enter Amount:");
        l2 = new JLabel("Enter PIN:");
        l3 = new JLabel("");

        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p1.add(l1);
        p1.add(tf);
        p1.add(l2);
        p1.add(pf);

        p2.add(b1);
        p2.add(b2);
        p2.add(b3);

        p3.add(l3);

        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
        add(p3, BorderLayout.SOUTH);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String s = ae.getActionCommand();
        if (s.equals("Deposit")) {
            String amountStr = tf.getText();
            double amount = Double.parseDouble(amountStr);
            bankAccount.deposit(amount);
            l3.setText("Deposited " + amount + " successfully.");
            tf.setText("");
            pf.setText("");
            return;
        }

        if (s.equals("Withdraw")) {
            String amountStr = tf.getText();
            double amount = Double.parseDouble(amountStr);
            if (bankAccount.withdraw(amount)) {
                l3.setText("Withdrew " + amount + " successfully.");
            } else {
                l3.setText("Insufficient balance.");
            }
            tf.setText("");
            pf.setText("");
            return;
        }

        if (s.equals("Check Balance")) {
            l3.setText("Your balance is $" + bankAccount.getBalance());
            tf.setText("");
            pf.setText("");
            return;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATM());
    }
}
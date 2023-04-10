import java.util.ArrayList;

public class Account{
    public Account(String owner_, double balance_, int secretNumber_)
    {
        this.owner = owner_;
        this.balance = balance_;
        this.movements = new ArrayList<String>();
    }

    public void deposit(double amount)
    {
        this.balance += amount;
        movements.add("Deposit " + amount + " euros. The balance is " + this.balance + " euros now.");
    }

    public void withdraw(double amount)
    {
        if(this.balance <= amount)
        {
            this.balance -= amount;
            movements.add("Withdraw " + amount + " euros. The balance is " + this.balance + " euros now.");
        }
    }

    private String owner;
    private double balance;
    private ArrayList<String> movements;
}
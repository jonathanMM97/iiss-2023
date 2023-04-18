import java.math.BigDecimal;

public class Account{

    private BigDecimal balance;
    private int numberSecret;
    private String owner;
    
    public Account(int b, int n, String nom)//balance, numberSecret and owner
    {
        this.balance = BigDecimal.valueOf(b);this.numberSecret = n; this.owner = nom;
    }

    public void deposit(int ammount)
    {
        this.balance = this.balance.add(BigDecimal.valueOf(ammount));
    }

    public void withdraw(int ammount)
    {
        if(this.balance.compareTo(BigDecimal.valueOf(ammount)) >= 0)
            this.balance = this.balance.subtract(BigDecimal.valueOf(ammount));
    }

    public BigDecimal balance_()
    {return this.balance;}

    public String getOwner()
    {return this.owner;}

    public int getSecretNumber(){return numberSecret;}
}
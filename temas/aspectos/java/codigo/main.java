public class main{
    public static void main(String[] args)
    {
        Account c = new Account(100, 1235, "Joni");

        c.deposit(100);
        c.withdraw(20);
        System.out.println("El saldo es: " + c.balance_());
    }
}
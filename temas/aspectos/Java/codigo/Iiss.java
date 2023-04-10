import com.google.inject.Guice;
import com.google.inject.Injector;

public class Iiss{
    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new TemaAspectos());
        Account account = injector.getInstance(Account.class);

        account.deposit(1997);
        account.withdraw(1031);

    }
}
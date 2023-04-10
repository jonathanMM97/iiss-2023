import com.google.inject.Inject;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import java.util.Scanner;

@Aspect
public class SecurityAspect{

    @Inject
    public SecurityAspect()
    {}

    @Before("execution(* Account.deposit(..)) || execution(* Account.withdraw(..))")
    public void askSecretNumber()
    {
        Scanner s = new Scanner(System.in);

        System.out.print("Insert his secret number please: ");

        int secret = s.nextInt();

        if(secret != this.secretNumber_)
        {
            throw new SecurityException("Incorrect secret number...");
        }
    }


    private int secretNumber_ = 1234;
}
# Aspectos
Lua no ofrece un soporte como tal para los aspectos, se puede llegar a implementar una funcionalidad parecida tras el uso de bibliotecas externas y/o módulos. Por lo que en este tema haremos uso del lenguaje de programación **java**, el cuál si que ofrece un soporte para los aspectos.
## Aspectos en Java
Para java en este tema usaremos el framework de inyección de dependencias llamado **Guice**, esta herramienta es muy popular y ofrece muchas técnicas además de los aspectos.

Para nuestro tema usaremos el caso de uso de una cuenta bancaria, como sabemos los aspectos se usan para separar la funcionalidad principal del problema de temas sencillos. Por ejemplo en el caso de una cuenta bancaria si decidieramos usar aspectos para el ingreso y el reintegro, podríamos separar la funcionalidad principal, la cuál es realizar el ingreso o el reintegro de la funcionalidad transversal, la cuál podría ser pedir el código secreto de la cuenta para confirmar la operación.

Dejado claro el caso de uso de ejemplo para mostrar la utilidad de los aspectos, dada la clase **Account** en java:

```java
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
```

Como podemos observar es una clase sencilla donde el método que contiene mas lógica es el reintegro(withdraw), este método debe comprobar que no se va a quedar el saldo(balance) en negativo tras realizar la operación. En este caso, dado el caso que se intentase realizar un reintegro de una cantidad superior del saldo actual, no lanzaría ninguna excepción y por consiguiente no se realiza ninguna acción.

Ahora mostraremos como se implementaría el aspecto de seguridad como es pedir el numero secreto de la cuenta al intentar realizar una de las operaciones del sistema:

```java
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

        if(secret != this.secretNumber)
        {
            throw new SecurityException("Incorrect secret number...");
        }
    }


    private int secretNumber_ = 1234;
}
```

Pues bien, ya tenemos la implementación del aspecto de seguridad requerido para el caso de uso que se propuso al inicio del documento. Pero este no es el final, ya que si queremos utilizar **Guice**, debemos implementar otra clase que incluya tanto la clase **Account** como la clase de aspecto de seguridad:

```java
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class TemaAspectos extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(Account.class);
        bind(SecurityAspect.class).toProvider()->new SecurityAspect(getInstance(Account.class).secretNumber());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(SecurityAspect.class), getInstance(SecurityAspect.class));
    }
}

```

Así conseguimos crear una instancia de Account con **Guice**. Lo ultimo que nos quedaría es realizar la clase con la que realizaremos la comprobación de que todo lo que hemos dicho funciona correctamente:

```java
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class TemaAspectos extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(Account.class);
        bind(SecurityAspect.class).toProvider()->new SecurityAspect(getInstance(Account.class).secretNumber());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(SecurityAspect.class), getInstance(SecurityAspect.class));
    }
}

```


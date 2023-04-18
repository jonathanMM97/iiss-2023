# Aspectos en Java
Para esta práctica hemos elegido el lenguaje de programación de Java, con el que trabajaremos además con AspectJ.
## ¿Que es y para que sirve AspectJ?
AspectJ es un lenguaje que extiende el lenguaje de programación Java. Este fue desarrollado para agregar los aspectos al lenguaje Java. Como sabemos los aspectos nos ayudan a diferenciar entre los comportamientos y las características de nuestro sistema.
## Ejemplo
Para nuestra práctica retomaremos el caso de uso de la clase cuenta (Account), esta clase tiene tres atributos privados, dos métodos modificadores, tres métodos observadores. 
- Los atributos privados son **balance**, **owner** y **numberSecret**, es decir, el saldo de la cuenta, el titular y el numero secreto de la tarjeta de crédito respectivamente. 
- Los métodos modificadores **deposit** y **withdraw**, no son más que un metodo para depositar (**deposit**), el cual recibirá un parámetro que es la cantidad a depositar que tiene como comportamiento añadir la cantidad al saldo actual. Y por otro lado, el método reintegro(**withdraw**), se dedicará a retirar dinero de la cuenta, su comportamiento será retirar el dinero siempre y cuando el saldo actual sea mayor o igual a la cantidad a retirar.
- Por último, los métodos observadores. Estos fueron llamados **balance_**, **getSecretNumber**, **getOwner**, los cuáles tienen como comportamiento devolver el saldo actual, el numero secreto de la tarjeta de crédito y el nombre del propietario de la cuenta respectivamente.
El código de la clase cuenta quedaría de la siguiente forma:
```java
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
```

Por otro lado, tenemos la parte mas importante de esta práctica, el aspecto. Para este ejemplo vamos a añadir a los métodos modificadores un comportamiento transversal. Por lo que lo primero que debemos hacer es añadir los puntos de ejecución donde el aspecto va a ser aplicado:

```java
public aspect TestNumberSecret{
    pointcut callDeposit(int ammount) : call(* Account.deposit(int)) && args(ammount);
    pointcut callWithDraw(int ammount) : call(* Account.withdraw(int)) && args(ammount);
    
    //...
}
```

En el codigo anterior señalamos que el aspecto se aplicará para el método **deposit** de la clase **Account**, el cuál recibe un valor entero.

El aspecto tendrá el comportamiento transversal en el método **callDeposit** (la segunda línea es similar pero con el método **withdraw**).

Una vez dicho lo anterior, lo único que nos queda es implementar dicho comportamiento:

```java
before(int ammount) : callDeposit(ammount) {

        Account account = (Account) thisJoinPoint.getTarget();

        Scanner s = new Scanner(System.in);

        boolean incorrecto = false;
        int i = 0;
        
        do{
            i++;
            System.out.print("\nSe esta intentando hacer un deposito de: " + ammount
                        + "\nPara poder realizar la operacion debe introducir el numero secreto de la tarjeta de credito: ");
            
            int secret = s.nextInt();
            if(secret != account.getSecretNumber())
            {
                System.out.println("Numero secreto incorrecto...(" + i + "/3)");
            }else{
                incorrecto = true;
            }
        }while(!incorrecto && i < 3);

        s.nextLine();//limpiamos el buffer de


        if(!incorrecto)
            throw new RuntimeException("Invalid deposit amount!");
        else{
            System.out.println("Numero secreto correcto..."
                            + "\n¿Desea comprobante?(S/n): ");
            char op = s.next().charAt(0);

            if(op == 's' || op == 'S')
                System.out.println("Se ha realizado el deposito de " + ammount + " euros a la cuenta de " + account.getOwner());

        }
    }


    before(int ammount) : callWithDraw(ammount) {

        Account account = (Account) thisJoinPoint.getTarget();

        Scanner s = new Scanner(System.in);

        boolean incorrecto = false;
        int i = 0;
        
        do{
            i++;
            System.out.print("\nSe esta intentando hacer un reintegro de: " + ammount
                        + "\nPara poder realizar la operacion debe introducir el numero secreto de la tarjeta de credito: ");
            
            int secret = s.nextInt();
            if(secret != account.getSecretNumber())
            {
                System.out.println("Numero secreto incorrecto...(" + i + "/3)");
            }else{
                incorrecto = true;
            }
        }while(!incorrecto && i < 3);

        s.nextLine();//limpiamos el buffer de


        if(!incorrecto)
            throw new RuntimeException("Invalid deposit amount!");
        else{
            System.out.println("Numero secreto correcto..."
                            + "\n¿Desea comprobante?(S/n): ");
            char op = s.next().charAt(0);
            if(op == 's' || op == 'S')
                System.out.println("Se ha realizado el reintegro de " + ammount + " euros a la cuenta de " + account.getOwner());

        }
    }
```

Como podemos ver, en la primera línea tenemos la siguiente línea de código: 

``` java
before(int ammount) : callDeposit(ammount) { //... 
```

Esta línea indica que cada vez que se llame al método **deposit** antes de ejecutar su comportamiento principal, se llamará al método **callDeposit**, el cual toma el valor pasado al método modificador y hace uso de él. 

Si nos predisponemos a comprender el código del método **callDeposit**, no es más que un método en el cuál pide por teclado el numero secreto al usuario, si el usuario falle mas de 3 veces, lanza una excepción del tipo "RuntimeException" con un mensaje de error detallado: 
```java
throw new RuntimeException("Invalid deposit amount!"); 
```
Si por el contrario el numero secreto se ha introducido correctamente, se pedirá al usuario si desea el comprobante de la operación, en caso positivo se mostraría por pantalla, en caso negativo no hace nada.

Si observamos detenidamente, el método **callWithDraw**, es similar al anterior método, tiene la misma estructura, las únicas diferencias radican en el texto que se muestra en pantalla.

## Clase principal

Ya se habrá dado cuenta que falta una parte crucial, el método **main**, pues este método se puede implementar en otra clase totalmente diferente, en nuestro caso la hemos llamado **main**.

```java
public class main{
    public static void main(String[] args)
    {
        Account c = new Account(100, 1235, "Joni");

        c.deposit(100);
        c.withdraw(20);
        System.out.println("El saldo es: " + c.balance_());
    }
}
```

Es una función de lo mas simple, este método main crea una cuenta con un saldo inicial de *100 euros*, con un numero secreto tal como *1235* y como titular firma *Joni*.

Cabe resaltar que tanto para la llamada **c.deposit(100)**, como por la llamada **c.withdraw(20)**, actuará el aspecto con el comportamiento transversal anteriormente explicado.

## Como se ejecuta
Para esta práctica necesitamos librerias expeciales, por lo que también están incluidas junto con el código (se recomienda no mover los archivos, en caso de hacerse los comandos recogidos en este documento pueden fallar).

Para compilar el código debe utilizarse el siguiente comando:

```
    ajc -cp .\lib\aspectjrt.jar -source 1.8 *.java *.aj
```

El cual haciendo uso de la libreria **aspectjrt.jar** y utilizando la versión de java **1.8** compila todos los archivos **.java** y **.aj** pudiendo así ejecutarse de forma correcta el aspecto.

Para ejecutar el archivo *main* de la práctica debe usar el comando:

```
    java -javaagent:.\lib\aspectjweaver-1.9.19.jar main
```

El cual haciendo uso de la librería **aspectjweaver-1.9.19.jar** para que el aspecto se pueda aplicar de forma correcta en la ejecución del programa, compila el archivo **main**.

# Conclusion
Hemos podido dividir la dificultad del problema usando los aspectos pudiendo incluso extender la complejidad tanto como queramos y aun así la clase principal **Account** permanece intacta y muy simple.
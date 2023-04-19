# Undefined


Para este tema al no tener soporte en Lua, hemos decido optar por Scala.


## Undefined en Scala


En Scala existen diversas formas para suplantar el valor **null**;


- **Option**: Este tipo de datos en Scala, se utiliza para representar valores opcionales o nulos. Así, al usar **Some** para devolver el valor en caso de estar presente o **None** en caso contrario, Scala consigue reemplazar el uso de **null** con este tipo de datos.


- **Either**: Este otro tipo de datos en Scala, tiene la misma finalidad que *Option*, pero en vez de hacer uso de *Some* y *None*, **Either** usa **Right** y **Left**, los cuales devuelven el valor en caso de existir o en caso contrario un tipo de valor que represente un resultado incorrecto o un error respectivamente.


## Undefined en Scala con Option


Dada la clase **Account** y una clase **Authorized** donde los atributos de las clases son:


- Para la clase **Account** tres atributos y dos métodos:
    - Los atributos son *balance*, *AccountNumber* y *Owners*, los cuáles representan el saldo de la cuenta, el número de la cuenta y titulares de la cuenta .
    - Los métodos son *deposit* y *withdraw*, los cuales tienen el comportamiento de depositar dinero en la cuenta, por otra parte el otro metodo retira dinero de la cuenta y devuelve la cantidad requerida en caso de haber suficiente saldo en la cuenta.


- Para la clase **Authorized** un solo atributo y un solo método:
    - El atributo es *listOwner*, el cual representa la lista de objetos de clase **Account**.
    - El atributo es *getAccount*, el cual tiene como comportamiento devolver un optional, este metodo recibe un 'String' y un numero de cuenta, si la cuenta existe y el 'String' coincide con uno de los titulares de la cuenta, devolvera el objeto **Account**, en caso contrario, devolvera un **None**, propio de los *Optional*.


Por lo que el código quedará tal que así;
**Clase AccountOptional**
```Scala
    class AccountOptional(var balance: Double, val accountNumber: String, val owners: List[String])
    {
        def deposit(ammount: Double): Unit = {
            balance += ammount
        }

        def withdraw(ammount: Double): Option[Double] = {
            if (balance >= ammount)
            {
                balance -= ammount
                Some(ammount)
            }else{
                None
            }
        }

        def actualBalance():Double = {
            balance
        }
    }
```

Como podemos observar la clase **AccountOptional** es muy sencilla no obstante ya podemos ver como se devuelve un *Option*. Como habíamos comentado anteriormente, si se dispone del saldo suficiente para realizar el reintegro, se resta la cantidad a retirar y se devuelve para que pueda tratar con el dato y en caso contrario devuelve un *None*.


**Clase AuthorizedOptional**
```Scala
    class AuthorizedOptional(accounts: List[AccountOptional])
    {
        def findAccount(owner: String, accountNumber: String): Option[AccountOptional] = {
            accounts.find(account => account.accountNumber == accountNumber && account.owners.contains(owner))
        }
    }
```

Si observamos la clase anterior, el único metodo que tiene devuelve un optional a partir de la llamada a la función **find** de una lista. El cuál devuelve un *Some* en caso de que se cumpla las condiciones detalladas en la llamada o un *None* en caso contrario.

**Objeto AtmOptional (objeto creado para main)**
```Scala
    object AtmOptional{
        def main(args: Array[String]): Unit = {
        
            val cuenta1 = new AccountOptional(1000.0, "123456789", List("Juan Perez", "Maria Gomez"))
            val cuenta2 = new AccountOptional(2000.0, "987654321", List("Pedro Rodriguez"))
            val cuenta3 = new AccountOptional(500.0, "567890123", List("Juan Perez", "Ana Lopez"))

            val autorizado = new AuthorizedOptional(List(cuenta1, cuenta2, cuenta3))

            print("Introduce el titular de la cuenta: ")
            val titular = scala.io.StdIn.readLine()
            print(titular + " introduce el numero de la cuenta: ")
            val num = scala.io.StdIn.readLine()
            val account = autorizado.findAccount(titular, num)

            account match {
            case Some(account) => 

                println("Hola de nuevo " + titular)
                println("Que operacion desea realizar?")
                
                var op = 0

                do{

                    println("1. Depositar")
                    println("2. Retirar")
                    println("3. Consultar saldo")
                    println("0. Salir")
                    print("Tu opcion: ")

                    op = scala.io.StdIn.readInt()

                    op match{
                        case 1 => print("Introduce la cantidad a depositar: ")
                            val cantidad = scala.io.StdIn.readInt()
                            account.deposit(cantidad)
                        case 2 => print("Introduce la cantidad a retirar: ")
                            val cantidad = scala.io.StdIn.readInt()
                            val cash = account.withdraw(cantidad)
                            
                            cash match{
                                case Some(cash) => println("Has recibido " + cash + " euros.")
                                case None => println("No hay suficiente saldo.")
                            }
                        case 3 => println("Tu saldo actual es: " + account.balance)
                        case 0 => println("ADIOS")
                    }
                }while(op != 0)

            case None => println("No se encontro ninguna cuenta de " + titular)
            }
        }
    }
```

En la función main podemos ver como tratar un *Option*. Scala para tratarlo hace uso de la expresión **match**, tiene un comportamiento similar a los **switch**, la expresión dada se compara con una serie de valores recogidos en **case**. Por lo tanto si lo hacemos muy simple, tendríamos el ejemplo:
```Scala
suma match{
    case Some(suma) => println("el resultado de la suma es: " + suma)
    case None => println("Imposible sumar.")
}
```

## Undefined en Scala con Option


Como los comportamientos de las funciones no cambian, solo mostraremos algunos cambios significativos en el código. Un cambio importante en el código, fue realizado a la función **findAccount** para acomodarlo a **Either**:

```Scala
    def findAccount(owner: String, accountNumber: String): Either[String, AccountEither] = {
            accounts.find(account => account.accountNumber == accountNumber && account.owners.contains(owner)) match {
                case Some(account) => Right(account)
                case None => Left("No se encontró ninguna cuenta")
            }
        }
```

Como podemos ver el resultado de la función **find** se trata en la función **findAccount** para tratarla como *Either*. El comportamiento de *Either* es un poco diferente, lo primero que vemos es que se indica que el Either devolverá un [String, Double], por lo que *Right* tomará el valor exitoso mientras que *Left* indica un resultado de error. Una vez vista como se trata **Either** para devolverlo en una función, veamos un cambio realizado en la función **main** para observar como tratar un **Either** recibido:

```Scala
    ...
    cash match{
        case Right(cash) => println("Has recibido " + cash + " euros.")
        case Left(mensaje) => println(mensaje)
    }  
    ...
```
Como podemos observar, usamos de nuevo la expresión **match**, lo único que cambia son los valores del **case**, en vez de *Some* y *None* esta vez son *Right* y *Left*. Además, el segundo valor también recibe un valor, es decir *Left* recibe un valor del tipo expecificado en la función que devuelve el **Either**.

## Como compilar

### Compilar clases con Option
Para compilar las clases que hace uso del tipo de datos *Option*, debe usar el siguiente comando para estar en el compilador de Scala:
```
    sbt
```

Una vez dentro, utiliza el comando:

```
    compile
```

Le saldrá un mensaje de confirmación y ejecuta el ultimo comando (si cambia los nombres de los archivos puede que el siguiente comando no funcione correctamente):

```
    runMain AtmOptional
```

### Compilar clases con Either

Sigue los mismos pasos que en el caso anterior:
```
    sbt
    compile
```

Para este caso se selecciona el objeto que hace uso del tipo de datos *Either*;

```
    runMain AtmEither
```

# Conclusion
Hemos podido suprimir el uso de **null** en Scala al usar estos tipos de datos, además es muy sencillo de recordar ya que se tratan de forma muy similar. En mi opinión, el uso de **Either** me parece mas adecuado ya que solo debemos tratar con los datos que nos pasan, por el contrario, si **Option** nos devuelve *None*, debemos actuar de acorde a ello, pero con **Either** obtenemos un valor junto con el *Left* y solo debemos tratar ese valor como corresponde.
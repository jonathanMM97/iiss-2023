object AtmEither{
    def main(args: Array[String]): Unit = {
    
        val cuenta1 = new AccountEither(1000.0, "123456789", List("Juan Perez", "Maria Gomez"))
        val cuenta2 = new AccountEither(2000.0, "987654321", List("Pedro Rodriguez"))
        val cuenta3 = new AccountEither(500.0, "567890123", List("Juan Perez", "Ana Lopez"))

        val autorizado = new AuthorizedEither(List(cuenta1, cuenta2, cuenta3))

        print("Introduce el titular de la cuenta: ")
        val titular = scala.io.StdIn.readLine()
        print(titular + " introduce el numero de la cuenta: ")
        val num = scala.io.StdIn.readLine()
        val account = autorizado.findAccount(titular, num)

        account match {
        case Right(account) => 

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
                            case Right(cash) => println("Has recibido " + cash + " euros.")
                            case Left(mensaje) => println(mensaje)
                        }
                    case 3 => println("Tu saldo actual es: " + account.balance)
                    case 0 => println("ADIOS")
                }
            }while(op != 0)

        case Left(mensaje) => println(mensaje)
        }
    }
}
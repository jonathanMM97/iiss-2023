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
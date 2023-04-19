class AccountEither(var balance: Double, val accountNumber: String, val owners: List[String])
{
    def deposit(ammount: Double): Unit = {
        balance += ammount
    }

    def withdraw(ammount: Double): Either[String, Double] = {
        if (balance >= ammount)
        {
            balance -= ammount
            Right(ammount)
        }else{
            Left("No hay suficiente saldo en la cuenta")
        }
    }

    def actualBalance():Double = {
        balance
    }
}
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
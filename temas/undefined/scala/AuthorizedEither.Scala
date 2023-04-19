class AuthorizedEither(accounts: List[AccountEither])
{
    def findAccount(owner: String, accountNumber: String): Either[String, AccountEither] = {
        accounts.find(account => account.accountNumber == accountNumber && account.owners.contains(owner)) match {
            case Some(account) => Right(account)
            case None => Left("No se encontró ninguna cuenta")
        }
    }
}
class AuthorizedOptional(accounts: List[AccountOptional])
{
    def findAccount(owner: String, accountNumber: String): Option[AccountOptional] = {
        accounts.find(account => account.accountNumber == accountNumber && account.owners.contains(owner))
    }
}
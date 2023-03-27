function createAccount(target: any, key: string, descriptor: PropertyDescriptor)
{
    const newMetod = descriptor.value;

    descriptor.value = function(...args: any[])
    {
        const amount = newMetod.apply(this, args);
        console.log("Se ha depositado " + amount);
        return amount;
    };

    return descriptor;
}

class Account{
    @createAccount
    deposit(ammount: number):number
    {
        return ammount;
    }
}

const account = new Account();
account.deposit(100);
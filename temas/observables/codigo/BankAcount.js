const { Observable } = require('rxjs');

class BankAccount {
  constructor(accountNumber, initialBalance) {
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
  }

  getAccountNumber() {
    return this.accountNumber;
  }

  getBalance() {
    return this.balance;
  }

  deposit(amount) {
    this.balance += amount;
    return Observable.of(amount);
  }
z
  withdraw(amount) {
    if (amount <= this.balance) {
      this.balance -= amount;
      return Observable.of(amount);
    } else {
      return Observable.throw(new Error('Insufficient funds'));
    }
  }
}

const account = new BankAccount('123456789', 1000.0);

const depositObservable = account.deposit(500.0);
const withdrawObservable = account.withdraw(800.0);

const observer = {
  next: (amount) => console.log('Operación exitosa:', amount),
  error: (error) => console.log('Error en la operación:', error.message),
  complete: () => console.log('Operación completada')
};

depositObservable.subscribe(observer);
withdrawObservable.subscribe(observer);

console.log('Saldo actual:', account.getBalance());

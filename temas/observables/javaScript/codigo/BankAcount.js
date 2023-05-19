const { Subject } = require('rxjs');

class BankAccount {
  constructor(initialBalance) {
    this.balance = initialBalance;
    this.balanceSubject = new Subject();
    this.balanceSubject.next(this.balance);
  }

  deposit(amount) {
    this.balance += amount;
    this.balanceSubject.next(this.balance);
  }

  withdraw(amount) {
    if (amount <= this.balance) {
      this.balance -= amount;
      this.balanceSubject.next(this.balance);
    } else {
      console.log('Fondos insuficientes');
    }
  }

  getBalance() {
    return this.balance;
  }

  subscribeToBalanceChanges() {
    this.balanceSubject.subscribe((balance) => {
      console.log('Nuevo saldo:', balance);
    });
  }
}

const account = new BankAccount(100);
account.subscribeToBalanceChanges();
account.deposit(50);
account.withdraw(25);
account.withdraw(200);

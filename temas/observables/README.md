# Observables en JavaScript
En esta práctica vamos a realizar un código en **javaScript** para demostrar el potencial de los observables. Para este lenguaje de programación hemos utilizado la biblioteca **RxJS** para trabajar con los *observables*. Esta librería de programación reactiva nos permite trabajar con datos y eventos de forma mucho más sencilla y eficiente.

# Explicación del código
En este apartado vamos a explicar el código implementado, el cuál tiene la misma orientación que muchos de los ejemplos de estas prácticas, **cuenta bancaria**, en este código gracias al uso de los observables veremos los eventos que ocurren en la clase **BankAcount**. Ahora daremos mas detalles:

- Crearemos la clase **Subject** de la librería **RxJS**, este tipo de observable puede actuar como fuente y emisor de eventos, en este ejemplo se usa para notificar los cambios en el saldo de la cuenta bancaria:
```javaScript
    const { Subject } = require('rxjs');
```

- Creamos la clase **BankAcount**, la cuál representa una cuenta bancaria e inicializamos todas nuestras variables en un estado inicial:
```javaScript
    class BankAccount {
    constructor(initialBalance) {
        this.balance = initialBalance;
        this.balanceSubject = new Subject();
        this.balanceSubject.next(this.balance);
    }
```

- Tendremos entre otros métodos, el método *deposit* el cuál recibirá una variable llamada *amount*. Este método tiene como función aumentar el saldo de la cuenta en la cantidad especificada, posteriormente hace uso del método del observable para emitir un evento con el saldo actualizado a todos los suscriptores de **balanceSubject**

```javaScript
    deposit(amount) {
        this.balance += amount;
        this.balanceSubject.next(this.balance);
    }
```

- Otro método que poseemos en la clase **BankAcount** es *withdraw*, el cuál hace el reitegro de la cantidad especificada como parámetro al método y posteriormente lanza el evento con el nuevo saldo.

```javaScript
    withdraw(amount) {
        if (amount <= this.balance) {
        this.balance -= amount;
        this.balanceSubject.next(this.balance);
        } else {
        console.log('Fondos insuficientes');
        }
    }
```

- Como método observador tenemos *getBalance()*, este método devuelve el saldo actual de la cuenta.

```javaScript
    getBalance() {
        return this.balance;
    }
```

- Como último método y mas importante es *suscribeToBalanceChanges()*, con este método podemos suscribirnos a los cambios de saldo utilizando el método *this.balanceSubject.subscribe()*. Cada vez que se emita un evento realiza una función callback que solo muestra el nuevo saldo de la cuenta.

```javaScript
    subscribeToBalanceChanges() {
        this.balanceSubject.subscribe((balance) => {
        console.log('Nuevo saldo:', balance);
        });
    }
```

# Como compilar
Hemos añadido las dependencias necesarias para ejecutar el código, aún así debes instalar **Node.js**, en caso de no tenerlo te dejo un link:
- [label](https://nodejs.org/)

Una vez instalado debes ejecutar el comando:
```
    node BankAcount.js
```

En caso de algún posible error por favor instala *RxJS*:

```
    npm install rxjs
```

# Conclusiones
Algunas personas pueden confundir aspectos con los observables, pero hay una gran diferencia, los observables son una parte fundamental de la programación reactiva, nos permiten manejar flujos de datos y reaccionar a ellos mediante la suscripción a los eventos que éstos emiten.

Por otro lado, los aspectos separan las preocupaciones transversales de las principales.
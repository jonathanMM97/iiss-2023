# Anotaciones en TypeScript
Debido a que en Lua las anotaciones son muy limitadas, en este tema vamos a recurrir a TypeScript. Los decoradores son la forma que tiene TypeScript de implementar las anotaciones.

## Decorators

Los decoratos se usan con la forma **@expresion** donde **expresion** debe ser la función que será llamada con la información sobre la declaración del decorator. Es decir, si establecemos un nombre a un decorator como **@aims**  podríamos hacer la función **aims** como sigue:
```TypeScript
    function aims(target)
    {
        //aims to 'target' ...
    }
```

Una aplicación interesante para los decorators es la siguiente; si queremos observar como un decorator es aplicado a una declaración, podríamos escribir un *decorator factory*. Este no es más que una función que devuelve la expresión que llamaremos por el decorator. Por ejemplo:

```TypeScript
    function color(value: string)
    {
        //este es un decorator factory

        return function (target){
            //este es el decorator
            //haz algo con 'target' y 'value'...
        };
    }
```

Otra característica interesante es la composición de decorators, es decir, podemos llamar a varios decorators en una sola línea:
```TypeScript
    @f  @g x
```

O en varias líneas:

```TypeScript
    @f
    @g
    x
```

Estas llamadas son equivalentes a una función matemática. En este caso sería: **f(g(x))**.

Veamos un ejemplo aclarativo para entender mejor los decorators en TypeScript:

```TypeScript
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
```

En el ejemplo, hemos aplicado el **decorator** *@createAccount* al método *deposit* de la clase *Account*. El decorator *createAccount* reemplazará el método original *deposit* con una nueva implementación que llama al método original y luego imprime el valor en la consola. Al llamar al método *deposit* en una instancia de la clase *Account*, el decorator se encarga de imprimir el resultado en la consola.
De esta manera, podemos utilizar los decoradores para agregar funcionalidades adicionales a nuestros métodos originales sin necesidad de modificarlos.

# Compilar el código
Si se desea compilar el código, primero deberá instalar TypeScript en su máquina, posteriormente necesitará estos dos comandos para su compilación y ejecución:

```
    tsc index.ts -target ES5 --emitDecoratorMetadata --experimentalDecorators
    node index.js
```

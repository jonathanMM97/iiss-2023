# Asincrono en Scala
Bienvenido a la práctica de asincrono con el lenguaje **Scala**.
Para el manejo de asincronos en Scala se usa *ExecutionContext*, mas específicamente con **Future**.

## Primer paso
Como primer paso para el ejemplo definiremos una función que devuelva un *Future*:

```Scala
    def procesarTarea(nombre: String, duracion: Long): Future[String] = Future {
    println(s"Iniciando tarea: $nombre")
    Thread.sleep(duracion)
    println(s"Tarea completada: $nombre")
    nombre.toUpperCase
    }
```

Como bien se aprecia en el código, la función procesar tarea recibirá un nombre propio y una duración determinada en milisegundos que es con lo que trabaja **Thread.sleep**. Por otra parte cabe resaltar que esta última función "duerme" al método tanto tiempo como se le especifíque en el parametro de entrada. Por lo tanto devolveremos un valor Future, es decir un valor que puede estar disponible en el futuro.

## Segundo paso
Ya tenemos nuestro **"run"**, ahora debemos lanzar los hilos:

```Scala
    val resultados: List[Future[String]] = tareas.map { case (nombre, duracion) =>
    procesarTarea(nombre, duracion)
    }
```

Bien, resultados recibirá una lista de **String** que los devolverá cada hilo.

## Tercer paso
Debemos manejar los resultados a medida que se proporcionen:

```Scala
    val resultadoFinal: Future[List[String]] = Future.sequence(resultados)

    resultadoFinal.onComplete {
    case Success(listaResultados) =>
        println("Resultados:")
        listaResultados.foreach(println)
    case Failure(ex) =>
        println(s"Se produjo un error: ${ex.getMessage}")
    }
```
A medida que los valores Future sean proporcionados, se irán mostrando por pantalla, si por el contrario no pudo ser proporcionado podemos mostrar un mensaje de error gracias a los valores de salida de Future.

Bien, este ejemplo es muy explicativo solo con el código, aun así mostraremos el programa en ejecución:

<h2> Programa en ejecución (Como compilar) </h2>

[scalaFuture.webm](https://github.com/jonathanMM97/iiss-2023/assets/116075515/40c868c3-15ac-4bd9-b76a-045fc71277fe)


# Conclusión
La programación asincrona nos permite aprovechar al máximo los recursos de la CPU al evitar bloqueos y permitir la ejecución concurrente de múltiples tareas, pero hay que tener en cuenta si hay memoria compartida, especialmente cuando varias hebras quieran acceder a un mismo recurso para leer y escribir, en dicho caso debemos tratar la exclusión mutua de forma correcta.

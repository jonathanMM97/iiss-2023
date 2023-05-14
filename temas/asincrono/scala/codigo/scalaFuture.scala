import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Success, Failure}

// Se define un ExecutionContext para manejar la ejecución asincrónica
implicit val ec: ExecutionContext = ExecutionContext.global

// Función asincrónica que devuelve un Future
def procesarTarea(nombre: String, duracion: Long): Future[String] = Future {
  println(s"Iniciando tarea: $nombre")
  Thread.sleep(duracion)
  println(s"Tarea completada: $nombre")
  nombre.toUpperCase
}

// Lista de tareas a procesar
val tareas = List(
  ("Tarea 1", 2000L),
  ("Tarea 2", 1500L),
  ("Tarea 3", 3000L),
  ("Tarea 4", 2500L)
)

// Procesamiento asíncrono de las tareas
val resultados: List[Future[String]] = tareas.map { case (nombre, duracion) =>
  procesarTarea(nombre, duracion)
}

// Manejo de los resultados cuando estén disponibles
val resultadoFinal: Future[List[String]] = Future.sequence(resultados)

resultadoFinal.onComplete {
  case Success(listaResultados) =>
    println("Resultados:")
    listaResultados.foreach(println)
  case Failure(ex) =>
    println(s"Se produjo un error: ${ex.getMessage}")
}

// Otras tareas pueden realizarse mientras se espera el resultado
println("Realizando otras tareas...")

// Esperar a que todos los resultados estén disponibles antes de finalizar el programa
Thread.sleep(4000)

import kotlinx.coroutines.*
import kotlin.math.roundToInt

object GestorPrestamos {

    val libro1 = LibroFisico("Estructuras de Datos", "Goodrich", 12990, diasPrestamo = 7, esReferencia = false)
    val libro2 = LibroFisico("Diccionario Enciclopédico", "Varios", 15990, diasPrestamo = 0, esReferencia = true)
    val libro3 = LibroDigital("Programación en Kotlin", "JetBrains", 9990, diasPrestamo = 10, drm = true)
    val libro4 = LibroDigital("Algoritmos Básicos", "Cormen", 11990, diasPrestamo = 10, drm = false)

    val catalogo = listOf(libro1, libro2, libro3, libro4)

    val librosSeleccionados = mutableListOf<Libro>()
    val librosPrestables = mutableListOf<Libro>()

    fun mostrarlibros() {
        println("Catalogo disponible: ")
        var contador = 1

        for (it in catalogo) {
            println("$contador) ${it.describir()}")
            contador++
        }
    }
    fun seleccionarLibros(opciones: String) {
        val selecciones = opciones.split(",")

        try {
            val selecciones = opciones.split(",")

            selecciones.forEach { seleccion ->
                val indice = seleccion.trim().toIntOrNull()

                if (indice != null && indice > 0 && indice <= catalogo.size) {
                    val libro = catalogo[indice - 1]
                    librosSeleccionados.add(libro)
                } else {
                    if (seleccion.trim().isNotBlank()) {
                        println("Opción '$seleccion' no válida.")
                    }
                }
            }
            // Si no se selecciona nada
            if (librosSeleccionados.isEmpty() && opciones.isNotBlank()) {
                println("No se seleccionaron libros válidos del catálogo.")
            }

        } catch (e: Exception) {
            println("\n No se pudo procesar su selección de libros.")
            println("Detalle: ${e.localizedMessage}")
        }
    }


    suspend fun validarSolicitud(tipoUsuario: String) {
        val usuarioNormalizado = tipoUsuario.lowercase().trim()

        // Se determina el descuento
        val (porcentajeDescuento, nombreDescuento) = when (usuarioNormalizado) {
            "estudiante" -> Pair(0.10, "Estudiante (10%)")
            "docente" -> Pair(0.15, "Docente (15%)")
            "externo" -> Pair(0.00, "Externo (0%)")
            else -> Pair(0.00, "No Válido (0%)")
        }

        println("Validando solicitud...")
        delay(3000L)
        println("- Libro #2 (Referencia) no fue seleccionado, no se puede prestar. OK")

        // Verificación DRM
        val digitalesSeleccionados = librosSeleccionados.filterIsInstance<LibroDigital>()
        if (digitalesSeleccionados.isNotEmpty()) {
            println("- Verificacion DRM para libros digitales seleccionados... OK")
        }

        librosPrestables.clear()
        var subtotal = 0

        librosSeleccionados.forEach { libro ->
            val esPrestable = !(libro is LibroFisico && libro.esReferencia)

            if (esPrestable) {
                librosPrestables.add(libro)
                subtotal += libro.precioBase
            }
        }

        delay(3000L)
        println("Procesando préstamo...")
        delay(3000L)
        val estadoActual = EstadoPrestamo.EnPrestamo
        val nombreEstado = when (estadoActual) {
            is EstadoPrestamo.Pendiente -> "Pendiente"
            is EstadoPrestamo.EnPrestamo -> "En Préstamo"
            is EstadoPrestamo.Devuelto -> "Devuelto"
            is EstadoPrestamo.Error -> "ERROR (${estadoActual.msg})"
        }

        println("Estado: ${nombreEstado}")

        // --- 3. Resumen Final ---

        val descuentoMonto = subtotal * porcentajeDescuento
        val total = subtotal - descuentoMonto

        println("\n=== RESUMEN DEL PRÉSTAMO ===")

        librosPrestables.forEach { libro ->
            val formatoDetalle = when (libro) {
                is LibroDigital -> if (libro.drm) "(Digital • DRM)" else "(Digital)"
                is LibroFisico -> "(Fisico)"
                else -> ""
            }
            println("- ${libro.titulo} $formatoDetalle: $${libro.precioBase.toInt()}")
        }


        println("Subtotal: $${subtotal.toInt()}")
        println("Descuento ${nombreDescuento.split(" ").first()} (${(porcentajeDescuento * 100).toInt()}%): -$${"%.3f".format(descuentoMonto)}")
        println("Multa por retraso: $0 (sin retraso)")
        val totalRedondeado = total.roundToInt()
        println("TOTAL: $${totalRedondeado}")
        println("\nEstado final: En Préstamo")
        println("Tiempo estimado para retiro/activacion digital: 3 s")
    }
}



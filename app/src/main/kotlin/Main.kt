
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("=== SISTEMA BOOKSMART ===")
    GestorPrestamos.mostrarlibros()

    var seleccioncorrecta = false

    while (!seleccioncorrecta) {
    println("Seleccione libros para préstamo (número separado por coma): ")
    val librosInput = readLine().toString().trim()
    GestorPrestamos.seleccionarLibros(librosInput)
        if (GestorPrestamos.librosSeleccionados.isNotEmpty()) {
            seleccioncorrecta = true
        } else if (librosInput.isNotBlank()) {
            println("\n**ERROR:** Ningún libro válido fue seleccionado. Intente de nuevo con números del catálogo.")
        } else {
            println("\n**ADVERTENCIA:** La selección no puede estar vacía. Intente de nuevo.")
        }
    }

    val tiposValidos = listOf("estudiante", "docente", "externo")
    var tipoUsuarioValido = false
    var tipoClienteInput = ""

    while (!tipoUsuarioValido) {
        println("Tipo de usuario (estudiante/docente/externo): ")
        tipoClienteInput = readLine().toString().lowercase().trim()

        if (tiposValidos.contains(tipoClienteInput)) {
            tipoUsuarioValido = true
        } else {
            println("\n **ERROR:** El tipo de usuario '$tipoClienteInput' no es válido. Debe ser uno de: estudiante, docente, externo. Intente de nuevo.")
        }
    }

    try {
        GestorPrestamos.validarSolicitud(tipoClienteInput)
    } catch (e: Exception) {
        println("\nERROR CRÍTICO: La operación no pudo completarse.")
        println("Detalle: ${e.localizedMessage}")
    }
}

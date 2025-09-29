sealed class EstadoPrestamo {
    object Pendiente
    object EnPrestamo
    object Devuelto
    data class Error(val msg: String)
}
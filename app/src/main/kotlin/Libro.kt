open class Libro (val titulo: String, val autor:String,val formato: String, val precioBase:Int, val diasPrestamo:Int){

    open fun describir(): String {
        return "Libro: $titulo - $precioBase "
    }
}
class LibroFisico (titulo: String, autor:String,formato: String, precioBase:Int, diasPrestamo:Int, val esReferencia:Boolean): Libro(titulo,autor,formato,precioBase,diasPrestamo){

    constructor(titulo: String, autor:String, precioBase:Int, diasPrestamo:Int, esReferencia:Boolean): this(titulo,autor,"Libro Fisico",precioBase,diasPrestamo,esReferencia){

    }

    override fun describir(): String {
        if (esReferencia)
            return super.describir() + "(Fisico - Referencia: NO SE PRESTA)"
        else
            return super.describir() + " (Fisico)"
    }
}
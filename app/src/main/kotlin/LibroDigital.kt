class LibroDigital (titulo: String, autor:String,formato: String, precioBase:Int, diasPrestamo:Int, val drm:Boolean):
    Libro(titulo,autor,formato,precioBase,diasPrestamo){

    constructor(titulo: String, autor:String, precioBase:Int, diasPrestamo:Int, drm:Boolean): this(titulo,autor,"Libro Digital",precioBase,diasPrestamo,drm){

    }

    override fun describir(): String {
        if (drm)
            return super.describir() + "( Digital + DRM)"
        else
            return super.describir() + " (Digital)"
    }
}
package mx.unam.ciencias.icc;

/**
 * Enumeración para los campos de un {@link Taekwondoin}.
 */
public enum CampoTaekwondoin {

    /** El nombre del taekwondoín. */
    NOMBRE,
    /** El país de origen del taekwondoín. */
    PAIS,
    /** La cinta del taekwondoín. */
    CINTA,
    /** El peso del taekwondoín. */
    PESO,
    /** La edad del taekwondoín. */
    EDAD;

    /**
     * Regresa una representación en cadena del campo para ser usada en
     * interfaces gráficas.
     * @return una representación en cadena del campo.
     */
    @Override public String toString() {
        switch(this){
            case NOMBRE: 
                return "Nombre";
            case PAIS: 
                return "País";
            case CINTA: 
                return "Cinta";
            case PESO : 
                return "Peso";
            case EDAD: 
                return "Edad";
            default: 
                throw new IllegalArgumentException(); 
        }
    }
}

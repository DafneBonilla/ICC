package mx.unam.ciencias.icc;

/**
 * Enumeración para los campos de un {@link Atleta}.
 */
public enum CampoAtleta {

    /** El nombre del atleta. */
    NOMBRE,
    /** El país de origen del atleta. */
    PAIS,
    /** La cinta del atleta. */
    CINTA,
    /** El peso del atleta. */
    PESO,
    /** La edad del atleta. */
    EDAD;

    /**
     * Regresa una representación en cadena del campo para ser usada en
     * interfaces gráficas.
     * @return una representación en cadena del campo.
     */
    @Override public String toString() {
        switch(this){
            case NOMBRE:  return "Nombre";
            case PAIS:    return "Pais";
            case CINTA:   return "Cinta";
            case PESO:    return "Peso";
            case EDAD:    return "Edad";
            default:      throw new IllegalArgumentException(); 
        }
    }
}

package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de estudiantes.
 */
public class BaseDeDatosTaekwondoines extends BaseDeDatos {

    /**
     * Crea un taekwondoín en blanco.
     * @return un taekwondoín en blanco.
     */
    @Override public Registro creaRegistro() {
        Taekwondoin a = new Taekwondoin(null, null, null, 0.0, 0);
        return a;
    }
}
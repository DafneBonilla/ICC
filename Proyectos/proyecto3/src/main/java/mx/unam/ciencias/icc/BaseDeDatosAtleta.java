package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de atletas.
 */
public class BaseDeDatosAtleta
    extends BaseDeDatos<Atleta, CampoAtleta> {

    /**
     * Crea un atleta en blanco.
     * @return un atleta en blanco.
     */
    @Override public Atleta creaRegistro() {
        Atleta a = new Atleta(null, null, null, 0.0, 0);
        return a;
    }
}

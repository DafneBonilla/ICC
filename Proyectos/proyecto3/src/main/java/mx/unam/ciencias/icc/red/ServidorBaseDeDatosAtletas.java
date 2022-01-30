package mx.unam.ciencias.icc.red;

import java.io.IOException;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosAtleta;
import mx.unam.ciencias.icc.CampoAtleta;
import mx.unam.ciencias.icc.Atleta;

/**
 * Clase para servidores de bases de datos de atletas.
 */
public class ServidorBaseDeDatosAtletas
    extends ServidorBaseDeDatos<Atleta> {

    /**
     * Construye un servidor de base de datos de atletas.
     * @param puerto el puerto dónde escuchar por conexiones.
     * @param archivo el archivo en el disco del cual cargar/guardar la base de
     *                datos.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public ServidorBaseDeDatosAtletas(int puerto, String archivo)
        throws IOException {
        super(puerto, archivo);
    }

    /**
     * Crea una base de datos de atletas.
     * @return una base de datos de atletas.
     */
    @Override public
    BaseDeDatos<Atleta, CampoAtleta> creaBaseDeDatos() {
        BaseDeDatosAtleta bdd = new BaseDeDatosAtleta();
        return bdd;
    }
}

package mx.unam.ciencias.icc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.fx.ControladorInterfazAtletas;
import mx.unam.ciencias.icc.fx.ControladorTablaAtletas;

/**
 * ClienteProyecto3: Parte del cliente para proyecto 3: Aplicación de 
 * base de datos.
 */
public class ClienteProyecto3 extends Application {

    /* Vista de la interfaz atletas. */
    private static final String INTERFAZ_ATLETAS_FXML =
        "fxml/interfaz-atletas.fxml";
    /* Vista de la tabla de atletas. */
    private static final String TABLA_ATLETAS_FXML =
        "fxml/tabla-atletas.fxml";
    /* Ícono de la Facultad de Ciencias. */
    private static final String ICONO_CIENCIAS =
        "icons/ciencias.png";

    /**
     * Inicia la aplicación.
     * @param escenario la ventana principal de la aplicación.
     * @throws Exception si algo sale mal. Literalmente así está definido.
     */
    @Override public void start(Stage escenario) throws Exception {
        ClassLoader cl = getClass().getClassLoader();
        String url = cl.getResource(ICONO_CIENCIAS).toString();
        escenario.getIcons().add(new Image(url));
        escenario.setTitle("Administrador de Atletas de Taekwondo");

        FXMLLoader cargador =
            new FXMLLoader(cl.getResource(TABLA_ATLETAS_FXML));
        GridPane tablaAtleta = (GridPane)cargador.load();
        ControladorTablaAtletas controladorTablaAtletas =
            cargador.getController();

        cargador = new FXMLLoader(cl.getResource(INTERFAZ_ATLETAS_FXML));
        BorderPane interfazAtletas = (BorderPane)cargador.load();
        interfazAtletas.setCenter(tablaAtleta);
        ControladorInterfazAtletas controladorInterfazAtletas =
            cargador.getController();
        controladorInterfazAtletas.setEscenario(escenario);
        controladorInterfazAtletas.setControladorTablaAtletas(
            controladorTablaAtletas);

        Scene escena = new Scene(interfazAtletas);
        escenario.setScene(escena);
        escenario.setOnCloseRequest(e -> controladorInterfazAtletas.salir(null));
        escenario.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

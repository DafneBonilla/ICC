package mx.unam.ciencias.icc.fx;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.BaseDeDatosAtleta;
import mx.unam.ciencias.icc.Atleta;
import mx.unam.ciencias.icc.EventoBaseDeDatos;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.red.Conexion;
import mx.unam.ciencias.icc.red.Mensaje;

/**
 * Clase para el controlador de la ventana principal de la aplicación.
 */
public class ControladorInterfazAtletas {

    /* Vista de la forma para conectarse. */
    private static final String CONECTAR_FXML =
        "fxml/forma-conectar.fxml";
    /* Vista de la forma para realizar búsquedas de atletas. */
    private static final String BUSQUEDA_ATLETAS_FXML =
        "fxml/forma-busqueda-atletas.fxml";
    /* Vista de la forma para agregar/editar atletas. */
    private static final String ATLETA_FXML =
        "fxml/forma-atleta.fxml";

    /* Opción de menu para conectar. */
    @FXML private MenuItem menuConectar;
    /* Opción de menu para desconectar. */
    @FXML private MenuItem menuDesconectar;
    /* Opción de menu para agregar. */
    @FXML private MenuItem menuAgregar;
    /* Opción de menu para editar. */
    @FXML private MenuItem menuEditar;
    /* Opción de menu para eliminar. */
    @FXML private MenuItem menuEliminar;
    /* Opción de menu para buscar. */
    @FXML private MenuItem menuBuscar;
    /* El botón de agregar. */
    @FXML private Button botonAgregar;
    /* El botón de editar. */
    @FXML private Button botonEditar;
    /* El botón de eliminar. */
    @FXML private Button botonEliminar;
    /* El botón de buscar. */
    @FXML private Button botonBuscar;

    /* La ventana. */
    private Stage escenario;
    /* El controlador de tabla. */
    private ControladorTablaAtletas controladorTablaAtletas;
    /* La base de datos. */
    private BaseDeDatosAtleta bdd;
    /* La conexión del cliente. */
    private Conexion<Atleta> conexion;
    /* Si hay o no conexión. */
    private boolean conectado;
    /* Número de atletas seleccionados. */
    private int seleccionados;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        setSeleccionados(0);
        setConectado(false);
        bdd = new BaseDeDatosAtleta();
        bdd.agregaEscucha((e, r1, r2) -> manejaEventoBaseDeDatos(e, r1, r2));
    }

    /* Conecta el cliente con el servidor. */
    @FXML private void conectar(ActionEvent evento) {
        if (conectado)
            return;

        String servidor = null;
        int puerto = -1;

        try {
            FXMLLoader cargador = new FXMLLoader();
            ClassLoader cl = getClass().getClassLoader();
            cargador.setLocation(cl.getResource(CONECTAR_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            escenario.setTitle("Conectar a servidor");
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaConectar controlador = cargador.getController();
            controlador.setEscenario(escenario);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            escenario.showAndWait();
            controladorTablaAtletas.enfocaTabla();
            if (!controlador.isAceptado())
                return;

            servidor = controlador.getServidor();
            puerto = controlador.getPuerto();
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "cargar el diálogo '%s'.", CONECTAR_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }

        try {
            Socket enchufe = new Socket(servidor, puerto);
            conexion = new Conexion<Atleta>(bdd, enchufe);
            new Thread(() -> conexion.recibeMensajes()).start();
            conexion.agregaEscucha((c, m) -> mensajeRecibido(c, m));
            conexion.enviaMensaje(Mensaje.BASE_DE_DATOS);
        } catch (IOException ioe) {
            conexion = null;
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "conectarnos a %s:%d.\n", servidor, puerto);
            dialogoError("Error al establecer conexión", mensaje);
            return;
        }
        setConectado(true);
    }

    /* Desconecta el cliente del servidor. */
    @FXML private void desconectar(ActionEvent evento) {
        if (!conectado)
            return;
        setConectado(false);
        conexion.desconecta();
        conexion = null;
        bdd.limpia();
    }

    /* Cambia la interfaz gráfica dependiendo de hay o no conexión. */
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
        menuConectar.setDisable(conectado);
        menuDesconectar.setDisable(!conectado);
        menuAgregar.setDisable(!conectado);
        menuBuscar.setDisable(!conectado);
        botonAgregar.setDisable(!conectado);
        botonBuscar.setDisable(!conectado);
    }

    /**
     * Termina el programa.
     * @param evento el evento que generó la acción.
     */
    @FXML public void salir(ActionEvent evento) {
        desconectar(evento);
        Platform.exit();
    }

    /* Agrega un nuevo atleta. */
    @FXML private void agregaAtleta(ActionEvent evento) {
        ControladorFormaAtleta controlador =
            construyeDialogoAtleta("Agregar atleta", null);
        if (controlador == null)
            return;
        controlador.setVerbo("Agregar");
        controlador.getEscenario().showAndWait();
        controladorTablaAtletas.enfocaTabla();
        if (!controlador.isAceptado())
            return;
        bdd.agregaRegistro(controlador.getAtleta());
        try {
            conexion.enviaMensaje(Mensaje.REGISTRO_AGREGADO);
            conexion.enviaRegistro(controlador.getAtleta());
        } catch (IOException ioe) {
            dialogoError("Error con el servidor",
                         "No se pudo enviar un atleta a agregar.");
        }
    }

    /* Edita un atleta. */
    @FXML private void editaAtleta(ActionEvent evento) {
        Atleta atleta =
            controladorTablaAtletas.getRenglonSeleccionado();
        ControladorFormaAtleta controlador =
            construyeDialogoAtleta("Editar atleta", atleta);
        if (controlador == null)
            return;
        controlador.setVerbo("Actualizar");
        controlador.getEscenario().showAndWait();
        controladorTablaAtletas.enfocaTabla();
        if (!controlador.isAceptado())
            return;
        try {
            conexion.enviaMensaje(Mensaje.REGISTRO_MODIFICADO);
            conexion.enviaRegistro(atleta);
            conexion.enviaRegistro(controlador.getAtleta());
        } catch (IOException ioe) {
            dialogoError("Error con el servidor",
                         "No se pudieron enviar atletas a modificar.");
        }
        bdd.modificaRegistro(atleta, controlador.getAtleta());
    }

    /* Elimina uno o varios atletas. */
    @FXML private void eliminaAtletas(ActionEvent evento) {
        String sujeto = (seleccionados == 1) ? "atleta" : "atletas";
        String titulo = "Eliminar " + sujeto;
        String mensaje = (seleccionados == 1) ?
            "Esto eliminará al atleta seleccionado" :
            "Esto eliminará a los atletas seleccionados";
        if (!dialogoDeConfirmacion(titulo, mensaje, "¿Está seguro?",
                                   "Eliminar " + sujeto,
                                   "Conservar " + sujeto))
            return;
        for (Atleta atleta :
                 controladorTablaAtletas.getSeleccion()) {
            bdd.eliminaRegistro(atleta);
            try {
                conexion.enviaMensaje(Mensaje.REGISTRO_ELIMINADO);
                conexion.enviaRegistro(atleta);
            } catch (IOException ioe) {
                dialogoError("Error con el servidor",
                             "No se pudo enviar un atleta a eliminar.");
            }
        }
    }

    /* Busca atletas. */
    @FXML private void buscaAtletas(ActionEvent evento) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            FXMLLoader cargador =
                new FXMLLoader(cl.getResource(BUSQUEDA_ATLETAS_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.setTitle("Buscar atletas");
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaBusquedaAtletas controlador;
            controlador = cargador.getController();
            controlador.setEscenario(escenario);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            escenario.showAndWait();
            controladorTablaAtletas.enfocaTabla();
            if (!controlador.isAceptado())
                return;

            Lista<Atleta> resultados =
                bdd.buscaRegistros(controlador.getCampo(),
                                   controlador.getValor());

            controladorTablaAtletas.seleccionaRenglones(resultados);
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "cargar el diálogo '%s'.",
                              BUSQUEDA_ATLETAS_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
        }
    }

    /* Muestra un diálogo con información del programa. */
    @FXML private void acercaDe(ActionEvent evento) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        dialogo.initOwner(escenario);
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.setTitle("Acerca de Administrador de Atletas de Taekwondo.");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Aplicación para administrar atletas de taekwondo.\n"  +
                               "Copyright © 2022 Facultad de Ciencias, UNAM.");
        dialogo.showAndWait();
        controladorTablaAtletas.enfocaTabla();
    }

    /**
     * Define el controlador de tabla.
     * @param controladorTablaAtletas el controlador de tabla.
     */
    public void setControladorTablaAtletas(ControladorTablaAtletas
                                               controladorTablaAtletas) {
        this.controladorTablaAtletas = controladorTablaAtletas;
        controladorTablaAtletas.agregaEscuchaSeleccion(
            n -> setSeleccionados(n));
    }

    /**
     * Define el escenario.
     * @param escenario el escenario.
     */
    public void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

    /* Maneja un evento de cambio en la base de datos. */
    private void manejaEventoBaseDeDatos(EventoBaseDeDatos evento,
                                         Atleta atleta1,
                                         Atleta atleta2) {
        switch (evento) {
        case BASE_LIMPIADA:
            Platform.runLater(() -> limpiaTabla());
            break;
        case REGISTRO_AGREGADO:
            Platform.runLater(() -> agregaAtleta(atleta1));
            break;
        case REGISTRO_ELIMINADO:
            Platform.runLater(() -> eliminaAtleta(atleta1));
            break;
        case REGISTRO_MODIFICADO:
            Platform.runLater(() -> reordenaTabla());
            break;
        }
    }

    /* Actualiza la interfaz dependiendo del número de renglones
     * seleccionados. */
    private void setSeleccionados(int seleccionados) {
        this.seleccionados = seleccionados;
        menuEliminar.setDisable(seleccionados == 0);
        menuEditar.setDisable(seleccionados != 1);
        botonEliminar.setDisable(seleccionados == 0);
        botonEditar.setDisable(seleccionados != 1);
    }

    /* Crea un diálogo con una pregunta que hay que confirmar. */
    private boolean dialogoDeConfirmacion(String titulo,
                                          String mensaje, String pregunta,
                                          String aceptar, String cancelar) {
        Alert dialogo = new Alert(AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText(pregunta);

        ButtonType si = new ButtonType(aceptar);
        ButtonType no = new ButtonType(cancelar, ButtonData.CANCEL_CLOSE);
        dialogo.getButtonTypes().setAll(si, no);

        Optional<ButtonType> resultado = dialogo.showAndWait();
        controladorTablaAtletas.enfocaTabla();
        return resultado.get() == si;
    }

    /* Recibe los mensajes de la conexión. */
    private void mensajeRecibido(Conexion<Atleta> conexion, Mensaje mensaje) {
        switch (mensaje) {
        case BASE_DE_DATOS:
            manejaBaseDeDatos(conexion);
            break;
        case REGISTRO_AGREGADO:
            manejaRegistroAlterado(conexion, mensaje);
            break;
        case REGISTRO_ELIMINADO:
            manejaRegistroAlterado(conexion, mensaje);
            break;
        case REGISTRO_MODIFICADO:
            manejaRegistroModificado(conexion);
            break;
        case DESCONECTAR:
            Platform.runLater(() -> desconectar(null));
            break;
        case DETENER_SERVICIO:
            // Se ignora.
            break;
        case ECO:
            // Se ignora.
            break;
        case INVALIDO:
            Platform.runLater(() -> dialogoError("Error con el servidor",
                                                 "Mensaje inválido recibido. " +
                                                 "Se finalizará la conexión."));
            break;
        }
    }

    /* Maneja el mensaje BASE_DE_DATOS. */
    private void manejaBaseDeDatos(Conexion<Atleta> conexion) {
        try {
            conexion.recibeBaseDeDatos();
        } catch (IOException ioe) {
            String m = "No se pudo recibir la base de datos. " +
                "Se finalizará la conexión.";
            Platform.runLater(() -> dialogoError("Error con el servidor", m));
            return;
        }
    }

    /* Maneja los mensajes REGISTRO_AGREGADO y REGISTRO_MODIFICADO. */
    private void manejaRegistroAlterado(Conexion<Atleta> conexion,
                                        Mensaje mensaje) {
        Atleta a;
        try {
            a = conexion.recibeRegistro();
        } catch (IOException ioe) {
            String m = "No se pudo recibir un registro. " +
                "Se finalizará la conexión.";
            Platform.runLater(() -> dialogoError("Error con el servidor", m));
            return;
        }
        if (mensaje == Mensaje.REGISTRO_AGREGADO)
            bdd.agregaRegistro(a);
        else
            bdd.eliminaRegistro(a);
    }

    /* Maneja el mensaje REGISTRO_MODIFICADO. */
    private void manejaRegistroModificado(Conexion<Atleta> conexion) {
        Atleta a1, a2;
        try {
            a1 = conexion.recibeRegistro();
            a2 = conexion.recibeRegistro();
        } catch (IOException ioe) {
            String m = "No se pudieron recibir registros." +
                "Se finalizará la conexión.";
            Platform.runLater(() -> dialogoError("Error con el servidor", m));
            return;
        }
        bdd.modificaRegistro(a1, a2);
    }

    /* Construye un diálogo para crear o editar un atleta. */
    private ControladorFormaAtleta
    construyeDialogoAtleta(String titulo,
                               Atleta atleta) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            FXMLLoader cargador =
                new FXMLLoader(cl.getResource(ATLETA_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.setTitle(titulo);
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaAtleta controlador = cargador.getController();
            controlador.setEscenario(escenario);
            controlador.setAtleta(atleta);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            return controlador;
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de cargar " +
                              "el diálogo '%s'.", ATLETA_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
            return null;
        }
    }

    /* Muestra un diálogo de advertencia. */
    private void dialogoAdvertencia(String titulo,
                                    String mensaje,
                                    boolean limpia) {
        Alert dialogo = new Alert(AlertType.WARNING);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        if (limpia)
            dialogo.setOnCloseRequest(e -> limpiaTabla());
        dialogo.show();
        controladorTablaAtletas.enfocaTabla();
    }

    /* Muestra un diálogo de error. */
    private void dialogoError(String titulo, String mensaje) {
        if (conectado)
            desconectar(null);
        Alert dialogo = new Alert(AlertType.ERROR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.setOnCloseRequest(e -> limpiaTabla());
        dialogo.show();
        controladorTablaAtletas.enfocaTabla();
    }

    /* Agrega un atleta a la tabla. */
    private void agregaAtleta(Atleta atleta) {
        controladorTablaAtletas.agregaRenglon(atleta);
    }

    /* Elimina un atleta de la tabla. */
    private void eliminaAtleta(Atleta atleta) {
        controladorTablaAtletas.eliminaRenglon(atleta);
    }

    /* Reordena la tabla. */
    private void reordenaTabla() {
        controladorTablaAtletas.reordena();
    }

    /* Limpia la tabla. */
    private void limpiaTabla() {
        controladorTablaAtletas.limpiaTabla();
    }
}

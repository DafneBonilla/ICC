package mx.unam.ciencias.icc.fx;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import mx.unam.ciencias.icc.Atleta;
import mx.unam.ciencias.icc.Lista;

/**
 * Clase para el controlador de la tabla para mostrar la base de datos.
 */
public class ControladorTablaAtletas {

    /* La tabla. */
    @FXML private TableView<Atleta> tabla;

    /* La columna del nombre. */
    @FXML private TableColumn<Atleta, String> columnaNombre;
    /* La columna del pais. */
    @FXML private TableColumn<Atleta, String> columnaPais;
    /* La columna de la cinta. */
    @FXML private TableColumn<Atleta, String> columnaCinta;
    /* La columna del peso. */
    @FXML private TableColumn<Atleta, Number> columnaPeso;
    /* La columna de la edad. */
    @FXML private TableColumn<Atleta, Number> columnaEdad;

    /* El modelo de la selección. */
    TableView.TableViewSelectionModel<Atleta> modeloSeleccion;
    /* La selección. */
    private ObservableList<TablePosition> seleccion;
    /* Lista de escuchas de selección. */
    private Lista<EscuchaSeleccion> escuchas;
    /* Los renglones en la tabla. */
    private ObservableList<Atleta> renglones;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        renglones = tabla.getItems();
        modeloSeleccion = tabla.getSelectionModel();
        modeloSeleccion.setSelectionMode(SelectionMode.MULTIPLE);
        seleccion = modeloSeleccion.getSelectedCells();
        ListChangeListener<TablePosition> lcl = c -> cambioEnSeleccion();
        seleccion.addListener(lcl);
        columnaNombre.setCellValueFactory(c -> c.getValue().nombreProperty());
        columnaPais.setCellValueFactory(c -> c.getValue().paisProperty());
        columnaCinta.setCellValueFactory(c -> c.getValue().cintaProperty());
        columnaPeso.setCellValueFactory(c -> c.getValue().pesoProperty());
        columnaEdad.setCellValueFactory(c -> c.getValue().edadProperty());
        escuchas = new Lista<EscuchaSeleccion>();
    }

    /* Notifica a los escuchas que hubo un cambio en la selección. */
    private void cambioEnSeleccion() {
        for (EscuchaSeleccion escucha : escuchas)
            escucha.renglonesSeleccionados(seleccion.size());
    }

    /**
     * Limpia la tabla.
     */
    public void limpiaTabla() {
        renglones.clear();
    }

    /**
     * Agrega un renglón a la tabla.
     * @param atleta el renglón a agregar.
     */
    public void agregaRenglon(Atleta atleta) {
        renglones.add(atleta);
        tabla.sort();
    }

    /**
     * Elimina un renglón de la tabla.
     * @param atleta el renglón a eliminar.
     */
    public void eliminaRenglon(Atleta atleta) {
        renglones.remove(atleta);
        tabla.sort();
    }

    /**
     * Selecciona renglones de la tabla.
     * @param atleta los renglones a seleccionar.
     */
    public void seleccionaRenglones(Lista<Atleta> atletas) {
        modeloSeleccion.clearSelection();
        for (Atleta atleta : atletas)
            modeloSeleccion.select(atleta);
    }

    /**
     * Regresa una lista con los registros seleccionados en la tabla.
     * @return una lista con los registros seleccionados en la tabla.
     */
    public Lista<Atleta> getSeleccion() {
        Lista<Atleta> seleccionados = new Lista<Atleta>();
        for (TablePosition tp : seleccion) {
            int r = tp.getRow();
            seleccionados.agregaFinal(renglones.get(r));
        }
        return seleccionados;
    }

    /**
     * Regresa el atleta seleccionado en la tabla.
     * @return el atleta seleccionado en la tabla.
     */
    public Atleta getRenglonSeleccionado() {
        int r = seleccion.get(0).getRow();
        return renglones.get(r);
    }

    /**
     * Agrega un escucha de selección.
     * @param escucha el escucha a agregar.
     */
    public void agregaEscuchaSeleccion(EscuchaSeleccion escucha) {
        escuchas.agregaFinal(escucha);
    }

    /**
     * Fuerza un reordenamiento de la tabla.
     */
    public void reordena() {
        tabla.sort();
    }

    /**
     * Enfoca la tabla.
     */
    public void enfocaTabla() {
        tabla.requestFocus();
    }
}

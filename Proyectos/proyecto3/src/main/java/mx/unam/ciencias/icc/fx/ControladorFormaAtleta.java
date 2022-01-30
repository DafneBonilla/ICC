package mx.unam.ciencias.icc.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mx.unam.ciencias.icc.Atleta;

/**
 * Clase para el controlador del contenido del diálogo para editar y crear
 * atletas.
 */
public class ControladorFormaAtleta extends ControladorForma {

    /* La entrada verificable para el nombre. */
    @FXML private EntradaVerificable entradaNombre;
    /* La entrada verificable para el país de origen. */
    @FXML private EntradaVerificable entradaPais;
    /* La entrada verificable para la cinta. */
    @FXML private EntradaVerificable entradaCinta;
    /* La entrada verificable para el peso. */
    @FXML private EntradaVerificable entradaPeso;
    /* La entrada verificable para la edad. */
    @FXML private EntradaVerificable entradaEdad;

    /* El valor del nombre. */
    private String nombre;
    /* El valor del país. */
    private String pais;
    /* El valor de la cinta. */
    private String cinta;
    /* El valor del peso. */
    private double peso;
    /* El valor de la edad. */
    private int edad;

    /* El atleta creado o editado. */
    private Atleta atleta;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaNombre.setVerificador(n -> verificaNombre(n));
        entradaPais.setVerificador(p -> verificaPais(p));
        entradaCinta.setVerificador(c -> verificaCinta(c));
        entradaPeso.setVerificador(w -> verificaPeso(w));
        entradaEdad.setVerificador(e -> verificaEdad(e));

        entradaNombre.textProperty().addListener(
            (o, v, n) -> verificaAtleta());
        entradaPais.textProperty().addListener(
            (o, v, n) -> verificaAtleta());
        entradaCinta.textProperty().addListener(
            (o, v, n) -> verificaAtleta());
        entradaPeso.textProperty().addListener(
            (o, v, n) -> verificaAtleta());
        entradaEdad.textProperty().addListener(
            (o, v, n) -> verificaAtleta());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        actualizaAtleta();
        aceptado = true;
        escenario.close();
    }

    /**
     * Define el atleta del diálogo.
     * @param atleta el nuevo atleta del diálogo.
     */
    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
        if (atleta == null)
            return;
        this.atleta = new Atleta(null, null, null, 0.0, 0);
        this.atleta.actualiza(atleta);
        entradaNombre.setText(atleta.getNombre());
        entradaPais.setText(atleta.getPais());
        entradaCinta.setText(atleta.getCinta());
        String p = String.format("%2.2f", atleta.getPeso());
        entradaPeso.setText(p);
        entradaEdad.setText(String.valueOf(atleta.getEdad()));
    }

    /* Verifica que los cuatro campos sean válidos. */
    private void verificaAtleta() {
        boolean n = entradaNombre.esValida();
        boolean p = entradaPais.esValida();
        boolean c = entradaCinta.esValida();
        boolean w = entradaPeso.esValida();
        boolean e = entradaEdad.esValida();
        botonAceptar.setDisable(!n || !p || !c || !w || !e);
    }

    /* Verifica que el nombre sea válido. */
    private boolean verificaNombre(String n) {
        if (n == null || n.isEmpty())
            return false;
        nombre = n;
        return true;
    }

    /* Verifica que el país sea válido. */
    private boolean verificaPais(String p) {
        if (p == null || p.isEmpty())
            return false;
        pais = p;
        return true;
    }

    /* Verifica que la cinta sea válida. */
    private boolean verificaCinta(String c) {
        if (c == null || c.isEmpty())
            return false;
        cinta = c;
        return true;
    }

    /* Verifica que el peso sea válido. */
    private boolean verificaPeso(String w) {
        if (w == null || w.isEmpty())
            return false;
        try {
            peso = Double.parseDouble(w);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return peso >= 40.0 && peso <= 90.0;
    }

    /* Verifica que la edad sea válida. */
    private boolean verificaEdad(String e) {
        if (e == null || e.isEmpty())
            return false;
        try {
            edad = Integer.parseInt(e);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return edad >= 16 && edad <= 35;
    }

    /* Actualiza al atleta, o lo crea si no existe. */
    private void actualizaAtleta() {
        if (atleta != null) {
            atleta.setNombre(nombre);
            atleta.setPais(pais);
            atleta.setCinta(cinta);
            atleta.setPeso(peso);
            atleta.setEdad(edad);
        } else {
            atleta = new Atleta(nombre, pais, cinta, peso, edad);
        }
    }

    /**
     * Regresa el atleta del diálogo.
     * @return el atleta del diálogo.
     */
    public Atleta getAtleta() {
        return atleta;
    }

    /**
     * Define el verbo del botón de aceptar.
     * @param verbo el nuevo verbo del botón de aceptar.
     */
    public void setVerbo(String verbo) {
        botonAceptar.setText(verbo);
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaNombre.requestFocus();
    }
}

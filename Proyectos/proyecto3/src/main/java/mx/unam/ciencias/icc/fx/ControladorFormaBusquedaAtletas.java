package mx.unam.ciencias.icc.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import mx.unam.ciencias.icc.CampoAtleta;

/**
 * Clase para el controlador del contenido del diálogo para buscar atletas.
 */
public class ControladorFormaBusquedaAtletas extends ControladorForma {

    /* El combo del campo. */
    @FXML private ComboBox<CampoAtleta> opcionesCampo;
    /* El campo de texto para el valor. */
    @FXML private EntradaVerificable entradaValor;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaValor.setVerificador(s -> verificaValor(s));
        entradaValor.textProperty().addListener(
            (o, v, n) -> revisaValor(null));
    }

    /* Revisa el valor después de un cambio. */
    @FXML private void revisaValor(ActionEvent evento) {
        Tooltip.install(entradaValor, getTooltip());
        String s = entradaValor.getText();
        botonAceptar.setDisable(!entradaValor.esValida());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        aceptado = true;
        escenario.close();
    }

    /* Obtiene la pista. */
    private Tooltip getTooltip() {
        String m = "";
        switch (opcionesCampo.getValue()) {
        case NOMBRE:
            m = "Buscar por nombre necesita al menos un carácter";
            break;
        case PAIS:
            m = "Buscar por país necesita al menos un carácter";
            break;
        case CINTA:
            m = "Buscar por cinta necesita al menos un carácter";
            break;
        case PESO:
            m = "Buscar por peso necesita un número entre 40.0 y 90.0";
            break;
        case EDAD:
            m = "Buscar por edad necesita un número entre 16 y 35";
            break;
        }
        return new Tooltip(m);
    }

    /* Verifica el valor. */
    private boolean verificaValor(String s) {
        switch (opcionesCampo.getValue()) {
        case NOMBRE:   return verificaNombre(s);
        case PAIS:     return verificaPais(s);
        case CINTA:    return verificaCinta(s);
        case PESO:     return verificaPeso(s);
        case EDAD:     return verificaEdad(s);
        default:       return false;
        }
    }

    /* Verifica que el nombre a buscar sea válido. */
    private boolean verificaNombre(String n) {
        return n != null && !n.isEmpty();
    }

    /* Verifica que el país a buscar sea válido. */
    private boolean verificaPais(String p) {
        return p != null && !p.isEmpty();
    }

    /* Verifica que la cinta a buscar sea válida. */
    private boolean verificaCinta(String c) {
        return c != null && !c.isEmpty();
    }

    /* Verifica que el peso a buscar sea válido. */
    private boolean verificaPeso(String w) {
        if (w == null || w.isEmpty())
            return false;
        double peso = -1.0;
        try {
            peso = Double.parseDouble(w);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return peso >= 40.0 && peso <= 90.0;
    }

    /* Verifica que la edad a buscar sea válida. */
    private boolean verificaEdad(String e) {
        if (e == null || e.isEmpty())
            return false;
        int edad = -1;
        try {
            edad = Integer.parseInt(e);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return edad >= 16 && edad <= 35;
    }

    /**
     * Regresa el campo seleccionado.
     * @return el campo seleccionado.
     */
    public CampoAtleta getCampo() {
        return opcionesCampo.getValue();
    }

    /**
     * Regresa el valor ingresado.
     * @return el valor ingresado.
     */
    public Object getValor() {
        switch (opcionesCampo.getValue()) {
        case NOMBRE:   return entradaValor.getText();
        case PAIS:     return entradaValor.getText();
        case CINTA:    return entradaValor.getText();
        case PESO:     return Double.parseDouble(entradaValor.getText());
        case EDAD:     return Integer.parseInt(entradaValor.getText());
        default:       return entradaValor.getText(); // No debería ocurrir.
        }
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaValor.requestFocus();
    }
}

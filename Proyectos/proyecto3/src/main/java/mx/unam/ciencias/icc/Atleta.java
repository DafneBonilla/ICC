package mx.unam.ciencias.icc;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase para representar atletas de taekwondo. Un atleta tiene nombre, país
 * de origen, cinta, peso y edad. La clase implementa {@link Registro}, por lo que
 * puede representarse con una línea de texto y definir sus propiedades con una
 * línea de texto; además de determinar si sus campos cazan valores arbitrarios.
 */
public class Atleta implements Registro<Atleta, CampoAtleta> {

    /* Nombre del atleta. */
    private StringProperty nombre;
    /* País de origen del atleta. */
    private StringProperty pais;
    /* Cinta del atleta. */
    private StringProperty cinta;
    /* Peso del atleta. */
    private DoubleProperty peso;
    /* Edad del atleta. */
    private IntegerProperty edad;

    /**
     * Define el estado inicial de un atleta.
     * @param nombre el nombre del atleta.
     * @param pais el país de origen del atleta.
     * @param cinta la cinta del atleta.
     * @param peso el peso del atleta.
     * @param edad la edad del atleta.
     */
    public Atleta(String nombre,
                  String pais,
                  String cinta,
                  double peso,
                  int    edad) {
        this.nombre = new SimpleStringProperty(nombre);
        this.pais   = new SimpleStringProperty(pais);
        this.cinta  = new SimpleStringProperty(cinta);
        this.peso   = new SimpleDoubleProperty(peso);
        this.edad   = new SimpleIntegerProperty(edad);
    }

    /**
     * Regresa el nombre del atleta.
     * @return el nombre del atleta.
     */
    public String getNombre() {
        return nombre.get();
    }

    /**
     * Define el nombre del atleta.
     * @param nombre el nuevo nombre del atleta.
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty nombreProperty() {
        return this.nombre;
    }

    /**
     * Regresa el país de origen del atleta.
     * @return el país de origen del atleta.
     */
    public String getPais() {
        return pais.get();
    }

    /**
     * Define el país de origen del atleta.
     * @param pais el nuevo país de origen del atleta.
     */
    public void setPais(String pais) {
        this.pais.set(pais);
    }

    /**
     * Regresa la propiedad del país.
     * @return la propiedad del país.
     */
    public StringProperty paisProperty() {
        return this.pais;
    }

    /**
     * Regresa la cinta del atleta.
     * @return la cinta del atleta.
     */
    public String getCinta() {
        return cinta.get();
    }

    /**
     * Define la cinta del atleta.
     * @param cinta la nueva cinta del atleta.
     */
    public void setCinta(String cinta) {
        this.cinta.set(cinta);
    }

    /**
     * Regresa la propiedad de la cinta.
     * @return la propiedad de la cinta.
     */
    public StringProperty cintaProperty() {
        return this.cinta;
    }

    /**
     * Regresa el peso del atleta.
     * @return el peso del atleta.
     */
    public double getPeso() {
        return peso.get();
    }

    /**
     * Define el peso del atleta.
     * @param peso el nuevo peso del atleta.
     */
    public void setPeso(double peso) {
        this.peso.set(peso);
    }

    /**
     * Regresa la propiedad del peso.
     * @return la propiedad del peso.
     */
    public DoubleProperty pesoProperty() {
        return this.peso;
    }

    /**
     * Regresa la edad del atleta.
     * @return la edad del atleta.
     */
    public int getEdad() {
        return edad.get();
    }

    /**
     * Define la edad del atleta.
     * @param edad la nueva edad del atleta.
     */
    public void setEdad(int edad) {
        this.edad.set(edad);
    }

    /**
     * Regresa la propiedad de la edad.
     * @return la propiedad de la edad.
     */
    public IntegerProperty edadProperty() {
        return this.edad;
    }

    /**
     * Regresa una representación en cadena del atleta.
     * @return una representación en cadena del atleta.
     */
    @Override public String toString() {
        String cadena = String.format("Nombre   : %s\n" +
                                      "Pais     : %s\n" +
                                      "Cinta    : %s\n" +
                                      "Peso     : %2.2f\n" +
                                      "Edad     : %d",
                                      nombre.get(), pais.get(), cinta.get(), peso.get(), edad.get());
        return cadena;
    }

    /**
     * Nos dice si el objeto recibido es un atleta igual al que manda llamar
     * el método.
     * @param objeto el objeto con el que el atleta se comparará.
     * @return <code>true</code> si el objeto o es un atleta con las mismas
     *         propiedades que el objeto que manda llamar al método,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (!(objeto instanceof Atleta))
            return false;
        Atleta atleta = (Atleta)objeto;
        if (atleta.getNombre().equals(getNombre()) && atleta.getPais().equals(getPais()) && atleta.getCinta().equals(getCinta())
        && atleta.getPeso() == getPeso() && atleta.getEdad() == getEdad()) {
            return true;
        } else 
          return false;
    }

    /**
     * Regresa el atleta serializado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Atleta#deserializa}.
     * @return la serialización del atleta en una línea de texto.
     */
    @Override public String serializa() {
        String linea = String.format("%s\t%s\t%s\t%2.2f\t%d\n",
                                     nombre.get(), pais.get(), cinta.get(), peso.get(), edad.get());
        return linea;
    }

    /**
     * Deserializa una línea de texto en las propiedades del atleta. La
     * serialización producida por el método {@link Atleta#serializa} debe
     * ser aceptada por este método.
     * @param linea la línea a deserializar.
     * @throws ExcepcionLineaInvalida si la línea recibida es nula, vacía o no
     *         es una serialización válida de un atleta.
     */
    @Override public void deserializa(String linea) {
        linea = linea.trim().strip();
        if (linea == null || linea.equals("")) 
            throw new ExcepcionLineaInvalida("Línea Inválida");
        String [] a = linea.split("\t");
        if (a.length != 5)
            throw new ExcepcionLineaInvalida();
        setNombre(a[0]);  
        setPais(a[1]);  
        setCinta(a[2]);  
        try {  
          setPeso(Double.parseDouble(a[3]));
          setEdad(Integer.parseInt(a[4]));
        } catch (NumberFormatException nfe) {  
            throw new ExcepcionLineaInvalida();
        }
    }

    /**
     * Actualiza los valores del atleta con los del atleta recibido.
     * @param atleta el atleta con el cual actualizar los valores.
     * @throws IllegalArgumentException si el atleta es <code>null</code>.
     */
    public void actualiza(Atleta atleta) {
        if (!(atleta instanceof Atleta))
            throw new IllegalArgumentException("Atleta Inválido");
        Atleta a = (Atleta) atleta;
        setNombre(a.getNombre());
        setPais(a.getPais());
        setCinta(a.getCinta());
        setPeso(a.getPeso());
        setEdad(a.getEdad());
    }

    /**
     * Nos dice si el atleta caza el valor dado en el campo especificado.
     * @param campo el campo que hay que cazar.
     * @param valor el valor con el que debe cazar el campo del registro.
     * @return <code>true</code> si:
     *         <ul>
     *           <li><code>campo</code> es {@link CampoAtleta#NOMBRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del nombre del atleta.</li>
     *           <li><code>campo</code> es {@link CampoAtleta#PAIS} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del país de origen del atleta.</li>
     *           <li><code>campo</code> es {@link CampoAtleta#CINTA} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena de la cinta del atleta.</li>
     *           <li><code>campo</code> es {@link CampoAtleta#PESO} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual al peso del
     *              atleta.</li>
     *           <li><code>campo</code> es {@link CampoAtleta#EDAD} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual a la edad del
     *              atleta.</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo es <code>null</code>.
     */
    @Override public boolean caza(CampoAtleta campo, Object valor) {
        if (!(campo instanceof CampoAtleta))
            throw new IllegalArgumentException();
        CampoAtleta c = (CampoAtleta)campo;
        switch(c){
            case NOMBRE: return cazaNombre(valor);
            case PAIS:   return cazaPais(valor);
            case CINTA:  return cazaCinta(valor);
            case PESO:   return cazaPeso(valor);
            case EDAD:   return cazaEdad(valor);
            default:     return false;
        }
    }
    //Métodos auxiliares
    private boolean cazaNombre(Object n) {
        if (!(n instanceof String))
            return false;
        String a = (String) n;
        if (a.isEmpty()) 
            return false;
        return getNombre().indexOf(a) != -1;
    }
    private boolean cazaPais(Object n) {
        if (!(n instanceof String))
            return false;
        String a = (String) n;
        if (a.isEmpty()) 
            return false;
        return getPais().indexOf(a) != -1;
    }
    private boolean cazaCinta(Object n) {
        if (!(n instanceof String))
            return false;
        String a = (String) n;
        if (a.isEmpty()) 
            return false;
        return getCinta().indexOf(a) != -1;
    }
    private boolean cazaPeso(Object n) {
        if (!(n instanceof Double)) 
            return false;
        Double a = (Double) n;
        return getPeso() >= a.doubleValue();
    }
    private boolean cazaEdad(Object n) {
        if (!(n instanceof Integer)) 
            return false;
        Integer a = (Integer) n;
        return getEdad() >= a.intValue();
    }
}

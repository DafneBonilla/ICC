package mx.unam.ciencias.icc;

/**
 * Clase para representar atletas de taekwondo (taekwondoines). Un taekwondoin
 * tiene nombre, país de origen, cinta(grado en taekwondo) peso y edad. La clase 
 * implementa {@link Registro}, por lo que puede serializarse en una línea de texto
 * y deserializarse de una línea de texto; además de determinar si sus campos cazan 
 * valores arbitrarios y actualizarse con los valores de otro taekwondoín.
 */
public class Taekwondoin implements Registro {

    /* Nombre del taekwondoín. */
    private String nombre;
    /* País de origen del taekwondoín. */
    private String pais;
    /* Cinta del taekwondoín. */
    private String cinta;
    /* Peso del taekwondoín. */
    private double peso;
    /* Edad del taekwondoín. */
    private int edad;

    /**
     * Define el estado inicial de un taekwondoín.
     * @param nombre el nombre del taekwondoín.
     * @param país el país de origen del taekwondoín.
     * @param cinta la cinta del taekwondoín.
     * @param peso el peso del taekwondoín.
     * @param edad la edad del taekwondoín.
     */
    public Taekwondoin(String nombre,
                       String pais,
                       String cinta,
                       double peso,
                       int    edad) {
        this.nombre = nombre;
        this.pais = pais;
        this.cinta = cinta;
        this.peso = peso;
        this.edad = edad;
    }

    /**
     * Regresa el nombre del taekwondoín.
     * @return el nombre del taekwondoín.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre del taekwondoín.
     * @param nombre el nuevo nombre del taekwondoín.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el país de origen del taekwondoín.
     * @return el país de origen del taekwondoín.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Define el país de origen del taekwondoín.
     * @param pais el nuevo país de origen del taekwondoín.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Regresa la cinta del taekwondoín.
     * @return la cinta del taekwondoín.
     */
    public String getCinta() {
      return cinta;
    }

    /**
     * Define la cinta del taekwondoín.
     * @param cinta la nueva cinta del taekwondoín.
     */
    public void setCinta(String cinta) {
        this.cinta = cinta;
    }

    /**
     * Regresa el peso del taekwondoín.
     * @return el peso del taekwondoín.
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Define el peso del taekwondoín.
     * @param peso el nuevo peso del taekwondoín.
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Regresa la edad del taekwondoin.
     * @return la edad del taekwondoin.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Define la edad del taekwondoin.
     * @param edad la nueva edad del taekwondoin.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Regresa una representación en cadena del taekwondoin.
     * @return una representación en cadena del taekwondoin.
     */
    @Override public String toString() {
        String cadena = String.format("Nombre   : %s\n" +
                                      "País     : %s\n" +
                                      "Cinta    : %s\n" +
                                      "Peso     : %2.2f\n" +
                                      "Edad     : %d",
                                      nombre, pais, cinta, peso, edad);
        return cadena;
    }

    /**
     * Nos dice si el objeto recibido es un taekwondoín igual al que manda llamar
     * el método.
     * @param objeto el objeto con el que el taekwondoín se comparará.
     * @return <code>true</code> si el objeto recibido es un taekwondoín con las
     *         mismas propiedades que el objeto que manda llamar al método,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (!(objeto instanceof Taekwondoin))
            return false;
        Taekwondoin taekwondoin = (Taekwondoin)objeto;
        if (taekwondoin.nombre.equals(nombre) && taekwondoin.pais.equals(pais) 
            && taekwondoin.cinta.equals(cinta) && taekwondoin.edad==edad && 
            taekwondoin.peso==peso) {
            return true;
        } else 
            return false;
    }

    /**
     * Regresa el taekwondoín serializado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Taekwondoin#deserializa}.
     * @return la serialización del taekwondoín en una línea de texto.
     */
    @Override public String serializa() {
        String linea = String.format("%s\t%s\t%s\t%2.2f\t%d\n",
                                     nombre, pais, cinta, peso, edad);
        return linea;
    }

    /**
     * Deserializa una línea de texto en las propiedades del taekwondoín. La
     * serialización producida por el método {@link Taekwondoin#serializa} debe
     * ser aceptada por este método.
     * @param linea la línea a deserializar.
     * @throws ExcepcionLineaInvalida si la línea recibida es nula, vacía o no
     *         es una serialización válida de un taekwondoín.
     */
    @Override public void deserializa(String linea) {
        linea = linea.strip();
        if (linea == null || linea.equals("")) 
          throw new ExcepcionLineaInvalida();
        String [] a = linea.split("\t");
        if (a.length != 5)
          throw new ExcepcionLineaInvalida();
        nombre = a[0];  
        pais = a[1];
        cinta = a[2];
        try {  
          peso = Double.parseDouble(a[3]);
          String.format("%2.2f", peso);
          edad = Integer.parseInt(a[4]);
        } catch (NumberFormatException nfe) {  
            throw new ExcepcionLineaInvalida();
        }
    }

    /**
     * Actualiza los valores del taekwondoín con los del registro recibido.
     * @param registro el registro con el cual actualizar los valores.
     * @throws IllegalArgumentException si el registro no es instancia de {@link
     *         Taekwondoin}.
     */
    @Override public void actualiza(Registro registro) {
        if (!(registro instanceof Taekwondoin))
            throw new IllegalArgumentException();
        Taekwondoin a = (Taekwondoin)registro;
        setNombre(a.nombre);
        setPais(a.pais);
        setCinta(a.cinta);
        setPeso(a.peso);
        setEdad(a.edad);
    }

    /**
     * Nos dice si el taekwondoín caza el valor dado en el campo especificado.
     * @param campo el campo que hay que cazar.
     * @param valor el valor con el que debe cazar el campo del registro.
     * @return <code>true</code> si:
     *         <ul>
     *           <li><code>campo</code> es {@link CampoTaekwondoin#NOMBRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del nombre del taekwondoín.</li>
     *           <li><code>campo</code> es {@link CampoTaekwondoin#PAIS} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del  país de origen del taekwondoín.</li>
     *           <li><code>campo</code> es {@link CampoTaekwondoin#CINTA} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena de la cinta del taekwondoín.</li>
     *           <li><code>campo</code> es {@link CampoTaekwondoin#PESO} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual al peso del 
     *              taekwondoín.</li>
     *           <li><code>campo</code> es {@link CampoTaekwondoin#EDAD} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual a la edad del
     *              taekwondoín.</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo no es instancia de {@link
     *         CampoTaekwondoin}.
     */
    @Override public boolean caza(Enum campo, Object valor) {   
        if (!(campo instanceof CampoTaekwondoin))
            throw new IllegalArgumentException();
        CampoTaekwondoin c = (CampoTaekwondoin)campo;
        switch(c){
            case NOMBRE:  return cazaNombre(valor);
            case PAIS:    return cazaPais(valor);
            case CINTA:   return cazaCinta(valor);
            case PESO:    return cazaPeso(valor);
            case EDAD:    return cazaEdad(valor);
            default:      return false;
        }
    }
    //Métodos auxiliares
    private boolean cazaNombre(Object n) {
      if (!(n instanceof String))
        return false;
      String a = (String) n;
      if (a.isEmpty()) 
        return false;
      return nombre.indexOf(a) != -1;
    }
    private boolean cazaPais(Object n) {
      if (!(n instanceof String)) 
        return false;
      String a = (String) n;
      return pais.indexOf(a) != -1;
    }
    private boolean cazaCinta(Object n) {
      if (!(n instanceof String)) 
        return false;
      String a = (String) n;
      return cinta.indexOf(a) != -1;
    }
    private boolean cazaPeso(Object n) {
      if (!(n instanceof Double)) 
        return false;
      Double a = (Double) n;
      return peso >= a.doubleValue();
    }
    private boolean cazaEdad(Object n) {
      if (!(n instanceof Integer)) 
        return false;
      Integer a = (Integer) n;
      return edad >= a.intValue();
    }
}
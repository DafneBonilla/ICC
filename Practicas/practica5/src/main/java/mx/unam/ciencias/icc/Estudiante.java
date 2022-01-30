package mx.unam.ciencias.icc;

/**
 * Clase para representar estudiantes. Un estudiante tiene nombre, número de
 * cuenta, promedio y edad. La clase implementa {@link Registro}, por lo que
 * puede serializarse en una línea de texto y deserializarse de una línea de
 * texto; además de determinar si sus campos cazan valores arbitrarios y
 * actualizarse con los valores de otro estudiante.
 */
public class Estudiante implements Registro {

    /* Nombre del estudiante. */
    private String nombre;
    /* Número de cuenta. */
    private int cuenta;
    /* Pormedio del estudiante. */
    private double promedio;
    /* Edad del estudiante.*/
    private int edad;

    /**
     * Define el estado inicial de un estudiante.
     * @param nombre el nombre del estudiante.
     * @param cuenta el número de cuenta del estudiante.
     * @param promedio el promedio del estudiante.
     * @param edad la edad del estudiante.
     */
    public Estudiante(String nombre,
                      int    cuenta,
                      double promedio,
                      int    edad) {
        // Aquí va su código.
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.promedio = promedio;
        this.edad = edad;
    }

    /**
     * Regresa el nombre del estudiante.
     * @return el nombre del estudiante.
     */
    public String getNombre() {
        // Aquí va su código.
        return nombre;
    }

    /**
     * Define el nombre del estudiante.
     * @param nombre el nuevo nombre del estudiante.
     */
    public void setNombre(String nombre) {
        // Aquí va su código.
        this.nombre = nombre;
    }

    /**
     * Regresa el número de cuenta del estudiante.
     * @return el número de cuenta del estudiante.
     */
    public int getCuenta() {
        // Aquí va su código.
        return cuenta;
    }

    /**
     * Define el número cuenta del estudiante.
     * @param cuenta el nuevo número de cuenta del estudiante.
     */
    public void setCuenta(int cuenta) {
        // Aquí va su código.
        this.cuenta = cuenta;
    }

    /**
     * Regresa el promedio del estudiante.
     * @return el promedio del estudiante.
     */
    public double getPromedio() {
        // Aquí va su código.
        return promedio;
    }

    /**
     * Define el promedio del estudiante.
     * @param promedio el nuevo promedio del estudiante.
     */
    public void setPromedio(double promedio) {
        // Aquí va su código.
        this.promedio = promedio;
    }

    /**
     * Regresa la edad del estudiante.
     * @return la edad del estudiante.
     */
    public int getEdad() {
        // Aquí va su código.
        return edad;
    }

    /**
     * Define la edad del estudiante.
     * @param edad la nueva edad del estudiante.
     */
    public void setEdad(int edad) {
        // Aquí va su código.
        this.edad = edad;
    }

    /**
     * Regresa una representación en cadena del estudiante.
     * @return una representación en cadena del estudiante.
     */
    @Override public String toString() {
        // Aquí va su código.
        String cadena = String.format("Nombre   : %s\n" +
                                      "Cuenta   : %09d\n" +
                                      "Promedio : %2.2f\n" +
                                      "Edad     : %d",
                                      nombre, cuenta, promedio, edad);
                                      return cadena;
    }

    /**
     * Nos dice si el objeto recibido es un estudiante igual al que manda llamar
     * el método.
     * @param objeto el objeto con el que el estudiante se comparará.
     * @return <code>true</code> si el objeto recibido es un estudiante con las
     *         mismas propiedades que el objeto que manda llamar al método,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        // Aquí va su código.
        if (!(objeto instanceof Estudiante))
            return false;
        Estudiante estudiante = (Estudiante)objeto;
        if (estudiante.nombre.equals(nombre) && estudiante.cuenta==cuenta &&
        estudiante.edad==edad && estudiante.promedio==promedio) {
            return true;
        }
        else {
          return false;
      }
    }

    /**
     * Regresa el estudiante serializado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Estudiante#deserializa}.
     * @return la serialización del estudiante en una línea de texto.
     */
    @Override public String serializa() {
        // Aquí va su código.
        String linea = String.format("%s\t%d\t%2.2f\t%d\n",
                                     nombre, cuenta, promedio, edad);
        return linea;
    }

    /**
     * Deserializa una línea de texto en las propiedades del estudiante. La
     * serialización producida por el método {@link Estudiante#serializa} debe
     * ser aceptada por este método.
     * @param linea la línea a deserializar.
     * @throws ExcepcionLineaInvalida si la línea recibida es nula, vacía o no
     *         es una serialización válida de un estudiante.
     */
    @Override public void deserializa(String linea) {
        // Aquí va su código.
        linea = linea.strip();
        if (linea == null || linea.equals("")) 
          throw new ExcepcionLineaInvalida();
        String [] a = linea.split("\t");
        if (a.length != 4)
          throw new ExcepcionLineaInvalida();
        nombre = a[0];  
        try {  
          cuenta = Integer.parseInt(a[1]);
          promedio = Double.parseDouble(a[2]);
          String.format("%2.2f", promedio);
          edad = Integer.parseInt(a[3]);
        } catch (NumberFormatException nfe) {  
            throw new ExcepcionLineaInvalida();
        }
    }

    /**
     * Actualiza los valores del estudiante con los del registro recibido.
     * @param registro el registro con el cual actualizar los valores.
     * @throws IllegalArgumentException si el registro no es instancia de {@link
     *         Estudiante}.
     */
    @Override public void actualiza(Registro registro) {
        // Aquí va su código.
        if (!(registro instanceof Estudiante))
            throw new IllegalArgumentException();
        
        Estudiante a = (Estudiante) registro;
        setNombre(a.nombre);
        setCuenta(a.cuenta);
        setPromedio(a.promedio);
        setEdad(a.edad);

    }

    /**
     * Nos dice si el estudiante caza el valor dado en el campo especificado.
     * @param campo el campo que hay que cazar.
     * @param valor el valor con el que debe cazar el campo del registro.
     * @return <code>true</code> si:
     *         <ul>
     *           <li><code>campo</code> es {@link CampoEstudiante#NOMBRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del nombre del estudiante.</li>
     *           <li><code>campo</code> es {@link CampoEstudiante#CUENTA} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual a la cuenta del
     *              estudiante.</li>
     *           <li><code>campo</code> es {@link CampoEstudiante#PROMEDIO} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual al promedio del
     *              estudiante.</li>
     *           <li><code>campo</code> es {@link CampoEstudiante#EDAD} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual a la edad del
     *              estudiante.</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo no es instancia de {@link
     *         CampoEstudiante}.
     */
    @Override public boolean caza(Enum campo, Object valor) {
        // Aquí va su código.     
        if (!(campo instanceof CampoEstudiante))
            throw new IllegalArgumentException();
        CampoEstudiante c = (CampoEstudiante)campo;
        switch(c){
            case NOMBRE:
              return cazaNombre(valor);
            case CUENTA:
              return cazaCuenta(valor);
            case EDAD:
              return cazaEdad(valor);
            case PROMEDIO:
              return cazaPromedio(valor);
            default:
              return false;
        }
    }

      //Método auxiliar
      
        private boolean cazaNombre(Object n){
          if(!(n instanceof String))
            return false;
          String a = (String) n;
          if(a.isEmpty()) 
            return false;
          return nombre.indexOf(a) != -1;
        }
        private boolean cazaCuenta(Object n){
          if(!(n instanceof Integer)) 
            return false;
          Integer a = (Integer) n;
          return cuenta >= a.intValue();
        }
        private boolean cazaEdad(Object n){
          if(!(n instanceof Integer)) 
            return false;
          Integer a = (Integer) n;
          return edad >= a.intValue();
        }
        private boolean cazaPromedio(Object n){
          if(!(n instanceof Double)) 
            return false;
          Double a = (Double) n;
          return promedio >= a.doubleValue();
        }
}
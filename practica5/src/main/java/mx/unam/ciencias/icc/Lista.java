package mx.unam.ciencias.icc;

import java.util.NoSuchElementException;

/**
 * <p>Clase para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas son iterables utilizando sus nodos. Las listas no aceptan a
 * <code>null</code> como elemento.</p>
 */
public class Lista {

    /**
     * Clase interna para nodos.
     */
    public class Nodo {

        /* El elemento del nodo. */
        private Object elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(Object elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }

        /**
         * Regresa el nodo anterior del nodo.
         * @return el nodo anterior del nodo.
         */
        public Nodo getAnterior() {
            // Aquí va su código.
            return anterior;
        }

        /**
         * Regresa el nodo siguiente del nodo.
         * @return el nodo siguiente del nodo.
         */
        public Nodo getSiguiente() {
            // Aquí va su código.
            return siguiente;
        }

        /**
         * Regresa el elemento del nodo.
         * @return el elemento del nodo.
         */
        public Object get() {
            // Aquí va su código.
            return elemento;
        }
    }

    //Método auxiliar para buscar un nodo
    private Nodo buscaNodo(Object buscado){ 
        Nodo n = cabeza;
        while (n != null){
            if(n.elemento.equals(buscado)){
                return n;
            }
            n = n.siguiente;
        }
        return null;
    }
    
    //Método auxiliar para eliminar un nodo
    private void eliminarNodo(Nodo porEliminar){ 
        Nodo s;
        Nodo a;
        if (rabo == cabeza) {
            cabeza = rabo = null;
            longitud = 0;
        }
        else if (porEliminar == cabeza) {
            s = porEliminar.siguiente;
            s.anterior = null;
            cabeza = s;
            longitud --;
        }
        else if (porEliminar == rabo){
            s = porEliminar.anterior;
            s.siguiente = null;
            rabo = s;
            longitud --;
        }
        else {
            a = porEliminar.anterior;
            s = porEliminar.siguiente;
            a.siguiente = s;
            s.anterior = a;
            longitud --;
        }
    }

    //Método auxiliar para buscar un nodo por índice 
    private Nodo buscaNodoIndice(int indiceBuscado){ 
        Nodo n = cabeza;
        int i = 0;
        while (n != null){
            if(i == indiceBuscado){
                return n;
            } 
            i++;
            n = n.siguiente;
        }
        return null;
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        if (cabeza != null){ 
            return false;
        }
        else {
          return true;
        }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(Object elemento) {
        // Aquí va su código.
        if(elemento == null) 
            throw new IllegalArgumentException("Elemento null");
        Nodo n = new Nodo(elemento);
        longitud++;
        if (rabo == null){
            rabo = cabeza = n;
        } else {
          rabo.siguiente = n;
          n.anterior = rabo;
          rabo = n;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(Object elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException("Elemento null");
        longitud ++;
        Nodo n = new Nodo(elemento);
        if (rabo == null){
            rabo = cabeza = n;
        } else {
          cabeza.anterior = n;
          n.siguiente = cabeza;
          cabeza = n;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, Object elemento) {
        // Aquí va su código.
        if (elemento == null){
            throw new IllegalArgumentException("Elemento null");
        }
        if (i <= 0){
            agregaInicio(elemento);
        }
        else if (i >= longitud){
            agregaFinal(elemento);
        }
        else {
            Nodo s = buscaNodoIndice(i);
            Nodo n = new Nodo(elemento);
            Nodo a;
            a = s.anterior;
            n.anterior = a;
            a.siguiente = n;
            n.siguiente = s;
            s.anterior = n;
            longitud ++;
        }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(Object elemento) {
        // Aquí va su código.
        if (elemento == null){
            return;
        }
        Nodo marcado = buscaNodo(elemento);
        if (marcado == null){
            return;
        }
        eliminarNodo(marcado);
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object eliminaPrimero() {
        // Aquí va su código.
        if (cabeza == null){
            throw new NoSuchElementException("Lista Vacia");
        }
        Object n;
        n = cabeza.elemento;
        if(rabo == cabeza){
            rabo = cabeza = null;
            longitud = 0;
        }
        if (cabeza != null){
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
            longitud --;
        }
        return n;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object eliminaUltimo() {
        // Aquí va su código.
        if (cabeza == null){
            throw new NoSuchElementException("Lista Vacia");
        }
        Object n;
        n = rabo.elemento;
        if(rabo == cabeza){
            rabo = cabeza = null;
            longitud = 0;
        }
        if (rabo != null){
            rabo = rabo.anterior;
            rabo.siguiente = null;
            longitud --;
        }
        return n;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(Object elemento) {
        // Aquí va su código.
        Nodo a = cabeza;
        while ( a != null) {
            if(a.elemento.equals(elemento))
                return true;
            a = a.siguiente;
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista reversa() {
        // Aquí va su código.
        Lista r = new Lista();
        Nodo a = cabeza;
        while ( a != null) {
            r.agregaInicio(a.elemento);
            a = a.siguiente;
        }
        return r;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista copia() {
        // Aquí va su código.
        Lista r = new Lista();
        Nodo a = cabeza;
        while ( a != null) {
            r.agregaFinal(a.elemento);
            a = a.siguiente;
        }
        return r;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    public void limpia() {
        // Aquí va su código.
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object getPrimero() {
        // Aquí va su código.
        if (cabeza == null){
            throw new NoSuchElementException("Lista vacia");
        } else {
            return cabeza.elemento;
        }
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public Object getUltimo() {
        // Aquí va su código.
        if (rabo == null){
            throw new NoSuchElementException("Lista vacia");
        } else {
            return rabo.elemento;
        }
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public Object get(int i) {
        // Aquí va su código.
        if (i < 0 || i >= longitud){
            throw new ExcepcionIndiceInvalido("Indice invalido");
        }
        int a = 0;
        Nodo b = cabeza;
        while (a++ < i){
            b = b.siguiente;
        }
        return b.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(Object elemento) {
        // Aquí va su código.
        int a = 0;
        Nodo b = cabeza;
        while (b != null){
            if(b.elemento.equals(elemento)){
                return a;
            }
            a ++;
            b = b.siguiente;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (cabeza == null){
            return "[]";
        }
        String r = "[";
        for (int a = 0; a < longitud - 1; a++)
            r += String.format("%s, ", this.get(a));
            r += String.format("%s]", this.get(longitud-1));
            return r;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        // Aquí va su código.
        if (!(objeto instanceof Lista))
            return false;
        
        Lista lista = (Lista)objeto;
        
        if(lista.getLongitud() != longitud)
            return false;
        
        Nodo n = lista.cabeza;
        Nodo s = cabeza;
        while(n != null) {
            if(n.elemento.equals(s.elemento)) {
                n = n.siguiente;
                s = s.siguiente;
            } else 
                return false;
        }
        return true;
    }

    /**
     * Regresa el nodo cabeza de la lista.
     * @return el nodo cabeza de la lista.
     */
    public Nodo getCabeza() {
        // Aquí va su código.
        return cabeza;
    }

    /**
     * Regresa el nodo rabo de la lista.
     * @return el nodo rabo de la lista.
     */
    public Nodo getRabo() {
        // Aquí va su código.
        return rabo;
    }
}

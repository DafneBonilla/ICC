package mx.unam.ciencias.icc;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length; i++) {
            int m = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[m]) < 0)
                    m = j;
            }
            intercambia(arreglo, i, m);
        }
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSortAuxiliar(arreglo, comparador, 0, arreglo.length - 1);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int left = 0;
        int right = arreglo.length - 1;
        int half, a;
        while (left < right) {
            half = (left + right) / 2;
            a = comparador.compare(elemento, arreglo[half]);
            if (a == 0)
                return half;
            if (a > 0)
                left = half + 1;
            else
                right = half - 1;
        }
        if (right == left) {
            if (comparador.compare(elemento, arreglo[left]) == 0)
                return left;
        }
        return -1;
    }
    //Método auxiliar intercambia
    private static <T> void intercambia(T[] arreglo, int i, int j) {
        T elem1 = arreglo[i];
        T elem2 = arreglo[j];
        arreglo[i] = elem2;
        arreglo[j] = elem1;
    }
    //Método auxiliar quickSort
    public static <T> void quickSortAuxiliar(T[] array, Comparator<T> comparador, int n, int m) {
        if (m <= n) 
            return;
        int i = n + 1;
        int j = m;
        while (i < j) {
            if (comparador.compare(array[i], array[n]) > 0 && comparador.compare(array[n], array[j]) >= 0) {
                intercambia(array, i, j);
                i++;
                j--;
            } 
            else if (comparador.compare(array[n], array[i]) >= 0)
                i++;
            else
                j--;
        }
        if (comparador.compare(array[i], array[n]) > 0)
            i--;
        intercambia(array, n, i);
        quickSortAuxiliar(array, comparador, n, i - 1);
        quickSortAuxiliar(array, comparador, i + 1, m);
    }
}

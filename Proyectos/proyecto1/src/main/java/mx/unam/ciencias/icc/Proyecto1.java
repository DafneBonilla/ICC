package mx.unam.ciencias.icc;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Proyecto 1: Base datos para un taekwondoín.
 */
public class Proyecto1 {

    /* Hace búsquedas por nombre, país de origen y cinta en la base de datos. */
    private static void busquedas(BaseDeDatosTaekwondoines bdd) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");

        System.out.printf("Ingresa el nombre de un taekwondoín para buscar: ");
        String nombre = sc.next();

        Lista r = bdd.buscaRegistros(CampoTaekwondoin.NOMBRE, nombre);
        if (r.esVacia()) {
            System.out.printf("\nNo se hallaron taekwondoínes " +
                              "con el nombre \"%s\".\n",
                              nombre);
        } else {
            System.out.printf("\nSe hallaron los siguientes " +
                              "taekwondoínes con el nombre \"%s\":\n\n",
                              nombre);
            Lista.Nodo nodo = r.getCabeza();
            while (nodo != null) {
                System.out.println(nodo.get().toString() + "\n");
                nodo = nodo.getSiguiente();
            }
        }

        System.out.printf("Ingresa el país de origen de un taekwondoín para buscar: ");
        String pais = "";
        try {
            pais = sc.next();
        } catch (InputMismatchException ime) {
            System.out.printf("Se entró un país de origen inválido. " +
                              "Se interpretará como cadena vacía.\n");
        }
        r = bdd.buscaRegistros(CampoTaekwondoin.PAIS, pais);
        if (r.esVacia()) {
            System.out.printf("\nNo se hallaron taekwondoínes " +
                              "con el país de origen \"%s\".\n",
                              pais);
        } else {
            System.out.printf("\nSe hallaron los siguientes " +
                              "países de origen con el taekwondoín \"%s\":\n\n",
                              pais);
            Lista.Nodo nodo = r.getCabeza();
            while (nodo != null) {
                System.out.println(nodo.get().toString() + "\n");
                nodo = nodo.getSiguiente();
            }
        }

        System.out.printf("Ingresa la cinta de un taekwondoín para buscar: ");
        String cinta = "";
        try {
            cinta = sc.next();
        } catch (InputMismatchException ime) {
            System.out.printf("Se entró una cinta inválida. " +
                              "Se interpretará como cadena vacía.\n");
        }
        r = bdd.buscaRegistros(CampoTaekwondoin.CINTA, cinta);
        if (r.esVacia()) {
            System.out.printf("\nNo se hallaron taekwondoínes " +
                              "con la cinta indicada \"%s\".\n",
                              cinta);
        } else {
            System.out.printf("\nSe hallaron las siguientes " +
                              "cintas con el taekwondoín \"%s\":\n\n",
                              cinta);
            Lista.Nodo nodo = r.getCabeza();
            while (nodo != null) {
                System.out.println(nodo.get().toString() + "\n");
                nodo = nodo.getSiguiente();
            }
        }

    }

    /* Crea una base de datos y la llena a partir de los datos que el usuario
       escriba a través del teclado. Después la guarda en disco duro y la
       regresa. */
    private static BaseDeDatosTaekwondoines escritura(String nombreArchivo) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");

        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
            System.out.printf("El archivo \"%s\" ya existe.\n" +
                              "Presiona Ctrl-C si no quieres reescribirlo, " +
                              "o Enter para continuar...\n", nombreArchivo);
            sc.nextLine();
        }

        System.out.println("Ingresa atletas de taekwondo a la base de datos.\n" +
                           "Cuando desees terminar, deja el nombre en blanco.\n");

        BaseDeDatosTaekwondoines bdd = new BaseDeDatosTaekwondoines();

        do {
            String nombre;
            String pais;
            String cinta;
            double peso = 0.0;
            int edad = 0;
            
            System.out.printf("Nombre   : ");
            nombre = sc.next();
            if (nombre.equals(""))
                break;
            try {
                System.out.printf("País     : ");
                pais = sc.next();
                System.out.printf("Cinta    : ");
                cinta = sc.next();
                System.out.printf("Peso(kg) : ");
                peso = sc.nextDouble();
                System.out.printf("Edad     : ");
                edad = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("\nNúmero inválido: Se descartará " +
                                   "este taekwondoín.\n");
                continue;
            }
            Taekwondoin e = new Taekwondoin(nombre,
                                            pais,
                                            cinta,
                                            peso,
                                            edad);
            bdd.agregaRegistro(e);
            System.out.println();
        } while (true);

        int n = bdd.getNumRegistros();
        if (n == 1)
            System.out.printf("\nSe agregó 1 taekwondoín.\n");
        else
            System.out.printf("\nSe agregaron %d taekwondoínes.\n", n);

        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            bdd.guarda(out);
            out.close();
        } catch (IOException ioe) {
            System.out.printf("No pude guardar en el archivo \"%s\".\n",
                              nombreArchivo);
            System.exit(1);
        }

        System.out.printf("\nBase de datos guardada exitosamente en \"%s\".\n",
                          nombreArchivo);

        return bdd;
    }

    /* Crea una base de datos y la llena cargándola del disco duro. Después la
       regresa. */
    private static BaseDeDatosTaekwondoines lectura(String nombreArchivo) {
        BaseDeDatosTaekwondoines bdd = new BaseDeDatosTaekwondoines();

        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            InputStreamReader isIn = new InputStreamReader(fileIn);
            BufferedReader in = new BufferedReader(isIn);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            System.out.printf("No pude cargar del archivo \"%s\".\n",
                              nombreArchivo);
            System.exit(1);
        }

        System.out.printf("Base de datos cargada exitosamente de \"%s\".\n\n",
                          nombreArchivo);

        Lista r = bdd.getRegistros();
        Lista.Nodo nodo = r.getCabeza();
        while (nodo != null) {
            System.out.println(nodo.get().toString() + "\n");
            nodo = nodo.getSiguiente();
        }

        return bdd;
    }

    /* Imprime en pantalla cómo debe usarse el programa y lo termina. */
    private static void uso() {
        System.out.println("Uso: java -jar proyecto1.jar [-g|-c] <archivo>");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length != 2)
            uso();

        String bandera = args[0];
        String nombreArchivo = args[1];

        if (!bandera.equals("-g") && !bandera.equals("-c"))
            uso();

        BaseDeDatosTaekwondoines bdd;

        if (bandera.equals("-g"))
            bdd = escritura(nombreArchivo);
        else
            bdd = lectura(nombreArchivo);

        busquedas(bdd);
    }
}

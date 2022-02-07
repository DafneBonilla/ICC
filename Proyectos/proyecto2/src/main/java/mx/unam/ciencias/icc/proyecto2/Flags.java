package mx.unam.ciencias.icc.proyecto2;

import mx.unam.ciencias.icc.proyecto2.Lista;
import mx.unam.ciencias.icc.proyecto2.Strip;
import mx.unam.ciencias.icc.proyecto2.ArgumentHandler;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Flags {

  private String[] flag;

  /**
    * Constructor de la clase.
    * Define el estado inicial del arreglo flag.
    * @param flag arreglo bandera que contiene todos los argumentos del programa inicial.
    */
  public Flags(String[] flag) {
    this.flag = flag;
  }

  /** 
    * Realiza la operación correspondiente a cada bandera si es ingresada en el programa.
    * @param lines argumentos ingresados al programa.
    * @param reverse bandera reversa que imprime las líneas en orden inverso.
    * @param output bandera salida que guarda su salida en un archivo llamado como el identificador.
    * @param file archivo en donde se guarda la salida de la bandera output.
    * @throws IOException si ocurre un error al guardar file.
    */
  public void runFlag(Lista<Strip> lines, boolean reverse, boolean output, String file) throws IOException {
    Lista<Strip> ordenada = lines.mergeSort(lines);
    if (reverse) {
      ordenada = ordenada.reversa();
      ArgumentHandler.printConsole(ordenada);
    } 
    else if (output) {
      ArgumentHandler.guarda(file, ordenada);
      ArgumentHandler.printConsole(ordenada);
      System.out.printf("\nGuardado exitosamente en \"%s\"\n", file);
    }
    else if(reverse && output) {
      ordenada = ordenada.reversa();
      ArgumentHandler.printConsole(ordenada);
      ArgumentHandler.guarda(file, ordenada);
      System.out.printf("\nGuardado exitosamente en \"%s\"\n", file);
    } else
        ArgumentHandler.printConsole(ordenada);
  }

  /** 
    * Determinar si el programa se ejecuta por la entrada estándar o se leen uno o más archivos.
    * @param args bandera ingresada al programa.
    * @throws IOException si no se da un archivo para guardar cuando se ingrese la bandera output.
    */
  public void run() throws IOException {
    String file = null;
    boolean reverse = false;
    boolean output = false;
    String[] id = {".txt", ".dbd", ".doc", ".pdf", ".docx", ".dic", ".htm", ".epub", ".csv", ".html", ".md", ".tex"};
    Lista<Strip> lines = new Lista<Strip>();
    if (flag.length == 0) {
      standard();
    } else {
      for (int i = 0; i < flag.length; i++) {
        for (int a = 0; a < id.length; a++) {
          if (flag[i].contains(id[a]))
            ArgumentHandler.carga(flag[i], lines);
          if (flag[i].equals("-r"))
            reverse = true;
          if (flag[i].equals("-o")){
            output = true;
            try {
              file = flag[i + 1];
              i += 1;
            } catch (Exception e) {
              output = false;
              operation();
              throw new IOException("No se dio un archivo para guardar.");
            }
          }
        }
      }
      runFlag(lines, reverse, output, file);
    }
  }

  /** 
    * Lee y ordena lo ingresado por el usuario a través de la línea de comandos.
    * @param args bandera ingresada al programa.
    * @throws IOException si ocurre un error en la entrada estándar.
    */
  public void standard() throws IOException{
    Lista<Strip> list = new Lista<>();
    Lista<Strip> ordenada = new Lista<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader((System.in), "UTF-8"))) {
      String lines = br.readLine();
      while (lines != null) {
        list.agregaFinal(new Strip(lines));
        lines = br.readLine();
      }
    } catch (IOException ioe) {
      operation();
      System.exit(1);
    }
    System.out.print("\n");
    ordenada = list.mergeSort(list);
    ArgumentHandler.printConsole(ordenada);
  }

  /** 
    * Imprime la descripción de como opera el programa.
    * @param args arreglo bandera ingresado al programa.
    */
  private static void operation() {
		System.out.println("\nUso: java -jar proyecto2.jar [OPCIONES] ... [ARCHIVOS] ...\n" +
                       "Escribe la concatenación ordenada de todos los ARCHIVO(s) a la salida estándar.\n" +
                       "Sin ARCHIVO se lee la entrada estándar.\n\n" +
                       "Opciones:\n" +
                       "-r:\t Imprime las líneas en orden inverso.\n" +
                       "-o:\t Define la salida a un archivo llamado como el identificador.\n");
	}
}
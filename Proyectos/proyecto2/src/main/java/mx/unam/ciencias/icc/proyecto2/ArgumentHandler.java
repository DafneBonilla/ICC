package mx.unam.ciencias.icc.proyecto2;

import mx.unam.ciencias.icc.proyecto2.Strip;
import mx.unam.ciencias.icc.proyecto2.Lista;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class ArgumentHandler {

  /* Constructor privado para evitar instanciación. */
  private ArgumentHandler() {}
      
  /**
    * Guarda el archivo ordenado en el disco.
    * @param fileName archivo ordenado a guardar en el disco.
    * @param list lista ordenada contenida en el archivo.
    * @throws IOException si ocurre un error al guardar el archivo en el disco.
    */
  public static <T> void guarda(String fileName, Lista<T> list) throws IOException{
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName);
      OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
      BufferedWriter out = new BufferedWriter(osOut);
      for (T n : list){
        out.write(n.toString());
        out.newLine();
      }
      out.close();  
    } catch (IOException ioe){
      System.out.printf("Ocurrió un error al guardar el archivo \"%s\"", fileName);
			System.exit(1);
    }
  }
  
  /**
    * Guarda las líneas de texto de un archivo en una lista dada.
    * @param fileName nombre del archivo del cual se cargan las líneas.
    * @param list lista donde se guardarán las líneas de texto.
    * @throws IOException si ocurre un error al cargar el archivo.
    */
  public static void carga(String fileName, Lista<Strip> list) throws IOException {
    try {
      FileInputStream fileIn = new FileInputStream(fileName);
      InputStreamReader isIn = new InputStreamReader(fileIn);
      BufferedReader in = new BufferedReader(isIn);
      String line = in.readLine();
      while (line != null) {
        list.agregaFinal(new Strip(line));
        line = in.readLine();  
      }
    } catch (IOException ioe) {
      System.out.printf("Ocurrió un error al cargar el archivo \"%s\".\n", fileName);
			System.exit(1);
    }
  }

  /**
    * Imprime todos los elementos de una lista en la consola.
    * @param list lista a imprimir.
    */
  public static <T> void printConsole(Lista<T> list) {
    for (T n : list){
      System.out.print(n.toString() + "\n");
    }
  }
}
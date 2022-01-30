package mx.unam.ciencias.icc.proyecto2;

import mx.unam.ciencias.icc.proyecto2.Lista;
import mx.unam.ciencias.icc.proyecto2.Flags;
import java.io.IOException;

public class Proyecto2 {
  public static void main(String[] args) throws IOException {
    String[] mainFlag = args;
    Flags initFlag = new Flags(mainFlag);
    initFlag.run();
  }
}
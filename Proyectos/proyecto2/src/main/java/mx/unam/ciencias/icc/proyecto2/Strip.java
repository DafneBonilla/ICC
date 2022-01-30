package mx.unam.ciencias.icc.proyecto2;

import java.text.Collator;

public class Strip implements Comparable<Strip> {
  
  String sentence;
  
  /**
  * Constructor de la clase. 
  * Define el estado inicial de la oración.
  * @param sentence oración a comparar.
  */
  public Strip(String sentence){
    this.sentence = sentence;
  }

  /**
  * Regresa una representación en cadena de la oración.
  * @return sentence una representación en cadena de la oración.
  */
  @Override public String toString(){
    return sentence;
  }

  /**
  * Regresa la comparación entre dos oraciones con formato.
  * @param fileName nombre del archivo en el cual se guardará la oración.
  * @return la comparación entre dos oraciones con formato.
  */
  @Override public int compareTo(Strip fileName) {
    Collator collator = Collator.getInstance();
    collator.setStrength(Collator.PRIMARY);
    return collator.compare(sentence.replaceAll("\\P{L}+",""), fileName.toString().replaceAll("\\P{L}+",""));
  }
}
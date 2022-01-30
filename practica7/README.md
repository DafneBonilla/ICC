Introducción a Ciencias de la Computación
=========================================

Práctica 7: Iteradores
----------------------

### Fecha de entrega: martes 30 de noviembre, 2021

Deben convertir su clase
[Lista](https://aztlan.fciencias.unam.mx/gitlab/2022-1-icc/practica7/-/blob/main/src/main/java/mx/unam/ciencias/icc/Lista.java)
a que sea iterable; consecuentemente, deben modificar
[BaseDeDatos](https://aztlan.fciencias.unam.mx/gitlab/2022-1-icc/practica7/-/blob/main/src/main/java/mx/unam/ciencias/icc/BaseDeDatos.java)
a usar esta nueva versión de la clase Lista.

Una vez que hayan terminado sus clases, deben compilar al hacer:

```
$ mvn compile
```

No debe haber **ninguna** advertencia al compilar; si su práctica muestra
advertencias al compilar, se considerará como si no compilara.

También deben pasar todas sus pruebas unitarias al hacer:

```
$ mvn test
```

Por último, se debe ejecutar correctamente el programa escrito en la clase
[Practica7](https://aztlan.fciencias.unam.mx/gitlab/2022-1-icc/practica7/-/blob/main/src/main/java/mx/unam/ciencias/icc/Practica7.java)
al hacer:

```
$ mvn install
...
$ java -jar target/practica7.jar -g archivo.db # guarda la base de datos
...
$ java -jar target/practica7.jar -c archivo.db # carga la base de datos
```

Los únicos archivos que deben modificar son:

* `BaseDeDatos.java`,
* `BaseDeDatosEstudiantes.java`,
* `CampoEstudiante.java`,
* `Estudiante.java` y
* `Lista.java`.

*No deben modificar de ninguna manera ninguno de los otros archivos de la práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```
$ git clone https://canek@aztlan.fciencias.unam.mx/gitlab/2022-1-icc/practica7.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica
7](https://aztlan.fciencias.unam.mx/~canek/2022-1-icc/practica7/apidocs/index.html)

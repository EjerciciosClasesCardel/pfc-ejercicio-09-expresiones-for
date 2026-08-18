# Clase 9 — Expresiones for

Fundamentos de Programación Funcional y Concurrente
Escuela de Ingeniería de Sistemas y Computación, Universidad del Valle
Carlos Andrés Delgado Saavedra

Generar todas las combinaciones posibles y quedarse con las que cumplen una
condición. Escrito con recursión sería un enredo de tres funciones anidadas;
con una expresión `for` cabe en cinco líneas.

## La expresión for

Una expresión `for` con `yield` produce una colección. Cada generador recorre
un rango, y las condiciones intercaladas descartan lo que no sirve:

```scala
for {
  x <- 1 to 5
  y <- x to 5
  if x + y == 6
} yield (x, y)
```

Eso devuelve `Vector((1,5), (2,4), (3,3))`. Fíjese en dos cosas: el segundo
generador arranca en `x`, así que nunca produce parejas con `y` menor que
`x`; y la condición se evalúa con los valores de los dos generadores.

La expresión no es un ciclo. Se traduce a `flatMap`, `map` y un filtro, y
por eso produce un valor en lugar de modificar algo.

## Lo que hay que resolver

En `app/src/main/scala/taller/Ejercicio.scala`:

```scala
def ejercicio(n: Int): List[(Int, Int, Int)]
```

Devuelve todas las ternas `(a, b, c)` de enteros entre 1 y `n` que cumplen

```
a² + b² = c²
```

con la restricción de que `a ≤ b ≤ c`. Sin esa restricción cada terna
aparecería dos veces, una por cada orden de los catetos.

El resultado va ordenado de forma ascendente: primero por `a`, y entre las
que comparten `a`, por `b`. Si los generadores se escriben en ese orden, el
orden sale solo.

### Ejemplos

| Llamada | Resultado |
|---|---|
| `ejercicio(10)` | `List((3,4,5), (6,8,10))` |
| `ejercicio(20)` | `List((3,4,5), (5,12,13), (6,8,10), (8,15,17), (9,12,15), (12,16,20))` |
| `ejercicio(2)` | `List()` |

Con `n = 30` salen once ternas y con `n = 100`, cincuenta y dos.

La terna (3,4,5) es la más pequeña: 9 + 16 = 25. La (6,8,10) es la misma
multiplicada por dos, y por eso las ternas se repiten a escala a medida que
`n` crece.

### El tipo del resultado

Una expresión `for` sobre rangos devuelve un `IndexedSeq`, no una `List`. La
firma pide una lista, así que hay que convertir el resultado al final.

## Cómo está organizado el proyecto

```
app/src/main/scala/taller/
    App.scala          programa de arranque
    Ejercicio.scala    aquí va el ejercicio

app/src/test/scala/taller/
    AppSuite.scala        comprueba que el entorno quedó bien
    EjercicioTest.scala   los casos de arriba
```

Su código va en `main`. Las pruebas viven aparte y no se tocan.

La última prueba no compara contra una lista fija: comprueba que **toda**
terna devuelta cumpla la relación y el orden. Sirve para detectar una
solución que acierte los casos conocidos por casualidad.

## Cómo se ejecuta

```bash
./gradlew test    # corre las pruebas
```

Las pruebas arrancan en rojo y el trabajo es ponerlas en verde. El informe
completo queda en `app/build/reports/tests/test/index.html`.

## Cómo se trabaja

1. Haga fork de este repositorio.
2. En su fork, abra la pestaña **Actions** y habilítelas. GitHub las deja
   desactivadas en las copias hasta que el dueño lo confirme.
3. Clone, resuelva, haga commit y suba a `main`.
4. Verifique en **Actions** que la última ejecución quedó en verde.

## Restricciones

Este curso trabaja sin estado mutable: nada de `var`, `while`, `return` ni
variables que cambien. El resultado correcto por el camino equivocado no
cuenta como resultado correcto.

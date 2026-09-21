# Ejercicio 9 — Colecciones y expresiones for

Fundamentos de Programación Funcional y Concurrente
Escuela de Ingeniería de Sistemas y Computación, Universidad del Valle
Carlos Andrés Delgado Saavedra

Cinco puntos que recorren la sesión de colecciones. Las operaciones que se
conocen de las listas sirven igual sobre cadenas, rangos y vectores; `flatMap`
arma una sola colección cuando cada elemento produce varios; la expresión `for`
escribe eso mismo de forma legible; los conjuntos olvidan repetidos, y las
ternas pitagóricas del final juntan todo.

## Las operaciones viajan entre colecciones

`map`, `filter`, `reverse`, `length`, `zip`, `exists`, `forall` y `sum` no son
de `List`: funcionan sobre `Vector`, sobre un `Range` como `1 to 10 by 3` y
sobre un `String`, que es una secuencia de caracteres.

```scala
"Hola mundo".filter(_.isUpper)       // "H"
(1 to 10 by 3).sum                   // 22
Vector(2, 4, 6).forall(_ % 2 == 0)   // true
```

Un `Set` no guarda el orden ni los repetidos, y trae las operaciones de
conjuntos:

```scala
Set(1, 2, 3, 4) intersect Set(3, 4, 5, 6)   // Set(3, 4)
(1 to 6).toSet.map(x => x / 2)              // Set(0, 1, 2, 3)
```

El segundo resultado tiene cuatro elementos y no seis: 2/2 y 3/2 dan 1, 4/2 y
5/2 dan 2, y el conjunto se queda con uno de cada.

## De flatMap a la expresión for

Cuando la función que se aplica a cada elemento devuelve una colección, `map`
deja una colección de colecciones. `flatMap` hace lo mismo y aplana:

```scala
(1 to 3).map(x => (1 to 2).map(y => (x, y)))
// Vector(Vector((1,1), (1,2)), Vector((2,1), (2,2)), Vector((3,1), (3,2)))

(1 to 3).flatMap(x => (1 to 2).map(y => (x, y)))
// Vector((1,1), (1,2), (2,1), (2,2), (3,1), (3,2))
```

La expresión `for` con `yield` es la forma legible de esa combinación. Cada
generador recorre una colección, y las condiciones intercaladas descartan lo
que no sirve:

```scala
for {
  x <- 1 to 5
  y <- x to 5
  if x + y == 6
} yield (x, y)
// Vector((1,5), (2,4), (3,3))
```

El segundo generador arranca en `x`, así que nunca produce parejas con `y`
menor que `x`; el último generador es el que varía más rápido. El compilador
traduce la expresión a llamadas conocidas:

| Expresión `for` | Se traduce a |
|---|---|
| un generador y `yield` | `map` |
| varios generadores | `flatMap` y `map` |
| `if` | `withFilter` |

Una expresión `for` sobre rangos devuelve un `IndexedSeq`, no una `List`.
Cuando la firma pide una lista, el resultado se convierte al final.

## Lo que hay que resolver

Todo va en `app/src/main/scala/taller/Ejercicio.scala`.

### Punto 1: cadenas, rangos y vectores como secuencias

```scala
def contarVocales(s: String): Int
def esPalindromo(s: String): Boolean
def sumaMultiplos(n: Int, k: Int): Int
def estaOrdenado(xs: Vector[Int]): Boolean
```

`contarVocales` cuenta las vocales `a`, `e`, `i`, `o`, `u` de la cadena, en
mayúscula o en minúscula.

| Llamada | Resultado |
|---|---|
| `contarVocales("euforia")` | 5 |
| `contarVocales("EUFORIA")` | 5 |
| `contarVocales("Anita lava la tina")` | 8 |
| `contarVocales("rtmp")` | 0 |
| `contarVocales("")` | 0 |

`esPalindromo` dice si la cadena se lee igual al revés. No distingue
mayúsculas de minúsculas y no cuenta los espacios: `Anita lava la tina` es
palíndromo aunque con los espacios puestos no lo parezca. La cadena vacía lo es.

| Llamada | Resultado |
|---|---|
| `esPalindromo("oso")` | true |
| `esPalindromo("Oso")` | true |
| `esPalindromo("Anita lava la tina")` | true |
| `esPalindromo("Scala")` | false |
| `esPalindromo("abca")` | false |
| `esPalindromo("")` | true |

`sumaMultiplos(n, k)` suma los múltiplos de `k` que hay entre 1 y `n`, con `n`
incluido cuando es múltiplo. `k` es al menos 1. Si en el rango no hay ningún
múltiplo, la suma es 0.

| Llamada | Resultado |
|---|---|
| `sumaMultiplos(10, 3)` | 18 |
| `sumaMultiplos(9, 3)` | 18 |
| `sumaMultiplos(10, 5)` | 15 |
| `sumaMultiplos(10, 1)` | 55 |
| `sumaMultiplos(2, 3)` | 0 |
| `sumaMultiplos(0, 4)` | 0 |

`estaOrdenado` dice si el vector va de menor a mayor. Los repetidos seguidos
no lo desordenan, y el vector vacío y el de un solo elemento están ordenados.
`Vector(1, 5, 3, 9)` empieza con su menor y termina con su mayor y no está
ordenado: hay que mirar cada pareja de vecinos, no los extremos.

| Llamada | Resultado |
|---|---|
| `estaOrdenado(Vector(1, 2, 2, 5))` | true |
| `estaOrdenado(Vector(3, 3, 3))` | true |
| `estaOrdenado(Vector(7))` | true |
| `estaOrdenado(Vector())` | true |
| `estaOrdenado(Vector(1, 3, 2))` | false |
| `estaOrdenado(Vector(1, 5, 3, 9))` | false |

### Punto 2: flatMap, una colección por cada elemento

```scala
def parejas(n: Int): List[(Int, Int)]
def expandir(xs: List[Int]): List[Int]
```

`parejas(n)` devuelve todas las parejas `(i, j)` con `1 ≤ i < j ≤ n`,
ordenadas por `i` y, entre las que comparten `i`, por `j`. Cada pareja sale
una sola vez: está `(1, 2)` y no `(2, 1)`, y no está `(1, 1)`. Para `n`
elementos hay `n(n − 1) / 2` parejas.

| Llamada | Resultado |
|---|---|
| `parejas(4)` | `List((1,2), (1,3), (1,4), (2,3), (2,4), (3,4))` |
| `parejas(2)` | `List((1,2))` |
| `parejas(1)` | `List()` |
| `parejas(0)` | `List()` |
| `parejas(6).length` | 15 |
| `parejas(10).length` | 45 |

`expandir` repite cada número de la lista tantas veces como su valor, en el
mismo orden en que vienen. El cero y los negativos no aparecen en el
resultado. Lo que distingue a `flatMap` de `map` está aquí: la función que se
aplica a cada elemento devuelve una colección, y esa colección puede tener
varios elementos, uno o ninguno.

| Llamada | Resultado |
|---|---|
| `expandir(List(3, 1, 2))` | `List(3, 3, 3, 1, 2, 2)` |
| `expandir(List(1, 1, 1))` | `List(1, 1, 1)` |
| `expandir(List(0, 4))` | `List(4, 4, 4, 4)` |
| `expandir(List(2, -1, 2))` | `List(2, 2, 2, 2)` |
| `expandir(List())` | `List()` |

### Punto 3: la misma función con for y con flatMap

```scala
def parejasVocalesFor(palabras: Vector[String]): List[(String, String)]
def parejasVocalesHOF(palabras: Vector[String]): List[(String, String)]
```

Las dos devuelven las parejas de palabras del vector que tienen el mismo
número de vocales, contadas con `contarVocales` del punto 1. Cada pareja sale
una sola vez y la primera palabra de la pareja aparece antes que la segunda
en el vector; las parejas van ordenadas por la posición de la primera palabra
y luego por la de la segunda. Las posiciones de un vector empiezan en 0.

`parejasVocalesFor` se escribe con una expresión `for`: generadores, un `if`
y `yield`. `parejasVocalesHOF` se escribe con `flatMap`, `map` y `filter`, sin
`for`. Una de las pruebas llama a las dos con los mismos vectores y exige que
den lo mismo.

| Llamada | Resultado |
|---|---|
| `parejasVocalesFor(Vector("sol", "mar", "luna", "casa", "pez"))` | `List(("sol","mar"), ("sol","pez"), ("mar","pez"), ("luna","casa"))` |
| `parejasVocalesFor(Vector("sol", "mar"))` | `List(("sol","mar"))` |
| `parejasVocalesFor(Vector("luna", "sol"))` | `List()` |
| `parejasVocalesFor(Vector("sol"))` | `List()` |
| `parejasVocalesHOF(Vector("sol", "mar", "luna", "casa", "pez"))` | `List(("sol","mar"), ("sol","pez"), ("mar","pez"), ("luna","casa"))` |
| `parejasVocalesHOF(Vector())` | `List()` |

`sol`, `mar` y `pez` tienen una vocal; `luna` y `casa` tienen dos. La pareja
es `("sol", "mar")` y no `("mar", "sol")`: manda la posición en el vector, no
el orden alfabético.

### Punto 4: conjuntos

```scala
def letrasDistintas(s: String): Int
def letrasComunes(a: String, b: String): Set[Char]
def sonAnagramas(a: String, b: String): Boolean
```

`letrasDistintas` cuenta cuántas letras distintas tiene la cadena, sin
distinguir mayúsculas y sin contar los espacios.

| Llamada | Resultado |
|---|---|
| `letrasDistintas("Anita lava la tina")` | 6 |
| `letrasDistintas("aaaa")` | 1 |
| `letrasDistintas("")` | 0 |

`letrasComunes` devuelve las letras que aparecen en las dos cadenas, en
minúscula. Los espacios no son letras y no entran.

| Llamada | Resultado |
|---|---|
| `letrasComunes("euforia", "aeiou")` | `Set('a', 'e', 'i', 'o', 'u')` |
| `letrasComunes("Scala", "casa")` | `Set('s', 'c', 'a')` |
| `letrasComunes("sol y mar", "luz de luna")` | `Set('l', 'a')` |
| `letrasComunes("abc", "xyz")` | `Set()` |
| `letrasComunes("", "abc")` | `Set()` |

`sonAnagramas` dice si las dos cadenas tienen las mismas letras con las mismas
repeticiones, sin distinguir mayúsculas y sin contar los espacios. Un conjunto
no sirve para esto: `"aab".toSet` y `"abb".toSet` son el mismo conjunto y las
dos palabras no son anagramas. Tampoco lo son `hola` y `holaa`, ni la cadena
vacía y `a`.

| Llamada | Resultado |
|---|---|
| `sonAnagramas("Roma", "amor")` | true |
| `sonAnagramas("Argentino", "Ignorante")` | true |
| `sonAnagramas("Ana lava", "lana ava")` | true |
| `sonAnagramas("", "")` | true |
| `sonAnagramas("aab", "abb")` | false |
| `sonAnagramas("hola", "holaa")` | false |
| `sonAnagramas("", "a")` | false |
| `sonAnagramas("sol", "mar")` | false |

### Punto 5: ternas pitagóricas

```scala
def ternas(n: Int): List[(Int, Int, Int)]
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

| Llamada | Resultado |
|---|---|
| `ternas(10)` | `List((3,4,5), (6,8,10))` |
| `ternas(20)` | `List((3,4,5), (5,12,13), (6,8,10), (8,15,17), (9,12,15), (12,16,20))` |
| `ternas(2)` | `List()` |

Con `n = 30` salen once ternas y con `n = 100`, cincuenta y dos.

La terna (3,4,5) es la más pequeña: 9 + 16 = 25. La (6,8,10) es la misma
multiplicada por dos, y por eso las ternas se repiten a escala a medida que
`n` crece.

La última prueba de este punto no compara contra una lista fija: comprueba
que **toda** terna devuelta cumpla la relación y el orden. Sirve para
detectar una solución que acierte los casos conocidos por casualidad.

## Cómo está organizado el proyecto

```
app/src/main/scala/taller/
    App.scala          programa de arranque
    Ejercicio.scala    aquí van los cinco puntos

app/src/test/scala/taller/
    AppSuite.scala        comprueba que el entorno quedó bien
    EjercicioTest.scala   los casos de las tablas
```

Su código va en `main`. Las pruebas viven aparte y no se tocan.

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

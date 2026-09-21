package taller

class Ejercicio {

  // Punto 1. Cadenas, rangos y vectores como secuencias.
  // Tal como están, las funciones devuelven un valor fijo y las pruebas
  // quedan en rojo.

  // Cuántas vocales (a, e, i, o, u) tiene s, en mayúscula o minúscula.
  def contarVocales(s: String): Int = {
    0 // Completar
  }

  // Si s se lee igual al revés, sin distinguir mayúsculas y sin contar
  // los espacios.
  def esPalindromo(s: String): Boolean = {
    false // Completar
  }

  // La suma de los múltiplos de k entre 1 y n, ambos incluidos. k >= 1.
  def sumaMultiplos(n: Int, k: Int): Int = {
    0 // Completar
  }

  // Si xs está en orden ascendente; los repetidos y el vector vacío cuentan
  // como ordenados.
  def estaOrdenado(xs: Vector[Int]): Boolean = {
    false // Completar
  }

  // Punto 2. flatMap: una colección por cada elemento, aplanadas.

  // Todas las parejas (i, j) con 1 <= i < j <= n, ordenadas por i y luego
  // por j.
  def parejas(n: Int): List[(Int, Int)] = {
    List() // Completar
  }

  // Cada número de xs repetido tantas veces como su valor, en el mismo
  // orden; el cero y los negativos no aparecen.
  def expandir(xs: List[Int]): List[Int] = {
    List() // Completar
  }

  // Punto 3. La misma función dos veces: con for y con flatMap, map y filter.

  // Las parejas de palabras con el mismo número de vocales. La primera de
  // cada pareja va antes que la segunda en el vector, y cada pareja sale
  // una sola vez.
  def parejasVocalesFor(palabras: Vector[String]): List[(String, String)] = {
    List() // Completar con una expresión for
  }

  def parejasVocalesHOF(palabras: Vector[String]): List[(String, String)] = {
    List() // Completar con flatMap, map y filter
  }

  // Punto 4. Conjuntos.

  // Cuántas letras distintas tiene s, sin distinguir mayúsculas y sin
  // contar espacios.
  def letrasDistintas(s: String): Int = {
    0 // Completar
  }

  // Las letras que aparecen en a y también en b, en minúscula.
  def letrasComunes(a: String, b: String): Set[Char] = {
    Set() // Completar
  }

  // Si a y b tienen las mismas letras con las mismas repeticiones, sin
  // distinguir mayúsculas y sin contar espacios.
  def sonAnagramas(a: String, b: String): Boolean = {
    false // Completar
  }

  // Punto 5. Las ternas (a, b, c) con a <= b <= c <= n que cumplen
  // a*a + b*b == c*c, en orden ascendente por a y luego por b.
  def ternas(n: Int): List[(Int, Int, Int)] = {
    List() // Completar con una expresión for
  }
}

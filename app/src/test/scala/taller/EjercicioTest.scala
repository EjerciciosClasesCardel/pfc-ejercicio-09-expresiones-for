package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EjercicioTest extends AnyFunSuite {
  val objEjercicio = new Ejercicio()

  // Punto 1: contarVocales, esPalindromo, sumaMultiplos y estaOrdenado

  test("contarVocales cuenta las cinco vocales sin importar mayúsculas") {
    assert(objEjercicio.contarVocales("euforia") == 5)
    assert(objEjercicio.contarVocales("EUFORIA") == 5)
    assert(objEjercicio.contarVocales("Anita lava la tina") == 8)
  }

  test("contarVocales sin vocales o con la cadena vacía da 0") {
    assert(objEjercicio.contarVocales("rtmp") == 0)
    assert(objEjercicio.contarVocales("") == 0)
  }

  test("esPalindromo ignora espacios y mayúsculas") {
    assert(objEjercicio.esPalindromo("oso"))
    assert(objEjercicio.esPalindromo("Oso"))
    assert(objEjercicio.esPalindromo("Anita lava la tina"))
  }

  test("esPalindromo rechaza lo que no se lee igual al revés") {
    assert(!objEjercicio.esPalindromo("Scala"))
    assert(!objEjercicio.esPalindromo("abca"))
    assert(objEjercicio.esPalindromo(""))
  }

  test("sumaMultiplos incluye a n cuando n es múltiplo de k") {
    assert(objEjercicio.sumaMultiplos(10, 3) == 18)
    assert(objEjercicio.sumaMultiplos(9, 3) == 18)
    assert(objEjercicio.sumaMultiplos(10, 5) == 15)
    assert(objEjercicio.sumaMultiplos(10, 1) == 55)
  }

  test("sumaMultiplos sin múltiplos en el rango da 0") {
    assert(objEjercicio.sumaMultiplos(2, 3) == 0)
    assert(objEjercicio.sumaMultiplos(0, 4) == 0)
  }

  test("estaOrdenado acepta repetidos y el vector vacío") {
    assert(objEjercicio.estaOrdenado(Vector(1, 2, 2, 5)))
    assert(objEjercicio.estaOrdenado(Vector(3, 3, 3)))
    assert(objEjercicio.estaOrdenado(Vector(7)))
    assert(objEjercicio.estaOrdenado(Vector()))
  }

  test("estaOrdenado mira todas las parejas vecinas, no solo los extremos") {
    assert(!objEjercicio.estaOrdenado(Vector(1, 3, 2)))
    assert(!objEjercicio.estaOrdenado(Vector(1, 5, 3, 9)))
  }

  // Punto 2: parejas y expandir

  test("parejas genera cada pareja una sola vez y en orden") {
    assert(objEjercicio.parejas(4) == List((1, 2), (1, 3), (1, 4), (2, 3), (2, 4), (3, 4)))
    assert(objEjercicio.parejas(2) == List((1, 2)))
  }

  test("parejas con n menor que 2 no produce nada y con n grande cuenta bien") {
    assert(objEjercicio.parejas(1) == List())
    assert(objEjercicio.parejas(0) == List())
    assert(objEjercicio.parejas(6).length == 15)
    assert(objEjercicio.parejas(10).length == 45)
  }

  test("expandir repite cada número tantas veces como su valor") {
    assert(objEjercicio.expandir(List(3, 1, 2)) == List(3, 3, 3, 1, 2, 2))
    assert(objEjercicio.expandir(List(1, 1, 1)) == List(1, 1, 1))
  }

  test("expandir hace desaparecer el cero y los negativos") {
    assert(objEjercicio.expandir(List(0, 4)) == List(4, 4, 4, 4))
    assert(objEjercicio.expandir(List(2, -1, 2)) == List(2, 2, 2, 2))
    assert(objEjercicio.expandir(List()) == List())
  }

  // Punto 3: parejasVocalesFor y parejasVocalesHOF

  val palabras = Vector("sol", "mar", "luna", "casa", "pez")
  val esperadoPalabras = List(("sol", "mar"), ("sol", "pez"), ("mar", "pez"), ("luna", "casa"))

  test("parejasVocalesFor empareja por posición, no por orden alfabético") {
    assert(objEjercicio.parejasVocalesFor(palabras) == esperadoPalabras)
    assert(objEjercicio.parejasVocalesFor(Vector("sol", "mar")) == List(("sol", "mar")))
  }

  test("parejasVocalesHOF da lo mismo que la versión con for") {
    assert(objEjercicio.parejasVocalesHOF(palabras) == esperadoPalabras)
    assert(objEjercicio.parejasVocalesHOF(Vector("sol", "mar")) == List(("sol", "mar")))
  }

  test("las dos versiones coinciden en todos los casos") {
    val casos = List(palabras, Vector("sol", "mar"), Vector("luna", "sol"), Vector("sol"), Vector[String](),
      Vector("euforia", "aeiou", "xyz", "ptr", "casa", "sol"))
    assert(casos.forall(v => objEjercicio.parejasVocalesFor(v) == objEjercicio.parejasVocalesHOF(v)))
  }

  test("sin dos palabras con igual número de vocales no hay parejas") {
    assert(objEjercicio.parejasVocalesFor(Vector("luna", "sol")) == List())
    assert(objEjercicio.parejasVocalesFor(Vector("sol")) == List())
    assert(objEjercicio.parejasVocalesHOF(Vector()) == List())
  }

  // Punto 4: letrasDistintas, letrasComunes y sonAnagramas

  test("letrasDistintas no cuenta repetidas, espacios ni mayúsculas aparte") {
    assert(objEjercicio.letrasDistintas("Anita lava la tina") == 6)
    assert(objEjercicio.letrasDistintas("aaaa") == 1)
    assert(objEjercicio.letrasDistintas("") == 0)
  }

  test("letrasComunes es la intersección de las letras de las dos cadenas") {
    assert(objEjercicio.letrasComunes("euforia", "aeiou") == Set('a', 'e', 'i', 'o', 'u'))
    assert(objEjercicio.letrasComunes("abc", "xyz") == Set())
    assert(objEjercicio.letrasComunes("", "abc") == Set())
  }

  test("letrasComunes ignora mayúsculas y espacios") {
    assert(objEjercicio.letrasComunes("Scala", "casa") == Set('s', 'c', 'a'))
    assert(objEjercicio.letrasComunes("sol y mar", "luz de luna") == Set('l', 'a'))
  }

  test("sonAnagramas acepta las mismas letras en otro orden") {
    assert(objEjercicio.sonAnagramas("Roma", "amor"))
    assert(objEjercicio.sonAnagramas("Argentino", "Ignorante"))
    assert(objEjercicio.sonAnagramas("Ana lava", "lana ava"))
    assert(objEjercicio.sonAnagramas("", ""))
  }

  test("sonAnagramas cuenta las repeticiones, no solo el conjunto de letras") {
    assert(!objEjercicio.sonAnagramas("aab", "abb"))
    assert(!objEjercicio.sonAnagramas("hola", "holaa"))
    assert(!objEjercicio.sonAnagramas("", "a"))
    assert(!objEjercicio.sonAnagramas("sol", "mar"))
  }

  // Punto 5: ternas

  test("Hasta 10 hay dos ternas") {
    assert(objEjercicio.ternas(10) == List((3, 4, 5), (6, 8, 10)))
  }

  test("Hasta 20 hay seis ternas, ordenadas por a y luego por b") {
    val esperado = List((3, 4, 5), (5, 12, 13), (6, 8, 10),
                        (8, 15, 17), (9, 12, 15), (12, 16, 20))
    assert(objEjercicio.ternas(20) == esperado)
  }

  test("Hasta 30 hay once ternas") {
    val esperado = List((3, 4, 5), (5, 12, 13), (6, 8, 10), (7, 24, 25),
                        (8, 15, 17), (9, 12, 15), (10, 24, 26), (12, 16, 20),
                        (15, 20, 25), (18, 24, 30), (20, 21, 29))
    assert(objEjercicio.ternas(30) == esperado)
  }

  test("Hasta 100 hay cincuenta y dos ternas") {
    assert(objEjercicio.ternas(100).length == 52)
  }

  test("Con n menor que 3 no hay ninguna") {
    assert(objEjercicio.ternas(2) == List())
  }

  test("Toda terna devuelta cumple la relación y el orden") {
    val ternas = objEjercicio.ternas(60)
    assert(ternas.nonEmpty)
    assert(ternas.forall { case (a, b, c) => a * a + b * b == c * c })
    assert(ternas.forall { case (a, b, c) => a <= b && b <= c && c <= 60 })
  }
}

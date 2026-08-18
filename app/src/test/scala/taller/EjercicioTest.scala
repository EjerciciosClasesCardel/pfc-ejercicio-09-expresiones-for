package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EjercicioTest extends AnyFunSuite {
  val objEjercicio = new Ejercicio()

  test("Hasta 10 hay dos ternas") {
    assert(objEjercicio.ejercicio(10) == List((3, 4, 5), (6, 8, 10)))
  }

  test("Hasta 20 hay seis ternas, ordenadas por a y luego por b") {
    val esperado = List((3, 4, 5), (5, 12, 13), (6, 8, 10),
                        (8, 15, 17), (9, 12, 15), (12, 16, 20))
    assert(objEjercicio.ejercicio(20) == esperado)
  }

  test("Hasta 30 hay once ternas") {
    val esperado = List((3, 4, 5), (5, 12, 13), (6, 8, 10), (7, 24, 25),
                        (8, 15, 17), (9, 12, 15), (10, 24, 26), (12, 16, 20),
                        (15, 20, 25), (18, 24, 30), (20, 21, 29))
    assert(objEjercicio.ejercicio(30) == esperado)
  }

  test("Hasta 100 hay cincuenta y dos ternas") {
    assert(objEjercicio.ejercicio(100).length == 52)
  }

  test("Con n menor que 3 no hay ninguna") {
    assert(objEjercicio.ejercicio(2) == List())
  }

  test("Toda terna devuelta cumple la relación y el orden") {
    val ternas = objEjercicio.ejercicio(60)
    assert(ternas.nonEmpty)
    assert(ternas.forall { case (a, b, c) => a * a + b * b == c * c })
    assert(ternas.forall { case (a, b, c) => a <= b && b <= c && c <= 60 })
  }
}

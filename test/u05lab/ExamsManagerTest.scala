package u05lab

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import u05lab.code.Kind


class ExamsManagerTest {

  @Test
  def testKind() {
    val k1 = Kind.SUCCEEDED
    assertEquals(k1, Kind.SUCCEEDED)
    val k2 = Kind.FAILED
    assertEquals(k2, Kind.FAILED)
  }

  @Test
  def testResults(): Unit ={

  }
}

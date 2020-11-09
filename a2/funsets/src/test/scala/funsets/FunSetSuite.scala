package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s12 = union(s1, s2)
    val s23 = union(s2, s3)

    val p1 = (x :Int) => x == 1
    val p2 = (x :Int) => x <= 2
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * @Ignore annotation.
   */
  @Test def `singleton set one contains one`: Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersection contains intersect elements of each set`: Unit = {
    new TestSets {
      val s = intersect(s1, s2)
      val sI = intersect(s12, s23)
      assert(!contains(s, 1), "Empty Intersect 1")
      assert(!contains(s, 2), "Empty Intersect 2")
      assert(!contains(sI, 1), "Intersect 1")
      assert(!contains(sI, 3), "Intersect 3")
      assert(contains(sI, 2), "Intersect contains 2")
    }
  }

  @Test def `diff contains elements of set but not t`: Unit = {
    new TestSets {
      val sD = diff(s12, s23)

      assert(contains(sD, 1), "Diff contains 1")
      assert(!contains(sD, 2), "Diff does not contain 2")
      assert(!contains(sD, 3), "Diff does not contain 3")
    }
  }

  @Test def `filter s with p`: Unit = {
    new TestSets {
      val sF1 = filter(s12, p1)
      val sF2 = filter(s23, p1)

      assert(contains(sF1, 1), "Filter contains 1")
      assert(!contains(sF1, 2), "Filter does not contain 2")
      assert(!contains(sF2, 3), "Filter does not contain 3")
    }
  }

  @Test def `forall s with p`: Unit = {
    new TestSets {
      assert(forall(s12, p2), "1, 2 <= 2")
      assert(!forall(s23, p2), "2,3 not <= 3")
    }
  }

  @Test def `exists s with p`: Unit = {
    new TestSets {
      assert(exists(s12, p2), "1, 2 <= 2")
      assert(exists(s23, p2), "2,3 contains 2 <= 2")
      assert(!exists(s3, p2), "3 not < 2")
    }
  }

  @Test def `map s with +1`: Unit = {
    new TestSets {
      val sM1 = map(s12, x => x+1)

      assert(!contains(sM1, 1), "1 not in (1,2)+1")
      assert(contains(sM1, 2), "2 in (1,2)+1")
      assert(contains(sM1, 3), "3 in (1,2)+1")
      assert(!contains(sM1, 4), "4 not in (1,2)+1")
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}

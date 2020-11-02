package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c==0 || c==r) 1
    else pascal(c-1, r-1) + pascal(c, r-1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def balanceWithStack(chars: List[Char], soFar: Int): Boolean = {
      if (chars.isEmpty) soFar == 0
      else if (soFar < 0) false
      else if (chars.head.equals('(')) balanceWithStack(chars.tail, soFar+1)
      else if (chars.head.equals(')')) balanceWithStack(chars.tail, soFar-1)
      else balanceWithStack(chars.tail, soFar)
    }

    balanceWithStack(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeCum(idx: Int, money: Int, coins: List[Int]): Int = {
      // recurrence: either contains c or not
      // idx: index of last coin used
      if (money == 0) 1
      else if (money < 0) 0
      else if (idx < 0) 0
      else countChangeCum(idx, money - coins(idx), coins) + countChangeCum(idx-1, money, coins)
    }
    countChangeCum(coins.length-1, money, coins)
  }
}

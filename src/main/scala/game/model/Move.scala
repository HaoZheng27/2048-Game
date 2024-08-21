package game.model

abstract class Move {
  def move(board: Board): Unit
  def mergeSequence(seq: Seq[Int]): Seq[Int] = {
    var score = 0
    val merged = seq.foldLeft(List[Int]()) { (acc, num) =>
      acc match {
        case x :: xs if x == num =>
          score += x * 2
          (x * 2) :: xs
        case _ => num :: acc
      }
    }.reverse
    merged ++ Seq.fill(seq.length - merged.length)(0)
  }
}

object MoveLeft extends Move {
  override def move(board: Board): Unit = {
    for (row <- 0 until board.size) {
      val rowSeq = board.grid(row).filter(_ != 0)
      val merged = mergeSequence(rowSeq)
      for (col <- 0 until board.size) {
        board.grid(row)(col) = if (col < merged.length) merged(col) else 0
      }
    }
  }
}

object MoveRight extends Move {
  override def move(board: Board): Unit = {
    for (row <- 0 until board.size) {
      val rowSeq = board.grid(row).filter(_ != 0).reverse
      val merged = mergeSequence(rowSeq)
      for (col <- 0 until board.size) {
        board.grid(row)(board.size - 1 - col) = if (col < merged.length) merged(col) else 0
      }
    }
  }
}

object MoveUp extends Move {
  override def move(board: Board): Unit = {
    for (col <- 0 until board.size) {
      val column = (0 until board.size).map(board.grid(_)(col)).filter(_ != 0)
      val merged = mergeSequence(column)
      for (row <- 0 until board.size) {
        board.grid(row)(col) = if (row < merged.length) merged(row) else 0
      }
    }
  }
}

object MoveDown extends Move {
  override def move(board: Board): Unit = {
    for (col <- 0 until board.size) {
      val column = (0 until board.size).map(board.grid(_)(col)).filter(_ != 0).reverse
      val merged = mergeSequence(column)
      for (row <- 0 until board.size) {
        board.grid(board.size - 1 - row)(col) = if (row < merged.length) merged(row) else 0
      }
    }
  }
}

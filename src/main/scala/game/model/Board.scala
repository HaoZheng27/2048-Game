package game.model

import scala.util.Random

class Board(val size: Int) {
  var grid: Array[Array[Int]] = Array.ofDim[Int](size, size)
  var score: Int = 0

  def initialize(): Unit = {
    grid = Array.fill(size, size)(0)
    addNewTile()
    addNewTile()
  }

  def addNewTile(): Unit = {
    val emptySpots = for {
      i <- 0 until size
      j <- 0 until size
      if grid(i)(j) == 0
    } yield (i, j)

    if (emptySpots.nonEmpty) {
      val (i, j) = emptySpots(Random.nextInt(emptySpots.length))
      grid(i)(j) = if (Random.nextDouble() < 0.9) 2 else 4
    }
  }

  def canMerge(a: Int, b: Int): Boolean = a == b || a == 0 || b == 0

  def isGameOver: Boolean = {
    val canMoveHorizontally = grid.exists(row =>
      row.sliding(2).exists { case Array(a, b) => canMerge(a, b) }
    )
    val canMoveVertically = (0 until size).exists(col =>
      (0 until size - 1).exists(row => canMerge(grid(row)(col), grid(row + 1)(col)))
    )
    !(canMoveHorizontally || canMoveVertically)
  }

  def move(direction: Move): Boolean = {
    val originalGrid = grid.map(_.clone())
    direction.move(this)
    !grid.deep.equals(originalGrid.deep)
  }
}

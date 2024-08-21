package game.model

abstract class Tile(val value: Int)

class NumberTile(v: Int) extends Tile(v)

object EmptyTile extends Tile(0)

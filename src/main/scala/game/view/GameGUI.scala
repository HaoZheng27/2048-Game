package game.view

import game.model.{Board, MoveDown, MoveLeft, MoveRight, MoveUp}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyEvent
import scalafx.Includes._
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import javafx.scene.input.KeyCode // Ensure this import is present

class GameGUI(board: Board) {
  val scoreLabel = new Label(s"Score: ${board.score}")
  val gridPane = new GridPane()
  gridPane.hgap = 5
  gridPane.vgap = 5

  val newGameButton = new Button("New Game")
  newGameButton.onAction = _ => newGame()

  val root = new VBox(10) {
    children = List(scoreLabel, gridPane, newGameButton)
    alignment = Pos.Center
    padding = Insets(20)
  }

  def updateGUI(): Unit = {
    gridPane.children.clear()
    for (i <- 0 until board.size; j <- 0 until board.size) {
      val tile = new Button(if (board.grid(i)(j) > 0) board.grid(i)(j).toString else "")
      tile.prefWidth = 80
      tile.prefHeight = 80
      tile.style = s"-fx-background-color: ${getTileColor(board.grid(i)(j))};"
      GridPane.setConstraints(tile, j, i)
      gridPane.children.add(tile)
    }
    scoreLabel.text = s"Score: ${board.score}"
  }

  def getTileColor(value: Int): String = value match {
    case 0 => "#CDC1B4"
    case 2 => "#EEE4DA"
    case 4 => "#EDE0C8"
    case 8 => "#F2B179"
    case 16 => "#F59563"
    case 32 => "#F67C5F"
    case 64 => "#F65E3B"
    case 128 => "#EDCF72"
    case 256 => "#EDCC61"
    case 512 => "#EDC850"
    case 1024 => "#EDC53F"
    case 2048 => "#EDC22E"
    case _ => "#3C3A32"
  }

  def newGame(): Unit = {
    board.initialize()
    board.score = 0
    updateGUI()
  }

  // Update this method to accept KeyCode
  def handleKeyEvent(keyCode: javafx.scene.input.KeyCode): Unit = {
    keyCode match {
      case KeyCode.UP => move(MoveUp)
      case KeyCode.DOWN => move(MoveDown)
      case KeyCode.LEFT => move(MoveLeft)
      case KeyCode.RIGHT => move(MoveRight)
      case _ => // Ignore other keys
    }
  }

  def move(direction: game.model.Move): Unit = {
    if (board.move(direction)) {
      board.addNewTile()
      updateGUI()
      if (board.isGameOver) {
        showGameOverAlert()
      }
    }
  }

  def showGameOverAlert(): Unit = {
    new Alert(AlertType.Information) {
      title = "Game Over"
      headerText = "Game Over!"
      contentText = s"Your final score is: ${board.score}"
    }.showAndWait()
  }
}

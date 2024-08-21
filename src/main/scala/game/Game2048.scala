package game.view

import game.model.{Board, MoveDown, MoveLeft, MoveRight, MoveUp}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.input.KeyEvent
import javafx.event.EventHandler


object Game2048 extends JFXApp {
  val board = new Board(4)
  board.initialize()
  val gui = new GameGUI(board)

  stage = new PrimaryStage {
    title = "2048 Game"
    scene = new Scene(400, 450) {
      root = gui.root

      // Use JavaFX KeyEvent and EventHandler
      onKeyPressed = new EventHandler[javafx.scene.input.KeyEvent] {
        override def handle(event: javafx.scene.input.KeyEvent): Unit = {
          // Directly use the KeyCode from the JavaFX KeyEvent
          gui.handleKeyEvent(event.getCode) // Pass only the KeyCode
        }
      }
    }
  }

  gui.updateGUI()
}

package fr.winbee.app

import fr.winbee.controller.MainController

import scala.swing._
import scala.swing.event.ButtonClicked

object SwingApp extends SwingApplication {

  def top = new MainFrame {
    peer.setLocationRelativeTo(null)
    title = "Reactive Swing App"
    val button = new Button {
      text = "Click me"
    }
    val label = new Label {
      text = "No button clicks registered"
    }
    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += label
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
    listenTo(button)
    var nClicks = 0
    reactions += {
      case ButtonClicked(b) =>
        nClicks += 1
        label.text = "Number of button clicks: " + nClicks

    }
  }

  /**
   * Calls `top`, packs the frame, and displays it.
   */
  override def startup(args: Array[String]) {
    val t = top
    if (t.size == new Dimension(0, 0)) t.pack()
    t.visible = true
  }

  def resourceFromClassloader(path: String): java.net.URL =
    this.getClass.getResource(path)

  def resourceFromUserDirectory(path: String): java.io.File =
    new java.io.File(util.Properties.userDir, path)
}

package com.morowin.simpleScalaGUIText2Speech

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext
import scala.swing._
import scala.swing.event._

/**
  * simple scala GUI text2speech main app
  */
object Application extends SimpleSwingApplication {

  val executor = Executors.newSingleThreadExecutor()
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(executor)

  // window title
  val titleLabel = "scala GUI text2speech"

  // text field definition
  def newField: TextField = new TextField {
    text = ""
    columns = 32
    horizontalAlignment = Alignment.Left
  }

  // config window: choose voiceName
  val configPanel = new BoxPanel(Orientation.Vertical)
  var voiceNameButtonlabel = s"${Conf.VOICE_NAME}:"
  val voiceName = new Button(
    Action(voiceNameButtonlabel) {
      import Dialog._
      val possibilities = List(Voice.UPMC_PIERRE_HSMM, Voice.ENST_DENNYS_HSMM)
      val res = showInput(configPanel,
        "config",
        titleLabel,
        Message.Plain,
        Swing.EmptyIcon,
        possibilities, Conf.VOICE_NAME)

      if ((res != None) && (res.get.length > 0))
        Conf.VOICE_NAME = res.get // if a choice is done, change VOICE_NAME in Conf object
      else
        Conf.VOICE_NAME = Voice.DEFAULT // else set DEFAULT voice as VOICE_NAME
    }
  )

  // text field to fill
  val inputTextToSpeech = newField

  // action button
  object speechActionButton extends Button {
    text = "Speech"
  }

  // select action listener
  listenTo(speechActionButton)

  reactions += {
    case ButtonClicked(_) | EditDone(_) => speakAction(inputTextToSpeech.text)
  }

  def speakAction(s: String): Unit = s match {
    case "" => println("input text is missing") // prevent null pointer exception when no text is filled
    case _ => Speecher.say(s)
  }

  // define GUI window
  def top: MainFrame = new MainFrame {

    title = titleLabel

    contents = new FlowPanel(voiceName, inputTextToSpeech, speechActionButton) {
      border = Swing.EmptyBorder(5, 5, 5, 5)
    }
  }
}
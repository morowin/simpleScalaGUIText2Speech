package com.morowin.simpleScalaGUIText2Speech

import java.util.Locale

import javax.sound.sampled.AudioInputStream
import marytts.LocalMaryInterface
import marytts.util.data.audio.AudioPlayer

import scala.concurrent.ExecutionContext

//TODO doc
trait TextToSpeech {

  val voiceName: String
  val locale: Locale

  private lazy val mary = {
    val m = new LocalMaryInterface()
    m.setLocale(locale)
    m.setVoice(voiceName)
    m
  }

  def getAudioStream(inputText: String): AudioInputStream = mary.generateAudio(inputText)

  def say(inputText: String)(implicit ec: ExecutionContext): Unit = {
    val audioStream = getAudioStream(inputText)
    ec.execute(new AudioPlayer(audioStream))
  }
}

//TODO doc
class SpeecherTTS(val voiceName: String, val locale: Locale) extends TextToSpeech

//TODO doc
object Speecher extends SpeecherTTS(Conf.VOICE_NAME, Locale.FRENCH) {
  override def say(inputText: String)(implicit ec: ExecutionContext): Unit = {
    super.say(
      inputText
        .replaceAll("-'", " ")
        .replaceAll("'", "")
        .replaceAll("est", "et")
    )
  }
}

//TODO doc
object Conf {
  var VOICE_NAME = Voice.UPMC_PIERRE_HSMM
}

//TODO doc
object Voice {
  val UPMC_PIERRE_HSMM = "upmc-pierre-hsmm"
  val ENST_DENNYS_HSMM = "enst-dennys-hsmm"
  val DEFAULT = UPMC_PIERRE_HSMM
}
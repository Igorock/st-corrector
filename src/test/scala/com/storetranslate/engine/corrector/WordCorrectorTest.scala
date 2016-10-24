package com.storetranslate.engine.corrector

import org.scalatest.FlatSpec

import scala.io.Source


/**
  * Created by ihor.dziuba on 15-Sep-16.
  */
class WordCorrectorTest() extends FlatSpec {
  it should "return the most similar word" in {
    val knownWords: String = Source.fromFile("src/test/resources/en-words.txt").mkString
    val alphabet = 'a' to 'z' toArray
    val corrector: WordCorrector = new WordCorrector(knownWords, alphabet)

    assertResult("distance") { corrector.prompt("distance") }
    assertResult("distance") { corrector.prompt("distanse") }
    assertResult("distance") { corrector.prompt("distnse") }
    assertResult("distance") { corrector.prompt("istance") }
    assertResult("distance") { corrector.prompt("distancer") }
    assertResult("distant") { corrector.prompt("distanc") }

    assertResult("height") { corrector.prompt("height") }
    assertResult("height") { corrector.prompt("heigt") }
    assertResult("height") { corrector.prompt("heght") }
    assertResult("heights") { corrector.prompt("heghtr") }
    assertResult("heigh") { corrector.prompt("heigh") }
  }
}

package com.storetranslate.engine.corrector

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import com.storetranslate.engine.cassandra.service.WordListService
import spray.json._

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationDouble}

final case class Result(word: String, success: Boolean = true, error: String = "")

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val resultFormat = jsonFormat3(Result)
}

/**
  * Created by ihor.dziuba on 15-Sep-16.
  */
class PromptRestService extends Directives with JsonSupport {

  def route =
    path("prompt") {
      get {
        parameter("word") { (word) =>
          try {
            val suggestedWord = PromptRestService.corrector.prompt(word)
            complete(Result(suggestedWord))
          } catch {
            case e: Exception => complete("", false, e.getMessage)
          }
        }
      }
    }
}

object PromptRestService {
  val EN = "en"
  val knownWords = Await.result(WordListService.getWordListByLang(EN), 15 second)
    .map(_.word + " ").mkString
  val alphabet = 'a' to 'z' toArray
  val corrector = new WordCorrector(knownWords, alphabet)
}

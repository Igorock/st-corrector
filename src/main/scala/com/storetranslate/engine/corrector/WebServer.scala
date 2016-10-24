package com.storetranslate.engine.corrector

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

/**
  * Created by ihor.dziuba on 15-Sep-16.
  */
object WebServer {
  def main(args: Array[String]) {
    val PORT = 8060
    val HOST = "0.0.0.0"

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route = new PromptRestService().route

    Http().bindAndHandle(route, HOST, PORT)

    println(s"Server online at http://$HOST:$PORT/")
  }
}

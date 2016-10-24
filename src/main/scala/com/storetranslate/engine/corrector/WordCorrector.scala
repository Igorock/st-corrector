package com.storetranslate.engine.corrector

import scala.util.matching.Regex.MatchIterator

/**
  * Created by ihor.dziuba on 15-Sep-16.
  */
class WordCorrector (val text: String, val alphabet:Array[Char]) {

  val DELIMITER: String = "[%s]+"

  def train(features: MatchIterator) =
    (Map[String, Int]() /: features) ((m, f) => m + ((f, m.getOrElse(f, 0) + 1)))

  def words(text: String) =
    (DELIMITER format alphabet.mkString).r.findAllIn(text.toLowerCase)

  val dict = train(words(text))

  def variate(s: Seq[(String, String)]) =
    s.filter(_._2.length > 0).map(x => x._1 + x._2.substring(1)) ++
    s.filter(_._2.length > 1).map(x => x._1 + x._2(1) + x._2(0) + x._2.substring(2)) ++
    s.filter(_._2.length > 0).flatMap(x => alphabet.map(y => x._1 + y + x._2.substring(1))) ++
    s.flatMap(x => alphabet.map(y => x._1 + y + x._2))

  def variate1(word: String) =
    variate(for (i <- 0 to word.length) yield (word take i, word drop i))

  def variate2(word: String) =
    for (e1 <- variate1(word); e2 <- variate1(e1)) yield e2

  def known(words: Seq[String]) =
    for (w <- words; found <- dict.get(w)) yield w

  def or[T](candidates: Seq[T], other: => Seq[T]) =
    if (candidates.isEmpty) other else candidates

  def candidates(word: String) =
    or(known(List(word)), or(known(variate1(word)), known(variate2(word))))

  def prompt(word: String) = ((-1, word) /: candidates(word)) (
    (max, word) => if (dict(word) > max._1) (dict(word), word) else max)._2
}

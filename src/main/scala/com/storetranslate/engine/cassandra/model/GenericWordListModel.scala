package com.storetranslate.engine.cassandra.model

import com.storetranslate.engine.cassandra.entity.WordList
import com.websudos.phantom.dsl._

import scala.concurrent.Future

/**
  * Created by ihor.dziuba on 12-Sep-16.
  */
class WordListModel extends CassandraTable[ConcreteWordListModel, WordList] {

  override def tableName: String = "wordList"

  object lang extends StringColumn(this) with PartitionKey[String]
  object word extends StringColumn(this)

  override def fromRow(r: Row): WordList = WordList(lang(r), word(r))
}

abstract class ConcreteWordListModel extends WordListModel with RootConnector {

  def getByLang(lang: String): Future[List[WordList]] = {
    select
      .where(_.lang eqs lang)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
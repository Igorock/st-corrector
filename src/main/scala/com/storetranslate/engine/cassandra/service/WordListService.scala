package com.storetranslate.engine.cassandra.service

import com.storetranslate.engine.cassandra.database.ProductionDatabase
import com.storetranslate.engine.cassandra.entity.WordList

import scala.concurrent.Future

/**
  * Created by ihor.dziuba on 12-Sep-16.
  */
trait WordListService extends ProductionDatabase {

  def getWordListByLang(artist: String): Future[List[WordList]] = {
    database.wordListModel.getByLang(artist)
  }
}

object WordListService extends WordListService with ProductionDatabase
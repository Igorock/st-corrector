package com.storetranslate.engine.cassandra.database

import com.storetranslate.engine.cassandra.connector.Connector
import com.storetranslate.engine.cassandra.model.ConcreteWordListModel
import com.websudos.phantom.db.DatabaseImpl
import com.websudos.phantom.dsl._

/**
  * Created by ihor.dziuba on 12-Sep-16.
  */
class StDatabase(override val connector: KeySpaceDef) extends DatabaseImpl(connector) {
  object wordListModel extends ConcreteWordListModel with connector.Connector
}

object ProductionDb extends StDatabase(Connector.connector)

trait ProductionDatabaseProvider {
  def database: StDatabase
}

trait ProductionDatabase extends ProductionDatabaseProvider {
  override val database = ProductionDb
}
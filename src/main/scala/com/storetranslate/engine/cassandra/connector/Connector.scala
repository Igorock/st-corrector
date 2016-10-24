package com.storetranslate.engine.cassandra.connector

import java.net.InetAddress

import com.typesafe.config.ConfigFactory
import com.websudos.phantom.connectors.ContactPoints

import scala.collection.JavaConversions._

/**
  * Created by ihor.dziuba on 12-Sep-16.
  */
object Connector {
  val config = ConfigFactory.load()

  val hosts = config.getStringList("cassandra.host")
  val port = config.getInt("cassandra.port")
  val inets = hosts.map(InetAddress.getByName)

  val keyspace: String = config.getString("cassandra.keyspace")

  lazy val connector = ContactPoints(hosts).withClusterBuilder(
    _.withPort(port)
  ).keySpace(keyspace)

}
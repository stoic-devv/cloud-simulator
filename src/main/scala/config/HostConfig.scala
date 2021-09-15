package config

import util.{CreateLogger, ObtainConfigReference}

case class HostConfig(configFile: String, configEntry: String) {

  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[HostConfig])

  // ToDo: Make robust with default values and exception handling
  val mips = config.getInt("host.mipsCapacity")
  val ram = config.getLong("host.RAMInMBs")
  val storage = config.getLong("host.StorageInMBs")
  val bw = config.getLong("host.BandwidthInMBps")
  val numPes = config.getInt("host.PEs")
  
  logger.debug(s"Host configured successfully for $configEntry")
}

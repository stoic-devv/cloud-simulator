package config

import util.{CreateLogger, ObtainConfigReference}

case class CloudletConfig(configFile: String, configEntry: String) {
  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[CloudletConfig])

  // ToDo: Make robust with default values and exception handling
  val size = config.getLong("cloudlet.size")
  val numPes = config.getInt("cloudlet.PEs")

  logger.debug(s"Cloudlet configured successfully for $configEntry")
}

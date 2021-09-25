package config

import constants.CloudletConfigConstants
import util.{CreateLogger, ObtainConfigReference}

/**
 * Parses and holds configuration for the cloudlet configuration
 **/
case class CloudletConfig(configFile: String, configEntry: String) {
  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[CloudletConfig])

  // ToDo: Make robust with default values and exception handling
  val length = config.getLong(CloudletConfigConstants.LENGTH)
  val size = config.getLong(CloudletConfigConstants.SIZE)
  val numPes = config.getInt(CloudletConfigConstants.PES)
  val utilRatio = config.getDouble(CloudletConfigConstants.UTILRATIO)
  val utilModel = config.getString(CloudletConfigConstants.UTILMODEL)

  logger.debug(s"Cloudlet configured successfully for $configEntry")
}

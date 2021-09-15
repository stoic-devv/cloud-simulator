package config

import util.{CreateLogger, ObtainConfigReference}

case class DatacenterConfig(configFile: String, configEntry: String) {

  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[DatacenterConfig])

  // ToDo: Make robust with default values and exception handling
  val hosts = config.getInt("dc.hosts")
  val vms = config.getLong("dc.vms")
  val cloudlets = config.getLong("dc.cloudlets")
  val arch = config.getLong("dc.arch")
  val os = config.getInt("dc.os")
  val vmm = config.getInt("dc.vmm")
  val costPerSecond = config.getInt("dc.costPerSecond")
  val costPerRam = config.getInt("dc.costPerRam")
  val costPerStorage = config.getInt("dc.costPerStorage")
  val costPerBw = config.getInt("dc.costPerBw")

  logger.debug(s"Datacenter configured successfully for $configEntry")
}

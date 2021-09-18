package config

import util.{CreateLogger, ObtainConfigReference}

case class DatacenterConfig(configFile: String, configEntry: String) {

  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[DatacenterConfig])

  // ToDo: Make robust with default values and exception handling.
  val hosts = config.getInt("dc.hosts")
  val vms = config.getInt("dc.vms")
  val cloudlets = config.getInt("dc.cloudlets")
  val arch = config.getString("dc.arch")
  val os = config.getString("dc.os")
  val vmm = config.getString("dc.vmm")
  val costPerSec = config.getDouble("dc.costPerSec")
  val costPerMem = config.getDouble("dc.costPerMem")
  val costPerStorage = config.getDouble("dc.costPerStorage")
  val costPerBw = config.getDouble("dc.costPerBw")
  val vmAllocationPolicy = config.getString("dc.vmAllocationPolicy")

  logger.debug(s"Datacenter configured successfully for $configEntry")
}

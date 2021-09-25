package config

import org.cloudbus.cloudsim.vms.VmSimple
import util.{CreateLogger, ObtainConfigReference}

/**
 * Data structure to parse and hold VM configuration.
 **/
case class VmConfig(configFile: String, configEntry: String) {

  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[VmConfig])

  // ToDo: Make robust with default values and exception handling
  val mips = config.getDouble("vm.mipsCapacity")
  val ram = config.getLong("vm.RAMInMBs")
  val bw = config.getLong("vm.BandwidthInMBps")
  val storage = config.getLong("vm.StorageInMBs")
  val cloudletScheduler = config.getString("vm.cloudletScheduler")
  val numOfPes = config.getInt("vm.PEs")
  
  logger.debug(s"VM configured successfully for $configEntry")
  
}

package config

import org.cloudbus.cloudsim.vms.VmSimple
import util.{CreateLogger, ObtainConfigReference}

case class VMConfig(configFile: String, configEntry: String) {

  private val config = ObtainConfigReference(configFile, configEntry) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  private val logger = CreateLogger(classOf[VMConfig])

  // ToDo: Make robust with default values and exception handling
  val mips = config.getLong("vm.mipsCapacity").toDouble
  val ram = config.getLong("vm.RAMInMBs")
  val bw = config.getLong("vm.BandwidthInMBps")
  val storage = config.getLong("vm.StorageInMBs")   
  
  logger.debug(s"VM configured successfully for $configEntry")
  
}

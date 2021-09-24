package factory

import config.{DatacenterConfig, HostConfig, VmConfig}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import util.CreateLogger

import scala.collection.mutable.ArrayBuffer

/**
 * Factory class to create VMs
 */
class VmFactory

object VmFactory:

  val logger = CreateLogger(classOf[VmFactory])


  /**
   * Creates VMs with given configuration.
   **/
  def createVms(vmConfig: VmConfig, hostConfig: HostConfig,
                datacenterConfig: DatacenterConfig): ArrayBuffer[Vm] = {
    ArrayBuffer.tabulate(datacenterConfig.vms)(_ =>
      new VmSimple(vmConfig.mips, vmConfig.numOfPes,
        CloudletSchedulerFactory.createCloudletScheduler(vmConfig.cloudletScheduler))
        .setRam(vmConfig.ram)
        .setBw(vmConfig.bw)
        .setSize(vmConfig.storage))
  }




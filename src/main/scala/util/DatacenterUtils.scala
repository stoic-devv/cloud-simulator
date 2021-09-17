package util

import config.{CloudletConfig, DatacenterConfig, HostConfig, VMConfig}
import util.{HostUtils}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyBestFit}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.Pe
import org.cloudbus.cloudsim.resources.PeSimple
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared}

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*

case class DatacenterUtils() {

  val logger = CreateLogger(classOf[DatacenterUtils])

  /**
   * Creates a datacenter with given configurations. Attaches hosts to it and
   * adds hosts and vm scheduling and allocation policy.
   * Defaults to */
  def createDatacenter(datacenterConfig: DatacenterConfig, hostConfig: HostConfig,
                       cloudSim: CloudSim, vmSchedulerOption: Option[VmScheduler],
                       vmAllocationPolicyOption: Option[VmAllocationPolicy],
                       activateHostsOption: Option[ArrayBuffer[Boolean]]): Datacenter = {

    // Creates data center with given config. Defaults to VmAllocationPolicyBestFit
    // and  VmSchedulerSpaceShared policies if not provided
    val dc = new DatacenterSimple(cloudSim,
      new HostUtils().createHosts(hostConfig, datacenterConfig, activateHostsOption, vmSchedulerOption).asJava,
      vmAllocationPolicyOption.getOrElse(new VmAllocationPolicyBestFit))

    // set characteristics of the datacenter
    // set characteristics of the datacenter
    dc.getCharacteristics
      .setArchitecture(datacenterConfig.arch)
      .setOs(datacenterConfig.os)
      .setVmm(datacenterConfig.vmm)
      .setCostPerSecond(datacenterConfig.costPerSecond)
      .setCostPerMem(datacenterConfig.costPerRam)
      .setCostPerStorage(datacenterConfig.costPerStorage)
      .setCostPerBw(datacenterConfig.costPerBw)
    return dc
  }
}

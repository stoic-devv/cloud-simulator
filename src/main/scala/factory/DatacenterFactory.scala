package factory

import config.{CloudletConfig, DatacenterConfig, HostConfig, VmConfig}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyBestFit}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared}
import util.{CreateLogger}

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*

class DatacenterFactory
object DatacenterFactory:

  val logger = CreateLogger(classOf[DatacenterFactory])

  /**
   * Creates a datacenter with given configurations. Attaches hosts to it and
   * adds hosts and vm scheduling and allocation policy.
   * Defaults to simple allocation policy and space-shared scheduler.
   **/
  def createDatacenter(datacenterConfig: DatacenterConfig, 
                       hostConfig: HostConfig, cloudSim: CloudSim,
                       activateHostsOption: Option[ArrayBuffer[Boolean]]): Datacenter = {

    // Creates data center with given config. Defaults to VmAllocationPolicyBestFit
    // and  VmSchedulerSpaceShared policies if not provided
    val dc = new DatacenterSimple(cloudSim,
      HostFactory.createHosts(hostConfig, datacenterConfig, activateHostsOption).asJava,
      VmAllocationFactory.createVmAllocationPolicy(datacenterConfig))

    // set characteristics of the datacenter
    // set characteristics of the datacenter
    dc.getCharacteristics
      .setArchitecture(datacenterConfig.arch)
      .setOs(datacenterConfig.os)
      .setVmm(datacenterConfig.vmm)
      .setCostPerSecond(datacenterConfig.costPerSec)
      .setCostPerMem(datacenterConfig.costPerMem)
      .setCostPerStorage(datacenterConfig.costPerStorage)
      .setCostPerBw(datacenterConfig.costPerBw)
    return dc
  }

  /**
   * Creates a datacenter with given configurations. Attaches hosts to it and
   * adds hosts and vm scheduling and allocation policy.
   * Defaults to simple allocation policy and space-shared scheduler.
   **/
  def createDatacenter(datacenterConfig: DatacenterConfig,
                       hostConfig: HostConfig, cloudSim: CloudSim): Datacenter = {
    return createDatacenter(datacenterConfig, hostConfig, cloudSim,
      Some(ArrayBuffer.fill(datacenterConfig.hosts)(true)))
  }

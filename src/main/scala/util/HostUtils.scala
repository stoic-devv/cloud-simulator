package util

import config.{DatacenterConfig, HostConfig}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared}

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*

case class HostUtils() {

  val logger = CreateLogger(classOf[HostUtils])
  /**
   * Creates a list of  CPU cores for a given host config.
   * @param hostConfig: configuration of a host
   * @return: list of CPU cores
   **/
  def createPes(hostConfig: HostConfig) : ArrayBuffer[Pe]  = {
    ArrayBuffer.tabulate(hostConfig.numPes)(i => {
      logger.debug("Resource {} added to list", i)
      new PeSimple(hostConfig.mips)
    })
  }

  /**
   * Creates a host with given VM allocation policy. By default it allocates resources
   * if available, else fails.
   * @param hostConfig: host configuration
   * @param vmScheduler: vm provisioning policy
   * @param activateHost: boolean flag of whether the host is active
   * @return : host with given
   * */
  def createHost(hostConfig: HostConfig, vmSchedulerOption: Option[VmScheduler],
                 activateHost: Boolean): Host = {

    logger.debug("Creating PEs for host")
    val pes = createPes(hostConfig)

    vmSchedulerOption.getOrElse(new VmSchedulerSpaceShared())

    // ToDo: check if each host needs its own scheduler
    // Defaults to space shared VM scheduler if scheduler not provided
    return new HostSimple(hostConfig.ram, hostConfig.bw,
      hostConfig.storage, pes.asJava, activateHost)
      .setVmScheduler(vmSchedulerOption.getOrElse(new VmSchedulerSpaceShared()))
  }

  /**
   * Creates Hosts and adds them to the Data center.
   * @param hostConfig: host configuration
   * @param datacenterConfig: data center configuration
   * @param activateHosts: boolean list of activated hosts
   * @param vmScheduler: vm provisioning policy for the host
   **/
  @throws(classOf[IllegalAccessException])
  @throws(classOf[InstantiationError])
  // ToDo: consider logging and making checked exception
  def createHosts(hostConfig: HostConfig,
                  datacenterConfig: DatacenterConfig, activateHostsOption: Option[ArrayBuffer[Boolean]],
                  vmSchedulerOption: Option[VmScheduler]) : ArrayBuffer[Host] = {
    logger.debug("Adding hosts to datacenter")
    val activateHosts = activateHostsOption
      .getOrElse(ArrayBuffer.fill(datacenterConfig.hosts)(true))
    return ArrayBuffer.tabulate(datacenterConfig.hosts)(i => {
      createHost(hostConfig, vmSchedulerOption, activateHosts(i))
    })
  }

  /**
   * Creates hosts with default vm scheduling strategy (space shared)
   * and all of them activated
   **/
  def createHosts(hostConfig: HostConfig,
                  datacenterConfig: DatacenterConfig): ArrayBuffer[Host] =
    createHosts(hostConfig, datacenterConfig,
      Some(ArrayBuffer.fill(datacenterConfig.hosts)(true)), Some(new VmSchedulerSpaceShared))

}

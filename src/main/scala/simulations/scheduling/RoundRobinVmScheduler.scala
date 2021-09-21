package simulations.scheduling

import config.{CloudletConfig, DatacenterConfig, HostConfig, VmConfig}
import constants.{CloudletConfigConstants, RoundRobinVmAllocationConstants}
import factory.{CloudletFactory, DatacenterFactory, VmFactory}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import util.{CloudletsTableDecorator, CreateLogger, ObtainConfigReference, SimulationUtils}

import scala.collection.JavaConverters.*

object RoundRobinVmScheduler {

  val config = ObtainConfigReference(RoundRobinVmAllocationConstants.CONFIG_FILE,
    RoundRobinVmAllocationConstants.CONFIG_ENTRY) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  // get config objects for this simulation
  val logger = CreateLogger(classOf[RoundRobinVmScheduler])

  val datacenterConfig = DatacenterConfig(RoundRobinVmAllocationConstants.CONFIG_FILE,
    RoundRobinVmAllocationConstants.CONFIG_ENTRY)
  val hostConfig = HostConfig(RoundRobinVmAllocationConstants.CONFIG_FILE,
    RoundRobinVmAllocationConstants.CONFIG_ENTRY)
  val vmConfig = VmConfig(RoundRobinVmAllocationConstants.CONFIG_FILE,
    RoundRobinVmAllocationConstants.CONFIG_ENTRY)
  val cloudletConfig = CloudletConfig(RoundRobinVmAllocationConstants.CONFIG_FILE,
    RoundRobinVmAllocationConstants.CONFIG_ENTRY)

  def Start() =
    val cloudsim = new CloudSim()

    //create datacenter with host
    val dc0 = DatacenterFactory.createDatacenter(datacenterConfig, hostConfig, cloudsim)
    logger.info(s"Created datacenter: $dc0")

    //create broker
    val broker0 = new DatacenterBrokerSimple(cloudsim)

    //create VMs
    val vms = VmFactory.createVms(vmConfig, hostConfig, datacenterConfig)
    logger.info(s"Created virtual machines: $vms")

    //create cloudlets
    val cloudletList = CloudletFactory.createCloudlets(cloudletConfig, datacenterConfig)

    logger.info(s"Created a list of cloudlets: $cloudletList")

    broker0.submitVmList(vms.asJava);
    broker0.submitCloudletList(cloudletList.asJava);

    logger.info("Starting cloud simulation...")
    cloudsim.start();

    // simulation analysis
    val ct = new CloudletsTableDecorator(asScala(broker0.getCloudletFinishedList()))
    ct.addCostColumn()
    ct.build()
    println("Total cost for the simulation: " + SimulationUtils.getSimulationCost(asScala(broker0.getCloudletFinishedList())))


}
class RoundRobinVmScheduler
package simulations

import config.{CloudletConfig, DatacenterConfig, HostConfig, VmConfig}
import constants.{BasicSimulationConstants, CloudletConfigConstants}
import factory.{DatacenterFactory, HostFactory, VmFactory}
import util.{CreateLogger, ObtainConfigReference}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.VmSimple
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

import collection.JavaConverters.*

class BasicCloudSimPlusExample

object BasicCloudSimPlusExample:
  
  val config = ObtainConfigReference(BasicSimulationConstants.CONFIG_FILE,
    BasicSimulationConstants.CONFIG_ENTRY) match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[BasicCloudSimPlusExample])

  // get config objects for this simulation
  logger.debug("Reading configurations for Basic Simulation")

  val datacenterConfig = DatacenterConfig(BasicSimulationConstants.CONFIG_FILE, 
    BasicSimulationConstants.CONFIG_ENTRY)
  val hostConfig = HostConfig(BasicSimulationConstants.CONFIG_FILE, 
    BasicSimulationConstants.CONFIG_ENTRY)
  val vmConfig = VmConfig(BasicSimulationConstants.CONFIG_FILE, 
    BasicSimulationConstants.CONFIG_ENTRY)
  val cloudletConfig = CloudletConfig(BasicSimulationConstants.CONFIG_FILE, 
    BasicSimulationConstants.CONFIG_ENTRY)

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
    val utilizationModel = new UtilizationModelDynamic(config.getDouble(CloudletConfigConstants.UTILRATIO))
    val cloudletList = new CloudletSimple(cloudletConfig.size, cloudletConfig.numPes, utilizationModel) ::
      new CloudletSimple(cloudletConfig.size, cloudletConfig.numPes, utilizationModel) :: Nil

    logger.info(s"Created a list of cloudlets: $cloudletList")

    broker0.submitVmList(vms.asJava);
    broker0.submitCloudletList(cloudletList.asJava);

    logger.info("Starting cloud simulation...")
    cloudsim.start();

    new CloudletsTableBuilder(broker0.getCloudletFinishedList()).build();
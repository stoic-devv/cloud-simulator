package simulations

import config.{ConfigData, ConfigStruct}
import factory.{CloudletFactory, DatacenterFactory, VmFactory}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import util.{CloudletsTableDecorator, CreateLogger, ObtainConfigReference, SimulationUtils}

import collection.JavaConverters.*
import scala.collection.mutable.ArrayBuffer


class GenericSimulation(configStruct: ConfigStruct) {

  def start(): Unit = {

    val logger = CreateLogger(classOf[GenericSimulation])

    SimulationUtils.checkConfig(configStruct.configFile, configStruct.configEntry)
    logger.info("Running configuration - " + configStruct.configFile)

    val config = new ConfigData(configStruct.configFile, configStruct.configEntry)

    val cloudsim = new CloudSim()

    //create datacenter with host
    val dc0 = DatacenterFactory.createDatacenter(config.datacenterConfig, config.hostConfig, cloudsim,
      Some(ArrayBuffer.fill(config.datacenterConfig.hosts)(false)))
    logger.info(s"Created datacenter: $dc0")

    //create broker
    val broker0 = new DatacenterBrokerSimple(cloudsim)

    //create VMs
    val vms = VmFactory.createVms(config.vmConfig, config.hostConfig, config.datacenterConfig)
    logger.info(s"Created virtual machines: $vms")

    //create cloudlets
    val cloudletList = CloudletFactory.createCloudlets(config.cloudletConfig, config.datacenterConfig)

    logger.info(s"Created a list of cloudlets: $cloudletList")

    broker0.submitVmList(vms.asJava);
    broker0.submitCloudletList(cloudletList.asJava);

    logger.info("Starting cloud simulation...")
    cloudsim.start();

    // simulation analysis
    val ct = new CloudletsTableDecorator(asScala(broker0.getCloudletFinishedList()))
    ct.addCostColumn()
    ct.build()
    // adds average and total costs and performance
    SimulationUtils.logAnalysisForCloudlets(asScala(broker0.getCloudletFinishedList()))

  }
}






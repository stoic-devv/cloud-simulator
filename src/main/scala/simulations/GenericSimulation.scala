package simulations

import config.ConfigData
import factory.{CloudletFactory, DatacenterFactory, VmFactory}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import util.{CloudletsTableDecorator, CreateLogger, ObtainConfigReference, SimulationUtils}

import collection.JavaConverters.*
import scala.collection.mutable.ArrayBuffer


class GenericSimulation(var configFile: String,
  var configEntry: String) {

  def start(): Unit = {

    val logger = CreateLogger(classOf[GenericSimulation])

    ObtainConfigReference(configFile, configEntry) match {
      case Some(value) => value
      case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
    }
    logger.info("Running configuration - " + configFile)

    val config = new ConfigData(configFile, configEntry)

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
    SimulationUtils.logAnalysisForCloudlets(asScala(broker0.getCloudletFinishedList()))

  }
}






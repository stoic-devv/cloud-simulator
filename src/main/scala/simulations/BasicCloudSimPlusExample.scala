package simulations

import config.{CloudletConfig, VMConfig}
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
  val config = ObtainConfigReference("basic-simulation", "cloudSimulator") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[BasicCloudSimPlusExample])

  val hostConfig = VMConfig("basic-simulation", "cloudSimulator")
  val vmConfig = VMConfig("basic-simulation", "cloudSimulator")


  def Start() =
    val cloudsim = new CloudSim();
    val broker0 = new DatacenterBrokerSimple(cloudsim);


    val hostPes = List(new PeSimple(hostConfig.mips))
    logger.info(s"Created one processing element: $hostPes")

    val hostList = List(
      new HostSimple(hostConfig.ram,
        hostConfig.storage,
        hostConfig.bw,
        hostPes.asJava))

    logger.info(s"Created one host: $hostList")

    val dc0 = new DatacenterSimple(cloudsim, hostList.asJava);
    val vmList = List(
      new VmSimple(vmConfig.mips, hostPes.length)
      .setRam(vmConfig.ram)
      .setBw(vmConfig.bw)
      .setSize(vmConfig.storage))
    logger.info(s"Created one virtual machine: $vmList")

    val utilizationModel = new UtilizationModelDynamic(config.getDouble("utilizationRatio"));
    val cloudletConfig = CloudletConfig("basic-simulation", "cloudSimulator")
    val cloudletList = new CloudletSimple(cloudletConfig.size, cloudletConfig.numPes, utilizationModel) ::
      new CloudletSimple(cloudletConfig.size, cloudletConfig.numPes, utilizationModel) :: Nil

    logger.info(s"Created a list of cloudlets: $cloudletList")

    broker0.submitVmList(vmList.asJava);
    broker0.submitCloudletList(cloudletList.asJava);

    logger.info("Starting cloud simulation...")
    cloudsim.start();

    new CloudletsTableBuilder(broker0.getCloudletFinishedList()).build();
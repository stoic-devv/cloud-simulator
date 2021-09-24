package simulations

import config.{ConfigData, ConfigStruct, DatacenterConfig, HostConfig}
import factory.{CloudletFactory, DatacenterFactory, VmFactory}
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import util.{CloudletsTableDecorator, CreateLogger, SimulationUtils}

import collection.JavaConverters.*
import scala.collection.mutable.ArrayBuffer

class NetworkTopologySimulation(configStruct: ConfigStruct, topologyFile: String) {

  val logger = CreateLogger(classOf[NetworkTopologySimulation])

  def start(): Unit = {

    SimulationUtils.checkConfig(configStruct.configFile, configStruct.configEntry)

    logger.info("Running configuration - " + configStruct.configFile)
    val config = new ConfigData(configStruct.configFile, configStruct.configEntry)

    val cloudsim = new CloudSim()

    //create broker
    val broker = new DatacenterBrokerSimple(cloudsim)

    //configures a network of datacenter
    createAndConnectDatacenters(cloudsim, config.datacenterConfig,
      config.hostConfig, broker, topologyFile)

    //create VMs
    val vms = VmFactory.createVms(config.vmConfig, config.hostConfig, config.datacenterConfig)
    logger.info(s"Created virtual machines: $vms")

    //create cloudlets
    val cloudletList = CloudletFactory.createCloudlets(config.cloudletConfig, config.datacenterConfig)
    cloudletList.zipWithIndex.foreach((cloudlet, idx) => {
      broker.bindCloudletToVm(cloudlet, vms(idx))
    })
    logger.info(s"Created a list of cloudlets: $cloudletList")

    broker.submitVmList(vms.asJava);
    broker.submitCloudletList(cloudletList.asJava);

    logger.info("Starting cloud simulation...")
    cloudsim.start();

    // simulation analysis
    val ct = new CloudletsTableDecorator(asScala(broker.getCloudletFinishedList()))
    ct.addCostColumn()
    ct.build()
    // adds average and total costs and performance
    SimulationUtils.logAnalysisForCloudlets(asScala(broker.getCloudletFinishedList()))

  }

  def createAndConnectDatacenters(cloudSim: CloudSim, datacenterConfig: DatacenterConfig,
                                  hostConfig: HostConfig, broker: DatacenterBroker, topologyFile: String): Unit = {

    // read the network topology
    val networkTopology = BriteNetworkTopology.getInstance(topologyFile)
    cloudSim.setNetworkTopology(networkTopology)

    // get number of nodes in the topology graph
    val nodes = networkTopology.getTopologicalGraph.getNumberOfNodes

    // ToDo: Add check on number of nodes.

    // map datacenters to BRITE nodes
    Range(0,nodes-1).foreach(briteNode => {
      var dc = createDatacenter(cloudSim, datacenterConfig, hostConfig)
      networkTopology.mapNode(dc, briteNode)
      logger.info(s"Datacenter $dc mapped to BRITE node $briteNode")
    })

    // broker added to the last BRITE node
    var brokerNode = nodes - 1
    networkTopology.mapNode(broker, brokerNode)
    logger.info(s"Broker mapped to BRITE node $brokerNode")
  }

  def createDatacenter(cloudSim: CloudSim, datacenterConfig: DatacenterConfig,
                       hostConfig: HostConfig): Datacenter = {
    //create datacenter with host
    val dc = DatacenterFactory.createDatacenter(datacenterConfig, hostConfig, cloudSim,
      Some(ArrayBuffer.fill(datacenterConfig.hosts)(true)))
    logger.info(s"Created datacenter: $dc")
    dc
  }

}

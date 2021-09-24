package simulations

import config.{CloudletConfig, ConfigData, ConfigStruct, DatacenterConfig, HostConfig, VmConfig}
import factory.{CloudletFactory, DatacenterFactory, VmFactory}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import util.SimulationUtils.checkConfig
import util.{CloudletsTableDecorator, CreateLogger, ObtainConfigReference, SimulationUtils}

import collection.JavaConverters.*
import scala.collection.mutable.ArrayBuffer

/**
 * Simulates computing models as saas, paas and iaas
 **/
class XasaServiceSimulation(saasConfStruct: ConfigStruct, paasConfStruct: ConfigStruct,
                            iaasConfStruct: ConfigStruct){

  val logger = CreateLogger(classOf[XasaServiceSimulation])

  def Start() = {

    // checking config file correctness
    checkConfig(saasConfStruct.configFile, saasConfStruct.configEntry)
    checkConfig(saasConfStruct.configFile, saasConfStruct.configEntry)
    checkConfig(saasConfStruct.configFile, saasConfStruct.configEntry)


    logger.info("Getting configurations ")
    // read configurations
    val saasConfig = new ConfigData(saasConfStruct.configFile, saasConfStruct.configEntry)
    val paasConfig = getPassConfig(saasConfStruct, paasConfStruct)
    val iaasConfig = new ConfigData(iaasConfStruct.configFile, iaasConfStruct.configEntry)

    // run simulations
    startSimulation(saasConfig.datacenterConfig, saasConfig.hostConfig,
      saasConfig.vmConfig, saasConfig.cloudletConfig)
    startSimulation(paasConfig.datacenterConfig, paasConfig.hostConfig,
      paasConfig.vmConfig, paasConfig.cloudletConfig)
    startSimulation(iaasConfig.datacenterConfig, iaasConfig.hostConfig,
      iaasConfig.vmConfig, iaasConfig.cloudletConfig)

  }


  /**
   * Generic simulator for saas, paas and iaas models of cloud computations
   * */
  def startSimulation(datacenterConfig: DatacenterConfig, hostConfig: HostConfig,
                      vmConfig: VmConfig, cloudletConfig: CloudletConfig): Unit = {
    val cloudsim = new CloudSim()

    //create datacenter with host
    val dc0 = DatacenterFactory.createDatacenter(datacenterConfig, hostConfig, cloudsim,
      Some(ArrayBuffer.fill(datacenterConfig.hosts)(false)))
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
    // adds average and total costs and performance
    SimulationUtils.logAnalysisForCloudlets(asScala(broker0.getCloudletFinishedList()))
  }

  /**
  * Returns a configuration for paas setup.
  * It uses default datacenters, host and vm config while customizing cloudlets and its scheduling policies.
   **/
  def getPassConfig(saasConfStruct: ConfigStruct, paasConfStruct: ConfigStruct): ConfigDataX = {
    val saasConfig = new ConfigData(saasConfStruct.configFile, saasConfStruct.configEntry)
    // reading vm and cloudlet config from pass.conf file
    // note: vm config is not on paas layer. here it is done to respect the config file syntax. We only use the cloudletScheduler attribute from it
    val vmConfig = new VmConfig(paasConfStruct.configFile, paasConfStruct.configEntry)
    val cloudletConfig = new CloudletConfig(paasConfStruct.configFile, paasConfStruct.configEntry)
    new ConfigDataX(saasConfig.datacenterConfig, saasConfig.hostConfig, vmConfig, cloudletConfig)
  }

  /**
   * Config data holder for cleaner code
   **/
  case class ConfigDataX(dcConfig: DatacenterConfig, hConfig: HostConfig, vConfig: VmConfig, cConfig: CloudletConfig) {
    val datacenterConfig = dcConfig
    val hostConfig = hConfig
    val vmConfig = vConfig
    val cloudletConfig = cConfig
  }
}


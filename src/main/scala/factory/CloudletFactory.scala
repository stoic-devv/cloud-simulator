package factory

import config.{CloudletConfig, DatacenterConfig}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletAbstract, CloudletSimple}
import util.CreateLogger

import scala.collection.mutable.ArrayBuffer

class CloudletFactory

object CloudletFactory:

  val logger = CreateLogger(classOf[CloudletFactory])

  /**
   * Creates a cloudlet with given configuration
   * @param cloudletConfig Cloudlet configuration object
   **/
  def createCloudlet(cloudletConfig: CloudletConfig): Cloudlet = {
    return new CloudletSimple(cloudletConfig.length, cloudletConfig.numPes,
      CloudletUtilizationFactory.createCloudletUtilModel(cloudletConfig))
      .setSizes(cloudletConfig.size)
  }

  /**
   * Creates a list of cloudlets with given configuration
   * @param cloudletConfig cloudlet configuration object
   * @param datacenterConfig datacenter configuration to get the number of cloulets to be created
   **/
  def createCloudlets(cloudletConfig: CloudletConfig,
                      datacenterConfig: DatacenterConfig): ArrayBuffer[Cloudlet] = {
    return ArrayBuffer.tabulate(datacenterConfig.cloudlets)(i => {
      createCloudlet(cloudletConfig)
    })
  }


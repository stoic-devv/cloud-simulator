package factory

import config.CloudletConfig
import constants.{CloudletConfigConstants, CloudletUtilizationConstants}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelAbstract, UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}

/**
 * Factory class to provide cloudlet utilization policy based on configuration.
 **/
class CloudletUtilizationFactory

object CloudletUtilizationFactory:

  /**
   * Creates cloudlet scheduler policy based on the constants provided.
   * */
  def createCloudletUtilModel(cloudletUtilModel: String, utilRatio: Double):  UtilizationModelAbstract = {
    cloudletUtilModel match {
      case CloudletUtilizationConstants.FULL => new UtilizationModelFull()
      case CloudletUtilizationConstants.DYNAMIC => new UtilizationModelDynamic(utilRatio)
      case CloudletUtilizationConstants.STOCHASTIC => new UtilizationModelStochastic()
      case default => new UtilizationModelFull()
    }
  }

  /**
   * Creates cloudlet scheduler policy based on the configuration.
   * */
  def createCloudletUtilModel(cloudletConfig: CloudletConfig): UtilizationModelAbstract = {
    createCloudletUtilModel(cloudletConfig.utilModel, cloudletConfig.utilRatio)
  }
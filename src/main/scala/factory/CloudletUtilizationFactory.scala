package factory

import config.CloudletConfig
import constants.{CloudletConfigConstants, CloudletUtilizationConstants}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}

class CloudletUtilizationFactory

object CloudletUtilizationFactory:

  def createCloudletUtilModel(cloudletConfig: CloudletConfig) = {
    cloudletConfig.utilModel match {
      case CloudletUtilizationConstants.FULL => new UtilizationModelFull()
      case CloudletUtilizationConstants.DYNAMIC => new UtilizationModelDynamic(cloudletConfig.utilRatio)
      case CloudletUtilizationConstants.STOCHASTIC => new UtilizationModelStochastic()
      case default => new UtilizationModelFull()
    }
  }
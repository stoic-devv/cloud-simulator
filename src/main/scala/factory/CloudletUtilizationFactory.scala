package factory

import config.CloudletConfig
import constants.{CloudletConfigConstants, CloudletUtilizationConstants}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull}

class CloudletUtilizationFactory

object CloudletUtilizationFactory:

  def createCloudletUtilModel(cloudletConfig: CloudletConfig) = {
    cloudletConfig.utilModel match {
      case CloudletUtilizationConstants.FULL => new UtilizationModelFull()
      case CloudletUtilizationConstants.DYNAMIC => new UtilizationModelDynamic(cloudletConfig.utilRatio)
      case default => new UtilizationModelFull()
    }
  }
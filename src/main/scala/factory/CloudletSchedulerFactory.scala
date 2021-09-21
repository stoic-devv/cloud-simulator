package factory

import config.VmConfig
import constants.CloudletSchedulerConstants
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}

class CloudletSchedulerFactory

object CloudletSchedulerFactory:

  def createCloudletScheduler(vmConfig: VmConfig) = {
    vmConfig.cloudletScheduler match {
      case CloudletSchedulerConstants.TIMESHARED => new CloudletSchedulerTimeShared()
      case CloudletSchedulerConstants.SPACESHARED => new CloudletSchedulerSpaceShared()
      case default => new CloudletSchedulerTimeShared()
    }
  }

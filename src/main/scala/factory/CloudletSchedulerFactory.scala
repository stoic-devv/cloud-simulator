package factory

import config.VmConfig
import constants.CloudletSchedulerConstants
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerAbstract, CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}

class CloudletSchedulerFactory

object CloudletSchedulerFactory:

  /**
   * Creates cloudlet scheduler policy based on constant provided.
   * */
  def createCloudletScheduler(cloudletScheduler: String): CloudletSchedulerAbstract = {
    cloudletScheduler match {
      case CloudletSchedulerConstants.TIMESHARED => new CloudletSchedulerTimeShared()
      case CloudletSchedulerConstants.SPACESHARED => new CloudletSchedulerSpaceShared()
      case default => new CloudletSchedulerTimeShared()
    }
  }

  /**
   * Creates cloudlet scheduler policy based on vm configuration.
   * */
  def createCloudletScheduler(vmConfig: VmConfig): CloudletSchedulerAbstract = {
    createCloudletScheduler(vmConfig.cloudletScheduler)
  }

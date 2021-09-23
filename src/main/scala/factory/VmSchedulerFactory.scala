package factory

import config.HostConfig
import constants.VmSchedulerConstants
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerTimeShared
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}

class VmSchedulerFactory

object VmSchedulerFactory:

  /**
   * Creates VM scheduler based on constant string.
   **/
  def createVmScheduler(schedulingPolicy: String): VmScheduler = {
    schedulingPolicy match {
      case VmSchedulerConstants.SPACESHARED => new VmSchedulerSpaceShared
      case VmSchedulerConstants.TIMESHARED => new VmSchedulerTimeShared
      case default => new VmSchedulerSpaceShared()
    }
  }

  /**
   *Creates VM scheduler based on host config
   **/
  def createVmScheduler(hostConfig: HostConfig): VmScheduler = {
    createVmScheduler(hostConfig.vmScheduler)
  }
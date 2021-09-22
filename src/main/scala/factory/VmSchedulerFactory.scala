package factory

import config.HostConfig
import constants.VmSchedulerConstants
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerTimeShared
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}

class VmSchedulerFactory

object VmSchedulerFactory:

  def createVmScheduler(hostConfig: HostConfig): VmScheduler = {
    hostConfig.vmScheduler match {
      case VmSchedulerConstants.SPACESHARED => new VmSchedulerSpaceShared
      case VmSchedulerConstants.TIMESHARED => new VmSchedulerTimeShared
      case default => new VmSchedulerSpaceShared()
    }
  }
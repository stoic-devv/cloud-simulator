package factory

import config.{DatacenterConfig, HostConfig}
import constants.VmAllocConstants
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyBestFit, VmAllocationPolicyRoundRobin}

class VmAllocationFactory

object VmAllocationFactory:

  /**
   * Creates VM allocation policy based upon datacenter configuration.
   **/
  def createVMAllocationPolicy(datacenterConfig: DatacenterConfig) : VmAllocationPolicy = {
    datacenterConfig.vmAllocationPolicy match {

      case VmAllocConstants.BESTFIT => new VmAllocationPolicyBestFit

      case VmAllocConstants.ROUNDROBIN => new VmAllocationPolicyRoundRobin

      case default => new VmAllocationPolicyBestFit
    }
  }


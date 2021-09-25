package factory

import config.{DatacenterConfig, HostConfig}
import constants.VmAllocConstants
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyBestFit, VmAllocationPolicyRandom, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.distributions.{NormalDistr, UniformDistr}

import scala.util.Random

/**
 * Factory class to provide VM allocation policy based on a given configuration.
 **/
class VmAllocationFactory

object VmAllocationFactory:

  val GAUSSIAN_MEAN = 0.5
  val GAUSSIAN_DEVIATION = 0.5
  /**
   * Creates VM allocation policy based on constant provided.
   * */
  def createVmAllocationPolicy(policy: String): VmAllocationPolicy = {
    policy match {

      case VmAllocConstants.BESTFIT => new VmAllocationPolicyBestFit

      case VmAllocConstants.ROUNDROBIN => new VmAllocationPolicyRoundRobin

      case VmAllocConstants.WORSTFIT => new VmAllocationPolicySimple

      case VmAllocConstants.UNIFORMRAND => new VmAllocationPolicyRandom(new UniformDistr())

      case VmAllocConstants.GAUSSIAN => new VmAllocationPolicyRandom(
        new NormalDistr(GAUSSIAN_MEAN, GAUSSIAN_DEVIATION))

      case default => new VmAllocationPolicySimple
    }
  }

  /**
   * Creates VM allocation policy based upon datacenter configuration.
   **/
  def createVmAllocationPolicy(datacenterConfig: DatacenterConfig) : VmAllocationPolicy = {
    createVmAllocationPolicy(datacenterConfig.vmAllocationPolicy)
  }


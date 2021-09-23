package factory

import config.DatacenterConfig
import constants.VmAllocConstants
import factory.VmAllocationFactory.GAUSSIAN_MEAN
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyRandom, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.distributions.{NormalDistr, UniformDistr}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import testconstants.TestConstants

class VmAllocationFactorySpecFactory extends FactoryBaseSpec with BeforeAndAfter {

  // for factory tests theSameInstanceAs
  // ref1 should be theSameInstanceAs ref2
  // result1 shouldBe a [Tiger]
  //result1 should not be an [Orangutan]

  val datacenterConfig = DatacenterConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  it should "create Vm policy from datacenter configuration" in {
    VmAllocationFactory.createVmAllocationPolicy(datacenterConfig) shouldBe a [VmAllocationPolicyBestFit]
  }

  it should "create Vm best-fit allocation policy" in {
    VmAllocationFactory.createVmAllocationPolicy(VmAllocConstants.BESTFIT) shouldBe a [VmAllocationPolicyBestFit]
  }

  it should "create Vm simple allocation policy" in {
    VmAllocationFactory.createVmAllocationPolicy(VmAllocConstants.WORSTFIT) shouldBe a [VmAllocationPolicySimple]
  }

  it should "create Vm uniformly random allocation policy" in {
    VmAllocationFactory.createVmAllocationPolicy(VmAllocConstants.UNIFORMRAND) shouldBe a
      [VmAllocationPolicyRandom]
  }

  it should "create Vm gaussian random allocation policy" in {
    VmAllocationFactory.createVmAllocationPolicy(VmAllocConstants.GAUSSIAN) shouldBe a
      [VmAllocationPolicyRandom]
  }

  it should "create Vm round-robin allocation policy" in {
    VmAllocationFactory.createVmAllocationPolicy(VmAllocConstants.ROUNDROBIN) shouldBe a [VmAllocationPolicyRoundRobin]
  }


}

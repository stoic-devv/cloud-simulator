package factory

import config.{DatacenterConfig, HostConfig, VmConfig}
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerSpaceShared
import org.cloudbus.cloudsim.vms.VmSimple
import testconstants.TestConstants

class VmFactorySpec extends FactoryBaseSpec {

  val datacenterConfig = DatacenterConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)
  val hostConfig = HostConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)
  val vmConfig = VmConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  behavior of "VM factory"

  it should "create VMs based on the given configuration" in {
    val vms = VmFactory.createVms(vmConfig, hostConfig, datacenterConfig)
    assert(vms.length == datacenterConfig.vms)
    vms.foreach(vm => {
      vm shouldBe a [VmSimple]
      vm.getCloudletScheduler shouldBe a [CloudletSchedulerSpaceShared]
      assert(vm.getRam().getCapacity() == vmConfig.ram)
      assert(vm.getStorage().getCapacity() == vmConfig.storage)
    })
  }

}

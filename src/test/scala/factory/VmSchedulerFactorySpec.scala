package factory

import config.HostConfig
import constants.VmSchedulerConstants
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import testconstants.TestConstants

class VmSchedulerFactorySpec extends FactoryBaseSpec {

  val hostConfig = HostConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  behavior of "VM Scheduler factory"

  it should "create a VM scheduler from given configuration" in {
    VmSchedulerFactory.createVmScheduler(hostConfig) shouldBe a [VmSchedulerTimeShared]
  }

  it should "create a time-shared VM scheduler" in {
    VmSchedulerFactory.createVmScheduler(VmSchedulerConstants.TIMESHARED) shouldBe a [VmSchedulerTimeShared]
  }

  it should "create a space-shared VM scheduler" in {
    VmSchedulerFactory.createVmScheduler(VmSchedulerConstants.SPACESHARED) shouldBe a [VmSchedulerSpaceShared]
  }

  it should "create a space-shared VM scheduler by default" in {
    VmSchedulerFactory.createVmScheduler("") shouldBe a [VmSchedulerSpaceShared]
  }


}

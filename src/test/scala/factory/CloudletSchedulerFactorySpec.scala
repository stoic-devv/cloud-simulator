package factory

import config.VmConfig
import constants.CloudletSchedulerConstants
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}
import org.scalatest.BeforeAndAfter
import testconstants.TestConstants
;

class CloudletSchedulerFactorySpec extends FactoryBaseSpec {

  val vmConfig = VmConfig(TestConstants.TEST_CONFIG_FILE,
    TestConstants.TEST_CONFIG_ENTRY)

  behavior of "Cloudlet factory scheduler"

  it should "create cloudlet scheduler from configuration" in {
    CloudletSchedulerFactory.createCloudletScheduler(vmConfig) shouldBe a [CloudletSchedulerSpaceShared]
  }

  it should "create a space-shared scheduler" in {
    CloudletSchedulerFactory.createCloudletScheduler(CloudletSchedulerConstants.SPACESHARED) shouldBe a [CloudletSchedulerSpaceShared]
  }

  it should "create a time-shared scheduler" in {
    CloudletSchedulerFactory.createCloudletScheduler(CloudletSchedulerConstants.TIMESHARED) shouldBe a [CloudletSchedulerTimeShared]
  }

  it should "create a time-shared scheduler by default" in {
    CloudletSchedulerFactory.createCloudletScheduler("") shouldBe a [CloudletSchedulerTimeShared]
  }
}
